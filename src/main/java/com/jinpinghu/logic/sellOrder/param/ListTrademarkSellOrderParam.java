package com.jinpinghu.logic.sellOrder.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class ListTrademarkSellOrderParam extends BaseZLogicParam{

	public ListTrademarkSellOrderParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String enterpriseId;
	private String sellName;
	private String sellSellOrderNumber;
	private String enterpriseName;
	private String beginCreateTime;
	private String endCreateTime;
	private String beginPayTime;
	private String endPayTime;
	private String status;
	private String check;
	private String type;
	private String nowPage;
	private String pageCount;
	private String trademarkId;
	private String name;
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
	public String getSellOrderNumber() {
		return sellSellOrderNumber;
	}
	public void setSellOrderNumber(String sellSellOrderNumber) {
		this.sellSellOrderNumber = sellSellOrderNumber;
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

	public String getSellName() {
		return sellName;
	}

	public void setSellName(String sellName) {
		this.sellName = sellName;
	}
	public String getTrademarkId() {
		return trademarkId;
	}
	public void setTrademarkId(String trademarkId) {
		this.trademarkId = trademarkId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
