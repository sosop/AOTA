package com.tcl.aota.admin.manager;

import com.tcl.aota.admin.dto.FileDTO;
import com.tcl.aota.admin.job.StoreFileThread;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * @author kelong
 * @date 11/6/14
 */
@Component("amazons3Manager")
public class Amazons3Manager {
    private static Logger LOG = Logger.getLogger(Amazons3Manager.class);
    @Resource
    private AmazonS3Op amazons3Op;


    public void reStore() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                LOG.debug("retore file to s3 thread is starting..");
//                while (true) {
//                    try {
//                        FileDTO fileDTO = queue.poll();
//                        if (fileDTO != null) {
//                            copy(fileDTO);
//                        }
//                        Thread.sleep(1000 * 2);
//                    } catch (InterruptedException e) {
//                        LOG.error(e.getMessage(), e);
//                    }
//                }
//            }
//        }).start();
    }

    public void copy(FileDTO fileDTO) {
        int i = 0;
        boolean flag = false;
        File file = fileDTO.getFile();
        do {
            flag = amazons3Op.copy(file, fileDTO.getDescPath());
            i++;
        } while (!flag && i < 3);

        if (!flag) {
            //warn TODO
            String filePath =Constants.Common.FAILURE_FILE_PATH;
            FileUtil.writeToFileByLock(filePath, JsonParser.toString(fileDTO));
            LOG.error("user store file to s3 failure through 3 times." + fileDTO.getDescPath());
        } else {
            //删除本地文件
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * add sync store file queue to s3
     *
     * @param fileMaps
     * @return
     */
    public void syncStoreFiles(Map<File, String> fileMaps) {
        for (Map.Entry<File, String> entry : fileMaps.entrySet()) {
            File file = entry.getKey();
            String descPath = entry.getValue();
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFile(file);
            fileDTO.setDescPath(descPath);
            StoreFileThread.add(fileDTO);
        }
    }

    /**
     * 同步上传文件到zmazons3,失败放入异步线程上传
     *
     * @param file
     * @param descPath
     */
    public void storeFiles(File file, String descPath) {
        boolean flag = amazons3Op.copy(file, descPath);
        if (!flag) {
            //上传失败，放入异步线程
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFile(file);
            fileDTO.setDescPath(descPath);
            StoreFileThread.add(fileDTO);
        }else{
            //删除本地文件
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * delete file from amazon s3
     *
     * @param filePaths
     */
    public void deleteFiles(List<String> filePaths) {
        for (String path : filePaths) {
            int i = 0;
            boolean flag = false;
            do {
                flag = amazons3Op.deleteFile(path);
                i++;
            } while (!flag && i < 3);

            if (!flag) {
                //warn TODO
                LOG.error("user delete file from s3 failure through 3 times." + path);
            }
        }
    }
}
