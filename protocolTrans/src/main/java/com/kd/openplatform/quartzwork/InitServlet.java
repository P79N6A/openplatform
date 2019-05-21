package com.kd.openplatform.quartzwork;


import com.kd.openplatform.util.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import sunbox.gateway.api.model.system.AccessTokenModel;
import sunbox.gateway.api.service.system.SysTokenApiService;

public class InitServlet {

    private static final Log log =  LogFactory.getLog(InitServlet.class);
    public static String accessToken;
    @Autowired
    private SysTokenApiService sysTokenApiService;
    @Autowired
    private RedisTemplate redisTemplate;

    public void init() {
        log.info("--------项目启动,执行获取accessToken方法---------");
        getAccessToken();
    }
    @Scheduled(cron="0 0 0/23 * * ?")
    private void getAccessToken(){
        log.info("------定时任务开始,执行获取accessToken方法-------");
        try {
            AccessTokenModel accessTokenModel =new AccessTokenModel();
            accessTokenModel.setUserName(Constants.accessUserName);
            log.info("accessUserName======"+Constants.accessUserName);
            accessTokenModel.setKey(Constants.accessKey);
            log.info("accessKey======"+Constants.accessKey);
            AccessTokenModel vo = null;
            try {
                vo = (AccessTokenModel)sysTokenApiService.getAccessToken(accessTokenModel).getData();
            } catch (Exception e) {
                log.info("获取accessToken调用出错："+e);
            }
            accessToken= vo.getAccessToken();
            //将accessToken存入redis中
            redisTemplate.boundValueOps("ACCESSTOKEN").set(accessToken);
            log.info("accessToken-------------"+vo.getAccessToken());
        } catch (Exception e) {
            log.error("Exception:"+e);
        }
    }
}
