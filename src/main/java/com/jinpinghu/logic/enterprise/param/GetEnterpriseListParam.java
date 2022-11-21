package com.jinpinghu.logic.enterprise.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetEnterpriseListParam extends BaseZLogicParam{
	public GetEnterpriseListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO �Զ����ɵĹ��캯�����
	}
	private String name;
	private String dscd;
	private String enterpriseCreditCode;
	private String enterpriseLegalPerson;
	private String enterpriseLegalPersonIdcard;
	private String enterpriseLinkPeople;
	private String enterpriseLinkMobile;
	private String enterpriseAddress;
	private String enterpriseType;
	private String status;
	private String nowPage;
	private String pageCount;
	private String selectAll;
	private String state;
	private String userName;
	private String userIdCard;
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
	public String getEnterpriseType() {
		return enterpriseType;
	}
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserIdCard() {
		return userIdCard;
	}
	public void setUserIdCard(String userIdCard) {
		this.userIdCard = userIdCard;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
