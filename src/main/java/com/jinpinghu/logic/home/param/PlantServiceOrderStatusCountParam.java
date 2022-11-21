package com.jinpinghu.logic.home.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class PlantServiceOrderStatusCountParam extends BaseZLogicParam{

	public PlantServiceOrderStatusCountParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getPlantEnterpriseId() {
		return plantEnterpriseId;
	}
	public void setPlantEnterpriseId(String plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}
	public String getPlantServiceenterpriseId() {
		return plantServiceenterpriseId;
	}
	public void setPlantServiceenterpriseId(String plantServiceenterpriseId) {
		this.plantServiceenterpriseId = plantServiceenterpriseId;
	}
	private String plantServiceenterpriseId;
	private String plantEnterpriseId;

}
