package com.jinpinghu.logic.weChat.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetWeChatUserInfo2Param extends BaseZLogicParam {

	public GetWeChatUserInfo2Param(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String openId;
	private String accessToken;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
