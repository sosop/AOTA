package com.tcl.aota.admin.controller;

import com.tcl.aota.admin.dto.AppDTO;
import com.tcl.aota.admin.dto.AppParamDTO;
import com.tcl.aota.admin.dto.FileReponseDTO;
import com.tcl.aota.admin.dto.Result;
import com.tcl.aota.admin.manager.AppManager;
import com.tcl.aota.admin.util.FileStoreUtil;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.manage.AppManage;
import com.tcl.aota.manage.PackageManage;
import com.tcl.aota.persistent.model.App;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AppController {

	@Resource(name = "appManage")
	private AppManage appManage;

	@Resource(name = "packageManage")
	private PackageManage packageManage;

	@Resource(name = "appManager")
	private AppManager appManager;

    @ResponseBody
	@RequestMapping(value = "/app/recycle/remove", method = RequestMethod.POST)
	public Result removerFromRecycle(@RequestBody List<Long> ids) {
		return Result.get(Constants.Common.NULL, appManage.deleteApps(ids));
	}

    @ResponseBody
	@RequestMapping(value = "/app/recycle/recovery", method = RequestMethod.POST)
	public Result recoveryFromTrash(@RequestBody List<Long> ids) {
		return Result.get(Constants.Common.NULL, appManage.putOutRecycle(ids));
	}

	/**
	 * app list by query condition
	 * 
	 * @param appParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/res/list/{pageNo}", method = RequestMethod.POST)
	public Result getAppList(@RequestBody AppParamDTO appParam,
			@PathVariable Integer pageNo) {
		// int page=0;
		// if(pageNo!=null){
		// page=pageNo;
		// }
		Map<String, Object> paramMap = appParam.putCondsMap();
		paramMap.put("packageId", 0);// 并且未打包的
		paramMap.put("start", (pageNo - 1) * Constants.Common.PAGE_SIZE);
		paramMap.put("pageSize", Constants.Common.PAGE_SIZE);
		int total = appManage.selectAppCountByConds(paramMap);
		List<App> appList = appManage.selectAppListByConds(paramMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("appList", appList);
		resultMap.put("total", total);
        resultMap.put("pageSize",Constants.Common.PAGE_SIZE);
		resultMap.put("pageNo", pageNo);
		return Result.get(resultMap, Constants.Common.SUCCESS);
	}

	/**
	 * add app
	 * 
	 * @param appDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/res/add", method = RequestMethod.POST)
	public Result add(@RequestBody AppDTO appDTO) {
		return Result.get(Constants.Common.NULL, appManager.insertApp(appDTO));
	}

	/**
	 * batch del apps
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/res/del", method = RequestMethod.POST)
	public Result del(@RequestBody List<Long> ids) {
		return Result.get(Constants.Common.NULL, appManage.putInRecycle(ids));
	}

	/**
	 * update app by sequence.
	 * 
	 * @param appId
	 * @param sequence
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/res/sequence/{appId}/{sequence}", method = RequestMethod.POST)
	public Result updateBySequence(@PathVariable Long appId,
			@PathVariable Integer sequence) {
		return Result.get(Constants.Common.NULL,
				appManage.updateAppBySeq(appId, sequence));
	}

	/**
	 * put apps in a new package
	 * 
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/res/putpackage", method = RequestMethod.POST)
	public Result putAppToPackage(@RequestBody List<Long> ids) {
		return Result.get(Constants.Common.NULL,
				packageManage.putAppToPackage(ids));
	}

	/**
	 * parse the apk and get app info
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/res/parseApk")
	public Result parseApk(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile apkFile = multipartRequest.getFile("apkFile");
		String uuid = FileStoreUtil.store(apkFile);
		return Result.get(appManager.parseApk(uuid), Constants.Common.SUCCESS);
	}

	/**
	 * upload the file to the tmp dir /tmp/aota-store/ and return the uuid.
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/res/upload", method = RequestMethod.POST)
	public Result upload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> filesMap = multipartRequest.getFileMap();
		List<FileReponseDTO> reponseDTOs = new ArrayList<FileReponseDTO>();
		if (filesMap != null) {
			for (Map.Entry<String, MultipartFile> entry : filesMap.entrySet()) {
				MultipartFile multipartFile = entry.getValue();
				FileReponseDTO result = new FileReponseDTO();
				result.setFileName(multipartFile.getOriginalFilename());
				String uuid = FileStoreUtil.store(multipartFile);
				if (uuid == null) {
					result.fail();
				} else {
					result.setFileUuid(uuid);
				}
				reponseDTOs.add(result);
			}
		}
		return Result.get(reponseDTOs, Constants.Common.SUCCESS);
	}
}
