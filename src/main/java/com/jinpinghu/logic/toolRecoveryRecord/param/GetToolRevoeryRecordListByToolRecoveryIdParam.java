package com.jinpinghu.logic.toolRecoveryRecord.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetToolRevoeryRecordListByToolRecoveryIdParam extends BaseZLogicParam{

	public GetToolRevoeryRecordListByToolRecoveryIdParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private String recoveryUnit;
	private String sellUnit;
	private String startTime;
	private String endTime;
	private Integer toolRecoveryId;
	private Integer nowPage;
	private Integer pageCount;
	private Integer enterpriseId;
	public String getRecoveryUnit() {
		return recoveryUnit;
	}
	public void setRecoveryUnit(String recoveryUnit) {
		this.recoveryUnit = recoveryUnit;
	}
	public String getSellUnit() {
		return sellUnit;
	}
	public void setSellUnit(String sellUnit) {
		this.sellUnit = sellUnit;
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
	public Integer getToolRecoveryId() {
		return toolRecoveryId;
	}
	public void setToolRecoveryId(Integer toolRecoveryId) {
		this.toolRecoveryId = toolRecoveryId;
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
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}
