package com.tcl.aota.admin.dto;

public class Result {
	private final static Result result = new Result();

	private Object data;
	
	private Object status;

	private Result() {
	}

	public static Result get(Object data, Object status) {
		result.setData(data);
		result.setStatus(status);
		return result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
		this.status = status;
	}
}
