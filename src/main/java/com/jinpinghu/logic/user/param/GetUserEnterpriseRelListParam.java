package com.jinpinghu.logic.user.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetUserEnterpriseRelListParam extends BaseZLogicParam {

	public GetUserEnterpriseRelListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	
	public String getUserTabId() {
		return userTabId;
	}
	public void setUserTabId(String userTabId) {
		this.userTabId = userTabId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getNowPage() {
		return nowPage;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getIsIn() {
		return isIn;
	}

	public void setIsIn(String isIn) {
		this.isIn = isIn;
	}
	public String getDscd() {
		return dscd;
	}

	public void setDscd(String dscd) {
		this.dscd = dscd;
	}
	private String dscd;
	private String isIn;
	private String name;
	private String userTabId;
	private String enterpriseId;
	private String nowPage;
	private String pageCount;
}
