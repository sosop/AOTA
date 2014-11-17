package com.tcl.aota.web.vo;

/**
 * @author kelong
 * @date 10/22/14
 */
public class AppLogVo {
    private Long appId;//appID
    private Integer status;//1=下载成功;0=下载失败

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
