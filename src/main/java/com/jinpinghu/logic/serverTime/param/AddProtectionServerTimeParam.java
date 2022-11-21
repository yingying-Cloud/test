package com.jinpinghu.logic.serverTime.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddProtectionServerTimeParam extends BaseZLogicParam{

	public AddProtectionServerTimeParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	
	public String getServerTime() {
		return serverTime;
	}
	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}

	public Integer getProtectionId() {
		return protectionId;
	}

	public void setProtectionId(Integer protectionId) {
		this.protectionId = protectionId;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	private Integer id;
	private String serverTime;
	private Integer protectionId; 

}
