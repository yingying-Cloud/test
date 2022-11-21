package com.jinpinghu.logic.offlineStore.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;


public class PayParam extends BaseZLogicParam {

	public PayParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String enterpriseId;
	private String toolIds;
	private String accountNums;
	private String prices;
	private Integer payType;
	private String orderTime;
	private String resultAmount;
	private String code;
	private String idcard;
	private String name;
	private String nation;
	private String sex;
	private String address;
	private String pic;
	private String finalRatioFees;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getToolIds() {
		return toolIds;
	}

	public void setToolIds(String toolIds) {
		this.toolIds = toolIds;
	}

	public String getAccountNums() {
		return accountNums;
	}

	public void setAccountNums(String accountNums) {
		this.accountNums = accountNums;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(String resultAmount) {
		this.resultAmount = resultAmount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getFinalRatioFees() {
		return finalRatioFees;
	}

	public void setFinalRatioFees(String finalRatioFees) {
		this.finalRatioFees = finalRatioFees;
	}

	

}
