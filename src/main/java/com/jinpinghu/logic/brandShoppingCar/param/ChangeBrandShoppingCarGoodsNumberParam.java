package com.jinpinghu.logic.brandShoppingCar.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class ChangeBrandShoppingCarGoodsNumberParam extends BaseZLogicParam {

	public ChangeBrandShoppingCarGoodsNumberParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String shoppingCarId;
	private Integer number;
	private String shopId;

	public String getBrandShoppingCarId() {
		return shoppingCarId;
	}

	public void setBrandShoppingCarId(String shoppingCarId) {
		this.shoppingCarId = shoppingCarId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

}
