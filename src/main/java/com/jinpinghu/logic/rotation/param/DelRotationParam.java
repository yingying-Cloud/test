package com.jinpinghu.logic.rotation.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class DelRotationParam extends BaseZLogicParam {

	public DelRotationParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getRotationIds() {
		return rotationIds;
	}
	public void setRotationIds(String rotationIds) {
		this.rotationIds = rotationIds;
	}
	private String rotationIds;

}
