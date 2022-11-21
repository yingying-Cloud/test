package com.jinpinghu.logic.linkOrderInfo.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetLinkOrderInfoByPicParam extends BaseZLogicParam{

	public GetLinkOrderInfoByPicParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	private String pic;

}
