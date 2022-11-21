package com.jinpinghu.logic.goodsGroup.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetGroupGoodsInfoParam extends BaseZLogicParam{

	public GetGroupGoodsInfoParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsIn() {
		return isIn;
	}
	public void setIsIn(String isIn) {
		this.isIn = isIn;
	}
	private String id;
	private String startTime;
	private String endTime;
	private String isIn;

}
