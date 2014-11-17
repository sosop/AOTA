package com.tcl.aota.manage;

import com.tcl.aota.persistent.model.App;

import java.util.List;
import java.util.Map;

public interface AppManage {


    /**
     * get app list by cache
     *
     * @return
     */
    public List<App> getCacheAppList();

    /**
     * get app list of latest release package by DB
     *
     * @return
     */
    public List<App> selectAppListByLatestPkg();

    /**
     * get app detail by appId
     *
     * @param appId
     * @return
     */
    public App selectById(Long appId);


    /**
     * put out apps from recycle
     *
     * @param appIds
     * @return
     */
    public int putOutRecycle(List<Long> ids);

    /**
     * delete apps forever
     *
     * @param appIds
     * @return
     */
    public int deleteApps(List<Long> ids);

    /**
     * put app in recycle
     *
     * @param ids
     * @return
     */
    public int putInRecycle(List<Long> ids);


    /**
     * upload app
     *
     * @param app
     * @return
     */
    public int insert(App app);

    /**
     * update app by sequence
     *
     * @param appId
     * @param sequence
     * @return
     */
    public int updateAppBySeq(Long appId, int sequence);

    /**
     * get app count by condition map
     *
     * @param param
     * @return
     */
    int selectAppCountByConds(Map<String, Object> param);

    /**
     * get app list by condition map
     *
     * @param param
     * @return
     */
    List<App> selectAppListByConds(Map<String, Object> param);
}
