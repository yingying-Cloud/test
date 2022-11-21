package com.jinpinghu.logic.rotation.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddRotationParam extends BaseZLogicParam{

	public AddRotationParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String rotationId;
	private String advice;
	private String enterprise;
	private String  tool;
	private String rotationTime;
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public String getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool = tool;
	}
	public String getRotationTime() {
		return rotationTime;
	}
	public void setRotationTime(String rotationTime) {
		this.rotationTime = rotationTime;
	}
	public String getRotationId() {
		return rotationId;
	}
	public void setRotationId(String rotationId) {
		this.rotationId = rotationId;
	}
	

}
