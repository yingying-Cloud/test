package com.jinpinghu.logic.linkOrderInfo.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetLinkOrderInfoByIdcardParam extends BaseZLogicParam {

	public GetLinkOrderInfoByIdcardParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	private String idcard;

}
