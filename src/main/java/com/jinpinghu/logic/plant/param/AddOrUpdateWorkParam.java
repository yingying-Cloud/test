package com.jinpinghu.logic.plant.param;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateWorkParam extends BaseZLogicParam{

	public AddOrUpdateWorkParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	private String id;
	private String enterpriseId;
	private String workName;
	private String workSn;
	private String landBlockSn;
	private String area;
	private String crop;
	private String startTime;
	private String endTime;
	private String recoveryTime;
	private String expectedProduction;
	private String json;
	private String purchaseSource;
	private String purchasePeople;
	private String purchaseTime;
	private String greenhousesId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getWorkSn() {
		return workSn;
	}
	public void setWorkSn(String workSn) {
		this.workSn = workSn;
	}
	public String getLandBlockSn() {
		return landBlockSn;
	}
	public void setLandBlockSn(String landBlockSn) {
		this.landBlockSn = landBlockSn;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCrop() {
		return crop;
	}
	public void setCrop(String crop) {
		this.crop = crop;
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
	public String getRecoveryTime() {
		return recoveryTime;
	}
	public void setRecoveryTime(String recoveryTime) {
		this.recoveryTime = recoveryTime;
	}
	public String getExpectedProduction() {
		return expectedProduction;
	}
	public void setExpectedProduction(String expectedProduction) {
		this.expectedProduction = expectedProduction;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getPurchaseSource() {
		return purchaseSource;
	}
	public void setPurchaseSource(String purchaseSource) {
		this.purchaseSource = purchaseSource;
	}
	public String getPurchasePeople() {
		return purchasePeople;
	}
	public void setPurchasePeople(String purchasePeople) {
		this.purchasePeople = purchasePeople;
	}
	public String getPurchaseTime() {
		return purchaseTime;
	}
	public void setPurchaseTime(String purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	public String getGreenhousesId() {
		return greenhousesId;
	}
	public void setGreenhousesId(String greenhousesId) {
		this.greenhousesId = greenhousesId;
	}
	
}
