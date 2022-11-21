package com.jinpinghu.action;


import com.jinpinghu.logic.brandOrder.*;
import com.jinpinghu.logic.brandOrder.param.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("brandOrder")
@Produces("application/json;charset=UTF-8")
public class BrandOrderAction extends BaseZAction{

	/**
	 * 新增订单
	 */
	@POST
	@Path("addBrandOrder.do")
	public String addBrandOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("carIds")  String carId,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@Context HttpServletRequest request) {
		AddBrandOrderParam myParam = new AddBrandOrderParam(userId, apiKey, request);
		myParam.setCarId(carId);
		myParam.setEnterpriseId(enterpriseId);
		return new AddBrandOrderLogic().process(myParam);
	}
	@POST
	@Path("changeStatusBrandOrder.do")
	public String changeStatusBrandOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("status") Integer status,
			@Context HttpServletRequest request) {
		ChangeStatusBrandOrderParam myParam = new ChangeStatusBrandOrderParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new ChangeStatusBrandOrderLogic().process(myParam);
	}
	
	/**
	 * 用户取消订单
	 */
	@POST
	@Path("cancleBrandOrder.do")
	public String cancleBrandOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("cancleInfo") String cancleInfo,
			@Context HttpServletRequest request) {
		CancleBrandOrderParam myParam = new CancleBrandOrderParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setCancleInfo(cancleInfo);
		return new CancleBrandOrderLogic().process(myParam);
	}
	
	
	@POST
	@Path("delBrandOrder.do")
	public String delBrandOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelBrandOrderParam myParam = new DelBrandOrderParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelBrandOrderLogic().process(myParam);
	}
	@POST
	@Path("detailBrandOrder.do")
	public String detailBrandOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DetailBrandOrderParam myParam = new DetailBrandOrderParam(userId, apiKey, request);
		myParam.setId(id);
		return new DetailBrandOrderLogic().process(myParam);
	}

	@POST
	@Path("listAllBrandOrder.do")
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
		return new ListAllBrandOrderLogic().process(myParam);
	}
	@POST
	@Path("addShoppingCarNum.do")
	public String addShoppingCarNum(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("carId") String carId,
			@FormParam("numJa") String numJa,
			@Context HttpServletRequest request) {
		AddShoppingCarNumParam myParam = new AddShoppingCarNumParam(userId, apiKey, request);
		myParam.setCarId(carId);
		myParam.setNumJa(numJa);
		return new AddShoppingCarNumLogic().process(myParam);
	}

	@POST
	@Path("getBrandTrademark.do")
	public String getBrandTrademark(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("brandId") String brandId,
			@FormParam("carId") String carId,
			@Context HttpServletRequest request) {
		GetBrandTrademarkParam myParam = new GetBrandTrademarkParam(userId, apiKey, request);
		myParam.setBrandId(brandId);
		myParam.setCarId(carId);
		return new GetBrandTrademarkLogic().process(myParam);
	}

	@POST
	@Path("exportByBrand.do")
	public String exportByBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("productName") String productName,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("isExport") String isExport,
			@FormParam("enterpriseName") String enterpriseName,
			@Context HttpServletRequest request) {
		ExportByBrandParam myParam = new ExportByBrandParam(userId, apiKey, request);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setIsExport(isExport);
		myParam.setProductName(productName);
		myParam.setStartTime(startTime);
		return new ExportByBrandLogic().process(myParam);
	}
	@POST
	@Path("exportByEnterprise.do")
	public String exportByEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("isExport") String isExport,
			@FormParam("enterpriseName") String enterpriseName,
			@Context HttpServletRequest request) {
		ExportByEnterpriseParam myParam = new ExportByEnterpriseParam(userId, apiKey, request);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setIsExport(isExport);
		myParam.setStartTime(startTime);
		return new ExportByEnterpriseLogic().process(myParam);
	}
	/**
	 * 统计该企业订单个数以及金额
	 */
	@POST
	@Path("getBrandOrderSum.do")
	public String getBrandOrderSum(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@Context HttpServletRequest request) {
		GetBrandOrderSumParam myParam = new GetBrandOrderSumParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetBrandOrderSumLogic().process(myParam);
	}
	
	/*
	 * 修改购物车内商品单价和订单总价
	 */
	@POST
	@Path("changeBrandOrderPrice.do")
	public String changeBrandOrderPrice(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("carInfo")  String carInfo,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		ChangeBrandOrderPriceParam myParam = new ChangeBrandOrderPriceParam(userId, apiKey, request);
		myParam.setCarInfo(carInfo);
		myParam.setId(id);
		myParam.setStatus(status);
		return new ChangeBrandOrderPriceLogic().process(myParam);
	}
	/**
	 * 统计该企业销售订单个数以及金额
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
		GetBrandOrderSumParam myParam = new GetBrandOrderSumParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetEnterpriseBrandOrderSumLogic().process(myParam);
	}
	
}
