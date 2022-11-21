package com.jinpinghu.logic.userProduction.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddEnterpriseUserProductionParam extends BaseZLogicParam{

	public AddEnterpriseUserProductionParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	
	private String enterpriseId;
	private String dscd;
	private String village;
	private String name;
	private String area;
	private String confirmArea;
	private String inflowArea;
	private String outflowArea;
	private String userJa;
	private String enterpriseType;
	private String nmLimitAmount;
	private String nyLimitAmount;
	public String getNmLimitAmount() {
		return nmLimitAmount;
	}
	public void setNmLimitAmount(String nmLimitAmount) {
		this.nmLimitAmount = nmLimitAmount;
	}
	public String getNyLimitAmount() {
		return nyLimitAmount;
	}
	public void setNyLimitAmount(String nyLimitAmount) {
		this.nyLimitAmount = nyLimitAmount;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getConfirmArea() {
		return confirmArea;
	}
	public void setConfirmArea(String confirmArea) {
		this.confirmArea = confirmArea;
	}
	public String getInflowArea() {
		return inflowArea;
	}
	public void setInflowArea(String inflowArea) {
		this.inflowArea = inflowArea;
	}
	public String getOutflowArea() {
		return outflowArea;
	}
	public void setOutflowArea(String outflowArea) {
		this.outflowArea = outflowArea;
	}
	public String getUserJa() {
		return userJa;
	}
	public void setUserJa(String userJa) {
		this.userJa = userJa;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getDscd() {
		return dscd;
	}
	public void setDscd(String dscd) {
		this.dscd = dscd;
	}
	public String getEnterpriseType() {
		return enterpriseType;
	}
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}


}
