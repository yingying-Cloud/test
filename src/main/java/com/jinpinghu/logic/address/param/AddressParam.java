package com.jinpinghu.logic.address.param;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddressParam extends BaseZLogicParam{

	public AddressParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	private Integer id;
	private String linkPeople;
	private String linkMobile;
	private String province;
	private String city;
	private String county;
	private String address;
	private Integer listIndex;
	
	public Integer getListIndex() {
		return listIndex;
	}
	public void setListIndex(Integer listIndex) {
		this.listIndex = listIndex;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkPeople() {
		return linkPeople;
	}
	public void setLinkPeople(String linkPeople) {
		this.linkPeople = linkPeople;
	}
	public String getLinkMobile() {
		return linkMobile;
	}
	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
