package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.index.BrandPlanLogic;
import com.jinpinghu.logic.index.CreditListLogic;
import com.jinpinghu.logic.index.CreditLogic;
import com.jinpinghu.logic.index.GetAllCompanyListLogic;
import com.jinpinghu.logic.index.StatisticAllBrandByAreaLogic;
import com.jinpinghu.logic.index.StatisticAllBrandLogic;
import com.jinpinghu.logic.index.StatisticAllBrandTypeCountLogic;
import com.jinpinghu.logic.index.StatisticAllCompanyBrandLogic;
import com.jinpinghu.logic.index.StatisticAllCompanyOrderBrandLogic;
import com.jinpinghu.logic.index.StatisticAllCompanyOrderDscdLogic;
import com.jinpinghu.logic.index.StatisticAllCompanyOrderLogic;
import com.jinpinghu.logic.index.StatisticAllCompanyOrderMonthLogic;
import com.jinpinghu.logic.index.StatisticAllCompanyWorkLogic;
import com.jinpinghu.logic.index.StatisticAllEnterpriseLogic;
import com.jinpinghu.logic.index.StatisticAllEnterpriseProductionLogic;
import com.jinpinghu.logic.index.StatisticAllOrderLogic;
import com.jinpinghu.logic.index.StatisticAllToolLogic;
import com.jinpinghu.logic.index.StatisticAllToolTopLogic;
import com.jinpinghu.logic.index.StatisticAllWaterLogic;
import com.jinpinghu.logic.index.StatisticAllWaterTopLogic;
import com.jinpinghu.logic.index.StatisticAllWorkBrandAreaLogic;
import com.jinpinghu.logic.index.StatisticAllWorkLogic;
import com.jinpinghu.logic.index.StatisticBrandTypeCountLogic;
import com.jinpinghu.logic.index.StatisticWeekOrderLogic;
import com.jinpinghu.logic.index.StreetNumLogic;
import com.jinpinghu.logic.index.param.SimpleParam;

@Path("index")
@Produces("application/json;charset=UTF-8")
public class IndexAction extends BaseZAction{


	/*
	 * 	各街道合作社数量以及占比
	 * */
	@POST
	@Path("streetNum.do")
	public String streetNum(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StreetNumLogic().process(myParam);
	}

	
	/*
	 * 	全部种植企业经纬度
	 * */
	@POST
	@Path("statisticAllEnterprise.do")
	public String statisticAllEnterprise(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllEnterpriseLogic().process(myParam);
	}
	
	/*
	 * 	信用
	 * */
	@POST
	@Path("credit.do")
	public String credit(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new CreditLogic().process(myParam);
	}
	
	/*
	 * 	信用列表
	 * */
	@POST
	@Path("creditList.do")
	public String creditList(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new CreditListLogic().process(myParam);
	}
	
	
	/*
	 * 	品牌计划
	 * */
	@POST
	@Path("brandPlan.do")
	public String brandPlan(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new BrandPlanLogic().process(myParam);
	}

	
	/*
	 * 	产品销售城市去向
	 * */
	@POST
	@Path("statisticAllBrandByArea.do")
	public String statisticAllBrandByArea(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllBrandByAreaLogic().process(myParam);
	}
	/*
	 * 	各产品销售量
	 * */
	@POST
	@Path("statisticAllBrand.do")
	public String statisticAllBrand(
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticAllBrandLogic().process(myParam);
	}
	
	/*
	 * 	公司产品类型数量
	 * */
	@POST
	@Path("statisticBrandTypeCount.do")
	public String statisticBrandTypeCount(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticBrandTypeCountLogic().process(myParam);
	}
	
	/*
	 * 	各街道合作社数量以及占比
	 * */
	@POST
	@Path("statisticAllEnterpriseProduction.do")
	public String statisticAllEnterpriseProduction(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllEnterpriseProductionLogic().process(myParam);
	}
	/*
	 * 	销售情况
	 * */
	@POST
	@Path("statisticAllOrder.do")
	public String statisticAllOrder(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllOrderLogic().process(myParam);
	}
	/*
	 * 	各街道合作社数量以及占比
	 * */
	@POST
	@Path("statisticAllWork.do")
	public String statisticAllWork(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllWorkLogic().process(myParam);
	}
	/*
	 * 	本周订单数
	 * */
	@POST
	@Path("statisticWeekOrder.do")
	public String statisticWeekOrder(
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticWeekOrderLogic().process(myParam);
	}
	
	/*
	 * 统计在company订单表里各个商品的销售量
	 * */
	@POST
	@Path("statisticAllCompanyOrderBrand.do")
	public String statisticAllCompanyOrderBrand(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllCompanyOrderBrandLogic().process(myParam);
	}
	/*
	 * 品牌
	 * */
	@POST
	@Path("statisticAllCompanyBrand.do")
	public String statisticAllCompanyBrand(
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticAllCompanyBrandLogic().process(myParam);
	}
	
	/*
	 * 	区域
	 * */
	@POST
	@Path("statisticAllCompanyOrderDscd.do")
	public String statisticAllCompanyOrderDscd(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllCompanyOrderDscdLogic().process(myParam);
	}
	
	/*
	 * 	金额
	 * */
	@POST
	@Path("statisticAllCompanyOrder.do")
	public String statisticAllCompanyOrder(
			@FormParam("name")  String name,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticAllCompanyOrderLogic().process(myParam);
	}
	
	/*
	 * 	每个月
	 * */
	@POST
	@Path("statisticAllCompanyOrderMonth.do")
	public String statisticAllCompanyOrderMonth(
			@FormParam("year")  String year,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		myParam.setYear(year);
		return new StatisticAllCompanyOrderMonthLogic().process(myParam);
	}
	
	/*
	 * 	统计在company里的种植任务产值
	 * */
	@POST
	@Path("statisticAllCompanyWork.do")
	public String statisticAllCompanyWork(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllCompanyWorkLogic().process(myParam);
	}
	
	/*
	 * 	企业列表
	 * */
	@POST
	@Path("getAllCompanyList.do")
	public String getAllCompanyList(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new GetAllCompanyListLogic().process(myParam);
	}
	
	/*
	 * 	各作物种植土地面积
	 * */
	@POST
	@Path("statisticAllWorkBrandArea.do")
	public String statisticAllWorkBrandArea(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllWorkBrandAreaLogic().process(myParam);
	}
	
	/*
	 * 	农资
	 * */
	@POST
	@Path("statisticAllTool.do")
	public String statisticAllTool(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllToolLogic().process(myParam);
	}
	

	/*
	 * 	农资top
	 * */
	@POST
	@Path("statisticAllToolTop.do")
	public String statisticAllToolTop(
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticAllToolTopLogic().process(myParam);
	}
	
	/*
	 * 	-总水量
	 * */
	@POST
	@Path("statisticAllWater.do")
	public String statisticAllWater(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllWaterLogic().process(myParam);
	}
	
	/*
	 * 	用水top
	 * */
	@POST
	@Path("statisticAllWaterTop.do")
	public String statisticAllWaterTop(
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticAllWaterTopLogic().process(myParam);
	}
	
	/*
	 * 	分类情况统计
	 * */
	@POST
	@Path("statisticAllBrandTypeCount.do")
	public String statisticAllBrandTypeCount(
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(null, null, request);
		return new StatisticAllBrandTypeCountLogic().process(myParam);
	}
	
}
