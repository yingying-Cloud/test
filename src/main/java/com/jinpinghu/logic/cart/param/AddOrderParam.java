package com.jinpinghu.logic.cart.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrderParam extends BaseZLogicParam {

	public AddOrderParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}

	private String wholesalersId;
	private String goodsArray;
	private String takeSelf;
	private String shareUserId;
	private String nt;
	private String deliveryMethod;
	private Integer receivePointId;
	private String linkInfoId;
	private String couponsIds;
	private Integer exchangeId;
	private String cartJson;
	private Integer addressId;
	

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getCartJson() {
		return cartJson;
	}

	public void setCartJson(String cartJson) {
		this.cartJson = cartJson;
	}

	public String getWholesalersId() {
		return wholesalersId;
	}

	public void setWholesalersId(String wholesalersId) {
		this.wholesalersId = wholesalersId;
	}

	public String getGoodsArray() {
		return goodsArray;
	}

	public void setGoodsArray(String goodsArray) {
		this.goodsArray = goodsArray;
	}


	public String getTakeSelf() {
		return takeSelf;
	}

	public void setTakeSelf(String takeSelf) {
		this.takeSelf = takeSelf;
	}

	public String getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}

	public String getNt() {
		return nt;
	}

	public void setNt(String nt) {
		this.nt = nt;
	}

	public Integer getReceivePointId() {
		return receivePointId;
	}

	public void setReceivePointId(Integer receivePointId) {
		this.receivePointId = receivePointId;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getLinkInfoId() {
		return linkInfoId;
	}

	public void setLinkInfoId(String linkInfoId) {
		this.linkInfoId = linkInfoId;
	}

	public String getCouponsIds() {
		return couponsIds;
	}

	public void setCouponsIds(String couponsIds) {
		this.couponsIds = couponsIds;
	}

	public Integer getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}

}
