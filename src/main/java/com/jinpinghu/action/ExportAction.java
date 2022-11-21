package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.brandOrder.param.ListAllBrandOrderParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseListParam;
import com.jinpinghu.logic.export.ExportAllToolLogic;
import com.jinpinghu.logic.export.ExportByBrandOrderLogic;
import com.jinpinghu.logic.export.ExportBySellOrderLogic;
import com.jinpinghu.logic.export.ExportByToolLogic;
import com.jinpinghu.logic.export.ExportByToolOrderLogic;
import com.jinpinghu.logic.export.ExportByToolOrderLogic2;
import com.jinpinghu.logic.export.ExportByToolRecordNoLogic;
import com.jinpinghu.logic.export.ExportEnterpriseByTypeLogic;
import com.jinpinghu.logic.export.ImportAllToolLogic;
import com.jinpinghu.logic.order.param.ListAllOrderParam;
import com.jinpinghu.logic.sellOrder.param.ListAllSellOrderParam;
import com.jinpinghu.logic.tool.param.GetToolListParam;
import com.jinpinghu.logic.tool.param.ImportToolParam;
import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogListParam;
@Path("export")
@Produces("application/json;charset=UTF-8")
public class ExportAction extends BaseZAction {

	
	@POST
	@Path("exportToolCatalogList.do")
	public String getToolCatalogList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name ,
			@FormParam("type")  String type,
			@FormParam("unit")  String unit,
			@FormParam("status")  String status,
			@FormParam("uniformPrice")  String uniformPrice ,
			@FormParam("productAttributes") String productAttributes,
			@Context HttpServletRequest request) {
		GetToolCatalogListParam myParam = new GetToolCatalogListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setUnit(unit);
		myParam.setStatus(status);
		myParam.setUniformPrice(uniformPrice);
		myParam.setProductAttributes(productAttributes);
		return new ExportByToolLogic().process(myParam);
	}
	@POST
	@Path("exportListAllOrder.do")
	public String listAllOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolName") String toolName ,
			@FormParam("orderNumber") String orderNumber,
			@FormParam("enterpriseName") String enterpriseName ,
			@FormParam("beginCreateTime") String beginCreateTime,
			@FormParam("endCreateTime") String endCreateTime,
			@FormParam("beginPayTime") String beginPayTime,
			@FormParam("endPayTime") String endPayTime,
			@FormParam("status") String status,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("check")  String check,
			@FormParam("name")  String name,
			@FormParam("isValidation")  String isValidation,
			@FormParam("selectAll") String selectAll,
			@FormParam("uniformPrice")  String uniformPrice,
			@Context HttpServletRequest request) {
		ListAllOrderParam myParam = new ListAllOrderParam(userId, apiKey, request);
		myParam.setStatus(status);
		myParam.setOrderNumber(orderNumber);
		myParam.setBeginCreateTime(beginCreateTime);
		myParam.setEndCreateTime(endCreateTime);
		myParam.setBeginPayTime(beginPayTime);
		myParam.setEndPayTime(endPayTime);
		myParam.setToolName(toolName);
		myParam.setOrderNumber(orderNumber);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setCheck(check);
		myParam.setName(name);
		myParam.setIsValidation(isValidation);
		myParam.setSelectAll(selectAll);
		myParam.setUniformPrice(uniformPrice);
		return new ExportByToolOrderLogic().process(myParam);
	}
	
	@POST
	@Path("exportListAllSellOrder.do")
	public String listAllSellOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("brandName") String brandName,
			@FormParam("orderNumber") String sellNumber,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("beginCreateTime") String beginCreateTime,
			@FormParam("endCreateTime") String endCreateTime,
			@FormParam("beginPayTime") String beginPayTime,
			@FormParam("endPayTime") String endPayTime,
			@FormParam("status") String status,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("check") String check,
			@FormParam("trademarkId") String trademarkId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("type") String type,
			@FormParam("name") String name,
			@Context HttpServletRequest request) {
		ListAllSellOrderParam myParam = new ListAllSellOrderParam(userId, apiKey, request);
		myParam.setStatus(status);
		myParam.setSellOrderNumber(sellNumber);
		myParam.setBeginCreateTime(beginCreateTime);
		myParam.setEndCreateTime(endCreateTime);
		myParam.setBeginPayTime(beginPayTime);
		myParam.setEndPayTime(endPayTime);
		myParam.setBrandName(brandName);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setCheck(check);
		myParam.setTrademarkId(trademarkId);
		myParam.setType(type);
		myParam.setName(name);
		return new ExportBySellOrderLogic().process(myParam);
	}

	@POST
	@Path("exportListAllBrandOrder.do")
	public String listAllBrandOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("brandName") String brandName,
			@FormParam("orderNumber") String brandNumber,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("beginCreateTime") String beginCreateTime,
			@FormParam("endCreateTime") String endCreateTime,
			@FormParam("beginPayTime") String beginPayTime,
			@FormParam("endPayTime") String endPayTime,
			@FormParam("status") String status,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("check") String check,
			@FormParam("trademarkId") String trademarkId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("type") String type,
			@Context HttpServletRequest request) {
		ListAllBrandOrderParam myParam = new ListAllBrandOrderParam(userId, apiKey, request);
		myParam.setStatus(status);
		myParam.setBrandOrderNumber(brandNumber);
		myParam.setBeginCreateTime(beginCreateTime);
		myParam.setEndCreateTime(endCreateTime);
		myParam.setBeginPayTime(beginPayTime);
		myParam.setEndPayTime(endPayTime);
		myParam.setBrandName(brandName);
		myParam.setBrandOrderNumber(brandNumber);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setCheck(check);
		myParam.setTrademarkId(trademarkId);
		myParam.setType(type);
		return new ExportByBrandOrderLogic().process(myParam);
	}
	
	@POST
	@Path("exportEnterpriseList.do")
	public String getEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseCreditCode")  String enterpriseCreditCode,
			@FormParam("enterpriseLegalPerson")  String enterpriseLegalPerson,
			@FormParam("enterpriseLegalPersonIdcard")  String enterpriseLegalPersonIdcard,
			@FormParam("enterpriseLinkPeople")  String enterpriseLinkPeople,
			@FormParam("enterpriseLinkMobile")  String enterpriseLinkMobile,
			@FormParam("enterpriseAddress")  String enterpriseAddress,
			@FormParam("enterpriseType")  String enterpriseType,
			@FormParam("status")  String status,
			@FormParam("dscd")  String dscd,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("state")  String state,
			@Context HttpServletRequest request) {
		GetEnterpriseListParam myParam = new GetEnterpriseListParam(userId, apiKey, request);
		myParam.setEnterpriseAddress(enterpriseAddress);
		myParam.setEnterpriseCreditCode(enterpriseCreditCode);
		myParam.setEnterpriseLegalPerson(enterpriseLegalPerson);
		myParam.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
		myParam.setEnterpriseLinkMobile(enterpriseLinkMobile);
		myParam.setEnterpriseLinkPeople(enterpriseLinkPeople);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setStatus(status);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setState(state);
		return new ExportEnterpriseByTypeLogic().process(myParam);
	}
	@POST
	@Path("exportAllTool.do")
	public String getToolCatalogList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		ListAllOrderParam myParam = new ListAllOrderParam(userId, apiKey, request);
		return new ExportByToolOrderLogic2().process(myParam);
	}
	
	@POST
	@Path("exportToolList.do")
	public String getToolList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name ,
			@FormParam("type")  String type,
			@FormParam("unit")  String unit,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("recordNo")  String recordNo,
			@FormParam("uniformPrice")  String uniformPrice ,
			@Context HttpServletRequest request) {
		GetToolListParam myParam = new GetToolListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setUnit(unit);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setRecordNo(recordNo);
		myParam.setUniformPrice(uniformPrice);
		return new ExportByToolRecordNoLogic().process(myParam);
	}
	
	//导入商品价格
		@POST
		@Path("importAllTool.do")
		public String importGoods(
				@Context HttpServletRequest request) {
			ImportToolParam myParam = new ImportToolParam(null,null,request);
			return new ImportAllToolLogic().process(myParam);
		}
}
