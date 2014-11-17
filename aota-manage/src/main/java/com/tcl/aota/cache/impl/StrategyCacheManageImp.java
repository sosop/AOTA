package com.tcl.aota.cache.impl;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Service;

import com.tcl.aota.cache.StrategyCacheManage;
import com.tcl.aota.cache.redis.cluster.ClusterInfo;
import com.tcl.aota.common.constants.Constants;
import com.tcl.aota.common.utils.JsonParser;
import com.tcl.aota.persistent.model.Strategy;

@Service("strategyCache")
public class StrategyCacheManageImp implements StrategyCacheManage {

	@Resource(name = "clusterInfo")
	private ClusterInfo clusterInfo;

	@Override
	@Profiled
	public Strategy getStrategyFromCache() {
		String objStr = String.valueOf(clusterInfo.cluster(Constants.REDIS.CLUSTER_1).get(Constants.REDIS.STRATEGY));
		return (Strategy) JsonParser.jsonStrToObj(objStr, Strategy.class);
	}

	@Override
	@Profiled
	public void upgradeStrategy(Strategy strategy) {
		clusterInfo.cluster(Constants.REDIS.CLUSTER_1).set(
				Constants.REDIS.STRATEGY, JsonParser.toString(strategy));
	}
}