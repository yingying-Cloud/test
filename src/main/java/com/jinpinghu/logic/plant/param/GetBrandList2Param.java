package com.jinpinghu.logic.plant.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetBrandList2Param extends BaseZLogicParam {

	public GetBrandList2Param(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private String enterpriseId;
	private String toolIds;
	
	public String getToolIds() {
		return toolIds;
	}
	public void setToolIds(String toolIds) {
		this.toolIds = toolIds;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}
