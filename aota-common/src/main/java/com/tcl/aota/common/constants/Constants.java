package com.tcl.aota.common.constants;

/**
 * 常量定义
 */
public abstract class Constants {
    public abstract class Common {
        public static final String LOG_PATH = "/data/aota-log/app_log_";
        public static final String FAILURE_FILE_PATH="/data/aota-log/s3failure_file.log";
        public static final String DATE_FORMART_1 = "yyyy-MM-dd";
        public static final String NULL = "";
        public static final int SUCCESS = 1;
        public static final int FAIL = 0;
        public static final int ZERO = 0;
        public static final int DAY = 24;
        public static final int WEEK = 7 * 24;
        public static final String PACKAGE_PREFIX = "PACKAGE_";
        public static final long DEFAULT_PACKAGEID = 0;
        public static final int DEFAULT_SEQUENCE = 9999;
        public static final int PAGE_SIZE = 50;
        public static final String USER_KEY = "user";
    }

    public abstract class APPStatus {
        public static final int DOWNLOAD = 1;
        public static final int UNGRADE = 2;
        public static final int FALIUR = 0;
        public static final int TRASH = 1;
        public static final int UNTRASH = 0;
    }

    public abstract class REDIS {
        public static final String APP = "latestPackageApp";
        public static final String CLUSTER_1 = "aota";
        public static final String STRATEGY = "strategy";
    }

    public abstract class APK {
        public static final int UPLOAD_FAIL = 0;
        public static final int PARSE_FAIL = 1;
        public static final int PARSE_SUCCESS = 2;

        public static final String APK_PATH = "aota/apk/";
        public static final String ICON_PATH = "aota/icon/";
        public static final String IMAGS_PATH = "aota/imgs/";

    }

}