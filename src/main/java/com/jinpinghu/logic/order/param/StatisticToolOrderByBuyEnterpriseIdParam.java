package com.jinpinghu.logic.order.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class StatisticToolOrderByBuyEnterpriseIdParam extends BaseZLogicParam {

	public StatisticToolOrderByBuyEnterpriseIdParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private Integer buyEnterpriseId;
	private String startTime;
	private String endTime;
	public Integer getBuyEnterpriseId() {
		return buyEnterpriseId;
	}
	public void setBuyEnterpriseId(Integer buyEnterpriseId) {
		this.buyEnterpriseId = buyEnterpriseId;
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

}
