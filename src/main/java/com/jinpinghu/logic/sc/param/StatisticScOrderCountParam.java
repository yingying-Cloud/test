package com.jinpinghu.logic.sc.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class StatisticScOrderCountParam extends BaseZLogicParam{

	public StatisticScOrderCountParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	private Integer enterpriseId;

}
