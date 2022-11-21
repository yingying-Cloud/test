package com.jinpinghu.logic.trademark.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetTrademarkListParam extends BaseZLogicParam{

	public GetTrademarkListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}
	private String name ;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
