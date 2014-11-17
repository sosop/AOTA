package com.tcl.aota.web.controller;

import com.tcl.aota.common.utils.JsonParser;
import com.tcl.aota.web.vo.AppLogVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kelong
 * @date 10/29/14
 */
public class AppTest {
    @Test
    public void log(){
        AppLogVo appLog=new AppLogVo();
        appLog.setAppId(new Long(48));
        appLog.setStatus(1);
        String logJson= JsonParser.toString(appLog);

        List<AppLogVo> appLogList = (List<AppLogVo>) JsonParser.jsonStrToList(logJson, ArrayList.class, AppLogVo.class);

    }
}
