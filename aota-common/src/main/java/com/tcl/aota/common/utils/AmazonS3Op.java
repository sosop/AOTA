package com.tcl.aota.common.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public class AmazonS3Op {

    private static final Logger logger = LoggerFactory.getLogger(AmazonS3Op.class);

    private String accessKey;
    private String secretKey;
    private String bucketName;


    private AmazonS3 s3;

    public AmazonS3Op(String accessKey, String secretKey, String bucketName) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    }

    private void connectAws() {
        if (s3 != null) {
            return;
        }
        synchronized (logger) {
            logger.info("--- init Amazon S3 start ---- ");
            long d1 = System.currentTimeMillis();

            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            s3 = new AmazonS3Client(credentials);

            logger.info("--- init Amazon S3 end, it used time : " + (System.currentTimeMillis() - d1) + " ms.");
        }
    }

    public boolean copy(File file, String destFile) {

        try {
            connectAws();
            s3.putObject(new PutObjectRequest(bucketName, destFile, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));    // 设置权限
            logger.info("copy file :" + destFile + " to amazon s3 success !");
            return true;
        } catch (Exception e) {
            logger.error("copy file :" + destFile + " to amazon s3 error :" + e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean copy(FileInputStream inputStream, String destFile) {
        throw new RuntimeException("Not supported this method..");
    }

    public boolean deleteFile(String fileURL) {
        try {
            connectAws();
            s3.deleteObject(bucketName, fileURL);
            logger.info("delete file:"+fileURL +"from amazon s3 success.");
            return true;
        } catch (Exception e) {
            logger.error("at amazon s3 delete file :" + fileURL + "  error :" + e.getLocalizedMessage(), e);
        }
        return false;
    }

    public boolean deleteDir(String dirURL) {
        try {
            connectAws();
            long d1 = System.currentTimeMillis();
            ObjectListing objectListing = s3.listObjects(
                    new ListObjectsRequest().withBucketName(bucketName).withPrefix(dirURL));
            logger.info("amazon list objects use time :" + (System.currentTimeMillis() - d1) + " ms .");
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                String fileName = objectSummary.getKey();
                s3.deleteObject(bucketName, fileName);
                logger.info("delete file :" + fileName + " form amazon s3 successful!");
            }
            return true;
        } catch (Exception e) {
            logger.error("at amazon s3 delete directory :" + dirURL + "  error :" + e.getLocalizedMessage(), e);
        }
        return false;
    }
}
