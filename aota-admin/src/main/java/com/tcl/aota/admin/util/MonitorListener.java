package com.tcl.aota.admin.util;

import com.tcl.aota.admin.job.StoreFileThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author kelong
 * @date 11/14/14
 */
public class MonitorListener implements ServletContextListener {

    StoreFileThread storeFileThread = null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (storeFileThread == null) {
            storeFileThread = new StoreFileThread();
            storeFileThread.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (storeFileThread != null && storeFileThread.isInterrupted()) {
            storeFileThread.interrupt();
        }
    }
}
