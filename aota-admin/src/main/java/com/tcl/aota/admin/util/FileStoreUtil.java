package com.tcl.aota.admin.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author kelong
 * @date 11/5/14
 */
public class FileStoreUtil {
    static Logger LOG = Logger.getLogger(FileStoreUtil.class);

    static {
        init();
    }

    private static void init() {
        String tempDir = FileStoreUtil.getUploadTempDir();
        File tempDirFile = new File(tempDir);
        if (!tempDirFile.exists()) {
            tempDirFile.mkdir();
        }
    }

    /**
     * copy file to the tmp dir
     *
     * @param file
     * @return
     */
    public static String store(MultipartFile file) {
        String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
        String originalFileName = FilenameUtils.getExtension(file.getOriginalFilename());
        String uuid = gerneratedFileUUid(baseName, originalFileName);
        String tempDir = FileStoreUtil.getUploadTempDir();
        try {
            FileOutputStream os = new FileOutputStream(tempDir + uuid);
            InputStream is = file.getInputStream();
            IOUtils.copy(file.getInputStream(), os);
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
        return uuid;
    }

    /**
     * new file name
     *
     * @param fileExtension
     * @return
     */
    public static String gerneratedFileUUid(String baseName, String fileExtension) {
//        UUID uuid = UUID.randomUUID();
        int randomNum = RandomUtils.nextInt(10000);
        if (StringUtils.isEmpty(fileExtension)) {
            return baseName + "_" + randomNum;
        }
        return baseName + "_" + randomNum + "." + fileExtension;
    }


    /**
     * tmp dir
     *
     * @return
     */
    public static String getUploadTempDir() {
        String tempDir = System.getProperty("temp_dir");
        if (tempDir == null) {
            tempDir = System.getProperty("java.io.tmpdir");
        }
        return tempDir + File.separator + "aota-store"
                + File.separator;
    }

    /**
     * get file by uuid
     *
     * @param uuid
     * @return
     */
    public static File getFileViaUUID(String uuid) {
        if(StringUtils.isEmpty(uuid)){
            return null;
        }
        String tempDir = FileStoreUtil.getUploadTempDir();
        File uuIdFile = new File(tempDir + uuid);
        if (uuIdFile.exists()) {
            return uuIdFile;
        }
        return null;
    }
}
