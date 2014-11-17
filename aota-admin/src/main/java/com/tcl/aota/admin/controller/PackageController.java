package com.tcl.aota.admin.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcl.aota.admin.dto.PackageDTO;
import com.tcl.aota.admin.dto.Result;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.manage.PackageManage;

@Controller
public class PackageController {

	@Resource(name = "packageManage")
	private PackageManage packageManage;

	@ResponseBody
	@RequestMapping(value = "/package", method = RequestMethod.POST)
	public Result packageList(@RequestBody PackageDTO packageDTO) {
		return Result.get(packageManage.queryByCondition(packageDTO.toMap()),
				Constants.Common.SUCCESS);
	}

	@ResponseBody
	@RequestMapping(value = "/package/release/{id}", method = RequestMethod.PUT)
	public Result release(@PathVariable long id) {
		return Result.get(Constants.Common.NULL, packageManage.release(id));
	}

	@ResponseBody
	@RequestMapping(value = "/package/dismiss", method = RequestMethod.POST)
	public Result dismiss(@RequestBody ArrayList<Long> ids) {
		return Result.get(Constants.Common.NULL, packageManage.dismiss(ids));
	}
	
	@ResponseBody
	@RequestMapping(value = "/package/apps/{packageId}", method = RequestMethod.GET)
	public Result appListById(@PathVariable long packageId) {
		return Result.get(packageManage.getAppsByPackage(packageId), Constants.Common.SUCCESS);
	}
}