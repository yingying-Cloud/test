package com.jinpinghu.logic.appUtil.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddAppUtilParam extends BaseZLogicParam{

	public AddAppUtilParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getUtilJa() {
		return utilJa;
	}
	public void setUtilJa(String utilJa) {
		this.utilJa = utilJa;
	}
	private String utilJa;

}
