package com.jinpinghu.logic.home.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class PostOrderStatusCountParam extends BaseZLogicParam{

	public PostOrderStatusCountParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getPlantEnterpriseId() {
		return plantEnterpriseId;
	}
	public void setPlantEnterpriseId(String plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	private String enterpriseId;
	private String plantEnterpriseId;
	private String expertId;

}
