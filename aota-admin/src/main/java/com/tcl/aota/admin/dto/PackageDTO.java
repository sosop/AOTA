package com.tcl.aota.admin.dto;

import java.util.HashMap;
import java.util.Map;

public class PackageDTO {
	private String packageName;
	private String createTime;
	private int quantity;
	private int sequence;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> condition = new HashMap<>();
		condition.put("packageName", this.packageName);
		condition.put("createTime", this.createTime);
		condition.put("quantity", this.quantity);
		condition.put("sequence", this.sequence);
		return condition;
	}
}
