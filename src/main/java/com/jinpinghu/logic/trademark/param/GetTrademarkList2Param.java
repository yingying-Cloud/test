package com.jinpinghu.logic.trademark.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetTrademarkList2Param extends BaseZLogicParam{

	public GetTrademarkList2Param(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}
	private String brandId;
	private String trademarkId;
	private String name ;
	private String productName;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTrademarkId() {
		return trademarkId;
	}
	public void setTrademarkId(String trademarkId) {
		this.trademarkId = trademarkId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
}
