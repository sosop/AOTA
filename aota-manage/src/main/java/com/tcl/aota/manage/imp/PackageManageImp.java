package com.tcl.aota.manage.imp;

import com.tcl.aota.cache.AppCacheManage;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.DateUtil;
import com.tcl.aota.manage.PackageManage;
import com.tcl.aota.persistent.dao.db.AppDAO;
import com.tcl.aota.persistent.dao.db.PackageDAO;
import com.tcl.aota.persistent.model.App;
import com.tcl.aota.persistent.model.Package;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("packageManage")
public class PackageManageImp implements PackageManage {

	@Resource(name = "packageDAO")
	private PackageDAO packageDAO;

	@Resource(name = "appDAO")
	private AppDAO appDAO;

	@Resource
	private AppCacheManage appCacheManage;

	@Override
	@Profiled
	public List<Package> queryByCondition(Map<String, Object> condition) {
		return packageDAO.selectPackages(condition);
	}

	@Override
	@Profiled
	public String release(long id) {
		String result = null;
		Date releaseTime = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("releaseTime", releaseTime);
		if (packageDAO.releasePackages(param) > 0) {
			List<App> latestPackageApps = appDAO.selectAppListByPackageId(id);
			appCacheManage.updateCacheApps(latestPackageApps);
			result = DateUtil.fomartDateToStr("yyyy-MM-dd HH:mm:ss", releaseTime);
		}
		return result;
	}

	@Override
	@Profiled
	@Transactional
	public int dismiss(List<Long> ids) {
		return appDAO.recoveryApp(ids) + packageDAO.deleteByPrimaryKeys(ids);
	}

	@Override
	@Profiled
	@Transactional(propagation = Propagation.REQUIRED)
	public int putAppToPackage(List<Long> ids) {
		List<App> appList = appDAO.selectAppListByIds(ids);
		int appCount = (appList == null) ? 0 : appList.size();
		float packageSize = 0;
		for (App app : appList) {
			packageSize += Long.parseLong(app.getAppSize());
		}
		Package pck = new Package();
		pck.setAppQuantity(appCount);
		pck.setPackageSize(packageSize);
		String packageName = Constants.Common.PACKAGE_PREFIX
				+ DateUtil.fomartDateToStr("yyyyMMddHHmmss", new Date());
		pck.setPackageName(packageName);
		pck.setCreateTime(new Date());
		pck.setPackageSequence(Constants.Common.DEFAULT_SEQUENCE);
		packageDAO.insert(pck);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("packageId", pck.getId());
		int count = appDAO.putAppToPackage(param);
		return count;
	}

	@Override
	public List<App> getAppsByPackage(long packageId) {
		return appDAO.selectAppListByPackageId(packageId);
	}
}
