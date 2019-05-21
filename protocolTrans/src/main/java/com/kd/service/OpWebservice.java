//package com.kd.service;//package com.kd.openplatform.ws.service;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.servlet.annotation.WebServlet;
//import javax.xml.ws.Endpoint;
//import java.net.InetAddress;
//
//@WebServlet("/OpService")
//public class OpWebservice {
//    private static final Log log = LogFactory.getLog(OpWebserviceI.class);
//    private InetAddress ia=null;
//    private String getLocalIp() {
//        try {
//            ia=ia.getLocalHost();
//            String localip=ia.getHostAddress();
//            log.info("本机的ip是 ："+localip);
//            return localip;
//        } catch (Exception e) {
//            log.error("本机ip获取失败"+e);
//            return null;
//        }
//    }
//
//    @Value("${service.addressEnd}")
//    private String addressEnd;
//
//    public void init() {
//        try {
//            String localIp = getLocalIp();
//            String address="http://"+localIp+addressEnd;
//            log.info("发布webservice服务");
//            log.info(address);
//            Endpoint.publish(address, new OpWebserviceImpl());
//            log.info("webservice服务发布成功") ;
//        } catch (Exception e) {
//            log.error("webservice服务发布失败"+e);
//        }
//    }
//}
