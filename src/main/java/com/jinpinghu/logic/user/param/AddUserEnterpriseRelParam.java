package com.jinpinghu.logic.user.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddUserEnterpriseRelParam extends BaseZLogicParam {

	public AddUserEnterpriseRelParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	
	public Integer getUserTabId() {
		return userTabId;
	}
	public void setUserTabId(Integer userTabId) {
		this.userTabId = userTabId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	private Integer id;
	private Integer userTabId;
	private String enterpriseId;

}
