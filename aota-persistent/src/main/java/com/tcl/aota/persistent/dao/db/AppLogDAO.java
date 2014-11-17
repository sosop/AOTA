package com.tcl.aota.persistent.dao.db;

import java.util.List;
import java.util.Map;

import com.tcl.aota.persistent.model.AppLog;

public interface AppLogDAO {

    public AppLog selectByPrimaryKey(Long id);

    public int selectBetweenDate(Map<String, Object> conditions);
    public List<AppLog> selectAppIdBetweenDate(Map<String, Object> conditions);

    /**
     * insert appLog data
     * @param appLog
     * @return
     */
    public int insertBath(List<AppLog> appLog)throws Exception;
      
}