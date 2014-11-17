package com.tcl.aota.web.util;

import com.tcl.aota.web.vo.AppLogVo;

import java.util.Date;

/**
 * @author kelong
 * @date 10/22/14
 */
public class EntryParser {
    /**
     * 将appLog拼成字符串
     *
     * @param appLog
     * @return
     */
    public static String parseAppLog(AppLogVo appLog) {
        StringBuilder sb = new StringBuilder();
        sb.append(appLog.getAppId() + ",");
        sb.append(appLog.getStatus()+ ",");
        sb.append(new Date());
        return sb.toString();
    }

}
