package com.tcl.aota.manage.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tcl.aota.cache.AppCacheManage;
import com.tcl.aota.common.utils.StringUtils;
import org.apache.log4j.Logger;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Service;

import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.manage.AppManage;
import com.tcl.aota.persistent.dao.db.AppDAO;
import com.tcl.aota.persistent.dao.db.PackageDAO;
import com.tcl.aota.persistent.model.App;
import com.tcl.aota.persistent.model.Package;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("appManage")
public class AppManageImp implements AppManage {

    private final static Logger LOG = Logger.getLogger(AppManageImp.class);
    @Resource
    private AppDAO appDAO;

    @Resource
    private PackageDAO packageDAO;

    @Resource
    private AppCacheManage appCacheManage;

    @Override
    @Profiled
    public List<App> getCacheAppList() {
        List<App> appList = appCacheManage.getCacheAppList();//获取缓存
        if (StringUtils.isNullList(appList)) {
            synchronized (this) {
                appList = appCacheManage.getCacheAppList();
                if (StringUtils.isNullList(appList)) {
                    appList = selectAppListByLatestPkg();
                    appCacheManage.updateCacheApps(appList);
                }
            }
        }
        return appList;
    }

    @Override
    @Profiled
    public List<App> selectAppListByLatestPkg() {
        List<App> appList = null;
        Package latestPackage = null;
        try {
            latestPackage = packageDAO.selectLatestPackage();
            if (latestPackage != null) {
                appList = appDAO.selectAppListByPackageId(latestPackage.getId());
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return appList;
    }


    @Override
    @Profiled
    public App selectById(Long appId) {
        App app = appCacheManage.getCacheAppDetail(appId);
        if (app == null) {
            app = appDAO.selectByPrimaryKey(appId);
        }
        return app;
    }


    @Override
    @Profiled
    @Transactional(propagation = Propagation.REQUIRED)
    public int putOutRecycle(List<Long> ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("trash", Constants.APPStatus.UNTRASH);
        return appDAO.trashOpt(params);
    }


    @Override
    @Profiled
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteApps(List<Long> ids) {
        return appDAO.deleteByIds(ids);
    }


    @Override
    @Profiled
    @Transactional(propagation = Propagation.REQUIRED)
    public int putInRecycle(List<Long> ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("trash", Constants.APPStatus.TRASH);
        return appDAO.trashOpt(params);
    }

    @Override
    @Profiled
    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(App app) {
        return appDAO.insert(app);
    }

    @Override
    @Profiled
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateAppBySeq(Long appId, int sequence) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appId", appId);
        params.put("sequence", sequence);
        return appDAO.updateAppBySeq(params);
    }

    @Override
    public int selectAppCountByConds(Map<String, Object> param) {
        return appDAO.selectAppCountByConds(param);
    }

    @Override
    public List<App> selectAppListByConds(Map<String, Object> param) {
        return appDAO.selectAppListByConds(param);
    }
}
