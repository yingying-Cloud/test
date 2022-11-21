package com.jinpinghu.logic.order.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class ListAllOrderParam extends BaseZLogicParam{

	public ListAllOrderParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String enterpriseId;
	private String toolName;
	private String orderNumber;
	private String enterpriseName;
	private String beginCreateTime;
	private String endCreateTime;
	private String beginPayTime;
	private String endPayTime;
	private String status;
	private String check;
	private String name;
	private String isValidation;
	private String nowPage;
	private String pageCount;
	private String selectAll;
	private String uniformPrice;
	public String getNowPage() {
		return nowPage;
	}
	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}
	public String getPageCount() {
		return pageCount;
	}
	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getBeginCreateTime() {
		return beginCreateTime;
	}
	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	public String getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	public String getBeginPayTime() {
		return beginPayTime;
	}
	public void setBeginPayTime(String beginPayTime) {
		this.beginPayTime = beginPayTime;
	}
	public String getEndPayTime() {
		return endPayTime;
	}
	public void setEndPayTime(String endPayTime) {
		this.endPayTime = endPayTime;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsValidation() {
		return isValidation;
	}
	public void setIsValidation(String isValidation) {
		this.isValidation = isValidation;
	}
	public String getSelectAll() {
		return selectAll;
	}
	public void setSelectAll(String selectAll) {
		this.selectAll = selectAll;
	}
	public String getUniformPrice() {
		return uniformPrice;
	}
	public void setUniformPrice(String uniformPrice) {
		this.uniformPrice = uniformPrice;
	}
}
