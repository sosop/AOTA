package com.tcl.aota.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Logger logger = LoggerFactory
            .getLogger(ConfigUtil.class);
    /**
     * This is the parameter name of JVM. You can use it by specify the JVM
     * startup parameter like this,
     * -Dapp_conf=/data/server/apache-tomcat-7.55/bin/app_conf
     */
    private static final String APP_CONF_NAME = "app_conf";

    /**
     * Get the config file as Properties object
     * 
     * @author yliu
     * @date Sep 22, 2014 1:21:34 PM
     * @param fileName
     * @return
     */
    public static Properties getConfigs(String fileName) {
        String confPath = getAppConfPath();
        InputStream inputStream = null;
        if (confPath == null) {
            logger.warn(
                    "Not {} parameter be specified,try to load resource from classpath",
                    APP_CONF_NAME);
            inputStream = ConfigUtil.class.getClassLoader()
                    .getResourceAsStream(fileName);
        } else {
            String filePath = confPath + fileName;
            try {
                inputStream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                logger.error("file not found:" + filePath);
                return null;
            }
        }

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }

        return properties;
    }
    /**
     * get the root conf path
     * 
     * @author yliu
     * @date Sep 22, 2014 1:22:03 PM
     * @return the path
     */
    public static String getAppConfPath() {
        String confPath = System.getProperty(APP_CONF_NAME);
        if (confPath == null) {
            return null;
        }
        if (confPath.endsWith("/")) {
            return confPath;
        }
        return confPath + "/";
    }
}
