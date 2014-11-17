package com.tcl.aota.admin.dto;

/**
 * @author kelong
 * @date 11/5/14
 */
public class ApkReponseDTO {
    //upload result
    private int resultFlag;//0=上传失败,1=解析失败,2=解析成功
    private String apkuuid;

    //apk info
    private String appName;
    private String packageName;
    private String versionCode;
    private String versionName;
    private long fileSize;
    private String permissions;

    public int getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(int resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getApkuuid() {
        return apkuuid;
    }

    public void setApkuuid(String apkuuid) {
        this.apkuuid = apkuuid;
    }


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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
