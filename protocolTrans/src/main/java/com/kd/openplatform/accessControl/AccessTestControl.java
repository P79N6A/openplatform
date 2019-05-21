package com.kd.openplatform.accessControl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kd.openplatform.cache.HttpAddrMapping;
import com.kd.openplatform.entity.ApiInvokeLogEntity;
import com.kd.openplatform.entity.UserToken;
import com.kd.openplatform.info.InfoBean;
import com.kd.openplatform.quartzwork.InitServlet;
//import com.kd.openplatform.quartzwork.QuartzJob;
import com.kd.openplatform.services.OpenPlatformBaseService;
import com.kd.openplatform.util.AESUtil;
import com.kd.openplatform.util.Constants;
import com.kd.openplatform.util.PropertyUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import serviceCom.DataCom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
@Component
/**
 * @author zhutianpeng
 * 能力平台http、https提供服务统一入口的AOP切点，这个类用来对accessControl进行hsf的通信
 */
public class AccessTestControl{
    @Autowired
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    private HttpServletRequest request;
    private AccessControl ac=new AccessControl();

    @Autowired
    private OpenPlatformBaseService openPlatformBaseService;


    private static final Log log =  LogFactory.getLog(AccessTestControl.class);
    @Pointcut("execution(public * com.kd.openplatform.action.OpenPlatformTestBaseController.baseAction(..))")
    public void accessControl(){
        log.info("accessControl");
    }

    @Around("accessControl()")
    public Object processTx(ProceedingJoinPoint jp) throws Throwable
    {
        log.info("------------------------");
        try {
            request = (HttpServletRequest) jp.getArgs()[1];
        } catch (Exception e) {
           log.info(e.getMessage());
        }
        log.info("accessControl");

        InfoBean bean = new InfoBean();
        HttpAddrMapping httpAddrMapping = openPlatformBaseService.getHttpAddrMapping();
     /* around方法作为目标方法的前后切点，用来对用户进行：
      *  @param ProceedingJoinPoint 切点的信息
      */
        String httpAddress = (String) jp.getArgs()[0];

        log.info(String.format("-----------httpAddress:%s",httpAddress));


        String argsStr = request.getParameter("args");
        JSONObject requestParam = JSONObject.parseObject(argsStr);
        log.info("传入的参数是:"+requestParam);
        JSONObject decryptArgs = new JSONObject();

        decryptArgs.put("path",httpAddress);

        log.info(decryptArgs);

//      2. 执行目标的方法
        log.info("执行目标方法...status: %s");
        Object rvt = jp.proceed(jp.getArgs());

        log.info(String.format("传入访问控制的参数是：%s",decryptArgs));


        //正确返回
        return ac.packingArgs("1","jsonpCallback",rvt.toString());
    }
}
