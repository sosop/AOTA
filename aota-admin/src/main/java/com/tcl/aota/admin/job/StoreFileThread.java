package com.tcl.aota.admin.job;

import com.tcl.aota.admin.dto.FileDTO;
import com.tcl.aota.admin.manager.Amazons3Manager;
import com.tcl.aota.admin.util.SpringApplicationContextHolder;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author kelong
 * @date 11/14/14
 */
public class StoreFileThread extends Thread {
    Logger LOG = Logger.getLogger(StoreFileThread.class);

    public static Queue<FileDTO> queue = new LinkedList<FileDTO>();

    private Amazons3Manager amazons3Manager = null;

    public StoreFileThread() {
        amazons3Manager = (Amazons3Manager) SpringApplicationContextHolder.getSpringBean("amazons3Manager");
    }

    public static void add(FileDTO fileDTO) {
        queue.add(fileDTO);
    }

    public void run() {
        LOG.debug("retore file to s3 thread is starting..");
        while (true) {
            try {
                FileDTO fileDTO = queue.poll();
                if (fileDTO != null) {
                    amazons3Manager.copy(fileDTO);
                }
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
