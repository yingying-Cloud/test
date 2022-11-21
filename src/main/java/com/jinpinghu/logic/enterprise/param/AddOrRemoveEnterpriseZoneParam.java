package com.jinpinghu.logic.enterprise.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrRemoveEnterpriseZoneParam extends BaseZLogicParam{

	public AddOrRemoveEnterpriseZoneParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private Integer enterpriseId;
	private String zoneIds;
	private String type;
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZoneIds() {
		return zoneIds;
	}
	public void setZoneIds(String zoneIds) {
		this.zoneIds = zoneIds;
	}

}
