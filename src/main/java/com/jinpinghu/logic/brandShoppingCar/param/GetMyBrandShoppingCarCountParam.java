package com.jinpinghu.logic.brandShoppingCar.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetMyBrandShoppingCarCountParam extends BaseZLogicParam {

	public GetMyBrandShoppingCarCountParam(String _userId, String _apiKey,
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
