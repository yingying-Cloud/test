package com.jinpinghu.logic.agriculturalGreenhouses.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class DelAgriculturalGreenhousesParam extends BaseZLogicParam {

	public DelAgriculturalGreenhousesParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	private String ids;

}
