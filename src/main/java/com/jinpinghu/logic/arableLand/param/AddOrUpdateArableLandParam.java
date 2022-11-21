package com.jinpinghu.logic.arableLand.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateArableLandParam extends BaseZLogicParam {

	public AddOrUpdateArableLandParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String id;
	private String villageGroup;
	private String userName;
	private String idCard;
	private String area;
	private String riceArea;
	private String wheatArea;
	private String vegetablesArea;
	private String fruitsArea;
	private String otherArea;
	private String remark;
	private String town;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVillageGroup() {
		return villageGroup;
	}
	public void setVillageGroup(String villageGroup) {
		this.villageGroup = villageGroup;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRiceArea() {
		return riceArea;
	}
	public void setRiceArea(String riceArea) {
		this.riceArea = riceArea;
	}
	public String getWheatArea() {
		return wheatArea;
	}
	public void setWheatArea(String wheatArea) {
		this.wheatArea = wheatArea;
	}
	public String getVegetablesArea() {
		return vegetablesArea;
	}
	public void setVegetablesArea(String vegetablesArea) {
		this.vegetablesArea = vegetablesArea;
	}
	public String getFruitsArea() {
		return fruitsArea;
	}
	public void setFruitsArea(String fruitsArea) {
		this.fruitsArea = fruitsArea;
	}
	public String getOtherArea() {
		return otherArea;
	}
	public void setOtherArea(String otherArea) {
		this.otherArea = otherArea;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
}
