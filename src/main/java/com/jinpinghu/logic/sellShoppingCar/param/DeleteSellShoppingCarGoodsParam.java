package com.jinpinghu.logic.sellShoppingCar.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class DeleteSellShoppingCarGoodsParam extends BaseZLogicParam {

	public DeleteSellShoppingCarGoodsParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String shoppingCarIds;

	public String getSellShoppingCarIds() {
		return shoppingCarIds;
	}

	public void setSellShoppingCarIds(String shoppingCarIds) {
		this.shoppingCarIds = shoppingCarIds;
	}

}
