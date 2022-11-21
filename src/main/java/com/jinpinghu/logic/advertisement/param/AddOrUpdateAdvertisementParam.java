package com.jinpinghu.logic.advertisement.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateAdvertisementParam extends BaseZLogicParam{

	public AddOrUpdateAdvertisementParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String id;
	private String title;
	private String fileUrls;
	private String visible;
	private String type;
	private String startTime;
	private String endTime;
	private String enterpriseJson;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileUrls() {
		return fileUrls;
	}
	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getEnterpriseJson() {
		return enterpriseJson;
	}
	public void setEnterpriseJson(String enterpriseJson) {
		this.enterpriseJson = enterpriseJson;
	}
	

}
