package com.tcl.aota.admin.controller;

import com.tcl.aota.admin.dto.AdminDTO;
import com.tcl.aota.admin.util.RequestUtil;
import com.tcl.aota.common.utils.PasswordEncrypt;
import com.tcl.aota.manage.AdminManage;
import com.tcl.aota.persistent.model.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author kelong
 * @date 11/7/14
 */
@Controller
public class AdminController {
    @Resource
    private AdminManage adminManage;

    /**
     * login page
     *
     * @return
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping(value = "/admin/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * index dashboard
     *
     * @return
     */
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "dashboard";
    }

    /**
     * login
     *
     * @param adminDTO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/loginSys", method = RequestMethod.POST)
    public String login(HttpServletRequest request,AdminDTO adminDTO) {
        String encryptPass = PasswordEncrypt.encrypt(adminDTO.getAdminPass(), 1);
        Admin admin = adminManage.login(adminDTO.getAdminName(), encryptPass);
        if (admin!=null) {
            RequestUtil.setSessionUser(request,admin);
            return "redirect:/dashboard";
        } else {
            return "redirect:/admin/login";
        }
    }

    /**
     * 注销用户
     *
     * @return
     */
    @RequestMapping(value = "/admin/loginOut")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/admin/login";
    }
}
