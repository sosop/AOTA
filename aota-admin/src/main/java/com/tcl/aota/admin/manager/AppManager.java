package com.tcl.aota.admin.manager;

import com.tcl.aota.admin.dto.ApkDTO;
import com.tcl.aota.admin.dto.ApkReponseDTO;
import com.tcl.aota.admin.dto.AppDTO;
import com.tcl.aota.admin.util.ApkParser;
import com.tcl.aota.admin.util.FileStoreUtil;
import com.tcl.aota.admin.util.S3Util;
import com.tcl.aota.cache.redis.utils.StringUtil;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.manage.AppManage;
import com.tcl.aota.persistent.model.App;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kelong
 * @date 11/5/14
 */
@Component
public class AppManager {

    private static Logger LOG = Logger.getLogger(AppManager.class);
    @Resource
    private AppManage appManage;
    @Resource(name="amazons3Manager")
    private Amazons3Manager amazons3Manager;


    /**
     * parse the apk infomation
     *
     * @param uuid
     * @return
     */
    public ApkReponseDTO parseApk(String uuid) {
        ApkReponseDTO reponseDTO = new ApkReponseDTO();
        try {
            if (uuid == null) {
                //上传本地失败
                reponseDTO.setResultFlag(Constants.APK.UPLOAD_FAIL);
                return reponseDTO;
            }
            File file = FileStoreUtil.getFileViaUUID(uuid);
            ApkParser parser = new ApkParser();
            ApkDTO apkDTO = parser.apkParse(file.getPath());
            if (apkDTO == null || !apkDTO.isValid()) {
                //解析失败
                reponseDTO.setResultFlag(Constants.APK.PARSE_FAIL);
                return reponseDTO;
            }
            reponseDTO.setApkuuid(uuid);
            reponseDTO.setAppName(apkDTO.getAppName());
            reponseDTO.setVersionName(apkDTO.getVersionName());
            reponseDTO.setVersionCode(apkDTO.getVersionCode());
            reponseDTO.setPackageName(apkDTO.getPackageName());
            reponseDTO.setFileSize(apkDTO.getFileSize());
            reponseDTO.setPermissions(apkDTO.parsePermitions());
            //解析成功
            reponseDTO.setResultFlag(Constants.APK.PARSE_SUCCESS);
            LOG.info("parse apk sucess."+uuid);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return reponseDTO;
    }

    /**
     * insert app
     *
     * @param appDTO
     * @return
     */
    public int insertApp(AppDTO appDTO) {
        int status = Constants.Common.FAIL;
        try {
            String resPrefix = S3Util.getResourcePrefix();
            Map<File, String> s3Map = new HashMap<File, String>();
            //store apk
            File apkFile = FileStoreUtil.getFileViaUUID(appDTO.getAppUrlUUID());
            String apkPath = "";
            if (apkFile != null) {
                apkPath = Constants.APK.APK_PATH + apkFile.getName();
                s3Map.put(apkFile, apkPath);
            }
            //store imgs
            String imgs = appDTO.getAppImgUUIDs();
            String[] imgsArray = imgs.split(",");
            StringBuilder imgsSb = new StringBuilder();
            if (imgsArray != null && imgsArray.length > 0) {
                for (String imgUuid : imgsArray) {
                    File imgFile = FileStoreUtil.getFileViaUUID(imgUuid);
                    if (imgFile != null) {
                        String imgPath = Constants.APK.IMAGS_PATH + imgFile.getName();
                        s3Map.put(imgFile, imgPath);
                        imgsSb.append(resPrefix + imgPath + ",");
                    }
                }
                if (imgsSb.length() > 0) {
                    imgsSb = imgsSb.delete(imgsSb.length() - 1, imgsSb.length());
                }
            }
            amazons3Manager.syncStoreFiles(s3Map);
            //store icon
            File iconFile = FileStoreUtil.getFileViaUUID(appDTO.getAppIconUUID());
            String iconPath = "";
            if (iconFile != null) {
                iconPath = Constants.APK.ICON_PATH + iconFile.getName();
                amazons3Manager.storeFiles(iconFile,iconPath);
            }
            App app = new App();
            app.setAppName(StringUtils.isEmpty(appDTO.getAppName()) ? null : appDTO.getAppName());
            app.setApkPackName(appDTO.getApkPackName());
            app.setAppSize(appDTO.getAppSize());
            app.setAppStartGrade(appDTO.getAppStartGrade());
            app.setAppDeveloper(appDTO.getAppDeveloper());
            app.setAppDetail(appDTO.getAppDetail());
            app.setAppPermission(appDTO.getAppPermission());
            app.setSourceChannel(appDTO.getSourceChannel());
            app.setVersionName(appDTO.getVersionName());
            app.setVersionCode(appDTO.getVersionCode());
            app.setAppUrl(StringUtils.isEmpty(apkPath) ? "" : resPrefix + apkPath);//apk S3 path
            app.setAppIcon(StringUtils.isEmpty(iconPath) ? "" : resPrefix + iconPath);//icon S3 path
            app.setAppImgs(imgsSb.toString());//imgs S3 path
            app.setAppSequence(Constants.Common.DEFAULT_SEQUENCE);
            app.setTrash(Byte.parseByte(Constants.APPStatus.UNTRASH + ""));
            app.setPackageId(Constants.Common.DEFAULT_PACKAGEID);
            app.setCreateTime(new Date());
            app.setUpdateTime(new Date());
            appManage.insert(app);
            LOG.info("add app success." + app.getAppName());
            status = Constants.Common.SUCCESS;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return status;
    }
}
