package com.kd.op.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kd.op.api.controller.ResourceController;
import com.kd.op.api.entity.*;
import com.kd.op.api.service.ApiAppRelaServiceI;
import com.kd.op.api.service.ApiAppServiceI;
import com.sgcc.hlht.entity.PTasklistInterface;
import com.sgcc.hlht.service.OpenPlatformWebSupportService;
import com.sgcc.hlht.vo.InterfaceAuthVo;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.system.service.SystemService;

@Service("apiAppRelaService")
@Transactional
public class ApiAppRelaServiceImpl extends CommonServiceImpl implements ApiAppRelaServiceI {
	private static final Logger logger = Logger.getLogger(ResourceController.class);
	@Autowired
	private SystemService systemService;

    @Autowired
    private OpenPlatformWebSupportService openPlatformWebSupportService;

	public void delete(ApiAppRelaEntity entity) throws Exception {
		super.delete(entity);
	}

	public void save(ApiAppRelaEntity entity) throws Exception {
		systemService.save(entity);
	}

	public void saveOrUpdate(ApiAppRelaEntity entity) throws Exception {
		super.saveOrUpdate(entity);
	}

	private Map<String, Object> populationMap(ApiAppRelaEntity t) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", t.getId());
		map.put("api_id", t.getApiId());
		map.put("api_name", t.getApiName());
		map.put("app_id", t.getAppId());
		map.put("app_name", t.getAppName());
		return map;
	}

	/**
	 * 替换sql中的变量
	 *
	 * @param sql
	 * @param t
	 * @return
	 */
	public String replaceVal(String sql, ApiAppRelaEntity t) {
		sql = sql.replace("#{id}", String.valueOf(t.getId()));
		sql = sql.replace("#{api_id}", String.valueOf(t.getApiId()));
		sql = sql.replace("#{api_name}", String.valueOf(t.getApiName()));
		sql = sql.replace("#{app_id}", String.valueOf(t.getAppId()));
		sql = sql.replace("#{app_name}", String.valueOf(t.getAppName()));
		sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
		return sql;
	}

	/**
	 * 执行JAVA增强
	 */
	private void executeJavaExtend(String cgJavaType, String cgJavaValue, Map<String, Object> data) throws Exception {
		if (StringUtil.isNotEmpty(cgJavaValue)) {
			Object obj = null;
			try {
				if ("class".equals(cgJavaType)) {
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				} else if ("spring".equals(cgJavaType)) {
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if (obj instanceof CgformEnhanceJavaInter) {
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("api_app_rela", data);
				}
			} catch (Exception e) {
				throw new Exception("执行JAVA增强出现异常！");
			}
		}
	}

	@Override
	public void updateApiAppRela(String appId, String apiAppRelas) {

		String oldHql = "delete FROM api_app_rela WHERE app_id = ?";
		//删除应用对应关系旧列表
		Integer res = systemService.executeSql(oldHql, appId);

		//新的接口id
		List<String> newApiIdList = Arrays.asList(apiAppRelas.split(","));

		ApiAppEntity app = this.getEntity(ApiAppEntity.class, appId);
		List<ApiAppRelaEntity> apiAppRelaList = new ArrayList<>();
		if (newApiIdList != null && newApiIdList.size() > 0) {
			newApiIdList.forEach(p -> {
				ApiAppRelaEntity rela = new ApiAppRelaEntity();
				rela.setApiId(p);
				rela.setAppName(app.getAppName());
				rela.setAppId(appId);
				apiAppRelaList.add(rela);
			});
		}

		if (apiAppRelaList.size() > 0) {
			systemService.batchSave(apiAppRelaList);
		}
	}


	@Override
	public void updateChargeRela(ApiOrderEntity apiOrder, List<ApiOrderDetailEntity> apiOrderDetail) {
//		TSUser currentUser = ResourceUtil.getSessionUserName();
        ApiOrderEntity apiOrderEntity = systemService.get(ApiOrderEntity.class, apiOrder.getId());
        ArrayList<InterfaceAuthVo> interfaceAuthVos = new ArrayList<>();
        String merchantNo=null;
        for (int i = 0; i < apiOrderDetail.size(); i++) {
            ApiAppRelaEntity rela = new ApiAppRelaEntity();
            //将信息存入api_app_rela
            apiAppRela(apiOrderDetail.get(i), rela, apiOrderEntity);
            //更新api_charge_account_entity
            saveApiChargeAccount(apiOrderDetail.get(i), rela);
            //将场景订单信息存入api_app_scene_rela
            saveApiAppSceneRela(apiOrder, apiOrderDetail.get(i), rela, apiOrderEntity);
            //修改api_isv_param可用状态
            updateApiIsvParam(apiOrderDetail.get(i),apiOrderEntity);
            //调用互联互通授权接口  待调试5.13
            try {
                String apiId = apiOrderDetail.get(i).getApiId();
                ApiInfoEntity apiInfoEntity = systemService.get(ApiInfoEntity.class, apiId);
                String groupName = apiInfoEntity.getGroupName();
                if (groupName.equals("互联互通")) {
                    InterfaceAuthVo interfaceAuthVo = new InterfaceAuthVo();
                    String appId = apiOrder.getAppId();
                    ApiAppEntity apiAppEntity = systemService.get(ApiAppEntity.class, appId);
                    merchantNo=apiAppEntity.getOperator_id();
                    List<PTasklistInterface> pTasklistInterfaces = openPlatformWebSupportService.queryAuthedInterface(merchantNo);
                    for (PTasklistInterface pTasklistInterface : pTasklistInterfaces) {
                        if (!pTasklistInterface.getInterfaceName().equals(apiInfoEntity.getApiName())) {
                            interfaceAuthVo.setInterfaceName(apiInfoEntity.getApiName());
                            interfaceAuthVo.setMerchantNo(merchantNo);
                            interfaceAuthVo.setIfResponse("false");
                            interfaceAuthVos.add(interfaceAuthVo);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("互联互通授权失败");
            }
        }
        try {
            if (interfaceAuthVos != null && interfaceAuthVos.size() > 0) {
                openPlatformWebSupportService.interfaceListAuth(merchantNo,interfaceAuthVos);
            }
        } catch (Exception e) {
            throw new RuntimeException("互联互通授权失败");
        }
    }

	/**
	 * 修改api_isv_param
	 */
	public void updateApiIsvParam(ApiOrderDetailEntity apiOrderDetail, ApiOrderEntity apiOrderEntity) {
		String sql = "update api_isv_param set is_visible=1  WHERE api_id = ? AND app_id=?";
		String appId = apiOrderEntity.getAppId();
		String apiId = apiOrderDetail.getApiId();
		systemService.executeSql(sql, apiId,appId);

	}

	/**
	 * 将信息存入api_app_rela
	 *
	 * @param apiOrderDetail
	 * @param rela
	 * @param apiOrderEntity
	 */
	public void apiAppRela(ApiOrderDetailEntity apiOrderDetail, ApiAppRelaEntity rela, ApiOrderEntity apiOrderEntity) {
		String appId = apiOrderEntity.getAppId();
		String userId = apiOrderEntity.getCreateBy();
		String apiId = apiOrderDetail.getApiId();
		String sceneId = apiOrderDetail.getSceneId();
		if (apiOrderDetail.getApiId() != null) {
			List<ApiAppRelaEntity> relaList1 = getApiAppRelaByAppIdUserIdApiId(appId, userId, apiId);
			//订购的是服务
			//判断是否订购过
			if (relaList1 != null && relaList1.size() == 0) {
				//若没有订购过,保存数据
				rela.setAppId(appId);
				rela.setAppName(apiOrderEntity.getAppName());
				rela.setUserId(userId);
				rela.setResourceId(apiOrderDetail.getResourceId());
				rela.setApiId(apiId);
				rela.setApiName(apiOrderDetail.getApiName());
				systemService.saveOrUpdate(rela);
			} else if (relaList1 != null) {
				//若订购过,查询apiAppRelaId赋给rela,用来更新api_charge_account_entity
				String id = relaList1.get(0).getId();
				rela.setId(id);
			}
		} else {
			//订购的是场景
			//判断是否订购过
			List<ApiAppRelaEntity> relaList2 = getApiAppRelaByAppIdUserIdSceneId(appId, userId, sceneId);
			if (relaList2 != null && relaList2.size() == 0) {
				//若没有订购过,保存数据
				rela.setAppId(appId);
				rela.setAppName(apiOrderEntity.getAppName());
				rela.setUserId(userId);
				rela.setResourceId(apiOrderDetail.getResourceId());
				rela.setSceneId(sceneId);
				systemService.saveOrUpdate(rela);
			} else if (relaList2 != null) {
				//若订购过,查询apiAppRelaId赋给rela,用来更新api_charge_account_entity
				String id = relaList2.get(0).getId();
				rela.setId(id);
			}
		}

	}

	/**
	 * 更新api_charge_account_entity
	 *
	 * @param apiOrderDetailEntity
	 * @param rela
	 */
	public void saveApiChargeAccount(ApiOrderDetailEntity apiOrderDetailEntity, ApiAppRelaEntity rela) {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = java.util.Calendar.getInstance();
		ApiChargeAccountEntity temp = new ApiChargeAccountEntity();
		BigInteger count = this.idCount(apiOrderDetailEntity.getChargeId());
		String chargeModelId = apiOrderDetailEntity.getChargeId();//提交过来的计费模型ID
		if (count.compareTo(new BigInteger("0")) == 1) {
//			temp = findUniqueByProperty(ApiChargeAccountEntity.class, "apiAppRelaId", rela.getId());
			String chargeAccountModelId ="";
			String chargeAccountRestState = "";
			boolean res = false;
			List<ApiChargeAccountEntity> apiChargeAccounts = systemService.findByProperty(ApiChargeAccountEntity.class, "apiAppRelaId", rela.getId());
			if (apiChargeAccounts!= null && apiChargeAccounts.size() > 0) {
				for (ApiChargeAccountEntity apiChargeAccountEntitys : apiChargeAccounts) {
					chargeAccountModelId = apiChargeAccountEntitys.getChargeModelId();
					chargeAccountRestState= apiChargeAccountEntitys.getRestState();
					if (chargeModelId.equals(chargeAccountModelId)) {
						res = true;
						break;
					}
				}
				//更新
				if (res) {
					String hql0 = "from ApiChargeAccountEntity where 1 = 1 AND api_app_rela_id = ? and charge_model_id =?";
					List<ApiChargeAccountEntity> apiChargeAcountEntityList = systemService.findHql(hql0, rela.getId(),chargeModelId);
					temp = apiChargeAcountEntityList.get(0);
					ApiChargeMode chargeMode = findUniqueByProperty(ApiChargeMode.class, "id", chargeAccountModelId);
					String restState = chargeAccountRestState;
					Integer startNum = chargeMode.getStartNum();
					Integer endNum = chargeMode.getEndNum();
					if (chargeMode.getType() == 1) {//type=1为按包年包月计算
						try {
							Date restStateDte = sdf.parse(restState);
							cal.setTime(restStateDte);
							Integer num = 0;
							if (chargeMode.getUnit() == 1) {//按周计算
								num = 7 * chargeMode.getNum();
								cal.add(java.util.Calendar.DATE, num);
							}
							if (chargeMode.getUnit() == 2) {//按月计算
								num = chargeMode.getNum();
								cal.add(Calendar.MONTH, num);
							}
							if (chargeMode.getUnit() == 3) {//按年计算
								num = chargeMode.getNum();
								cal.add(Calendar.YEAR, num);
							}
							temp.setRestState(sdf.format(cal.getTime()));
//                        }
						} catch (ParseException e) {
							logger.error("error:", e);
						}
					} else if (chargeMode.getType() == 2) {//type=2为按次计算
						Integer queryNum = endNum - startNum;
						Integer a = Integer.parseInt(restState);
						Integer intRestState = queryNum + a;
						temp.setRestState(intRestState.toString());
					} else if (chargeMode.getType() == 3) {//type=3为按流量计算
						Integer FlowNum = endNum - startNum;
						Integer a = Integer.parseInt(restState);
						Integer intRestState = FlowNum + a;
						temp.setRestState(intRestState.toString());
					}
				} else {//选择一种计费模型后又选择了另一种计费模型,新增
					temp.setApiAppRelaId(rela.getId());
					temp.setChargeModelId(apiOrderDetailEntity.getChargeId());
					ApiChargeMode chargeMode = findUniqueByProperty(ApiChargeMode.class, "id", temp.getChargeModelId());
					if (chargeMode.getType() == 1) {//type=1为按包年包月计算
						Integer num = 0;
						if (chargeMode.getUnit() == 1) {//按周计算
							num = 7 * chargeMode.getNum();
							cal.add(java.util.Calendar.DATE, num);
						} else if (chargeMode.getUnit() == 2) {//按月计算
							num = chargeMode.getNum();
							cal.add(Calendar.MONTH, num);
						} else if (chargeMode.getUnit() == 3) {//按年计算
							num = chargeMode.getNum();
							cal.add(Calendar.YEAR, num);
						}
						temp.setRestState(sdf.format(cal.getTime()));
						temp.setTypename("com.kd.openplatform.charge.service.impl.ChargeByPeriodServiceImpl");
					} else if (chargeMode.getType() == 2) {//type=2为按次计算
						Integer startNum = chargeMode.getStartNum();
						Integer endNum = chargeMode.getEndNum();
						Integer restState = endNum - startNum;
						temp.setRestState(restState.toString());
						temp.setTypename("com.kd.openplatform.charge.service.impl.ChargeByQueryServiceImpl");
					} else if (chargeMode.getType() == 3) {//type=3为按流量计算
						Integer startNum = chargeMode.getStartNum();
						Integer endNum = chargeMode.getEndNum();
						Integer restState = endNum - startNum;
						temp.setRestState(restState.toString());
						temp.setTypename("com.kd.openplatform.charge.service.impl.ChargeByFlowServiceImpl");

					}
				}
			} else {
				//新增
				temp.setApiAppRelaId(rela.getId());
				temp.setChargeModelId(apiOrderDetailEntity.getChargeId());
				ApiChargeMode chargeMode = findUniqueByProperty(ApiChargeMode.class, "id", temp.getChargeModelId());
				if (chargeMode.getType() == 1) {//type=1为按包年包月计算
					Integer num = 0;
					if (chargeMode.getUnit() == 1) {//按周计算
						num = 7 * chargeMode.getNum();
						cal.add(java.util.Calendar.DATE, num);
					} else if (chargeMode.getUnit() == 2) {//按月计算
						num = chargeMode.getNum();
						cal.add(Calendar.MONTH, num);
					} else if (chargeMode.getUnit() == 3) {//按年计算
						num = chargeMode.getNum();
						cal.add(Calendar.YEAR, num);
					}
					temp.setRestState(sdf.format(cal.getTime()));
					temp.setTypename("com.kd.openplatform.charge.service.impl.ChargeByPeriodServiceImpl");
				} else if (chargeMode.getType() == 2) {//type=2为按次计算
					Integer startNum = chargeMode.getStartNum();
					Integer endNum = chargeMode.getEndNum();
					Integer restState = endNum - startNum;
					temp.setRestState(restState.toString());
					temp.setTypename("com.kd.openplatform.charge.service.impl.ChargeByQueryServiceImpl");
				} else if (chargeMode.getType() == 3) {//type=3为按流量计算
					Integer startNum = chargeMode.getStartNum();
					Integer endNum = chargeMode.getEndNum();
					Integer restState = endNum - startNum;
					temp.setRestState(restState.toString());
					temp.setTypename("com.kd.openplatform.charge.service.impl.ChargeByFlowServiceImpl");

				}
			}
		}
		systemService.saveOrUpdate(temp);
	}

	/**
	 * 将场景订单信息存入ApiAppSceneRela
	 *
	 * @param apiOrder
	 * @param apiOrderDetailEntity
	 * @param rela
	 * @param apiOrderEntity
	 */
	public void saveApiAppSceneRela(ApiOrderEntity apiOrder, ApiOrderDetailEntity apiOrderDetailEntity, ApiAppRelaEntity rela, ApiOrderEntity apiOrderEntity) {
		//判断是否为场景订单
		if (apiOrderEntity.getOrderType().equals("scene")) {
			ApiAppSceneRelaEntity apiAppSceneRelaEntity = new ApiAppSceneRelaEntity();
			//通过apiAppRelaId查询ApiChargeAccountEntity
			List<ApiChargeAccountEntity> list = this.getApiChargeAccountEntity(rela.getId());
			if (list != null && list.size() != 0) {
				String chargeModeId = apiOrderDetailEntity.getChargeId();
				String chargeAccountId = list.get(0).getId();
				List<ApiAppSceneRelaEntity> apiAppSceneRelaList = getApiAppSceneRela(chargeModeId, chargeAccountId);
				//判断是否存储过
				if (apiAppSceneRelaList != null && apiAppSceneRelaList.size() == 0) {
					//未存储时执行保存
					apiAppSceneRelaEntity.setAppId(apiOrderEntity.getAppId());
					apiAppSceneRelaEntity.setAppName(apiOrderEntity.getAppName());
					apiAppSceneRelaEntity.setSceneId(apiOrderDetailEntity.getSceneId());
					apiAppSceneRelaEntity.setSceneName(apiOrderDetailEntity.getSceneName());
					apiAppSceneRelaEntity.setChargeModeId(chargeModeId);
					apiAppSceneRelaEntity.setUserId(apiOrderEntity.getCreateBy());
					apiAppSceneRelaEntity.setChargeAccountId(chargeAccountId);
					systemService.saveOrUpdate(apiAppSceneRelaEntity);
				}
			}
		}
	}

	@Override
	//查询是否重名
	public BigInteger idCount(String apiAppRelaId) {
		String sqlQuery = "select count(id) from api_charge_mode where id='%s';";
		sqlQuery = String.format(sqlQuery, apiAppRelaId);
		List<BigInteger> num = findListbySql(sqlQuery);
		BigInteger count = num.get(0);
		return count;
	}

	/**
	 * 根据计费模型id查询chargeAccount对象
	 *
	 * @param apiAppRelaId
	 * @return
	 */
	public List<ApiChargeAccountEntity> getApiChargeAccountEntity(String apiAppRelaId) {
		String sqlQuery = "from ApiChargeAccountEntity where apiAppRelaId = ?";
		List<ApiChargeAccountEntity> ApiChargeAccounts = findHql(sqlQuery, apiAppRelaId);
		return ApiChargeAccounts;
	}

	/**
	 * 根据appId,userId,apiId查询ApiAppRelaEntity对象
	 *
	 * @param appId
	 * @param userId
	 * @param apiId
	 * @return
	 */
	public List<ApiAppRelaEntity> getApiAppRelaByAppIdUserIdApiId(String appId, String userId, String apiId) {
		String sqlQuery = "from ApiAppRelaEntity where appId = ? and userId= ? and apiId = ?";
		List<ApiAppRelaEntity> apiAppRelaList = findHql(sqlQuery, appId, userId, apiId);
		return apiAppRelaList;
	}

	/**
	 * 根据appId,userId,sceneId查询ApiAppRelaEntity对象
	 *
	 * @param appId
	 * @param userId
	 * @param sceneId
	 * @return
	 */
	public List<ApiAppRelaEntity> getApiAppRelaByAppIdUserIdSceneId(String appId, String userId, String sceneId) {
		String sqlQuery = "from ApiAppRelaEntity where appId = ? and userId= ? and sceneId = ?";
		List<ApiAppRelaEntity> apiAppRelaList = findHql(sqlQuery, appId, userId, sceneId);
		return apiAppRelaList;
	}

	/**
	 * 根据chargeModeId,chargeAccountId查询ApiAppSceneRelaEntity对象
	 *
	 * @param chargeModeId
	 * @param chargeAccountId
	 * @return
	 */
	public List<ApiAppSceneRelaEntity> getApiAppSceneRela(String chargeModeId, String chargeAccountId) {
		String sqlQuery = "from ApiAppSceneRelaEntity where chargeModeId = ? and chargeAccountId= ?";
		List<ApiAppSceneRelaEntity> apiAppSceneRelaList = findHql(sqlQuery, chargeModeId, chargeAccountId);
		return apiAppSceneRelaList;
	}
}