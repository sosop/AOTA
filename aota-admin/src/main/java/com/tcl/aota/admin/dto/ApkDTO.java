package com.tcl.aota.admin.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kelong
 * @date 11/4/14
 */
public class ApkDTO {
    private String appName;
    private String packageName;
    private String versionCode;
    private String versionName;
    private long fileSize;
    private List<String> permissions = new ArrayList<String>();


    //是否有效
    public boolean isValid;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 添加权限
     *
     * @param permission
     */
    public void addPermissions(String permission) {
        permissions.add(permission);
    }

    public String parsePermitions() {
        StringBuilder sb = new StringBuilder();
        for (String s : permissions) {
            sb.append(s + ";");
        }
        if (sb.length() > 0) {
            sb = sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }


    public boolean isValid() {
        if (StringUtils.isEmpty(packageName) || StringUtils.isEmpty(versionCode) || StringUtils.isEmpty(versionName)) {
            return false;
        }
        return true;
    }
}
