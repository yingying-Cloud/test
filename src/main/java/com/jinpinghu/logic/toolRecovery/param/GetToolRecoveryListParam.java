package com.jinpinghu.logic.toolRecovery.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetToolRecoveryListParam extends BaseZLogicParam{

	public GetToolRecoveryListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}
	private String name ;
	private String startTime;
	private String endTime;
	private String enterpriseId;
	private String dscd;
	private String nowPage;
	private String pageCount;
	private String selectAll;
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
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDscd() {
		return dscd;
	}
	public void setDscd(String dscd) {
		this.dscd = dscd;
	}
	public String getSelectAll() {
		return selectAll;
	}
	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
	}
	
}
