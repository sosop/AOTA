package com.tcl.aota.admin.dto;



/**
 * @author kelong
 * @date 11/4/14
 */
public class AppDTO {
    private Long id;
    private String appName;
    private String apkPackName;
    private String appSize;
    private String appStartGrade;
    private String appDeveloper;
    private String appDetail;
    private String appPermission;
    private String appIconUUID;//iconUUID
    private String appImgUUIDs;//imgUUIDS
    private String sourceChannel;
    private String versionName;
    private String versionCode;
    private String appUrlUUID;//apkUUID

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApkPackName() {
        return apkPackName;
    }

    public void setApkPackName(String apkPackName) {
        this.apkPackName = apkPackName;
    }

    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    public String getAppStartGrade() {
        return appStartGrade;
    }

    public void setAppStartGrade(String appStartGrade) {
        this.appStartGrade = appStartGrade;
    }

    public String getAppDeveloper() {
        return appDeveloper;
    }

    public void setAppDeveloper(String appDeveloper) {
        this.appDeveloper = appDeveloper;
    }

    public String getAppDetail() {
        return appDetail;
    }

    public void setAppDetail(String appDetail) {
        this.appDetail = appDetail;
    }

    public String getAppPermission() {
        return appPermission;
    }

    public void setAppPermission(String appPermission) {
        this.appPermission = appPermission;
    }

    public String getAppIconUUID() {
        return appIconUUID;
    }

    public void setAppIconUUID(String appIconUUID) {
        this.appIconUUID = appIconUUID;
    }

    public String getAppImgUUIDs() {
        return appImgUUIDs;
    }

    public void setAppImgUUIDs(String appImgUUIDs) {
        this.appImgUUIDs = appImgUUIDs;
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppUrlUUID() {
        return appUrlUUID;
    }

    public void setAppUrlUUID(String appUrlUUID) {
        this.appUrlUUID = appUrlUUID;
    }
}
