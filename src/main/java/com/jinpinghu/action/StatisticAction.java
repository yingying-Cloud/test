package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.statistic.AllRecoveryStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllEnterpriseToolStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolCatalogStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderDscdStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderEnterpriseStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderLinkInfoStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderTimeStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderToolStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderToolTopStatisticLogic;
import com.jinpinghu.logic.statistic.GetAllToolOrderToolTypeStatisticLogic;
import com.jinpinghu.logic.statistic.GetToolEnterpriseListLogic;
import com.jinpinghu.logic.statistic.RecStatByEnterpriseLogic;
import com.jinpinghu.logic.statistic.RecStatByKindsLogic;
import com.jinpinghu.logic.statistic.RecStatByLinkPeopleLogic;
import com.jinpinghu.logic.statistic.RecStatByMonthLogic;
import com.jinpinghu.logic.statistic.RecStatByStoreLogic;
import com.jinpinghu.logic.statistic.RecStatByStreetLogic;
import com.jinpinghu.logic.statistic.RecStatByTimeLogic;
import com.jinpinghu.logic.statistic.RecStatByToolLogic;
import com.jinpinghu.logic.statistic.RecStatByTypeLogic;
import com.jinpinghu.logic.statistic.StatisticEnterpriseLogic;
import com.jinpinghu.logic.statistic.StatisticFlowInfoLogic;
import com.jinpinghu.logic.statistic.StatisticInLogic;
import com.jinpinghu.logic.statistic.StatisticOrderByToolTypeLogic;
import com.jinpinghu.logic.statistic.StatisticOut2Logic;
import com.jinpinghu.logic.statistic.StatisticOutLogic;
import com.jinpinghu.logic.statistic.StatisticStockLogic;
import com.jinpinghu.logic.statistic.StatisticToolCatalogLogic;
import com.jinpinghu.logic.statistic.StatisticToolIngredientByDscdLogic;
import com.jinpinghu.logic.statistic.StatisticToolIngredientDetailByDscdLogic;
import com.jinpinghu.logic.statistic.StatisticToolOrderLogic;
import com.jinpinghu.logic.statistic.StatisticToolWeightByDscdLogic;
import com.jinpinghu.logic.statistic.StatisticValidationDscdEnterpriseLogic;
import com.jinpinghu.logic.statistic.StatisticValidationDscdLandLogic;
import com.jinpinghu.logic.statistic.StatisticValidationDscdOrderLogic;
import com.jinpinghu.logic.statistic.StatisticValidationDscdPeopleLogic;
import com.jinpinghu.logic.statistic.StatisticValidationLogic;
import com.jinpinghu.logic.statistic.param.AllRecoveryStatisticParam;
import com.jinpinghu.logic.statistic.param.GetAllToolOrderStatisticParam;
import com.jinpinghu.logic.statistic.param.GetToolEnterpriseListParam;
import com.jinpinghu.logic.statistic.param.RecStatByEnterpriseParam;
import com.jinpinghu.logic.statistic.param.RecStatByKindsParam;
import com.jinpinghu.logic.statistic.param.RecStatByStreetParam;
import com.jinpinghu.logic.statistic.param.RecStatByTimeParam;
import com.jinpinghu.logic.statistic.param.RecStatByToolParam;
import com.jinpinghu.logic.statistic.param.RecStatByTypeParam;
import com.jinpinghu.logic.statistic.param.StatisticEnterpriseParam;
import com.jinpinghu.logic.statistic.param.StatisticFlowInfoParam;
import com.jinpinghu.logic.statistic.param.StatisticInParam;
import com.jinpinghu.logic.statistic.param.StatisticOrderByToolTypeParam;
import com.jinpinghu.logic.statistic.param.StatisticOut2Param;
import com.jinpinghu.logic.statistic.param.StatisticOutParam;
import com.jinpinghu.logic.statistic.param.StatisticStockParam;
import com.jinpinghu.logic.statistic.param.StatisticToolCatalogParam;
import com.jinpinghu.logic.statistic.param.StatisticToolIngredientByDscdParam;
import com.jinpinghu.logic.statistic.param.StatisticToolOrderParam;
import com.jinpinghu.logic.statistic.param.StatisticValidationParam;

@Path("statistic")
@Produces("application/json;charset=UTF-8")
public class StatisticAction extends BaseZAction{

	/*
	 * 平湖市农资农废统计
	 * */
	@POST
	@Path("allRecStat.do")
	public String allRecStat(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		AllRecoveryStatisticParam myParam = new AllRecoveryStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setSelectAll(selectAll);
		return new AllRecoveryStatisticLogic().process(myParam);
	}
	
	
	/*
	 * 各街道回收统计
	 * */
	@POST
	@Path("recStatByStreet.do")
	public String recStatByStreet(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("type") String type,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		RecStatByStreetParam myParam = new RecStatByStreetParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setType(type);
		myParam.setSelectAll(selectAll);
		return new RecStatByStreetLogic().process(myParam);
	}
	
	/*
	 * 各店铺回收统计
	 * */
	@POST
	@Path("recStatByStore.do")
	public String recStatByStore(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("name") String name,
			@FormParam("selectAll") String selectAll,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		RecStatByStreetParam myParam = new RecStatByStreetParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setName(name);
		myParam.setSelectAll(selectAll);
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		return new RecStatByStoreLogic().process(myParam);
	}
	
	/*
	 * 各用户回收统计
	 * */
	@POST
	@Path("recStatByLinkPeople.do")
	public String recStatByLinkPeople(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("name") String name,
			@FormParam("selectAll") String selectAll,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		RecStatByStreetParam myParam = new RecStatByStreetParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setName(name);
		myParam.setSelectAll(selectAll);
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		return new RecStatByLinkPeopleLogic().process(myParam);
	}
	
	/*
	 * 本年度每月回收统计
	 * */
	@POST
	@Path("recStatByMonth.do")
	public String recStatByMonth(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("type") String type,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		RecStatByStreetParam myParam = new RecStatByStreetParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setType(type);
		myParam.setSelectAll(selectAll);
		return new RecStatByMonthLogic().process(myParam);
	}
	
	
	/*
	 * 集中回收时间段
	 * */
	@POST
	@Path("recStatByTime.do")
	public String recStatByTime(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("year") String year,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		RecStatByTimeParam myParam = new RecStatByTimeParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setYear(year);
		myParam.setSelectAll(selectAll);
		return new RecStatByTimeLogic().process(myParam);
	}
	
	/*
	 * 各农资店农废回收统计
	 * */
	@POST
	@Path("recStatByEnterprise.do")
	public String recStatByEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		RecStatByEnterpriseParam myParam = new RecStatByEnterpriseParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new RecStatByEnterpriseLogic().process(myParam);
	}
	
	/*
	 * 各农资店同一农资商品回收数量统计
	 * */
	@POST
	@Path("recStatByTool.do")
	public String recStatByTool(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("recoveryId") String recoveryId,
			@Context HttpServletRequest request) {
		RecStatByToolParam myParam = new RecStatByToolParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setRecoveryId(recoveryId);
		return new RecStatByToolLogic().process(myParam);
	}
	
	/*
	 * 农资企业/个人提供回收量统计
	 * */
	@POST
	@Path("recStatByType.do")
	public String recStatByType(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("type") String type,
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		RecStatByTypeParam myParam = new RecStatByTypeParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setType(type);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new RecStatByTypeLogic().process(myParam);
	}
	
	/*
	 * 回收种类回收量统计
	 * */
	@POST
	@Path("recStatByKinds.do")
	public String recStatByKinds(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		RecStatByKindsParam myParam = new RecStatByKindsParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new RecStatByKindsLogic().process(myParam);
	}
	
	/**
	  * 根据地区获取订单
	 * @param userId
	 * @param apiKey
	 * @param enterpriseId
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @return
	 */
	//修改菜订单数

	@POST
	@Path("getAllToolOrderDscdStatistic.do")
	public String getAllToolOrderDscdStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("code") String code,
			@FormParam("productAttributes") String productAttributes,
			@FormParam("toolType") String toolType,
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setSelectAll(selectAll);
		myParam.setCode(code);
		myParam.setProductAttributes(productAttributes);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setToolType(toolType);
		myParam.setName(name);
		return new GetAllToolOrderDscdStatisticLogic().process(myParam);
	}
	/**
	 * 根据企业统计订单
	 */
	@POST
	@Path("getAllToolOrderEnterpriseStatistic.do")
	public String getAllToolOrderEnterpriseStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("dscd") String dscd,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setSelectAll(selectAll);
		myParam.setDscd(dscd);
		return new GetAllToolOrderEnterpriseStatisticLogic().process(myParam);
	}
	//修改成订单数
	@POST
	@Path("getAllToolOrderStatistic.do")
	public String getAllToolOrderStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setSelectAll(selectAll);
		return new GetAllToolOrderStatisticLogic().process(myParam);
	}
	//修改成订单数
	@POST
	@Path("getAllToolOrderTimeStatistic.do")
	public String getAllToolOrderTimeStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("year") String year,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setYear(year);
		myParam.setSelectAll(selectAll);
		return new GetAllToolOrderTimeStatisticLogic().process(myParam);
	}
	
	@POST
	@Path("getAllToolOrderToolStatistic.do")
	public String getAllToolOrderToolStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("code") String code,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setCode(code);
		return new GetAllToolOrderToolStatisticLogic().process(myParam);
	}
	
	@POST
	@Path("getAllToolOrderToolTypeStatistic.do")
	public String getAllToolOrderToolTypeStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("toolType") String toolType,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setToolType(toolType);
		myParam.setSelectAll(selectAll);
		return new GetAllToolOrderToolTypeStatisticLogic().process(myParam);
	}
	@POST
	@Path("getAllToolOrderLinkInfoStatistic.do")
	public String getAllToolOrderLinkInfoStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolId") String toolId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("type") String type,
			@FormParam("name") String name,
			@FormParam("code") String code,
			@FormParam("productAttributes") String productAttributes,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setType(type);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setSelectAll(selectAll);
		myParam.setToolId(toolId);
		myParam.setCode(code);
		myParam.setProductAttributes(productAttributes);
		return new GetAllToolOrderLinkInfoStatisticLogic().process(myParam);
	}
	
	@POST
	@Path("getAllToolOrderToolTopStatistic.do")
	public String getAllToolOrderToolTopStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("code") String code,
			@FormParam("productAttributes") String productAttributes,
			@FormParam("toolType") String toolType,
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setCode(code);
		myParam.setProductAttributes(productAttributes);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setSelectAll(selectAll);
		myParam.setToolType(toolType);
		return new GetAllToolOrderToolTopStatisticLogic().process(myParam);
	}
	
	@POST
	@Path("getToolEnterpriseList.do")
	public String getToolEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type") String type,
			@Context HttpServletRequest request) {
		GetToolEnterpriseListParam myParam = new GetToolEnterpriseListParam(userId, apiKey, request);
		myParam.setType(type);
		return new GetToolEnterpriseListLogic().process(myParam);
	}
	
	@POST
	@Path("getAllToolCatalogStatistic.do")
	public String getAllToolCatalogStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type") String type,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setType(type);
		myParam.setEnterpriseId(enterpriseId);
		return new GetAllToolCatalogStatisticLogic().process(myParam);
	}
	@POST
	@Path("getAllEnterpriseToolStatistic.do")
	public String getAllEnterpriseToolStatistic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type") String type,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetAllToolOrderStatisticParam myParam = new GetAllToolOrderStatisticParam(userId, apiKey, request);
		myParam.setType(type);
		myParam.setEnterpriseId(enterpriseId);
		return new GetAllEnterpriseToolStatisticLogic().process(myParam);
	}
	
	/**
	 * 	统计农资店
	 * @createTime:2019-10-31 11:17:00
	 * @author:harrychao
	 */
	@POST
	@Path("statisticEnterprise.do")
	public String statisticEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticEnterpriseParam myParam = new StatisticEnterpriseParam(userId, apiKey, request);
		myParam.setIsExport(isExport);
		myParam.setSelectAll(selectAll);
		return new StatisticEnterpriseLogic().process(myParam);
	}
	
	/**
	 * 	统计农资备案
	 * @createTime:2019-10-31 11:43:49
	 * @author:harrychao
	 */
	@POST
	@Path("statisticToolCatalog.do")
	public String statisticToolCatalog(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("status") String status,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticToolCatalogParam myParam = new StatisticToolCatalogParam(userId, apiKey, request);
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		myParam.setIsExport(isExport);
		myParam.setSelectAll(selectAll);
		return new StatisticToolCatalogLogic().process(myParam);
	}
	
	/**
	 * 	购销情况统计
	 * @createTime:2019-10-31 13:53:18
	 * @author:harrychao
	 */
	@POST
	@Path("statisticToolOrder.do")
	public String statisticToolOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("type") String type,
			@FormParam("dscd") String dscd,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticToolOrderParam myParam = new StatisticToolOrderParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setType(type);
		myParam.setDscd(dscd);
		myParam.setIsExport(isExport);
		myParam.setSelectAll(selectAll);
		return new StatisticToolOrderLogic().process(myParam);
	}
	
	/**
	 * 	流向情况统计
	 * @createTime:2019-10-31 13:52:54
	 * @author:harrychao
	 */
	@POST
	@Path("statisticFlowInfo.do")
	public String statisticFlowInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolName") String toolName,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("type") String type,
			@FormParam("dscd") String dscd,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticFlowInfoParam myParam = new StatisticFlowInfoParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setToolName(toolName);
		myParam.setType(type);
		myParam.setDscd(dscd);
		myParam.setIsExport(isExport);
		myParam.setSelectAll(selectAll);
		return new StatisticFlowInfoLogic().process(myParam);
	}
	
	/**
	 * 	库存统计
	 * @createTime:2019-10-31 14:08:28
	 * @author:harrychao
	 */
	@POST
	@Path("statisticStock.do")
	public String statisticStock(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type") String type,
			@FormParam("dscd") String dscd,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticStockParam myParam = new StatisticStockParam(userId, apiKey, request);
		myParam.setType(type);
		myParam.setDscd(dscd);
		myParam.setIsExport(isExport);
		myParam.setSelectAll(selectAll);
		return new StatisticStockLogic().process(myParam);
	}
	
	/**
	 * 	销售统计
	 * @createTime:2019-10-31 14:41:46
	 * @author:harrychao
	 */
	@POST
	@Path("statisticOut.do")
	public String statisticOut(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("tm") String tm,
			@FormParam("name") String name,
			@FormParam("dscd") String dscd,
			@FormParam("isValidation") String isValidation,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@FormParam("uniformPrice") String uniformPrice,
			@Context HttpServletRequest request) {
		StatisticOutParam myParam = new StatisticOutParam(userId, apiKey, request);
		myParam.setTm(tm);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setIsExport(isExport);
		myParam.setIsValidation(isValidation);
		myParam.setSelectAll(selectAll);
		myParam.setUniformPrice(uniformPrice);
		return new StatisticOutLogic().process(myParam);
	}
	
	/**
	 * 	入库统计
	 * @createTime:2019-10-31 14:41:46
	 * @author:harrychao
	 */
	@POST
	@Path("statisticIn.do")
	public String statisticIn(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("tm") String tm,
			@FormParam("name") String name,
			@FormParam("dscd") String dscd,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticInParam myParam = new StatisticInParam(userId, apiKey, request);
		myParam.setTm(tm);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setIsExport(isExport);
		myParam.setSelectAll(selectAll);
		return new StatisticInLogic().process(myParam);
	}
	
	/**
	 * 	入库统计
	 * @createTime:2019-10-31 14:41:46
	 * @author:harrychao
	 */
	@POST
	@Path("statisticOut2.do")
	public String statisticOut2(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("tm") String tm,
			@FormParam("name") String name,
			@FormParam("dscd") String dscd,
			@FormParam("isExport") String isExport,
			@FormParam("selectAll") String selectAll,
			@FormParam("uniformPrice") String uniformPrice,
			@Context HttpServletRequest request) {
		StatisticOut2Param myParam = new StatisticOut2Param(userId, apiKey, request);
		myParam.setTm(tm);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setIsExport(isExport);
		myParam.setSelectAll(selectAll);
		myParam.setUniformPrice(uniformPrice);
		return new StatisticOut2Logic().process(myParam);
	}
	
	/**
	 * 	统计实名制/订单/人数/信息
	 * @createTime:2019-10-31 11:17:00
	 */
	@POST
	@Path("statisticValidation.do")
	public String statisticValidation(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		StatisticValidationParam myParam = new StatisticValidationParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new StatisticValidationLogic().process(myParam);
	}
	/**
	 * 	统计实名制/订单/人数/信息 按照乡镇
	 */
	@POST
	@Path("statisticValidationDscdOrder.do")
	public String statisticValidationDscdOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticValidationParam myParam = new StatisticValidationParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		return new StatisticValidationDscdOrderLogic().process(myParam);
	}
	
	/**
	 * 	统计实名制会员人数 按照乡镇
	 */
	@POST
	@Path("statisticValidationDscdPeople.do")
	public String statisticValidationDscdPeople(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticValidationParam myParam = new StatisticValidationParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		return new StatisticValidationDscdPeopleLogic().process(myParam);
	}
	
	/**
	 * 	统计农资店 按照乡镇
	 */
	@POST
	@Path("statisticValidationDscdEnterprise.do")
	public String statisticValidationDscdEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticValidationParam myParam = new StatisticValidationParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		return new StatisticValidationDscdEnterpriseLogic().process(myParam);
	}
	
	/**
	 * 	统计耕地面积 按照乡镇
	 */
	@POST
	@Path("statisticValidationDscdLandLogic.do")
	public String statisticValidationDscdLandLogic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		StatisticValidationParam myParam = new StatisticValidationParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		return new StatisticValidationDscdLandLogic().process(myParam);
	}
	
	/**
	 * 	统计农资npk含量
	 */
	@POST
	@Path("statisticToolIngredientByDscd.do")
	public String statisticToolIngredientByDscd(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@FormParam("startMonth") String startMonth,
			@FormParam("endMonth") String endMonth,
			@FormParam("isExport") String isExport,
			@Context HttpServletRequest request) {
		StatisticToolIngredientByDscdParam myParam = new StatisticToolIngredientByDscdParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		myParam.setIsExport(isExport);
		myParam.setStartMonth(startMonth);
		myParam.setEndMonth(endMonth);
		return new StatisticToolIngredientByDscdLogic().process(myParam);
	}
	/**
	 * 	统计农资总重
	 */
	@POST
	@Path("statisticToolWeightByDscd.do")
	public String statisticToolWeightByDscd(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@FormParam("startMonth") String startMonth,
			@FormParam("endMonth") String endMonth,
			@FormParam("isExport") String isExport,
			@Context HttpServletRequest request) {
		StatisticToolIngredientByDscdParam myParam = new StatisticToolIngredientByDscdParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		myParam.setIsExport(isExport);
		myParam.setStartMonth(startMonth);
		myParam.setEndMonth(endMonth);
		return new StatisticToolWeightByDscdLogic().process(myParam);
	}
	/**
	 * 	统计农资总重
	 */
	@POST
	@Path("statisticToolIngredientDetailByDscd.do")
	public String statisticToolIngredientDetailByDscd(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@FormParam("startMonth") String startMonth,
			@FormParam("endMonth") String endMonth,
			@FormParam("dscd") String dscd,
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		StatisticToolIngredientByDscdParam myParam = new StatisticToolIngredientByDscdParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		myParam.setStartMonth(startMonth);
		myParam.setEndMonth(endMonth);
		myParam.setDscd(dscd);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticToolIngredientDetailByDscdLogic().process(myParam);
	}
	/**
	 * 	统计农资npk含量
	 */
	@POST
	@Path("statisticToolIngredientByLinkOrderInfoId.do")
	public String statisticToolIngredientByLinkOrderInfoId(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@FormParam("startMonth") String startMonth,
			@FormParam("endMonth") String endMonth,
			@FormParam("linkOrderInfoId") String linkOrderInfoId,
			@Context HttpServletRequest request) {
		StatisticToolIngredientByDscdParam myParam = new StatisticToolIngredientByDscdParam(userId, apiKey, request);
		myParam.setSelectAll(selectAll);
		myParam.setStartMonth(startMonth);
		myParam.setEndMonth(endMonth);
		myParam.setLinkOrderInfoId(linkOrderInfoId);
		return new StatisticToolIngredientByDscdLogic().process(myParam);
	}
	
	@POST
	@Path("statisticOrderByToolType.do")
	public String statisticOrderByToolType(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("selectAll") String selectAll,
			@FormParam("month") String month,
			@FormParam("dscd") String dscd,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		StatisticOrderByToolTypeParam myParam = new StatisticOrderByToolTypeParam(userId,apiKey,request);
		myParam.setSelectAll(selectAll);
		myParam.setMonth(month);
		myParam.setDscd(dscd);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new StatisticOrderByToolTypeLogic().process(myParam);
	}
}
