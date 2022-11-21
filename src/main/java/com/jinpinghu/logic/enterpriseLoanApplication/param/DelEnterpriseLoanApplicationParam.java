package com.jinpinghu.logic.enterpriseLoanApplication.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class DelEnterpriseLoanApplicationParam extends BaseZLogicParam{

	public DelEnterpriseLoanApplicationParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
