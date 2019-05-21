package com.kd.op.util.quartz;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {

    private final static Logger logger = Logger.getLogger(QuartzManager.class);

	public static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
//    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    
    private static Scheduler scheduler;
    
    private static QuartzManager quartzManager = new QuartzManager();
    
    private QuartzManager(){
    }
    
    public static QuartzManager getInstance(Scheduler scheduler){
    	try {
			QuartzManager.scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			logger.error("getInstance error");
		}
    	return quartzManager;
    }
    
}
