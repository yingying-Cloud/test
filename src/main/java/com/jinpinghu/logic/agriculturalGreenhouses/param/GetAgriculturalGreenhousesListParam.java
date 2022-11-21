package com.jinpinghu.logic.agriculturalGreenhouses.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetAgriculturalGreenhousesListParam extends BaseZLogicParam {

	public GetAgriculturalGreenhousesListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String enterpriseId;
	private String greenhousesName;
	private String type;
	private String nowPage;
	private String pageCount;
	public String getGreenhousesName() {
		return greenhousesName;
	}
	public void setGreenhousesName(String greenhousesName) {
		this.greenhousesName = greenhousesName;
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
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
