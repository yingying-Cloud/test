package com.jinpinghu.logic.enterprise.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class UpdateEnterpriseParam extends BaseZLogicParam{
	public UpdateEnterpriseParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO �Զ����ɵĹ��캯�����
	}
	private Integer id;
	private String name;
	private String enterpriseCreditCode;
	private String enterpriseLegalPerson;
	private String enterpriseLegalPersonIdcard;
	private String enterpriseLinkPeople;
	private String enterpriseLinkMobile;
	private String enterpriseAddress;
	private String enterpriseType;
	private String file;
	private String dscd;
	private String registeredFunds;
	private String changes;
	private String enterpriseNature;
	private String brand;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEnterpriseType() {
		return enterpriseType;
	}
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
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
}
