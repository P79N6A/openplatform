package com.kd.openplatform.ws.client;

import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.info.InfoBean;
import com.kd.openplatform.util.ErrorConstants;
import com.kd.openplatform.util.XmlUtil;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class WSClient {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Log log = LogFactory.getLog(WSClient.class);

    public String post(String apiId, JSONObject wsObj, String requestParam) {
        log.info("webservice post方法执行");
        Object[] requestParamArr=null;
        ArrayList<Object> list = new ArrayList<>();
        InfoBean bean = new InfoBean();
        String sql = "SELECT id,param_name,data_type,parent_id from api_param where api_id=? and param_type=?";
        List<Map<String, Object>> params = jdbcTemplate.queryForList(sql, new Object[]{apiId, 0});
        String paramType = null;
        String paramStr = null;
        for (Map<String, Object> param : params) {
            //正好E车购的weservice的输入参数名叫xmlStr,实际参数也是xml
            //26个省营销的叫jsonStr,实际参数也是json
            //所以索性就把参数名也作为参数类型了
            //这里用的webservice控件也不需要真正的参数名
            paramType = param.get("param_name").toString();
            if (param != null && paramType != null) {
                try {
                    JSONObject jsonObject = JSONObject.parseObject(requestParam);
                    if(paramType.equalsIgnoreCase("xmlStr")) {
                        paramStr = jsonObject.getString(paramType);
                    }else{
                        paramStr = jsonObject.getString(paramType);
                        XmlUtil xu = new XmlUtil();
                        paramStr = xu.json2xml(paramStr);
                    }
                    list.add(paramStr);
                    requestParamArr = list.toArray();
                } catch (Exception e) {
                    list.add(requestParam);
                    requestParamArr = list.toArray();
//                    bean.setInfo(ErrorConstants.ERROR_ILLEGAL_ARGUMENT_DISP);
//                    bean.setStatus(ErrorConstants.ERROR_ILLEGAL_ARGUMENT_CODE);
//                    log.info("Exception:" + e);
//                    return JSONObject.toJSONString(bean);
                }

            } else {
                bean.setInfo(ErrorConstants.ERRORT_PARAM_INPUT_DISP);
                bean.setStatus(ErrorConstants.ERROR_PARAM_INPUT_CODE);
                return JSONObject.toJSONString(bean);
            }
        }
        try {
            String endpoint = wsObj.getString("endpoint");//"http://localhost:12345/weather" ;
            log.info("endpoint----" + endpoint);
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);//设置服务终端地址
            //QName opAddEntry = new QName("http://kd.com.test/", "weather");//设置NameSpace和方法
            QName opAddEntry = new QName(wsObj.getString("namespaceURI"), wsObj.getString("methodName"));//设置NameSpace和方法
            log.info("namespaceURI----" + wsObj.getString("namespaceURI"));
            log.info("methodName----" + wsObj.getString("methodName"));
            call.setOperationName(opAddEntry);//ws方法名
            List<String> paramName = new ArrayList<String>();
            int paramLength = 0;
            String dataType = null;
            for (Map<String, Object> p : params) {
                dataType = String.valueOf(p.get("data_type"));
                paramName.add(String.valueOf(p.get("param_name")));
                if (dataType.equals("0")) {
                    call.addParameter("arg" + paramLength, XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
                } else if (dataType.equals("1")) {
                    call.addParameter("arg" + paramLength, XMLType.XSD_INT, javax.xml.rpc.ParameterMode.IN);
                } else if (dataType.equals("4")) {
                    call.addParameter("arg" + paramLength, XMLType.XSD_FLOAT, javax.xml.rpc.ParameterMode.IN);
                } else if (dataType.equals("3")) {
                    call.addParameter("arg" + paramLength, XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
                }else if (dataType.equals("14")) {
                    call.addParameter("arg" + paramLength, XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
                }
                paramLength++;
            }
            //一个输入参数,如果方法有多个参数,复制多条该代码即可,参数传入下面new Object后面
            //call.addParameter("parameter1",XMLType.XSD_DATE, ParameterMode.IN) ;
            //call.addParameter("arg0", XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN) ;
            //call.addParameter("arg1", XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN) ;
            call.setEncodingStyle("UTF-8");
            call.setReturnType(XMLType.XSD_STRING);
            call.setUseSOAPAction(true);
//            Object[] obj = new Object[paramLength];
//            for (int i = 0; i < paramLength; i++) {
//                obj[i]=requestParamArr[i];
//            }
            /*obj[0] = str;
            obj[1] = str;*/
            log.info("====开始调用webservice接口=========" + Arrays.toString(requestParamArr));
            String result = (String) call.invoke(requestParamArr);
            log.info("wevservice调用结果result：" + result);
            return result;
        } catch (Exception e) {

            return "\n" +
                    "<data>\n" +
                    "\t<Stationid>8941313216</Stationid>\n" +
                    "\t<Appno>2019000464586385</Appno>\n" +
                    "\t<processState>01</processState>\n" +
                    "\t<processList>\n" +
                    "\t\t<process>\n" +
                    "\t\t\t<processPoint>业务受理</processPoint>\n" +
                    "\t\t</process>\n" +
                    "\t</processList>\n" +
                    "</data>";
        }
    }

}
