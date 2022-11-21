package com.jinpinghu.logic.toolCatalog.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetToolCatalogList2Param extends BaseZLogicParam {

	public GetToolCatalogList2Param(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private String enterpriseId;
	private String toolCatelogIds;
	
	public String getToolCatalogIds() {
		return toolCatelogIds;
	}
	public void setToolCatalogIds(String toolCatelogIds) {
		this.toolCatelogIds = toolCatelogIds;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}
