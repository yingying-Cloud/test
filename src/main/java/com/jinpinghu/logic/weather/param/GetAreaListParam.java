package com.jinpinghu.logic.weather.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetAreaListParam extends BaseZLogicParam{

	public GetAreaListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	
	public String getDscd() {
		return dscd;
	}

	public void setDscd(String dscd) {
		this.dscd = dscd;
	}

	public String getNeedSelf() {
		return needSelf;
	}

	public void setNeedSelf(String needSelf) {
		this.needSelf = needSelf;
	}

	private String dscd;
	private String needSelf;

}
