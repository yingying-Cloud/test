package com.jinpinghu.logic.plantProtectionOrder.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddPlantProtectionOrderParam extends BaseZLogicParam{

	public AddPlantProtectionOrderParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private String plantProtectionId;
	private String area;
	private String name;
	private String address;
	private String contact;
	private String phone;
	private String serviceStartTime;
	private String serviceEndTime;
	public String getServiceStartTime() {
		return serviceStartTime;
	}
	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}
	public String getServiceEndTime() {
		return serviceEndTime;
	}
	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}
	public String getPlantProtectionId() {
		return plantProtectionId;
	}
	public void setPlantProtectionId(String plantProtectionId) {
		this.plantProtectionId = plantProtectionId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
