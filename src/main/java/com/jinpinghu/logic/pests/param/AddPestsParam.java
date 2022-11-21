package com.jinpinghu.logic.pests.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddPestsParam extends BaseZLogicParam {

	public AddPestsParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String id;
	private String type;
	private String title;
	private String features;
	private String happen;
	private String agriculturalControl;
	private String greenControl;
	private String organicControl;
	private String allControl;
	private String greenControlMedicine;
	private String organicControlMedicine;
	private String allControlMedicine;
	private String file;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public String getHappen() {
		return happen;
	}
	public void setHappen(String happen) {
		this.happen = happen;
	}
	public String getAgriculturalControl() {
		return agriculturalControl;
	}
	public void setAgriculturalControl(String agriculturalControl) {
		this.agriculturalControl = agriculturalControl;
	}
	public String getGreenControl() {
		return greenControl;
	}
	public void setGreenControl(String greenControl) {
		this.greenControl = greenControl;
	}
	public String getOrganicControl() {
		return organicControl;
	}
	public void setOrganicControl(String organicControl) {
		this.organicControl = organicControl;
	}
	public String getAllControl() {
		return allControl;
	}
	public void setAllControl(String allControl) {
		this.allControl = allControl;
	}
	public String getGreenControlMedicine() {
		return greenControlMedicine;
	}
	public void setGreenControlMedicine(String greenControlMedicine) {
		this.greenControlMedicine = greenControlMedicine;
	}
	public String getOrganicControlMedicine() {
		return organicControlMedicine;
	}
	public void setOrganicControlMedicine(String organicControlMedicine) {
		this.organicControlMedicine = organicControlMedicine;
	}
	public String getAllControlMedicine() {
		return allControlMedicine;
	}
	public void setAllControlMedicine(String allControlMedicine) {
		this.allControlMedicine = allControlMedicine;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
}
