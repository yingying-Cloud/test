package com.jinpinghu.action;


import com.jinpinghu.logic.sellOrder.*;
import com.jinpinghu.logic.sellOrder.param.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("sellOrder")
@Produces("application/json;charset=UTF-8")
public class SellOrderAction extends BaseZAction{

	/**
	 * 新增订单
	 */
	@POST
	@Path("addSellOrder.do")
	public String addSellOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("carIds")  String carId,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@Context HttpServletRequest request) {
		AddSellOrderParam myParam = new AddSellOrderParam(userId, apiKey, request);
		myParam.setCarId(carId);
		myParam.setEnterpriseId(enterpriseId);
		return new AddSellOrderLogic().process(myParam);
	}
	@POST
	@Path("changeStatusSellOrder.do")
	public String changeStatusSellOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("status") Integer status,
			@Context HttpServletRequest request) {
		ChangeStatusSellOrderParam myParam = new ChangeStatusSellOrderParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new ChangeStatusSellOrderLogic().process(myParam);
	}
	
	/**
	 * 用户取消订单
	 */
	@POST
	@Path("cancleSellOrder.do")
	public String cancleSellOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("cancleInfo") String cancleInfo,
			@Context HttpServletRequest request) {
		CancleSellOrderParam myParam = new CancleSellOrderParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setCancleInfo(cancleInfo);
		return new CancleSellOrderLogic().process(myParam);
	}
	
	
	@POST
	@Path("delSellOrder.do")
	public String delSellOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelSellOrderParam myParam = new DelSellOrderParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelSellOrderLogic().process(myParam);
	}
	@POST
	@Path("detailSellOrder.do")
	public String detailSellOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DetailSellOrderParam myParam = new DetailSellOrderParam(userId, apiKey, request);
		myParam.setId(id);
		return new DetailSellOrderLogic().process(myParam);
	}

	@POST
	@Path("listAllSellOrder.do")
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
		return new ListAllSellOrderLogic().process(myParam);
	}

	@POST
	@Path("getSellTrademark.do")
	public String getSellTrademark(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("sellId") String sellId,
			@FormParam("carId") String carId,
			@Context HttpServletRequest request) {
		GetSellTrademarkParam myParam = new GetSellTrademarkParam(userId, apiKey, request);
		myParam.setSellId(sellId);
		myParam.setCarId(carId);
		return new GetSellTrademarkLogic().process(myParam);
	}

	@POST
	@Path("exportBySell.do")
	public String exportBySell(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("productName") String productName,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("isExport") String isExport,
			@FormParam("enterpriseName") String enterpriseName,
			@Context HttpServletRequest request) {
		ExportBySellParam myParam = new ExportBySellParam(userId, apiKey, request);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setIsExport(isExport);
		myParam.setProductName(productName);
		myParam.setStartTime(startTime);
		return new ExportBySellLogic().process(myParam);
	}
//	@POST
//	@Path("exportByEnterprise.do")
//	public String exportByEnterprise(
//			@FormParam("userId") String userId,
//			@FormParam("apiKey") String apiKey,
//			@FormParam("startTime") String startTime,
//			@FormParam("endTime") String endTime,
//			@FormParam("isExport") String isExport,
//			@FormParam("enterpriseName") String enterpriseName,
//			@Context HttpServletRequest request) {
//		ExportByEnterpriseParam myParam = new ExportByEnterpriseParam(userId, apiKey, request);
//		myParam.setEndTime(endTime);
//		myParam.setEnterpriseName(enterpriseName);
//		myParam.setIsExport(isExport);
//		myParam.setStartTime(startTime);
//		return new ExportByEnterpriseLogic().process(myParam);
//	}
	/**
	 * 统计该企业订单个数以及金额
	 */
	@POST
	@Path("getSellOrderSum.do")
	public String getSellOrderSum(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@Context HttpServletRequest request) {
		GetSellOrderSumParam myParam = new GetSellOrderSumParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetSellOrderSumLogic().process(myParam);
	}
	
	/*
	 * 修改购物车内商品单价和订单总价
	 */
	@POST
	@Path("changeSellOrderPrice.do")
	public String changeSellOrderPrice(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("carInfo")  String carInfo,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		ChangeSellOrderPriceParam myParam = new ChangeSellOrderPriceParam(userId, apiKey, request);
		myParam.setCarInfo(carInfo);
		myParam.setId(id);
		myParam.setStatus(status);
		return new ChangeSellOrderPriceLogic().process(myParam);
	}
	
	
}
