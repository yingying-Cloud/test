package com.jinpinghu.logic.warehouse.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetPlantWarehouseInfoParam extends BaseZLogicParam{

	public GetPlantWarehouseInfoParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String id;

}