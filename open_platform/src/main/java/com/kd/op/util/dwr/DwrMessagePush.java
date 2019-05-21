package com.kd.op.util.dwr;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.util.RandomStr;

import com.alibaba.fastjson.JSONObject;
import com.kd.op.util.sm.SM2Key;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class DwrMessagePush{
	
    final public static String SCRIPT_SESSION_MSG = "showMessage"; //这是页面上当后台消息推送时，自动触发的js方法名称
	private final static Logger logger = Logger.getLogger(DwrMessagePush.class);
    
	/**
     * 采用dwr的方式向前台推送消息
     */
    public void sendMessageAuto() {
        ScriptBuffer script = new ScriptBuffer();
        HttpSession session = ContextHolderUtils.getSession();
        Map<String, String> onLineUsers = ResourceUtil.onlineUsers;
        Map<String, ScriptSession> scripeSessionMap = DWRScriptSessionListener.scriptSessionMap;
        TSUser user = ResourceUtil.getSessionUserName();
        
        if (user != null) {
			String currentUserId = user.getId();
			
			//判断当前登陆用户是否在已登录缓存中，并且判断是否是自己
			//如果此次进入首页的账号已经在缓存中，并且不是当前会话，说明该账号已被人登陆
			if (onLineUsers.get(currentUserId)!= null && 
					!onLineUsers.get(currentUserId).equals(session.getId())) {
				//发送掉线通知之后需要将上一个人的会话信息删除
				Client oldClient = null;
				Map<String, Client> clients = ClientManager.getInstance().getClientCache();
				for(String key:clients.keySet()){
					if (key.equals(onLineUsers.get(currentUserId))) {
						oldClient = clients.get(key);
					}
				}
				String oldIp = "";
				if (oldClient != null) {
					oldIp = oldClient.getIp();
				}
				
				//获取上一个登陆人的scriptSession信息
				ScriptSession scriptSession = scripeSessionMap.get(onLineUsers.get(currentUserId));
				if (scriptSession != null) {
					JSONObject obj = new JSONObject();
					obj.put("message", "该账号在其他地方登陆，您被迫下线！");
					obj.put("ip", oldIp);
					obj.put("sessionId", onLineUsers.get(currentUserId));
					script.appendCall(SCRIPT_SESSION_MSG, obj); //推送消息
					scriptSession.addScript(script);
				}
				ClientManager.getInstance().removeClinet(onLineUsers.get(user.getId()));
				onLineUsers.put(user.getId(), session.getId());
			}else if(onLineUsers.get(currentUserId) == null){
				onLineUsers.put(user.getId(), session.getId()); //如果不存在，则将当前用户加入到缓存中
				logger.info("add new user");
			}
			ResourceUtil.onlineUsers = onLineUsers;
		}
    }
    
    /**
     * 在点击登陆之前在后台为此次登陆准备随机密钥和随机数
     */
    public JSONObject prepareLogin(String userName){
    	HttpSession session = ContextHolderUtils.getSession();
    	//在登陆之前先为此次会话生成随机密钥
		String pubk = SM2Key.DBPubKey;//公钥
    	//然后生成一个15位随机数，防止会话重放攻击
    	String randomStr = RandomStr.createRandomString(15);
    	session.setAttribute(randomStr, pubk);
    	session.setAttribute(pubk, randomStr);
    	session.setAttribute(userName, pubk);
    	JSONObject data = new JSONObject();
    	data.put("passKey", pubk);
    	data.put("randomStr", randomStr);
    	return data;
    }
    
    public JSONObject getRandom(String str) {
    	JSONObject data = new JSONObject();
    	//然后生成一个15位随机数，防止会话重放攻击
    	String randomStr = RandomStr.createRandomString(15);
    	data.put("randomStr", randomStr);
    	//将生成的随机数放到当前client中
    	Client client = ClientManager.getInstance().getClient();
    	if(client != null) {
    		client.setRandomStr(randomStr);
    	}
    	return data;
    }
}
