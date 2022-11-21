package com.jinpinghu.logic.cashRegister.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateCashRegisterParam extends BaseZLogicParam{

	public AddOrUpdateCashRegisterParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private Integer id;
	private String cashRegisterId;
	private String baiduAifaceSn;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCashRegisterId() {
		return cashRegisterId;
	}
	public void setCashRegisterId(String cashRegisterId) {
		this.cashRegisterId = cashRegisterId;
	}
	public String getBaiduAifaceSn() {
		return baiduAifaceSn;
	}
	public void setBaiduAifaceSn(String baiduAifaceSn) {
		this.baiduAifaceSn = baiduAifaceSn;
	}
	

}
