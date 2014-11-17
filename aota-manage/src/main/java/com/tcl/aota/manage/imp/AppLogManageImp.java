package com.tcl.aota.manage.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Service;

import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.DateUtil;
import com.tcl.aota.manage.AppLogManage;
import com.tcl.aota.persistent.dao.db.AppDAO;
import com.tcl.aota.persistent.dao.db.AppLogDAO;
import com.tcl.aota.persistent.model.AppLog;

@Service("appLogManage")
public class AppLogManageImp implements AppLogManage {

	private AppLogDAO appLogDAO;

	@Resource(name = "appLogDAO")
	public void setAppLogDAO(AppLogDAO appLogDAO) {
		this.appLogDAO = appLogDAO;
	}

	@Resource(name = "appDAO")
	private AppDAO appDAO;

	@Override
	@Profiled
	public int[] getDownloadByDay() {
		return statisticsSplit(Constants.Common.DAY, 3, Constants.APPStatus.DOWNLOAD, Constants.APPStatus.UNGRADE);
	}

	@Override
	@Profiled
	public int[] getDownloadByWeek() {
		return statisticsSplit(Constants.Common.WEEK, Constants.Common.DAY, Constants.APPStatus.DOWNLOAD,
				Constants.APPStatus.UNGRADE);
	}

	@Override
	@Profiled
	public int[] getUpgradeByDay() {
		return statisticsSplit(Constants.Common.DAY, 3, Constants.APPStatus.UNGRADE);
	}

	@Override
	@Profiled
	public int[] getUpgradeByWeek() {
		return statisticsSplit(Constants.Common.WEEK, Constants.Common.DAY, Constants.APPStatus.UNGRADE);
	}

	@Override
	@Profiled
	public int batchInsert(List<AppLog> appLogs) throws Exception {
		int result = 0;
		if (appLogs != null && appLogs.size() > 0) {
			result = appLogDAO.insertBath(appLogs);
		}
		return result;
	}

	@Override
	@Profiled
	public List<Object> statistics(int type, int day, float percent) {

		int start, step;
		if (day == 1) {
			start = Constants.Common.DAY;
			step = 3;
		} else if (day == 7) {
			start = Constants.Common.WEEK;
			step = Constants.Common.DAY;
		} else {
			return null;
		}

		List<Object> objs = new LinkedList<>();

		// 统计结果
		int[] result = new int[8];

		// 算出时间段中的所有app_id及其数量
		List<AppLog> allApps = appLogDAO.selectAppIdBetweenDate(conditions(start, Constants.Common.ZERO, null));

		List<AppLog> optApps = appLogDAO.selectAppIdBetweenDate(conditions(start, Constants.Common.ZERO,
				Constants.APPStatus.DOWNLOAD, Constants.APPStatus.UNGRADE));
		if (type == Constants.APPStatus.DOWNLOAD) {
			result = statisticsSplit(start, step, Constants.APPStatus.DOWNLOAD, Constants.APPStatus.UNGRADE);
		} else if (type == Constants.APPStatus.UNGRADE) {
			allApps = optApps;
			optApps = appLogDAO.selectAppIdBetweenDate(conditions(start, Constants.Common.ZERO,
					Constants.APPStatus.UNGRADE));
			result = statisticsSplit(start, step, Constants.APPStatus.UNGRADE);
		}
		objs.add(result);

		List<Long> ids = new ArrayList<>(100);
		if (optApps != null && optApps.size() > 0) {
			AppLog log = null;
			for (AppLog opt : optApps) {
				log = binarySearch(allApps, opt.getAppId());
				if (calculate(opt.getStatus(), log.getStatus(), percent)) {
					ids.add(opt.getAppId());
				}
			}
		}

		if (ids.size() > 0) {
			objs.add(appDAO.selectAppListByIds(ids));
		} else {
			objs.add(new ArrayList<>());
		}

		return objs;
	}

	@Override
	@Profiled
	public Map<String, Object> getAll() {
		Map<String, Object> map = new HashMap<>();
		map.put("dd", getDownloadByDay());
		map.put("dw", getDownloadByWeek());
		map.put("ud", getUpgradeByDay());
		map.put("uw", getUpgradeByWeek());
		return map;
	}

	@Profiled
	private Map<String, Object> conditions(int start, int end, int... status) {
		Map<String, Object> conditions = new HashMap<>();
		conditions.put("start", DateUtil.getBeforeHours(start));
		conditions.put("end", DateUtil.getBeforeHours(end));
		conditions.put("status", status);
		return conditions;
	}

	@Profiled
	private boolean calculate(float dividend, float divisor, float percent) {
		return ((dividend / divisor) * 100.0F) >= percent;
	}

	@Profiled
	private AppLog binarySearch(List<AppLog> logs, long appId) {
		int low = 0, high = logs.size() - 1, mid;
		AppLog log = null;
		while (low <= high) {
			mid = (high + low) / 2;
			if (appId == logs.get(mid).getAppId()) {
				log = logs.get(mid);
				break;
			} else if (appId > logs.get(mid).getAppId()) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return log;
	}

	@Profiled
	private int[] statisticsSplit(int start, int step, int... status) {
		int[] result = new int[8];
		int end = 0, i = 0;
		do {
			end = start - step;
			result[i] = appLogDAO.selectBetweenDate(conditions(start, end, status));
			start = end;
			i++;
		} while (end != 0);
		return result;
	}
}