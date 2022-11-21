package com.jinpinghu.logic.enterpriseLoanApplication.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class GetEnterpriseLoanApplicationInfoParam extends BaseZLogicParam{
	public GetEnterpriseLoanApplicationInfoParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO �Զ����ɵĹ��캯�����
	}
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
