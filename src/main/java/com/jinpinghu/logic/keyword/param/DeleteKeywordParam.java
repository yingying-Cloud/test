package com.jinpinghu.logic.keyword.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class DeleteKeywordParam extends BaseZLogicParam {

	public DeleteKeywordParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String keyworkIds;

	public String getKeyworkIds() {
		return keyworkIds;
	}

	public void setKeyworkIds(String keyworkIds) {
		this.keyworkIds = keyworkIds;
	}

}
