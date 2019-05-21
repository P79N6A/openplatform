//package com.kd.openplatform.util;
//
//
//import org.apache.axis.client.Call;
//import org.apache.axis.client.Service;
//import org.apache.axis.encoding.XMLType;
//
//import javax.xml.namespace.QName;
//import javax.xml.rpc.ParameterMode;
//import javax.xml.rpc.ServiceException;
//
//public class Test {
//        public void post(){
//            try {
//                String str = "北京" ;
//                String endpoint = "http://192.168.3.101:12345/weather" ;
//                Service service = new Service() ;
//                Call call = (Call) service.createCall() ;
//                call.setTargetEndpointAddress(endpoint) ;//设置服务终端地址
//                QName opAddEntry = new QName("http://kd.com.test/", "weather");//设置NameSpace和方法
//                //call.setOperationName("weather") ;//ws方法名
//                call.setOperationName(opAddEntry) ;//ws方法名
//
//                //一个输入参数,如果方法有多个参数,复制多条该代码即可,参数传入下面new Object后面
//                //call.addParameter("parameter1",XMLType.XSD_DATE, ParameterMode.IN) ;
//                call.addParameter("arg0", XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN) ;
//                call.addParameter("arg1", XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN) ;
//                call.setEncodingStyle("UTF-8");
//                call.setReturnType(XMLType.XSD_STRING) ;
//                call.setUseSOAPAction(true) ;
//                Object[] obj = new Object[2];
//                obj[0] = str;
//                obj[1] = str;
//                String result = (String) call.invoke(obj) ;
//
////                System.out.println(result);
//
//            } catch (Exception e) {
////                e.printStackTrace();
//            }
//        }
//
//       /* public static void main(String args[]){
//            Test t = new Test();
//            t.post();
//        }*/
//}
