package com.jinpinghu.logic.brandOrder.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class DetailBrandOrderParam extends BaseZLogicParam{

	public DetailBrandOrderParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTrademarkId() {
		return trademarkId;
	}
	public void setTrademarkId(String trademarkId) {
		this.trademarkId = trademarkId;
	}
	private String id;
	private String trademarkId;
}
