package com.jinpinghu.logic.statistic.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class RecStatByToolParam extends BaseZLogicParam{

	public RecStatByToolParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}

	private String enterpriseId;
	private String startTime;
	private String endTime;
	private String recoveryId;
	
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getRecoveryId() {
		return recoveryId;
	}
	public void setRecoveryId(String recoveryId) {
		this.recoveryId = recoveryId;
	}
	
	
}
