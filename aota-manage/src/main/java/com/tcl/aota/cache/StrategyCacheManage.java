package com.tcl.aota.cache;

import com.tcl.aota.persistent.model.Strategy;

public interface StrategyCacheManage {
	public Strategy getStrategyFromCache();
	public void upgradeStrategy(Strategy strategy);
}
