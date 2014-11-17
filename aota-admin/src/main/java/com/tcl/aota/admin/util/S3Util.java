package com.tcl.aota.admin.util;

import java.util.Properties;

public class S3Util {
    private static Properties staticResProp;
    private static final String staticResFile = "props/staticres.properties";
    static {
        staticResProp = ConfigUtil.getConfigs(staticResFile);
    }

    public static String getAccessKey() {
        return staticResProp.getProperty("s3.access.key");
    }

    public static String getSecretKey() {
        return staticResProp.getProperty("s3.secret.key");
    }

    public static String getBucketName() {
        return staticResProp.getProperty("s3.bucket.name");
    }

    public static String getResourcePrefix() {
        return staticResProp.getProperty("s3.static.url");
    }
}
