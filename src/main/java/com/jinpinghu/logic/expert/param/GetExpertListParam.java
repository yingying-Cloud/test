package com.jinpinghu.logic.expert.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetExpertListParam extends BaseZLogicParam{
	public GetExpertListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO �Զ����ɵĹ��캯�����
	}
	private String name;
	private String status;
	private String type;
	private String lowIntegral;
	private String highIntegral;
	private String adnm;
	private String productTeam;
	private String nowPage;
	private String pageCount;
	private String orderby;
	private String sortDirection;
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getNowPage() {
		return nowPage;
	}
	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLowIntegral() {
		return lowIntegral;
	}
	public void setLowIntegral(String lowIntegral) {
		this.lowIntegral = lowIntegral;
	}
	public String getHighIntegral() {
		return highIntegral;
	}
	public void setHighIntegral(String highIntegral) {
		this.highIntegral = highIntegral;
	}
	public String getAdnm() {
		return adnm;
	}
	public void setAdnm(String adnm) {
		this.adnm = adnm;
	}
	public String getProductTeam() {
		return productTeam;
	}
	public void setProductTeam(String productTeam) {
		this.productTeam = productTeam;
	}
}
