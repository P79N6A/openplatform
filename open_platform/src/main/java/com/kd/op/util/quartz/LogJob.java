package com.kd.op.util.quartz;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
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
@Component("logJob")
public class LogJob implements Job {
	private final static Logger logger = Logger.getLogger(LogJob.class);
	
	@Autowired
	private SystemService systemService;
	
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("---------------------------LogJob定时任务，开始处理审计记录大小-----------------------");
		String sql = "select sum(DATA_LENGTH)+sum(INDEX_LENGTH) as num from information_schema.tables " + 
				"where table_schema='iesp'  AND TABLE_NAME='t_s_log'";
		List<Map<String, Object>> list = systemService.findForJdbc(sql, null);
		if(list != null && list.size() > 0) {
			Long num = ((BigDecimal) list.get(0).get("num")).longValue();
			Integer logLength = ResourceUtil.safeParams.get("logLength");
			
			Long length = logLength.longValue();

			if(num > length * 1024 * 1024) {
				systemService.createException("审计记录存储容量告警",
						"审计记录存储容量即将达到上限，请尽快处理", "2",null,Globals.Exception_type_Log_Max);
				logger.info("审计记录存储容量告警");
			}
		}
    }
	
}