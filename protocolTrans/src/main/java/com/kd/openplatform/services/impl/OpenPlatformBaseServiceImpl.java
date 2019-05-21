package com.kd.openplatform.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.bean.ColumnsTypeBean;
import com.kd.openplatform.cache.HttpAddrMapping;
import com.kd.openplatform.hsf.consumer.bean.ConsumerBean;
import com.kd.openplatform.quartzwork.InitServlet;
import com.kd.openplatform.services.OpenPlatformBaseService;
import com.kd.openplatform.util.ParamsAdapter;
import com.taobao.hsf.lightapi.ConsumerService;
import com.taobao.hsf.lightapi.ServiceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
 

@Service("openPlatformBaseService")
public class OpenPlatformBaseServiceImpl implements OpenPlatformBaseService{
	private static final Log log =  LogFactory.getLog(OpenPlatformBaseServiceImpl.class);

	@Autowired
	private HttpAddrMapping httpAddrMapping;
	
	@Autowired
	private ServiceFactory consumerService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
    private HttpServletRequest request;
	@Autowired
	private RedisTemplate redisTemplate;

	public Object hsfInterfaceService(String path, JSONObject  args) throws InstantiationException, IllegalAccessException, ParseException, IllegalArgumentException, InvocationTargetException{

		ConsumerBean bean = httpAddrMapping.getHttpAddr_HsfAddr().get(path);
		if(bean == null) {
			return "未找到hsf服务";
		}
		JSONObject jsonobj = args;
		log.info("----------hsfInterfaceService接受的输入的参数是："+jsonobj.toString());
		String hsfAddr = bean.getHttpRestfulAddr();
		log.info("hsfAddr:----"+hsfAddr);
		Object json = "";
		if (hsfAddr != null) {
			Object obj = httpAddrMapping.getHsfAddr_ObjectClass().get(hsfAddr);
			if (obj != null) {
				ConcurrentHashMap<String, Method> methodMap = httpAddrMapping.getObjectClass_Method().get(hsfAddr);
				log.info("methodMap:----"+methodMap.size());
				Method method = methodMap.get(bean.getHsfMethod());
				log.info("method:----"+method.getName());
				Class<?>[] paramTypes = method.getParameterTypes();
				Map<String, List<ColumnsTypeBean>> methodKey_Type = httpAddrMapping.getMethodKey_Type();
                log.error("参数个数......"+paramTypes.length+"  "+methodKey_Type.get(hsfAddr)+"-----"+hsfAddr);
				if (paramTypes.length > methodKey_Type.get(hsfAddr).size()) {
					log.error("参数个数不一致......");
					return "参数不正确";
				}
				if (paramTypes.length > 0) {
					Object[] os = new Object[paramTypes.length];
					List<ColumnsTypeBean> cols = methodKey_Type.get(hsfAddr);
					List<String> paramNameLst = new ArrayList<String>();
					for (int i = 0; i < paramTypes.length; i++) {
						//方法的参数和传递的参数一致，所以直接获取对应的数据
						Class<?> class2 = paramTypes[i];
						log.info("数据类型........" + class2.getName());
						//Object methodObj = class2.newInstance();
						Object methodObj = class2;
						String key = cols.get(i).getKey();
						paramNameLst.add(key);
                        request.setAttribute("paramName",paramNameLst);
                        log.info("------从HSF反射出来的参数名key--:"+key);//从HSF反射出来的参数名
						String type = cols.get(i).getType();
						log.info("------从HSF反射出来的参数类型type--"+type);

                        if ("Object".equalsIgnoreCase(type)) {
//							methodObj = class2.newInstance();
//							Method[] ms = methodObj.getClass().getMethods();
//
                            JSONObject keyJsonObj = JSONObject.parseObject(jsonobj.getString(key));
//                            JSONObject keyJsonObj = jsonobj.getJSONObject(key);
                            log.info("========反射调用方法前的请求参数（未封装accesstoken）keyJsonObj========="+keyJsonObj);
                            String accesstoken = (String) redisTemplate.boundValueOps("ACCESSTOKEN").get();
                            log.info("=========从redis取出的accesstoken为========"+accesstoken);
                            keyJsonObj.put("accessToken",accesstoken.replace("\"",""));
                            jsonobj.put(key,keyJsonObj);
                            log.info("-----类"+methodObj.getClass().getName()+"最终的请求参数:"+jsonobj.get(key));
                            if(jsonobj.get(key)==null||jsonobj.get(key).toString().equalsIgnoreCase("null")) {
								os[i] = null;
							}else {
								boolean b = false;
								JSONObject item = null;
								if (!(jsonobj.get(key) instanceof JSONObject)) {
									Object jo = JSONObject.parse(jsonobj.get(key).toString());
									if (jo instanceof JSONObject){
										item = (JSONObject)jo;
									}else {
										b = true;
									}
								}else{
									item = (JSONObject) jsonobj.get(key);
								}
								if(b){
									log.error("参数类型不正确");
									return "参数类型不正确";
								}
								methodObj = ParamsAdapter.toJavaObject(item, class2);
								log.info("java  对象--------------------------"+JSONObject.toJSONString(method));

//								List<ColumnsTypeBean> suncols =  cols.get(i).getBeans();
//								log.info("---------key好像没有用过"+key+"-------"+item.toString());
//								for (Method method2 : ms) {
//									String m = "";
//									String k= "";
//									String t= "";
//									for (ColumnsTypeBean columnsTypeBean : suncols) {
//										k = columnsTypeBean.getKey();
//										String setMethod = ("set"+k.substring(0,1).toUpperCase()+k.substring(1));
//										t = columnsTypeBean.getType();
//										log.info(method2.getName()+ "---------setMethod----------------"+setMethod);
//										if(method2.getName().equalsIgnoreCase(setMethod)) {
//											m = setMethod;
//											break;
//										}
//
//									}
//									if("".equals(m)) {
//										continue;
//									}
//									log.info("---------------kk----------------"+m+" ---k== "+k);
//									log.info("-----------------用到的参数名是："+k+" type="+method2.getParameterTypes()[0].getName()+" ++ value item.get(itemkey)="+item.get(k));
//									String v = item.get(k)+"";
//									Object oo = null;
//									if(!"null".equalsIgnoreCase(v)) {
//										//判断参数类型和方法的参数类型是否一致
//										if(!t.equals(method2.getParameterTypes()[0].getName())) {
//											log.info(t+"  methodName------参数类型不一致---"+method2.getParameterTypes()[0].getName());
//										}
//										oo = ParamsAdapter.typeCastParams(t/*method2.getParameterTypes()[0].getName()*/,v);
//									} 
//									method2.invoke(methodObj, oo);
//									log.info("methodName------"+method2.getName()+"-------");
//								}
								os[i] = methodObj;
							}

						} else {
							String v = jsonobj.get(key)+"";
							os[i] = null;
							if(!"null".equalsIgnoreCase(v)) {
                                os[i] = ParamsAdapter.typeCastParams(cols.get(i).getType()/*class2.getName()*/, v);
							}
						}
					}

					log.info("-----------用反射调用方法开始----------"+obj+""+Arrays.asList(os)+"通过反射调用方法传入的参数为:"+Arrays.toString(os));
					/*Object res*/ json = method.invoke(obj, os);
//					json = JSONObject.toJSONString(res);
					log.info("-----------用反射调用方法结束----------");
				} else {
					 /*Object res*/ json= method.invoke(obj);
//					json = JSONObject.toJSONString(res);
				}
			}

		}
//		log.info(json);
		return json;
	}
	
	
	/* (non-Javadoc)
	 * 加载http、请求的对应关系
	 */
	public void loadAddrMapping(String path) {
		
		log.info("==================初始化数据，用户传过来的path=================="+path);
		//存储接口的信息
		List<ConsumerBean> cons = new ArrayList<>();
		/*String sql = "select id, api_name,req_addr_http,req_addr_hsf,hsf_group,api_class_name,api_method_name,version from api_info A where A.api_status=? and req_addr_http=? ";
		List<Map<String, Object>> apis= jdbcTemplate.queryForList(sql, new Object[] {1,path});*/

		String sql = "select id, api_name,req_addr_http,req_addr_hsf,hsf_group,api_class_name,api_method_name,version from api_info A where req_addr_hsf=? ";
		List<Map<String, Object>> apis= jdbcTemplate.queryForList(sql, new Object[] {path});
		log.info("从api_info中查询出"+apis.size()+"条记录条件是req_addr_http="+path);
		for (Map<String, Object> map : apis) {
			
			ConsumerBean bean = new ConsumerBean();
			bean.setConsumerId(String.valueOf(map.get("id")));
			String api_class_name = String.valueOf(map.get("api_class_name"));
			String hsf_group = String.valueOf(map.get("hsf_group"));
//			String req_addr_http = String.valueOf(map.get("req_addr_http"));
			String req_addr_hsf = String.valueOf(map.get("req_addr_hsf"));
			String api_method_name = String.valueOf(map.get("api_method_name"));
			String version = String.valueOf(map.get("version"));
			if("null".equals(api_class_name)||"null".equals(hsf_group)
					||"null".equals(req_addr_hsf)||"null".equals(api_method_name)||"null".equals(version)) {
				continue;
			}
			bean.setConsumerClass(api_class_name);
			bean.setConsumerGroup(hsf_group);
			bean.setHttpRestfulAddr(req_addr_hsf);
			bean.setHsfMethod(api_method_name);//
			bean.setConsumerVersion(version);
			bean.setApiName(String.valueOf(map.get("api_name")));
			
			sql = "SELECT id,param_name,data_type,parent_id from api_param where api_id=? and param_type=?";
			List<Map<String, Object>> params = jdbcTemplate.queryForList(sql, new Object[] {map.get("id"),0});
			
			List<ColumnsTypeBean> colList = new ArrayList<>();
			ColumnsTypeBean columnsTypeBean = null;
			HashMap<String,String> tempCols = new HashMap<String,String>();
			log.info("能力开放平台设置的参数个数是："+params.size());
			for (Map<String, Object> map2 : params) {
				log.info("能力开放平台设置的参数名是："+map2.get("param_name"));
				columnsTypeBean = new ColumnsTypeBean();
				String t = ParamsAdapter.getType(String.valueOf(map2.get("data_type")));
				String id = String.valueOf(map2.get("id"));
				
				if(tempCols.containsKey(id)) {
					continue;
				}else {
					tempCols.put(id, id);
				}
				if ("object".equalsIgnoreCase(t)) {
					List<ColumnsTypeBean> bs = new ArrayList<ColumnsTypeBean>();
					columnsTypeBean.setBeans(bs);
					for (Map<String, Object> map3 : params) {
						ColumnsTypeBean columnsTypeBean1 = new ColumnsTypeBean();
						String parent_id = String.valueOf(map3.get("parent_id"));
						if (parent_id != null &&parent_id.length()>0 && id.equals(parent_id)) {
							tempCols.put(String.valueOf(map3.get("id")), String.valueOf(map3.get("id")));
							columnsTypeBean1.setId(String.valueOf(map3.get("id")));
							columnsTypeBean1.setKey(String.valueOf(map3.get("param_name")));
							columnsTypeBean1.setType(ParamsAdapter.getType(String.valueOf(map3.get("data_type"))));
							bs.add(columnsTypeBean1);
						}
					}
				} 
				columnsTypeBean.setId(id);
				columnsTypeBean.setKey(String.valueOf(map2.get("param_name")));
				columnsTypeBean.setType(ParamsAdapter.getType(String.valueOf(map2.get("data_type"))));
				colList.add(columnsTypeBean);

				log.info("param_name ==="+String.valueOf(map2.get("param_name")));
			}
				
			bean.setColList(colList);
			cons.add(bean);
		}
		//此处
		for (ConsumerBean consumerBean : cons) {
			log.info("consumerBean.getHttpRestfulAddr()==="+consumerBean.getHttpRestfulAddr());
			Object	o = null;
			if(!httpAddrMapping.getHsfAddr_ObjectClass().containsKey(consumerBean.getConsumerClass())) {
				ConsumerService cs = consumerService.consumer(consumerBean.getConsumerId()).
						service(consumerBean.getConsumerClass()).group(consumerBean.getConsumerGroup())
						.version(consumerBean.getConsumerVersion()).timeout(50000);

				log.info("ConsumerId--"+consumerBean.getConsumerId()
					+"--consumerClass--"+consumerBean.getConsumerClass()
					+"--group--"+consumerBean.getConsumerGroup()
					+"--version--"+consumerBean.getConsumerVersion());
				o = cs.subscribe();
				Optional<?> objN=Optional.ofNullable(o);
				if(/*o == null*/!objN.isPresent()) {
					log.info("cs.subscribe()为空");
					throw new RuntimeException("服务订阅生成有误");
					//continue;
				}else {
					log.info("开始设置httpAddrMapping");
					httpAddrMapping.getHsfAddr_ObjectClass().put(consumerBean.getHttpRestfulAddr(), o);
				}
			}else {
				log.info("!consumerBean.getConsumerClass() ");
				o = httpAddrMapping.getHsfAddr_ObjectClass().get(consumerBean.getHttpRestfulAddr());
			}
			log.info("执行 存储---------1-------"+consumerBean.getHttpRestfulAddr());
			httpAddrMapping.getHttpAddr_HsfAddr().put(consumerBean.getHttpRestfulAddr(), consumerBean);

//			String calssMethodName = o.getClass().getName()+"_"+consumerBean.getHsfMethod();
			httpAddrMapping.getMethodKey_Type().put(consumerBean.getHttpRestfulAddr(), consumerBean.getColList());//
			log.info("执行 存储------2----------"+consumerBean.getColList());
			Method[] ms = o.getClass().getMethods();
			ConcurrentHashMap<String, Method> methodMap = new ConcurrentHashMap<>();
			int paramsConut = consumerBean.getColList().size();
			for (Method method : ms) {
				String name = method.getName();
				if(!name.equals(consumerBean.getHsfMethod())){
					log.info("检查方法名："+name+"----"+consumerBean.getHsfMethod());
					continue;
				}
				if(paramsConut != method.getParameterCount()){
					log.info("检查参数个数："+paramsConut+"----"+method.getParameterCount());
					continue;
				}
				log.info("--------------方法名：" + name+"参数个数："+paramsConut);
				methodMap.put(name, method);
			}
			log.info("执行 存储-----3-----------"+methodMap);
			httpAddrMapping.getObjectClass_Method().put(consumerBean.getHttpRestfulAddr(), methodMap);
		}
		log.info("==================初始化数据结束===================");
		
	}

	public HttpAddrMapping getHttpAddrMapping() {
		return httpAddrMapping;
	}

	@Override
	public Map<String, String> getKeysByAppId(String appId) {
		String sql = "select pass_key,iv_str from api_app_keys A where A.app_id=?";
		log.info("appId:===="+appId);
		List<Map<String, Object>> keys= jdbcTemplate.queryForList(sql, new Object[] {appId});
//        List<Map<String, Object>> keys= jdbcTemplate.queryForList(sql);
//        log.info(sql);
		log.info("load keys num::::::" + keys);
		if(keys != null && keys.size() > 0){
			Map<String,String> key = new ConcurrentHashMap<>();
			key.put("pass_key",keys.get(0).get("pass_key") + "");
			key.put("iv_str",keys.get(0).get("iv_str") + "");
			return key;
		}
		return null;
	}
}
