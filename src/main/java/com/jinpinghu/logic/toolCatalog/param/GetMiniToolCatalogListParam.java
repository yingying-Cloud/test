package com.jinpinghu.logic.toolCatalog.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetMiniToolCatalogListParam extends BaseZLogicParam {

	public GetMiniToolCatalogListParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private String toolName;
	private String productionUnits;
	private String type;
	private Integer nowPage;
	private Integer pageCount;
	private String selectAll;
	private String uniformPrice;
	private String productAttributes;
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getProductionUnits() {
		return productionUnits;
	}
	public void setProductionUnits(String productionUnits) {
		this.productionUnits = productionUnits;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getProductAttributes() {
		return productAttributes;
	}
	public void setProductAttributes(String productAttributes) {
		this.productAttributes = productAttributes;
	}

}
