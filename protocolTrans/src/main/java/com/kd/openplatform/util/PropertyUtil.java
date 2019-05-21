package com.kd.openplatform.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import com.kd.openplatform.quartzwork.InitServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;

public class PropertyUtil {
    private static final Log log =  LogFactory.getLog(InitServlet.class);
//    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;
    static{

        loadProps();
    }

    synchronized static private void loadProps(){
      //  logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
               /*第一种，通过类加载器进行获取properties文件流*/
            ClassLoader classLoader = PropertyUtil.class.getClassLoader();
            if (classLoader!=null) {
                in = classLoader.getResourceAsStream("queryToken.properties");
            }
             /* 第二种，通过类进行获取properties文件流*/
                    //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            if (in!=null) {
                props.load(in);
            }
        } catch (Exception e) {
            log.error("Exception"+e);
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (Exception e) {
                log.error("Exception"+e);
            }
        }
      //  logger.info("加载properties文件内容完成...........");
       // logger.info("properties文件内容：" + props);
    }

    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

}
