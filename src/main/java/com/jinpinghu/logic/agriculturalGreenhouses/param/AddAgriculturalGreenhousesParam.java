package com.jinpinghu.logic.agriculturalGreenhouses.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddAgriculturalGreenhousesParam extends BaseZLogicParam {

	public AddAgriculturalGreenhousesParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private Integer enterpriseId;
	private String greenhousesName;
	private String area;
	private String center;
	private String mu;
	private String file;
	private String classify;
	public String getGreenhousesName() {
		return greenhousesName;
	}
	public void setGreenhousesName(String greenhousesName) {
		this.greenhousesName = greenhousesName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getMu() {
		return mu;
	}
	public void setMu(String mu) {
		this.mu = mu;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}
