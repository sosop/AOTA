package com.tcl.aota.cache.impl;

import com.tcl.aota.cache.AppCacheManage;
import com.tcl.aota.cache.redis.cluster.Cluster;
import com.tcl.aota.cache.redis.cluster.ClusterInfo;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.SerializeUtil;
import com.tcl.aota.persistent.model.App;

import org.apache.log4j.Logger;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author kelong
 * @date 10/30/14
 */
@Service("appCacheManage")
public class AppCacheManageImpl implements AppCacheManage {
    private final static Logger LOG = Logger.getLogger(AppCacheManageImpl.class);
    @Resource(name = "clusterInfo")
    private ClusterInfo clusterInfo;


    @Override
    @Profiled
    public List<App> getCacheAppList() {
        List<App> appList = new ArrayList<App>();
        try {
            Cluster cluster = clusterInfo.cluster(Constants.REDIS.CLUSTER_1);
            byte[] appKey = Constants.REDIS.APP.getBytes();
            Map<byte[], byte[]> appCacheMap = cluster.hGetAll(appKey);
            if (appCacheMap != null && appCacheMap.size() > 0) {
                Collection<byte[]> appCaches = appCacheMap.values();
                for (byte[] appCache : appCaches) {
                    App app = (App) SerializeUtil.unserialize(appCache);
                    if (app != null) {
                        appList.add(app);
                    }
                }
                LOG.debug("读取缓存数据." + appList.size());
            } else {
                LOG.debug("缓存中未获取到数据.");
            }
        } catch (Exception e) {
            LOG.error("获取app缓存异常" + e.getMessage(), e);
        }
        return appList;
    }

    @Override
    @Profiled
    public App getCacheAppDetail(Long appId) {
        App app = null;
        try {
            Cluster cluster = clusterInfo.cluster(Constants.REDIS.CLUSTER_1);
            byte[] appKey = Constants.REDIS.APP.getBytes();
            byte[] field = appId.toString().getBytes();
            byte[] appCache = cluster.hGet(appKey, field);
            if (appCache != null) {
                app = (App) SerializeUtil.unserialize(appCache);
                LOG.debug("获取缓存app:" + app.getId());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return app;
    }


    @Override
    @Profiled
    public int updateCacheApps(List<App> latestPackageApps) {
        int result = 0;
        try {
            Cluster cluser = clusterInfo.cluster(Constants.REDIS.CLUSTER_1);
            if (latestPackageApps != null) {
                //del cache
                cluser.del(Constants.REDIS.APP.getBytes());
                //add cache
                for (App app : latestPackageApps) {
                    cluser.hSet(Constants.REDIS.APP.getBytes(), app.getId().toString().getBytes(), SerializeUtil.serialize(app));
                }
                result = latestPackageApps.size();
                LOG.info("update latestPackageApps:" + result);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }
}
