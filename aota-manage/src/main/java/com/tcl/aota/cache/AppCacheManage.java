package com.tcl.aota.cache;

import com.tcl.aota.persistent.model.App;

import java.util.List;

/**
 * @author kelong
 * @date 10/30/14
 */
public interface AppCacheManage {
    /**
     * get cache app list
     *
     * @return
     */
    public List<App> getCacheAppList();

    /**
     * get cache detail
     *
     * @param appId
     * @return
     */
    public App getCacheAppDetail(Long appId);

    /**
     * update latestpackageApps cache when release package
     *
     * @param latestPackageApps
     * @return
     */
    public int updateCacheApps(List<App> latestPackageApps);
}
