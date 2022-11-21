package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.toolRecord.AddOrUpdateToolRecordLogic;
import com.jinpinghu.logic.toolRecord.AddToolRecordLogic;
import com.jinpinghu.logic.toolRecord.DelToolRecordLogic;
import com.jinpinghu.logic.toolRecord.GetInToolRecordListLogic;
import com.jinpinghu.logic.toolRecord.GetOutToolRecordListLogic;
import com.jinpinghu.logic.toolRecord.GetToolRecordInfoLogic;
import com.jinpinghu.logic.toolRecord.GetToolRecordListLogic;
import com.jinpinghu.logic.toolRecord.StatisticPfToolRecordLogic;
import com.jinpinghu.logic.toolRecord.param.AddOrUpdateToolRecordParam;
import com.jinpinghu.logic.toolRecord.param.AddToolRecordParam;
import com.jinpinghu.logic.toolRecord.param.DelToolRecordParam;
import com.jinpinghu.logic.toolRecord.param.GetInToolRecordListParam;
import com.jinpinghu.logic.toolRecord.param.GetOutToolRecordListParam;
import com.jinpinghu.logic.toolRecord.param.GetToolRecordInfoParam;
import com.jinpinghu.logic.toolRecord.param.GetToolRecordListParam;


@Path("toolRecord")
@Produces("application/json;charset=UTF-8")
public class ToolRecordAction extends BaseZAction {

	@POST
	@Path("getToolRecordList.do")
	public String getToolRecordRecordList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("toolId") String toolId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("toolEnterpriseId") String toolEnterpriseId,
			@FormParam("recordType") String recordType,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("type")  String type,
			@FormParam("toEnterprise")  String toEnterprise,
			@FormParam("isExport")  String isExport,
			@FormParam("outName") String outName,
			@FormParam("fromName") String fromName,
			@FormParam("selectAll") String selectAll,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("uniformPrice") String uniformPrice,
			@Context HttpServletRequest request) {
		GetToolRecordListParam myParam = new GetToolRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setName(name);
		myParam.setToolEnterpriseId(toolEnterpriseId);
		myParam.setToolId(toolId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setRecordType(recordType);
		myParam.setType(type);
		myParam.setToEnterprise(toEnterprise);
		myParam.setIsExport(isExport);
		myParam.setOutName(outName);
		myParam.setFromName(fromName);
		myParam.setSelectAll(selectAll);
		myParam.setUniformPrice(uniformPrice);
		return new GetToolRecordListLogic().process(myParam);
	}
	
	
	@POST
	@Path("addOrUpdateToolRecord.do")
	public String addToolRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("toolEnterpriseId")  String toolEnterpriseId,
			@FormParam("toolId")  String toolId,
			@FormParam("recordType")  String recordType,
			@FormParam("number")  String number,
			@FormParam("supplierName")  String supplierName,
			@FormParam("useName")  String useName,
			@FormParam("useTime")  String useTime,
			@FormParam("file")  String file,
			@FormParam("outName")  String outName,
			@FormParam("outMobile")  String outMobile,
			@FormParam("price")  String price,
			@Context HttpServletRequest request) {
		AddOrUpdateToolRecordParam myParam = new AddOrUpdateToolRecordParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setToolId(toolId);
		myParam.setNumber(number);
		myParam.setRecordType(recordType);
		myParam.setFile(file);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setToolEnterpriseId(toolEnterpriseId);
		myParam.setUseName(useName);
		myParam.setSupplierName(supplierName);
		myParam.setUseTime(useTime);
		myParam.setOutMobile(outMobile);
		myParam.setOutName(outName);
		myParam.setPrice(price);
		return new AddOrUpdateToolRecordLogic().process(myParam);
	}
	
	
	@POST
	@Path("delToolRecord.do")
	public String delToolRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelToolRecordParam myParam = new DelToolRecordParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelToolRecordLogic().process(myParam);
	}
	
	@POST
	@Path("getToolRecordInfo.do")
	public String getToolRecordInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetToolRecordInfoParam myParam = new GetToolRecordInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetToolRecordInfoLogic().process(myParam);
	}
	/*
	 * 进货记录
	 */
	
	@POST
	@Path("getInToolRecordList.do")
	public String getInToolRecordRecordList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("toolId") String toolId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("type")  String type,
			@FormParam("code")  String code,
			@FormParam("productionUnit")  String productionUnit,
			@FormParam("supplierName")  String supplierName,
			@FormParam("isExport")  String isExport,
			@FormParam("buyName")  String buyName,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetInToolRecordListParam myParam = new GetInToolRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setName(name);
		myParam.setToolId(toolId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setType(type);
		myParam.setCode(code);
		myParam.setProductionUnit(productionUnit);
		myParam.setSupplierName(supplierName);
		myParam.setIsExport(isExport);
		myParam.setBuyName(buyName);
		myParam.setSelectAll(selectAll);
		return new GetInToolRecordListLogic().process(myParam);
	}
	/*
	 * 销售记录
	 */
	@POST
	@Path("getOutToolRecordList.do")
	public String getOutToolRecordRecordList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("toolId") String toolId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("plantEnterpriseId") String plantEnterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("type")  String type,
			@FormParam("code")  String code,
			@FormParam("productionUnit")  String productionUnit,
			@FormParam("supplierName")  String supplierName,
			@FormParam("buyName")  String buyName,
			@FormParam("isExport")  String isExport,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetOutToolRecordListParam myParam = new GetOutToolRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setName(name);
		myParam.setToolId(toolId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setType(type);
		myParam.setCode(code);
		myParam.setProductionUnit(productionUnit);
		myParam.setSupplierName(supplierName);
		myParam.setIsExport(isExport);
		myParam.setBuyName(buyName);
		myParam.setPlantEnterpriseId(plantEnterpriseId);
		return new GetOutToolRecordListLogic().process(myParam);
	}
	@POST
	@Path("addToolRecord.do")
	public String addToolRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("fromEnterpriseId")  String fromEnterpriseId,
			@FormParam("outEnterpriseId")  String outEnterpriseId,
			@FormParam("toolJa")  String toolJa,
			@FormParam("useName")  String useName,
			@Context HttpServletRequest request) {
		AddToolRecordParam myParam = new AddToolRecordParam(userId, apiKey, request);
		myParam.setFromEnterpriseId(fromEnterpriseId);
		myParam.setOutEnterpriseId(outEnterpriseId);
		myParam.setToolJa(toolJa);
		myParam.setUseName(useName);
		return new AddToolRecordLogic().process(myParam);
	}
	@POST
	@Path("statisticPfToolRecord.do")
	public String statisticPfToolRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("toolId") String toolId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("toolEnterpriseId") String toolEnterpriseId,
			@FormParam("recordType") String recordType,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("type")  String type,
			@FormParam("toEnterprise")  String toEnterprise,
			@FormParam("outName") String outName,
			@FormParam("fromName") String fromName,
			@FormParam("selectAll") String selectAll,
			@FormParam("uniformPrice") String uniformPrice,
			@Context HttpServletRequest request) {
		GetToolRecordListParam myParam = new GetToolRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setName(name);
		myParam.setToolEnterpriseId(toolEnterpriseId);
		myParam.setToolId(toolId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setRecordType(recordType);
		myParam.setType(type);
		myParam.setToEnterprise(toEnterprise);
		myParam.setOutName(outName);
		myParam.setFromName(fromName);
		myParam.setSelectAll(selectAll);
		myParam.setUniformPrice(uniformPrice);
		return new StatisticPfToolRecordLogic().process(myParam);
	}
}
