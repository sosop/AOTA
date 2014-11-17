package com.tcl.aota.manage;

import java.util.List;
import java.util.Map;

import com.tcl.aota.persistent.model.AppLog;

public interface AppLogManage {
	public Map<String, Object> getAll();
	
	public int[] getDownloadByDay();

	public int[] getDownloadByWeek();

	public int[] getUpgradeByDay();

	public int[] getUpgradeByWeek();

	public List<Object> statistics(int type, int day, float percent);

	/**
	 * add a app log data
	 * 
	 * @param appLogs
	 * @return
	 */
	public int batchInsert(List<AppLog> appLogs) throws Exception;
}
