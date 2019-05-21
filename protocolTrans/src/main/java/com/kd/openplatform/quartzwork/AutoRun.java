package com.kd.openplatform.quartzwork;

import com.kd.openplatform.mq.MQ;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AutoRun implements ServletContextListener {
    private static final Log log = LogFactory.getLog(AutoRun.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("MQ监听开始启动");
        MQ mq = new MQ();
        mq.receive();
        log.info("MQ监听启动成功");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
