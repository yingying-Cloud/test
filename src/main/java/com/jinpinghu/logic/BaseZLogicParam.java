package com.jinpinghu.logic;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fw.jbiz.logic.ZLogicParam;

public class BaseZLogicParam extends ZLogicParam {
	
	//dump时要隐藏的字段
	private static List<String> mMaskNames = new ArrayList<String>();
	
	static {
		mMaskNames.add("password");
	}
	
	@Override
	public List<String> getMaskNamesForDump(){
		return mMaskNames;
	}
	
	public BaseZLogicParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}
	
	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
