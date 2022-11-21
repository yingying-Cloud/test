package com.jinpinghu.logic.shoppingCar.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class DeleteShoppingCarGoodsParam extends BaseZLogicParam {

	public DeleteShoppingCarGoodsParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String shoppingCarIds;

	public String getShoppingCarIds() {
		return shoppingCarIds;
	}

	public void setShoppingCarIds(String shoppingCarIds) {
		this.shoppingCarIds = shoppingCarIds;
	}

}
