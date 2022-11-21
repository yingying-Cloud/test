package com.jinpinghu.logic.toolRecoveryRecord.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateToolRecoveryRecordParam extends BaseZLogicParam{

	public AddOrUpdateToolRecoveryRecordParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存�?
	}
	private String id;
	private String enterpriseId;
	private String toolRecoveryId;
	private String recordType;
	private String number;
	private String file;
	private String useName;
	private String inputTime;
	private String operator;
	private String useMobile;
	private String linkOrderInfoId;
	private String toolId;
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
	public String getToolRecoveryId() {
		return toolRecoveryId;
	}
	public void setToolRecoveryId(String toolRecoveryId) {
		this.toolRecoveryId = toolRecoveryId;
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
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getUseMobile() {
		return useMobile;
	}
	public void setUseMobile(String useMobile) {
		this.useMobile = useMobile;
	}
	public String getLinkOrderInfoId() {
		return linkOrderInfoId;
	}
	public void setLinkOrderInfoId(String linkOrderInfoId) {
		this.linkOrderInfoId = linkOrderInfoId;
	}
	public String getToolId() {
		return toolId;
	}
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
}
