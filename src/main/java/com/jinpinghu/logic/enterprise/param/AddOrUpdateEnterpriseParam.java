package com.jinpinghu.logic.enterprise.param;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateEnterpriseParam extends BaseZLogicParam{

	public AddOrUpdateEnterpriseParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}

	private String id;
	private String name;
	private String enterpriseCreditCode;
	private String enterpriseLegalPerson;
	private String enterpriseLegalPersonIdcard;
	private String enterpriseLinkPeople;
	private String enterpriseLinkMobile;
	private String enterpriseAddress;
	private String plantScope;
	private String x;
	private String y;
	private String enterpriseType;
	private String baseAddress;
	private String plantName;
	private String dscd;
	private String file;
	private String registeredFunds;
	private String changes;
	private String enterpriseNature;
	private String brand;
	private String type;
	private String businessScope;
	private String permitForoperationNum;
	private String operationMode;
	
	
	
	public String getOperationMode() {
		return operationMode;
	}
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	
	
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getPermitForoperationNum() {
		return permitForoperationNum;
	}
	public void setPermitForoperationNum(String permitForoperationNum) {
		this.permitForoperationNum = permitForoperationNum;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnterpriseType() {
		return enterpriseType;
	}
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnterpriseCreditCode() {
		return enterpriseCreditCode;
	}
	public void setEnterpriseCreditCode(String enterpriseCreditCode) {
		this.enterpriseCreditCode = enterpriseCreditCode;
	}
	public String getEnterpriseLegalPerson() {
		return enterpriseLegalPerson;
	}
	public void setEnterpriseLegalPerson(String enterpriseLegalPerson) {
		this.enterpriseLegalPerson = enterpriseLegalPerson;
	}
	public String getEnterpriseLegalPersonIdcard() {
		return enterpriseLegalPersonIdcard;
	}
	public void setEnterpriseLegalPersonIdcard(String enterpriseLegalPersonIdcard) {
		this.enterpriseLegalPersonIdcard = enterpriseLegalPersonIdcard;
	}
	public String getEnterpriseLinkPeople() {
		return enterpriseLinkPeople;
	}
	public void setEnterpriseLinkPeople(String enterpriseLinkPeople) {
		this.enterpriseLinkPeople = enterpriseLinkPeople;
	}
	public String getEnterpriseLinkMobile() {
		return enterpriseLinkMobile;
	}
	public void setEnterpriseLinkMobile(String enterpriseLinkMobile) {
		this.enterpriseLinkMobile = enterpriseLinkMobile;
	}
	public String getEnterpriseAddress() {
		return enterpriseAddress;
	}
	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}
	public String getPlantScope() {
		return plantScope;
	}
	public void setPlantScope(String plantScope) {
		this.plantScope = plantScope;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getBaseAddress() {
		return baseAddress;
	}
	public void setBaseAddress(String baseAddress) {
		this.baseAddress = baseAddress;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getDscd() {
		return dscd;
	}
	public void setDscd(String dscd) {
		this.dscd = dscd;
	}

	public String getRegisteredFunds() {
		return registeredFunds;
	}

	public void setRegisteredFunds(String registeredFunds) {
		this.registeredFunds = registeredFunds;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public String getEnterpriseNature() {
		return enterpriseNature;
	}

	public void setEnterpriseNature(String enterpriseNature) {
		this.enterpriseNature = enterpriseNature;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
