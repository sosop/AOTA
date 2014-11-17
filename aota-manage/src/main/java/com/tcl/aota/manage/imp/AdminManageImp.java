package com.tcl.aota.manage.imp;

import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.manage.AdminManage;
import com.tcl.aota.persistent.dao.db.AdminDAO;
import com.tcl.aota.persistent.model.Admin;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kelong
 * @date 11/7/14
 */
@Service("adminManage")
public class AdminManageImp implements AdminManage {
    Logger LOG = Logger.getLogger(AdminManageImp.class);
    @Resource
    private AdminDAO adminDAO;

    @Override
    public Admin login(String adminName, String adminPass) {
        Admin admin = null;
        try {
            List<Admin> adminList = adminDAO.selectByName(adminName);
            if (adminList != null && adminList.size() > 0) {
                admin = adminList.get(0);
            }
            if (admin != null && admin.getAdminPass().equals(adminPass)) {
                return admin;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
