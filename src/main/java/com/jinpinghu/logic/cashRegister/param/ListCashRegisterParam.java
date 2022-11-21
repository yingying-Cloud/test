package com.jinpinghu.logic.cashRegister.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class ListCashRegisterParam extends BaseZLogicParam {

	public ListCashRegisterParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	
	private Integer nowPage;
	private Integer pageCount;
	private String name;
	private String cashRegisterId;
	private String baiduAifaceSn;
	public String getCashRegisterId() {
		return cashRegisterId;
	}
	public void setCashRegisterId(String cashRegisterId) {
		this.cashRegisterId = cashRegisterId;
	}
	public String getBaiduAifaceSn() {
		return baiduAifaceSn;
	}
	public void setBaiduAifaceSn(String baiduAifaceSn) {
		this.baiduAifaceSn = baiduAifaceSn;
	}
	public Integer getNowPage() {
		return nowPage;
	}
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
