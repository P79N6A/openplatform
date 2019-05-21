package com.kd.op.util.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.LogService;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * quartz示例定时器类
 * 
 * @author Administrator
 * 
 */
@Component("userJob")
public class UserJob implements Job {
	private final static Logger logger = Logger.getLogger(UserJob.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final Integer MAX_MONTH_TIME = 6;//最长休眠时间6个月
	private static final Integer MAX_DAY_TIME = 8;//提前8天开始记录异常告警，推送给管理员
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private LogService logService;
	
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("---------------------------UserJob定时任务，开始处理用户-----------------------");
		//1.判断临时用户是否需要注销
		//获取所有的临时用户
		List<TSUser> tempUsers = systemService.findByProperty(TSUser.class, "type", (short)0);
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		//循环判断临时用户的创建时间是否距离当前时间超过3个月，如果超过，就将状态置为休眠
		Calendar temp = Calendar.getInstance();
		Date tempDate = null;
		for(TSUser user:tempUsers) {
			//只处理非休眠和非注销的用户
			if(!user.getStatus().equals(Globals.User_Sleep) && !user.getStatus().equals(Globals.User_Delete)) {
				tempDate = user.getCreateDate();
				if(tempDate != null) {
					temp.setTime(tempDate);
					//让创建时间增加3个月再和当前时间比较
					temp.add(Calendar.MONTH, 3);
					if(temp.before(calendar)) {//创建时间增加3个月之后还小于当前时间，就将状态改为休眠
						user.setStatus(Globals.User_Sleep);
						user.setUpdateDate(now);
						systemService.updateEntitie(user);
						logger.info("临时用户" + user.getUserName() + "休眠成功");
					}
				}
			}
		}
		
		//2.判断所有的长期用户，如果超过3个月未登录就置为休眠
		//获取所有的休眠用户
		List<TSUser> longUsers = systemService.findByProperty(TSUser.class, "type", (short)1);
		//循环判断每个用户的登陆成功的日志，获取最新的那个
		for(TSUser user:longUsers) {
			//只处理非休眠和非注销的用户
			if(!user.getStatus().equals(Globals.User_Sleep) && !user.getStatus().equals(Globals.User_Delete)) {
				//获取用户的最新登陆成功的日志
				TSLog log = logService.getNewestLoginForUser(user);
				Date compareTime = null;
				if(log != null) {
					compareTime = log.getOperatetime();
				}else {//如果该用户没有登陆过，就根据用户的创建时间进行判断
					compareTime = user.getCreateDate();
				}
				if(compareTime != null) {
					temp.setTime(compareTime);
					temp.add(Calendar.MONTH, 3);
					//如果最新的登录时间+3个月之后早于当前时间，说明已经3个月未登录了
					if(temp.before(calendar)) {
						user.setStatus(Globals.User_Sleep);
						user.setUpdateDate(now);
						systemService.updateEntitie(user);
						logger.info("长期用户" + user.getUserName() + "休眠成功");
					}
				}
			}
		}
		
		//3.判断所有休眠用户是否需要注销
		//获取所有的休眠用户
		List<TSUser> sleepUsers = systemService.findByProperty(TSUser.class, "status", Globals.User_Sleep);
		//循环判断用户的更新时间+6个月之后和当前时间作比较
		for(TSUser user:sleepUsers) {
			tempDate = user.getUpdateDate();
			temp.setTime(tempDate);
			temp.add(Calendar.MONTH, MAX_MONTH_TIME);
			//更新时间+6个月之后，如果超过了当前时间，就将状态置为注销，否则再进行其他判断
			if(temp.before(calendar)) {
				user.setStatus(Globals.User_Delete);
				user.setUpdateDate(now);
				systemService.updateEntitie(user);
				logger.info(user.getUserName() + "注销成功");
			}else {
				//再让更新时间+8天，如果超过当前时间，就记录异常告警，否则就不做处理
				temp.add(Calendar.DAY_OF_YEAR, -MAX_DAY_TIME);
				if(temp.before(calendar)) {
					systemService.createException("用户"+user.getUserName()+"即将注销",
							"休眠用户"+user.getUserName()+"即将被注销，如需激活，请尽快处理", "2",null,Globals.Exception_type_User_Sleep);
				}
			}
		}
    }
}