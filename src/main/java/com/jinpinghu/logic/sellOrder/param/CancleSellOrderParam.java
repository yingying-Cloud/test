package com.jinpinghu.logic.sellOrder.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class CancleSellOrderParam extends BaseZLogicParam{

	public CancleSellOrderParam(String _userId, String _apiKey,
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

	public String getCancleInfo() {
		return cancleInfo;
	}
	public void setCancleInfo(String cancleInfo) {
		this.cancleInfo = cancleInfo;
	}

	private String id;
	private String cancleInfo;
}
