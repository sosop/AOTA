package com.tcl.aota.manage;

import com.tcl.aota.persistent.model.Strategy;

/**
 * @author kelong
 * @date 10/29/14
 */
public interface StrategyManage {
	/**
	 * 获取更新策略配置
	 * 
	 * @return
	 */
	public Strategy selectStrategy();
	
	/**
	 * 更新策略配置
	 * 
	 * @return
	 */
	public int updateStrategy(Strategy strategy);
}
