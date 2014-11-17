package com.tcl.aota.persistent.dao.db;

import java.util.List;

public interface BaseDAO<T> {
	public int insert(T obj);
	public int update(T obj);
	public int delete(T obj);
	public int delete(int id);
	public T selectByCondition(Object condition);
	public List<T> selectMulByCondition(Object condition);
	public List<T> select();
	public long count(String ... condition);
}