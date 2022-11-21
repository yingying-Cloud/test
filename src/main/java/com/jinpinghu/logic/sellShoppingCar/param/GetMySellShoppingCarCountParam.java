package com.jinpinghu.logic.sellShoppingCar.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetMySellShoppingCarCountParam extends BaseZLogicParam {

	public GetMySellShoppingCarCountParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	private String enterpriseId;


}
