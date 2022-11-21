package com.jinpinghu.logic.home.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class PlantOrderStatusCountParam extends BaseZLogicParam{

	public PlantOrderStatusCountParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getPlantEnterpriseId() {
		return plantEnterpriseId;
	}
	public void setPlantEnterpriseId(String plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}
	public String getPlantProtectionenterpriseId() {
		return plantProtectionenterpriseId;
	}
	public void setPlantProtectionenterpriseId(String plantProtectionenterpriseId) {
		this.plantProtectionenterpriseId = plantProtectionenterpriseId;
	}
	private String plantProtectionenterpriseId;
	private String plantEnterpriseId;

}
