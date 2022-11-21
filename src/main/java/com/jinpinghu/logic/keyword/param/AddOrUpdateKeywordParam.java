package com.jinpinghu.logic.keyword.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateKeywordParam extends BaseZLogicParam {

	public AddOrUpdateKeywordParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private Integer keyworkId;
	private String name;

	public Integer getKeyworkId() {
		return keyworkId;
	}

	public void setKeyworkId(Integer keyworkId) {
		this.keyworkId = keyworkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
