package com.jinpinghu.logic.farmingWater.param;


import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateFarmingWaterParam extends BaseZLogicParam{

	public AddOrUpdateFarmingWaterParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	private String id;
	private String workId;
	private String enterpriseId;
	private String addPeople;
	private String json;
	private String inputTime;
	private String traffic;
	private String startIrrigationTime;
	private String waterAmount;
	private String endIrrigationTime;
	private String describe;
	private String file;
	
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getAddPeople() {
		return addPeople;
	}
	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}
	public String getTraffic() {
		return traffic;
	}
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	public String getStartIrrigationTime() {
		return startIrrigationTime;
	}
	public void setStartIrrigationTime(String startIrrigationTime) {
		this.startIrrigationTime = startIrrigationTime;
	}
	public String getWaterAmount() {
		return waterAmount;
	}
	public void setWaterAmount(String waterAmount) {
		this.waterAmount = waterAmount;
	}
	public String getEndIrrigationTime() {
		return endIrrigationTime;
	}
	public void setEndIrrigationTime(String endIrrigationTime) {
		this.endIrrigationTime = endIrrigationTime;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
}
