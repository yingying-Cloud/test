package com.jinpinghu.logic.brandShoppingCar.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddToBrandShoppingCarParam extends BaseZLogicParam {

	public AddToBrandShoppingCarParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String enterpriseId;
	private String num;
	private String brandId;
	private String type;
	private String brandSaleId;
	private String sellId;
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrandSaleId() {
		return brandSaleId;
	}
	public void setBrandSaleId(String brandSaleId) {
		this.brandSaleId = brandSaleId;
	}
	public String getSellId() {
		return sellId;
	}
	public void setSellId(String sellId) {
		this.sellId = sellId;
	}



}
