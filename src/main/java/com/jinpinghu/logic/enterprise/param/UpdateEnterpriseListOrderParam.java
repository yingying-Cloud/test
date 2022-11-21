package com.jinpinghu.logic.enterprise.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class UpdateEnterpriseListOrderParam extends BaseZLogicParam{

	public UpdateEnterpriseListOrderParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getListOrderJa() {
		return listOrderJa;
	}
	public void setListOrderJa(String listOrderJa) {
		this.listOrderJa = listOrderJa;
	}
	private String listOrderJa;
}
