package com.tcl.aota.cache.redis.thrift;

import org.apache.thrift.TException;


public class RemoteImp implements Remote.Iface {

	@Override
	public String sset(String key, String value) throws TException {
		return null;
	}

	@Override
	public String get(String key) throws TException {
		return null;
	}

	@Override
	public long delKey(String key) throws TException {
		return 0;
	}

	@Override
	public String lpopList(String key) throws TException {
		return null;
	}

	@Override
	public long rpushList(String key, String values) throws TException {
		return 0;
	}

	@Override
	public long expire(String key, int time) throws TException {
		return 0;
	}

	@Override
	public long hsetnx(String key, String field, String value) throws TException {
		return 0;
	}

	@Override
	public boolean exist(String key) throws TException {
		return false;
	}

	@Override
	public boolean existInSet(String key, String member) throws TException {
		return false;
	}

	@Override
	public long saddSet(String key, String members) throws TException {
		return 0;
	}

	@Override
	public long sremSet(String key, String members) throws TException {
		return 0;
	}

	@Override
	public String spopSet(String key) throws TException {
		return null;
	}
}