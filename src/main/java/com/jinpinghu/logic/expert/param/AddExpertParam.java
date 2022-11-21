package com.jinpinghu.logic.expert.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddExpertParam extends BaseZLogicParam{
	public AddExpertParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO �Զ����ɵĹ��캯�����
	}
	private String id;
	private String address;
	private String goodsField;
	private String idcard;
	private String status;
	private String synopsis;
	private String file;
	private String userTabId;
	private String type;
	private String cost;
	private String adnm;
	private String productTeam;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGoodsField() {
		return goodsField;
	}
	public void setGoodsField(String goodsField) {
		this.goodsField = goodsField;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getUserTabId() {
		return userTabId;
	}
	public void setUserTabId(String userTabId) {
		this.userTabId = userTabId;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAdnm() {
		return adnm;
	}
	public void setAdnm(String adnm) {
		this.adnm = adnm;
	}
	public String getProductTeam() {
		return productTeam;
	}
	public void setProductTeam(String productTeam) {
		this.productTeam = productTeam;
	}
	
}
