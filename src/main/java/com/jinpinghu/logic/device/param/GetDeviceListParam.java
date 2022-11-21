package com.jinpinghu.logic.device.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetDeviceListParam extends BaseZLogicParam{

	public GetDeviceListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}

	private String baseId;
	private String deviceName;
	private Integer nowPage;
	private Integer pageCount;
	private String greenhousesName;
	private String greenhousesId;
	private Integer classify;
	
	
	public String getGreenhousesName() {
		return greenhousesName;
	}
	public void setGreenhousesName(String greenhousesName) {
		this.greenhousesName = greenhousesName;
	}
	public Integer getNowPage() {
		return nowPage;
	}
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getGreenhousesId() {
		return greenhousesId;
	}
	public void setGreenhousesId(String greenhousesId) {
		this.greenhousesId = greenhousesId;
	}
	public Integer getClassify() {
		return classify;
	}
	public void setClassify(Integer classify) {
		this.classify = classify;
	}
	
}
