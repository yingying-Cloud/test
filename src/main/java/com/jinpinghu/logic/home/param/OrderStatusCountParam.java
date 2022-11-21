package com.jinpinghu.logic.home.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class OrderStatusCountParam extends BaseZLogicParam{

	public OrderStatusCountParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getPlantEnterpriseId() {
		return plantEnterpriseId;
	}
	public void setPlantEnterpriseId(String plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}
	public String getToolEnterpriseId() {
		return toolEnterpriseId;
	}
	public void setToolEnterpriseId(String toolEnterpriseId) {
		this.toolEnterpriseId = toolEnterpriseId;
	}
	private String toolEnterpriseId;
	private String plantEnterpriseId;

}
