package com.jinpinghu.logic.serverTime.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetPlantProtectionListParam extends BaseZLogicParam{

	public GetPlantProtectionListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String protectionId;
	private String nowPage;
	private String pageCount;
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
	public String getProtectionId() {
		return protectionId;
	}
	public void setProtectionId(String protectionId) {
		this.protectionId = protectionId;
	}
}
