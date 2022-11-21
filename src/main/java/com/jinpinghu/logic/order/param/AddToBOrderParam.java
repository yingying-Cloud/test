package com.jinpinghu.logic.order.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddToBOrderParam extends BaseZLogicParam {

	public AddToBOrderParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private String sellEnterpriseId;
	private String buyEnterpriseId;
	private String orderInfo;
	private String idcard;
	private String name;
	private String sex;
	private String nation;
	private String address;
	private String price;
	private String phone;
	public String getSellEnterpriseId() {
		return sellEnterpriseId;
	}
	public void setSellEnterpriseId(String sellEnterpriseId) {
		this.sellEnterpriseId = sellEnterpriseId;
	}
	public String getBuyEnterpriseId() {
		return buyEnterpriseId;
	}
	public void setBuyEnterpriseId(String buyEnterpriseId) {
		this.buyEnterpriseId = buyEnterpriseId;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
