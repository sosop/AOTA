package com.tcl.aota.admin.dto;

/**
 * @author kelong
 * @date 11/5/14
 */
public class FileReponseDTO {
    private String fileName;
    private String result = "success";
    private String fileUuid;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFileUuid() {
        return fileUuid;
    }

    public void setFileUuid(String fileUuid) {
        this.fileUuid = fileUuid;
    }

    public void fail(){
        result="failure";
    }
}
