package com.tcl.aota.web.manage;

import com.tcl.aota.cache.AppCacheManage;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.DateUtil;
import com.tcl.aota.common.utils.FileUtil;
import com.tcl.aota.common.utils.JsonParser;
import com.tcl.aota.common.utils.StringUtils;
import com.tcl.aota.manage.AppLogManage;
import com.tcl.aota.manage.AppManage;
import com.tcl.aota.web.vo.AppInfo;
import com.tcl.aota.web.vo.AppVo;
import com.tcl.aota.persistent.model.App;
import com.tcl.aota.persistent.model.AppLog;
import com.tcl.aota.web.util.EntryParser;
import com.tcl.aota.web.vo.AppLogVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author kelong
 * @date 10/29/14
 */
@Component
public class AppCheckManage {

    private final static Logger LOG = Logger.getLogger(AppCheckManage.class);
    @Resource
    private AppLogManage appLogManage;

    @Resource
    private AppManage appManage;

    @Resource
    private AppCacheManage redisManage;


    /**
     * 获取最新发布包的applist
     *
     * @return
     */
    public List<AppInfo> selectAppListByLatestPkg() {
        List<AppInfo> appInfoList = new ArrayList<AppInfo>();
        List<App> appList = appManage.getCacheAppList();
        if (appList != null) {
            for (App app : appList) {
                AppInfo appInfo = new AppInfo();
                appInfo.setId(app.getId());
                appInfo.setPackageId(app.getPackageId());
                appInfo.setApkPackName(app.getApkPackName());
                appInfo.setAppName(app.getAppName());
                appInfo.setAppIcon(app.getAppIcon());
                appInfo.setAppSize(app.getAppSize());
                appInfo.setVersionName(app.getVersionName());
                appInfo.setVersionCode(app.getVersionCode());
                appInfo.setAppUrl(app.getAppUrl());
                appInfoList.add(appInfo);
            }
        }
        return appInfoList;
    }

    /**
     * 获取app详情,先取缓存，在
     *
     * @param appId
     * @return
     */
    public AppVo selectById(Long appId) {
        App app = appManage.selectById(appId);
        if(app==null){
            LOG.error("get app detail failure."+appId);
            return null;
        }
        AppVo appVo = new AppVo();
        appVo.setId(app.getId());
        appVo.setPackageId(app.getPackageId());
        appVo.setApkPackName(app.getApkPackName());
        appVo.setAppName(app.getAppName());
        appVo.setVersionName(app.getVersionName());
        appVo.setVersionCode(app.getVersionCode());
        appVo.setAppIcon(app.getAppIcon());
        appVo.setAppSize(app.getAppSize());
        appVo.setSourceChannel(app.getSourceChannel());
        appVo.setAppStartGrade(app.getAppStartGrade());
        appVo.setAppDeveloper(app.getAppDeveloper());
        appVo.setAppDetail(app.getAppDetail());
        appVo.setAppPermission(app.getAppPermission());
        appVo.setAppImgs(app.getAppImgs());
        appVo.setAppUrl(app.getAppUrl());
        appVo.setAppSequence(app.getAppSequence());
        return appVo;
    }


    /**
     * 存储日志数据到文件,入库mysql
     *
     * @param logJson
     * @return
     */
    @SuppressWarnings("unchecked")
    public int insertAppLog(String logJson) {
        int status = Constants.Common.FAIL;
        // 获取日志文件路径
        String filePath = StringUtils.append(Constants.Common.LOG_PATH,
                DateUtil.fomartDateToStr(Constants.Common.DATE_FORMART_1, new Date()),
                ".log");
        List<AppLog> insertLogList = new ArrayList<AppLog>();
        List<AppLogVo> appLogList = (List<AppLogVo>) JsonParser.jsonStrToList(logJson, ArrayList.class, AppLogVo.class);
        if (appLogList != null) {
            for (AppLogVo log : appLogList) {
                FileUtil.writeToFileByLock(filePath, EntryParser.parseAppLog(log));//写文件到日志
                AppLog appLog = new AppLog();
                appLog.setAppId(log.getAppId());
                appLog.setStatus(log.getStatus());
                appLog.setCreateTime(new Date());
                insertLogList.add(appLog);
            }
        }
        LOG.info("write File:" + appLogList == null ? 0 : appLogList.size());
        try {
            if (insertLogList.size() > 0) {
                int result = appLogManage.batchInsert(insertLogList);
                if (result > 0) {
                    status = Constants.Common.SUCCESS;
                    LOG.info("insert DB:" + result);
                }
            }
        } catch (Exception e) {
            LOG.error("add app log failure:" + e.getMessage(), e);
        }

        return status;
    }
}
