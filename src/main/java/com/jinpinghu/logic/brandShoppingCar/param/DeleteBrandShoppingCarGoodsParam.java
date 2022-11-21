package com.jinpinghu.logic.brandShoppingCar.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class DeleteBrandShoppingCarGoodsParam extends BaseZLogicParam {

	public DeleteBrandShoppingCarGoodsParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String shoppingCarIds;

	public String getBrandShoppingCarIds() {
		return shoppingCarIds;
	}

	public void setBrandShoppingCarIds(String shoppingCarIds) {
		this.shoppingCarIds = shoppingCarIds;
	}

}
