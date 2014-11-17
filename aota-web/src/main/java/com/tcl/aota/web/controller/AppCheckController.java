package com.tcl.aota.web.controller;

import com.tcl.aota.manage.StrategyManage;
import com.tcl.aota.persistent.model.Strategy;
import com.tcl.aota.web.manage.AppCheckManage;
import com.tcl.aota.web.vo.AppInfo;
import com.tcl.aota.web.vo.AppVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiaolong.hou
 * @version 1.0.0
 * @decs API for client
 * @date 2013-10-21
 */

@Controller
public class AppCheckController {

    @Resource
    private StrategyManage strategyManage;

    @Resource
    private AppCheckManage appCheckManage;

    /**
     * 功能： 客户端获取APPS接口
     * tips: 服务器根据什么条件返回APPS？
     */
    @ResponseBody
    @RequestMapping(value = "/app/checkList", method = RequestMethod.GET)
    public List<AppInfo> appList() {
        return appCheckManage.selectAppListByLatestPkg();
    }

    /**
     * app详情
     *
     * @param appId：appId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/app/appDetail", method = RequestMethod.GET)
    public AppVo appDetail(Long appId) {
        return appCheckManage.selectById(appId);
    }


    /**
     * app更新策略
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/app/strategy", method = RequestMethod.GET)
    public Strategy getStrategy() {
        return strategyManage.selectStrategy();
    }


    /**
     * 上传app下载安装成功失败日志
     *
     * @param logJson:json字符串
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/app/applog", method = RequestMethod.POST)
    public int appLog(String logJson) {
        return appCheckManage.insertAppLog(logJson);
    }
}