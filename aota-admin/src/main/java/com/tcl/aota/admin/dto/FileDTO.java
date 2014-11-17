package com.tcl.aota.admin.dto;

import java.io.File;
import java.util.Date;

/**
 * @author kelong
 * @date 11/11/14
 */
public class FileDTO {
    private String descPath;
    private File file;
    private Date uploadTime;

    public FileDTO() {
        uploadTime = new Date();
    }

    public String getDescPath() {
        return descPath;
    }

    public void setDescPath(String descPath) {
        this.descPath = descPath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
