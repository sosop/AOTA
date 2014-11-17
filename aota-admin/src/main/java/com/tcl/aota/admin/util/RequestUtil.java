package com.tcl.aota.admin.util;

import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.persistent.model.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author kelong
 * @date 11/7/14
 */
public class RequestUtil {
    public static Admin getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(Constants.Common.USER_KEY);
        return admin;
    }

    public static void setSessionUser(HttpServletRequest request, Admin user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }
}
