package com.tcl.aota.manage.imp;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tcl.aota.cache.StrategyCacheManage;
import com.tcl.aota.manage.StrategyManage;
import com.tcl.aota.persistent.dao.db.StrategyDAO;
import com.tcl.aota.persistent.model.Strategy;

/**
 * @author kelong
 * @date 10/29/14
 */
@Service("strategyManage")
@Scope("singleton")
public class StrategyManageImp implements StrategyManage {
	@Resource
	private StrategyDAO strategyDAO;
	
	@Resource(name = "strategyCache")
	private StrategyCacheManage strategyCache;

	@Override
	@Profiled
	public Strategy selectStrategy() {
		Strategy strategy = strategyCache.getStrategyFromCache();
		if(strategy == null) {
			synchronized (this) {
				if((strategy = strategyCache.getStrategyFromCache()) == null) {
					strategy = strategyDAO.selectStrategyConfig(); 
					if(strategy != null) {
						strategyCache.upgradeStrategy(strategy);
					}
				}
			}
		}
		return strategy;
	}

	@Override
	@Profiled
	public int updateStrategy(Strategy strategy) {
		int status = -1;
		if((status = strategyDAO.updateByPrimaryKeySelective(strategy)) > 0) {
			strategyCache.upgradeStrategy(strategy);
		}
		return status;
	}
}