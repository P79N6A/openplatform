package com.kd.op.util.rtdb;

import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.system.util.RandomStr;
import org.jeecgframework.web.system.util.SendMail;

import redis.clients.jedis.Jedis;


/**
 * 管理邮件认证码
 * @author Administrator
 *
 */
public class CheckMailCode {
	private final static Logger logger = Logger.getLogger(CheckMailCode.class);

	private static String mailCodeFlag = "_mailCode";
	
	public static String createMailCode(String userName,String email){
		Jedis jedis = RtdbClient.getResource();
		//获取账号对应的邮件认证码的key
		String codeKey = userName + mailCodeFlag;
		String code = null;
		//判断缓存中的key是否存在，若存在，则表示已经发送过邮箱
		code = getMailCode(userName);
		if(code == null) {
			try {
				//获取缓存中的邮件认证码的有效期
				//Integer codeTime = ResourceUtil.safeParams.get("mailCodeTime");
				//邮件认证码的有效期限固定为5分钟
				Integer codeTime = 5;
				//获取6位随机码
				code = RandomStr.createRandomString(6);
				//存放到实时库中，并设置有效期
				jedis.setex(codeKey, codeTime*60, code);
				//将认证码发送到邮箱中
				SendMail.sendMail(userName, email, code);
			} catch (Exception e) {
				code = null;
			} finally {
				RtdbClient.returnResource(jedis);
			}
			return code;
		} else {
			return Globals.MAIL_STATUS_SEND; //邮件已存在
		}
	}
	
	/**
	 * 获取账号在邮件中的认证码
	 * @param userName
	 * @return
	 */
	public static String getMailCode(String userName){
		Jedis jedis = RtdbClient.getResource();
		String code = null;
		String codeKey = userName + mailCodeFlag;
		try {
			code = jedis.get(codeKey);
		} catch (Exception e) {
			logger.error("get code error");
		} finally {
			RtdbClient.returnResource(jedis);
		}
		if (code != null) {
			return code;
			//code = code.toLowerCase(); 邮箱验证码区分大小写
		}
		return null;
	}
	
	//手动清除邮件认证码
	public static void clearMailCode(String userName){
		Jedis jedis = RtdbClient.getResource();
		String codeKey = userName + mailCodeFlag;
		try {
			if (jedis.exists(codeKey)) {
				jedis.del(codeKey);
			}
		} catch (Exception e) {
			logger.error("clearMailCode error");
		} finally {
			RtdbClient.returnResource(jedis);
		}
	}
	
}
