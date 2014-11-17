package com.tcl.aota.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcl.aota.admin.dto.Result;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.manage.StrategyManage;
import com.tcl.aota.persistent.model.Strategy;

@Controller
public class StrategyController {

	@Resource(name = "strategyManage")
	private StrategyManage strategyManage;

	@ResponseBody
	@RequestMapping(value = "/strategy", method = RequestMethod.GET)
	public Result strategy() {
		return Result.get(strategyManage.selectStrategy(),
				Constants.Common.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/strategy/update", method = RequestMethod.POST)
	public Result editStrategy(@RequestBody Strategy strategy) {
		return Result.get(Constants.Common.NULL,
				strategyManage.updateStrategy(strategy));
	}
}