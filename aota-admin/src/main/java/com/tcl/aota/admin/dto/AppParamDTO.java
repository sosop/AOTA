package com.tcl.aota.admin.dto;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kelong
 * @date 11/4/14
 */
public class AppParamDTO {
    private String appName;
    private String sourceChannel;
    private String appSequence;
    private String versionName;
    private String appDeveloper;
    private String uploadTime;
    private Integer trash;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getAppSequence() {
        return appSequence;
    }

    public void setAppSequence(String appSequence) {
        this.appSequence = appSequence;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppDeveloper() {
        return appDeveloper;
    }

    public void setAppDeveloper(String appDeveloper) {
        this.appDeveloper = appDeveloper;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getTrash() {
        return trash;
    }

    public void setTrash(Integer trash) {
        this.trash = trash;
    }

    public Map<String, Object> putCondsMap() {
        Map<String, Object> param = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(appName)) {
            param.put("appName", appName);
        }
        if (!StringUtils.isEmpty(appDeveloper)) {
            param.put("appDeveloper", appDeveloper);
        }
        if (!StringUtils.isEmpty(appSequence)) {
            param.put("appSequence", appSequence);
        }
        if (!StringUtils.isEmpty(versionName)) {
            param.put("versionName", versionName);
        }
        if (!StringUtils.isEmpty(sourceChannel)) {
            param.put("sourceChannel", sourceChannel);
        }
        if (!StringUtils.isEmpty(uploadTime)) {
            param.put("uploadTime", uploadTime);
        }
        if (trash != null) {
            param.put("trash", trash);
        }
        return param;
    }
}
