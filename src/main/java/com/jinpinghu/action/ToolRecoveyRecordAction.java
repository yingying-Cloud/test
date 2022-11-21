package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.toolRecoveryRecord.AddOrUpdateToolRecoveryRecordLogic;
import com.jinpinghu.logic.toolRecoveryRecord.AddToolRecoveryRecordFileLogic;
import com.jinpinghu.logic.toolRecoveryRecord.DelToolRecoveryRecordLogic;
import com.jinpinghu.logic.toolRecoveryRecord.GetToolRecoveryRecordInfoLogic;
import com.jinpinghu.logic.toolRecoveryRecord.GetToolRecoveryRecordListLogic;
import com.jinpinghu.logic.toolRecoveryRecord.GetToolRevoeryRecordListByToolRecoveryIdLogic;
import com.jinpinghu.logic.toolRecoveryRecord.StatisticBuyZeroYearLogic;
import com.jinpinghu.logic.toolRecoveryRecord.StatisticEnterpriseBuyZeroListLogic;
import com.jinpinghu.logic.toolRecoveryRecord.StatisticEnterpriseZeroListLogic;
import com.jinpinghu.logic.toolRecoveryRecord.StatisticRecoveryYearLogic;
import com.jinpinghu.logic.toolRecoveryRecord.StatisticToolRecoveryRecordLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.AddOrUpdateToolRecoveryRecordParam;
import com.jinpinghu.logic.toolRecoveryRecord.param.DelToolRecoveryRecordParam;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRecoveryRecordInfoParam;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRecoveryRecordListParam;
import com.jinpinghu.logic.toolRecoveryRecord.param.GetToolRevoeryRecordListByToolRecoveryIdParam;
import com.jinpinghu.logic.toolRecoveryRecord.param.StatisticBuyZeroYearParam;


@Path("toolRecoveryRecord")
@Produces("application/json;charset=UTF-8")
public class ToolRecoveyRecordAction extends BaseZAction {

	@POST
	@Path("getToolRecoveryRecordList.do")
	public String getToolRecoveryRecordRecordList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("useName") String useName,
			@FormParam("toolRecoveryId") String toolRecoveryId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("isExport")  String isExport,
			@FormParam("linkOrderInfoId")  String linkOrderInfoId,
			@FormParam("toolName")  String toolName,
			@FormParam("recordNumber")  String recordNumber,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll") String selectAll,
			@FormParam("dscd") String dscd,
			@Context HttpServletRequest request) {
		GetToolRecoveryRecordListParam myParam = new GetToolRecoveryRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setName(name);
		myParam.setToolRecoveryId(toolRecoveryId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setIsExport(isExport);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setUseName(useName);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setLinkOrderInfoId(linkOrderInfoId);
		myParam.setToolName(toolName);
		myParam.setRecordNumber(recordNumber);
		myParam.setSelectAll(selectAll);
		myParam.setDscd(dscd);
		return new GetToolRecoveryRecordListLogic().process(myParam);
	}
	
	
	@POST
	@Path("addOrUpdateToolRecoveryRecord.do")
	public String addToolRecoveryRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("toolRecoveryId")  String toolRecoveryId,
			@FormParam("number")  String number,
			@FormParam("linkOrderInfoId")  String linkOrderInfoId,
			@FormParam("useName")  String useName,
			@FormParam("useMobile")  String useMobile,
			@FormParam("inputTime")  String inputTime,
			@FormParam("file")  String file,
			@FormParam("operator")  String operator,
			@FormParam("toolId")  String toolId,
			@Context HttpServletRequest request) {
		AddOrUpdateToolRecoveryRecordParam myParam = new AddOrUpdateToolRecoveryRecordParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setToolRecoveryId(toolRecoveryId);
		myParam.setNumber(number);
		myParam.setFile(file);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setUseName(useName);
		myParam.setInputTime(inputTime);
		myParam.setOperator(operator);
		myParam.setUseMobile(useMobile);
		myParam.setLinkOrderInfoId(linkOrderInfoId);
		myParam.setToolId(toolId);
		return new AddOrUpdateToolRecoveryRecordLogic().process(myParam);
	}
	
	
	@POST
	@Path("delToolRecoveryRecord.do")
	public String delToolRecoveryRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelToolRecoveryRecordParam myParam = new DelToolRecoveryRecordParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelToolRecoveryRecordLogic().process(myParam);
	}
	
	@POST
	@Path("getToolRecoveryRecordInfo.do")
	public String getToolRecoveryRecordInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetToolRecoveryRecordInfoParam myParam = new GetToolRecoveryRecordInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetToolRecoveryRecordInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getToolRecoveryRecordList2.do")
	public String getToolRecoveryRecordList2(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolRecoveryId")  String toolRecoveryId,
			@FormParam("sellUnit")  String sellUnit,
			@FormParam("recoveryUnit")  String recoveryUnit,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetToolRevoeryRecordListByToolRecoveryIdParam myParam = new GetToolRevoeryRecordListByToolRecoveryIdParam(userId, apiKey, request);
		myParam.setToolRecoveryId(StringUtils.isEmpty(toolRecoveryId) ? null : Integer.valueOf(toolRecoveryId));
		myParam.setSellUnit(sellUnit);
		myParam.setRecoveryUnit(recoveryUnit);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		return new GetToolRevoeryRecordListByToolRecoveryIdLogic().process(myParam);
	}
	@POST
	@Path("addToolRecoveryRecordFile.do")
	public String addToolRecoveryRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("file")  String file,
			@Context HttpServletRequest request) {
		AddOrUpdateToolRecoveryRecordParam myParam = new AddOrUpdateToolRecoveryRecordParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setFile(file);
		return new AddToolRecoveryRecordFileLogic().process(myParam);
	}
	@POST
	@Path("statisticToolRecoveryRecordList.do")
	public String statisticToolRecoveryRecordList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("useName") String useName,
			@FormParam("toolRecoveryId") String toolRecoveryId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("isExport")  String isExport,
			@FormParam("linkOrderInfoId")  String linkOrderInfoId,
			@FormParam("toolName")  String toolName,
			@FormParam("recordNumber")  String recordNumber,
			@FormParam("selectAll") String selectAll,
			@FormParam("dscd") String dscd,
			@Context HttpServletRequest request) {
		GetToolRecoveryRecordListParam myParam = new GetToolRecoveryRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setName(name);
		myParam.setToolRecoveryId(toolRecoveryId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setIsExport(isExport);
		myParam.setUseName(useName);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setLinkOrderInfoId(linkOrderInfoId);
		myParam.setToolName(toolName);
		myParam.setRecordNumber(recordNumber);
		myParam.setSelectAll(selectAll);
		myParam.setDscd(dscd);
		return new StatisticToolRecoveryRecordLogic().process(myParam);
	}
	
	
	
	// 补贴统计 零差价月销售统计
	@POST
	@Path("statisticBuyZeroYear.do")
	public String StatisticBuyZeroYearLogic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("year")  String year,
			@FormParam("dscd")  String dscd,
			@FormParam("selectAll")  String selectAll,
			@FormParam("isExport")  String isExport,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		StatisticBuyZeroYearParam myParam = new StatisticBuyZeroYearParam(userId, apiKey, request);
		myParam.setYear(year);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setSelectAll(selectAll);
		myParam.setDscd(dscd);
		myParam.setIsExport(isExport);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticBuyZeroYearLogic().process(myParam);
	}
	// 预警统计 废物回收  
	@POST
	@Path("statisticEnterpriseBuyZeroList.do")
	public String StatisticEnterpriseBuyZeroList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("isExport")  String isExport,
			@FormParam("dscd")  String dscd,
			@FormParam("selectAll") String selectAll,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetToolRecoveryRecordListParam myParam = new GetToolRecoveryRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setIsExport(isExport);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setSelectAll(selectAll);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setEnterpriseType(type);
		return new StatisticEnterpriseBuyZeroListLogic().process(myParam);
	}
	//预警统计 零差价农药
	@POST
	@Path("statisticEnterpriseZeroList.do")
	public String StatisticEnterpriseZeroList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("isExport")  String isExport,
			@FormParam("selectAll") String selectAll,
			@FormParam("dscd")  String dscd,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetToolRecoveryRecordListParam myParam = new GetToolRecoveryRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setIsExport(isExport);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setSelectAll(selectAll);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setEnterpriseType("99");
		return new StatisticEnterpriseZeroListLogic().process(myParam);
	}
	
	// 补贴统计 废物回收月统计
	@POST
	@Path("statisticRecoveryYear.do")
	public String StatisticRecoveryYear(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("year") String year,
			@FormParam("dscd")  String dscd,
			@FormParam("selectAll")  String selectAll,
			@FormParam("isExport")  String isExport,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		StatisticBuyZeroYearParam myParam = new StatisticBuyZeroYearParam(userId, apiKey, request);
		myParam.setYear(year);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setDscd(dscd);
		myParam.setSelectAll(selectAll);
		myParam.setIsExport(isExport);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticRecoveryYearLogic().process(myParam);
	}
	
	
}
