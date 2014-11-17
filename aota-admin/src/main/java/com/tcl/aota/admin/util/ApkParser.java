package com.tcl.aota.admin.util;

import com.tcl.aota.admin.dto.ApkDTO;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author kelong
 * @date 11/4/14
 */
public class ApkParser {

    private final static Logger LOG = Logger.getLogger(ApkParser.class);
    public static final String USES_PERMISSION = "uses-permission";
    public static final String PACKAGE = "package";
    public static final String APPLICATION_LABEL = "application-label";

//    public String aaptPath = "/data/work/aapt/";

    private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";


    static {
        init();
    }

    public static void init() {
        String aaptPath = ApkParser.class.getResource("/").getPath();
        String command ="chmod 755 "+aaptPath+"aapt";
        try {
            Process process = Runtime.getRuntime().exec(
                    new String[]{"/bin/sh", "-c", command});
        } catch (IOException e) {
            LOG.error("command error!" + e.getMessage(), e);
        }
    }

    /**
     * 解析apk字段
     *
     * @return
     */
    public ApkDTO apkParse(String apkPath) {
        String aaptPath = ApkParser.class.getResource("/").getPath();
        String command = aaptPath + "aapt d badging \"" + apkPath + "\"";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(
                    new String[]{"/bin/sh", "-c", command});
        } catch (IOException e) {
            process = null;
            LOG.error("command error!" + e.getMessage(), e);
        }
        if (process == null) {
            return null;
        }
        InputStream is = null;
        BufferedReader br = null;
        ApkDTO apkDto = null;
        try {
            is = process.getInputStream();
            br = new BufferedReader(
                    new InputStreamReader(is, "utf8"));
            String text = null;
            apkDto = new ApkDTO();
            while ((text = br.readLine()) != null) {
                parseColumn(apkDto, text);
            }
            //packageName,VersionName,VersionCode
            apkDto.setFileSize(getSize(apkPath));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (is != null) is.close();
                if (br != null) br.close();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return apkDto;
    }

    /**
     * 解析字段
     *
     * @param apkDTO
     * @param source
     */
    private void parseColumn(ApkDTO apkDTO, String source) {
        if (source.startsWith(PACKAGE)) {
            splitPackageInfo(apkDTO, source);
        } else if (source.startsWith(USES_PERMISSION)) {
            apkDTO.addPermissions(getPropertyInQuote(source));
        } else if (source.startsWith(APPLICATION_LABEL)) {
            apkDTO.setAppName(getPropertyInQuote(source));
        }
    }

    private String getPropertyInQuote(String source) {
        int index = source.indexOf("'") + 1;
        return source.substring(index, source.indexOf('\'', index));
    }

    /**
     * 解析package,versionCode,versionName
     *
     * @param apkDto
     * @param packageSource
     */
    private void splitPackageInfo(ApkDTO apkDto, String packageSource) {
        String[] packageInfo = packageSource.split(SPLIT_REGEX);
        apkDto.setPackageName(packageInfo[2]);
        apkDto.setVersionCode(packageInfo[4]);
        apkDto.setVersionName(packageInfo[6]);
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    private long getSize(String path) {
        File file = new File(path);
        long size = 0;
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);) {
                size = fis.available();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            LOG.error("file is not exist.");
        }
        return size;
    }
}
