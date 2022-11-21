package com.jinpinghu.logic.toolRecord.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddToolRecordParam extends BaseZLogicParam{

	public AddToolRecordParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存�?
	}
	private String id;
	private String fromEnterpriseId;
	private String outEnterpriseId;
	private String toolJa;
	private String useName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFromEnterpriseId() {
		return fromEnterpriseId;
	}
	public void setFromEnterpriseId(String fromEnterpriseId) {
		this.fromEnterpriseId = fromEnterpriseId;
	}
	public String getToolJa() {
		return toolJa;
	}
	public void setToolJa(String toolJa) {
		this.toolJa = toolJa;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getOutEnterpriseId() {
		return outEnterpriseId;
	}
	public void setOutEnterpriseId(String outEnterpriseId) {
		this.outEnterpriseId = outEnterpriseId;
	}
}
