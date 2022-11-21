package com.jinpinghu.logic.sellBrandRecord.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateSellBrandRecordParam extends BaseZLogicParam{

	public AddOrUpdateSellBrandRecordParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存�?
	}
	private String id;
	private String enterpriseId;
	private String sellBrandId;
	private String recordType;
	private String number;
	private String file;
	private String useName;
	private String useTime;
	private String outName;
	private String outMobile;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getOutName() {
		return outName;
	}
	public void setOutName(String outName) {
		this.outName = outName;
	}
	public String getOutMobile() {
		return outMobile;
	}
	public void setOutMobile(String outMobile) {
		this.outMobile = outMobile;
	}
	public String getSellBrandId() {
		return sellBrandId;
	}
	public void setSellBrandId(String sellBrandId) {
		this.sellBrandId = sellBrandId;
	}
}
