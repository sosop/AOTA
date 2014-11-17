package com.tcl.aota.manage;

import com.tcl.aota.persistent.model.Admin;

/**
 * @author kelong
 * @date 11/7/14
 */
public interface AdminManage {
    /**
     * login admin
     * @param adminName
     * @param adminPass
     * @return
     */
    public Admin login(String adminName,String adminPass);
}
