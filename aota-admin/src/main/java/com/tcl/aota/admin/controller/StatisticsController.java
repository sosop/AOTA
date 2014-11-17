package com.tcl.aota.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcl.aota.admin.dto.Result;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.manage.AppLogManage;

@Controller
public class StatisticsController {

	@Resource(name = "appLogManage")
	private AppLogManage appLogManage;

	@ResponseBody
	@RequestMapping(value = "/statistics/static", method = RequestMethod.GET)
	public Result staticSta() {
		return Result.get(appLogManage.getAll(), Constants.Common.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/statistics/dynamic/{type}/{day}/{percent}", method = RequestMethod.GET)
	public Result dynamicSta(@PathVariable int type, @PathVariable int day,
			@PathVariable float percent) {
		return Result.get(appLogManage.statistics(type, day, percent),
				Constants.Common.SUCCESS);
	}
}