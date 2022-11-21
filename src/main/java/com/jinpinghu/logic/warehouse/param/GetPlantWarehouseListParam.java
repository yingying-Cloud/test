package com.jinpinghu.logic.warehouse.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetPlantWarehouseListParam extends BaseZLogicParam{

	public GetPlantWarehouseListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}

	private String plantName;
	private String recordType;
	private String enterpriseId;
	private String endTime;
	private String startTime;
	private String nowPage;
	private String pageCount;
	private String workSn;
	
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getWarehouseType() {
		return recordType;
	}
	public void setWarehouseType(String recordType) {
		this.recordType = recordType;
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
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getWorkSn() {
		return workSn;
	}
	public void setWorkSn(String workSn) {
		this.workSn = workSn;
	}
}