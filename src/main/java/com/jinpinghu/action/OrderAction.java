package com.jinpinghu.action;


import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.logic.brandOrder.GetBrandOrderSumLogic;
import com.jinpinghu.logic.brandOrder.param.GetBrandOrderSumParam;
import com.jinpinghu.logic.order.AddCompleteOrderLogic;
import com.jinpinghu.logic.order.AddLinkOrderInfoLogic;
import com.jinpinghu.logic.order.AddOrderLogic;
import com.jinpinghu.logic.order.AddToBOrderLogic;
import com.jinpinghu.logic.order.CancleOrderLogic;
import com.jinpinghu.logic.order.ChangeOrderCheckLogic;
import com.jinpinghu.logic.order.ChangeStatusOrderLogic;
import com.jinpinghu.logic.order.DelLinkOrderInfoLogic;
import com.jinpinghu.logic.order.DelOrderLogic;
import com.jinpinghu.logic.order.DetailOrderLogic;
import com.jinpinghu.logic.order.DetailToBOrderInfoLogic;
import com.jinpinghu.logic.order.FindLinkOrderInfoListLogin;
import com.jinpinghu.logic.order.GetOrderSumLogic;
import com.jinpinghu.logic.order.GetTbLinkOrderInfoLogic;
import com.jinpinghu.logic.order.ListAllOrderLogic;
import com.jinpinghu.logic.order.ListToolOrderByBuyEnterpriseIdLogic;
import com.jinpinghu.logic.order.StatisticToolOrderByBuyEnterpriseIdLogic;
import com.jinpinghu.logic.order.UpdLinkOrderInfoLogic;
import com.jinpinghu.logic.order.param.AddCompleteOrderParam;
import com.jinpinghu.logic.order.param.AddLinkOrderInfoParam;
import com.jinpinghu.logic.order.param.AddOrderParam;
import com.jinpinghu.logic.order.param.AddToBOrderParam;
import com.jinpinghu.logic.order.param.CancleOrderParam;
import com.jinpinghu.logic.order.param.ChangeOrderCheckParam;
import com.jinpinghu.logic.order.param.ChangeStatusOrderParam;
import com.jinpinghu.logic.order.param.DelLinkOrderInfoParam;
import com.jinpinghu.logic.order.param.DelOrderParam;
import com.jinpinghu.logic.order.param.DetailOrderParam;
import com.jinpinghu.logic.order.param.DetailToBOrderInfoParam;
import com.jinpinghu.logic.order.param.FindLinkOrderInfoListParam;
import com.jinpinghu.logic.order.param.GetOrderSumParam;
import com.jinpinghu.logic.order.param.GetTbLinkOrderInfoParam;
import com.jinpinghu.logic.order.param.ListAllOrderParam;
import com.jinpinghu.logic.order.param.ListToolOrderByBuyEnterpriseIdParam;
import com.jinpinghu.logic.order.param.StatisticToolOrderByBuyEnterpriseIdParam;
import com.jinpinghu.logic.order.param.UpdLinkOrderInfoParam;
import com.jinpinghu.logic.shoppingCar.GetShoppingCarListLogic;
import com.jinpinghu.logic.shoppingCar.param.GetShoppingCarListParam;

import fw.jbiz.ext.memcache.ZCacheManager;
import fw.jbiz.ext.memcache.interfaces.ICache;

@Path("order")
@Produces("application/json;charset=UTF-8")
public class OrderAction extends BaseZAction{

	/**
	 * 新增订单
	 */
	@POST
	@Path("addOrder.do")
	public String addOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("carIds")  String carId,
			@FormParam("enterpriseId")  Integer plantEnterpriseId,
			@Context HttpServletRequest request) {
		AddOrderParam myParam = new AddOrderParam(userId, apiKey, request);
		myParam.setCarId(carId);
		myParam.setPlantEnterpriseId(plantEnterpriseId);
		return new AddOrderLogic().process(myParam);
	}
	@POST
	@Path("changeStatusOrder.do")
	public String changeStatusOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("status") Integer status,
			@Context HttpServletRequest request) {
		ChangeStatusOrderParam myParam = new ChangeStatusOrderParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new ChangeStatusOrderLogic().process(myParam);
	}
	
	/**
	 * 用户取消订单
	 */
	@POST
	@Path("cancleOrder.do")
	public String cancleOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("cancleInfo") String cancleInfo,
			@Context HttpServletRequest request) {
		CancleOrderParam myParam = new CancleOrderParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setCancleInfo(cancleInfo);
		return new CancleOrderLogic().process(myParam);
	}
	
	
	@POST
	@Path("delOrder.do")
	public String delOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelOrderParam myParam = new DelOrderParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelOrderLogic().process(myParam);
	}
	@POST
	@Path("detailOrder.do")
	public String detailOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DetailOrderParam myParam = new DetailOrderParam(userId, apiKey, request);
		myParam.setId(id);
		return new DetailOrderLogic().process(myParam);
	}
	
	@POST
	@Path("listAllOrder.do")
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
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("selectAll") String selectAll,
			@FormParam("uniformPrice") String uniformPrice,
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
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setCheck(check);
		myParam.setName(name);
		myParam.setIsValidation(isValidation);
		myParam.setSelectAll(selectAll);
		myParam.setUniformPrice(uniformPrice);
		return new ListAllOrderLogic().process(myParam);
	}
	/**
	 * 新增订单
	 */
	@POST
	@Path("addCompleteOrder.do")
	public String addCompleteOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("carJa")  String carJa,
			@FormParam("orderInfoId")String orderInfoId,
			@FormParam("linkOrderInfoId")String linkOrderInfoId,
			@FormParam("name")String name,
			@FormParam("creditCode")String creditCode,
			@FormParam("legalPerson")String legalPerson,
			@FormParam("legalPersonIdcard")String legalPersonIdcard,
			@FormParam("linkPeople")String linkPeople,
			@FormParam("linkMobile")String linkMobile,
			@FormParam("address")String address,
			@FormParam("file")String file,
			@FormParam("area")String area,
			@FormParam("type")String type,
			@Context HttpServletRequest request) {
		AddCompleteOrderParam myParam = new AddCompleteOrderParam(userId, apiKey, request);
		myParam.setCarJa(carJa);
		myParam.setAddress(address);
		myParam.setCreditCode(creditCode);
		myParam.setLegalPerson(legalPerson);
		myParam.setLegalPersonIdcard(legalPersonIdcard);
		myParam.setLinkMobile(linkMobile);
		myParam.setLinkPeople(linkPeople);
		myParam.setName(name);
		myParam.setArea(area);
		myParam.setFile(file);
		myParam.setOrderInfoId(orderInfoId);
		myParam.setType(type);
		myParam.setLinkOrderInfoId(linkOrderInfoId);
		return new AddCompleteOrderLogic().process(myParam);
	}
	/*
	 * 核定补贴
	 */
	@POST
	@Path("changeOrderCheck.do")
	public String changeOrderCheck(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("checkJa") String checkJa,
			@Context HttpServletRequest request) {
		ChangeOrderCheckParam myParam = new ChangeOrderCheckParam(userId, apiKey, request);
		myParam.setCheckJa(checkJa);
		return new ChangeOrderCheckLogic().process(myParam);
	}
	@POST
	@Path("getShoppingCarList.do")
	public String getMyShoppingCarList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("unit") String unit,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("buyEnterpriseName") String buyEnterpriseName,
			@FormParam("toolName") String  toolName,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("isExport") String isExport,
			@Context HttpServletRequest request) {
		GetShoppingCarListParam myParam = new GetShoppingCarListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setUnit(unit);
		myParam.setToolName(toolName);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setIsExport(isExport);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setBuyEnterpriseName(buyEnterpriseName);
		return new GetShoppingCarListLogic().process(myParam);
	}

	@POST
	@Path("addLinkOrderInfo.do")
	public String addLinkOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("zoneName") String zoneName,
			@FormParam("name") String name,
			@FormParam("creditCode") String creditCode,
			@FormParam("legalPerson") String legalPerson,
			@FormParam("legalPersonIdcard") String legalPersonIdcard,
			@FormParam("linkPeople") String linkPeople,
			@FormParam("linkMobile") String linkMobile,
			@FormParam("address") String address,
			@FormParam("inputTime") String inputTime,
			@FormParam("area") String area,
			@FormParam("type") String type,
			@FormParam("file") String file,
			@Context HttpServletRequest request){
		AddLinkOrderInfoParam myParam=new AddLinkOrderInfoParam(userId,apiKey,request);
		TbLinkOrderInfo tbLinkOrderInfo=new TbLinkOrderInfo();
		tbLinkOrderInfo.setEnterpriseId(StringUtil.isNullOrEmpty(enterpriseId)?null:Integer.valueOf(enterpriseId));
		tbLinkOrderInfo.setZoneName(StringUtil.isNullOrEmpty(zoneName)?null:zoneName.trim());
		tbLinkOrderInfo.setName(StringUtil.isNullOrEmpty(name)?null:name);
		tbLinkOrderInfo.setCreditCode(StringUtil.isNullOrEmpty(creditCode)?null:creditCode);
		tbLinkOrderInfo.setLegalPerson(StringUtil.isNullOrEmpty(legalPerson)?null:legalPerson);
		tbLinkOrderInfo.setLegalPersonIdcard(StringUtil.isNullOrEmpty(legalPersonIdcard)?null:legalPersonIdcard.trim());
		tbLinkOrderInfo.setLinkPeople(StringUtil.isNullOrEmpty(linkPeople)?null:linkPeople);
		tbLinkOrderInfo.setLinkMobile(StringUtil.isNullOrEmpty(linkMobile)?null:linkMobile);
		tbLinkOrderInfo.setAddress(StringUtil.isNullOrEmpty(address)?null:address);
		tbLinkOrderInfo.setInputTime(StringUtil.isNullOrEmpty(inputTime)?null:DateTimeUtil.formatTime2(inputTime));
		tbLinkOrderInfo.setArea(StringUtil.isNullOrEmpty(area)?null:area);
		tbLinkOrderInfo.setType(StringUtil.isNullOrEmpty(type)?null:Integer.valueOf(type.trim()));
		tbLinkOrderInfo.setDelFlag(0);
		myParam.setTbLinkOrderInfo(tbLinkOrderInfo);
		myParam.setFile(file);
		return new AddLinkOrderInfoLogic().process(myParam);
	}

	@POST
	@Path("delLinkOrderInfo.do")
	public String delLinkOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request){
		DelLinkOrderInfoParam myParam=new DelLinkOrderInfoParam(userId,apiKey,request);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id.trim()));
		return new DelLinkOrderInfoLogic().process(myParam);
	}

	@POST
	@Path("updLinkOrderInfo.do")
	public String updLinkOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("zoneName") String zoneName,
			@FormParam("name") String name,
			@FormParam("creditCode") String creditCode,
			@FormParam("legalPerson") String legalPerson,
			@FormParam("legalPersonIdcard") String legalPersonIdcard,
			@FormParam("linkPeople") String linkPeople,
			@FormParam("linkMobile") String linkMobile,
			@FormParam("address") String address,
			@FormParam("area") String area,
			@FormParam("type") String type,
			@FormParam("file") String file,
			@Context HttpServletRequest request){
		UpdLinkOrderInfoParam myParam=new UpdLinkOrderInfoParam(userId,apiKey,request);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id.trim()));
		myParam.setEnterpriseId(StringUtil.isNullOrEmpty(enterpriseId)?null:Integer.valueOf(enterpriseId));
		myParam.setZoneName(StringUtil.isNullOrEmpty(zoneName)?null:zoneName.trim());
		myParam.setName(StringUtil.isNullOrEmpty(name)?null:name);
		myParam.setCreditCode(StringUtil.isNullOrEmpty(creditCode)?null:creditCode);
		myParam.setLegalPerson(StringUtil.isNullOrEmpty(legalPerson)?null:legalPerson);
		myParam.setLegalPersonIdcard(StringUtil.isNullOrEmpty(legalPersonIdcard)?null:legalPersonIdcard);
		myParam.setLinkPeople(StringUtil.isNullOrEmpty(linkPeople)?null:linkPeople);
		myParam.setLinkMobile(StringUtil.isNullOrEmpty(linkMobile)?null:linkMobile);
		myParam.setAddress(StringUtil.isNullOrEmpty(address)?null:address);
		myParam.setArea(StringUtil.isNullOrEmpty(area)?null:area);
		myParam.setType(StringUtil.isNullOrEmpty(type)?null:Integer.valueOf(type.trim()));
		myParam.setFile(file);
		return new UpdLinkOrderInfoLogic().process(myParam);
	}

	@POST
	@Path("findLinkOrderInfoList.do")
	public String findLinkOrderInfoList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("type") String type,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request){
		Calendar calendar = Calendar.getInstance();
		if (!StringUtil.isNullOrEmpty(endTime)) {
			calendar.setTime(StringUtil.isNullOrEmpty(endTime) ? null : DateTimeUtil.formatTime(endTime));
			calendar.add(calendar.DATE, 1);
		}
		FindLinkOrderInfoListParam myParam=new FindLinkOrderInfoListParam(userId,apiKey,request);
		myParam.setName(StringUtil.isNullOrEmpty(name)?null:name);
		myParam.setType(StringUtil.isNullOrEmpty(type)?null:Integer.valueOf(type));
		myParam.setStartTime(StringUtil.isNullOrEmpty(startTime)?null:DateTimeUtil.formatTime(startTime));
		myParam.setEndTime(StringUtil.isNullOrEmpty(endTime)?null:calendar.getTime());
		myParam.setNowPage(StringUtil.isNullOrEmpty(nowPage)?null:Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtil.isNullOrEmpty(pageCount)?null:Integer.valueOf(pageCount));
		return new FindLinkOrderInfoListLogin().process(myParam);
	}

	@POST
	@Path("getTbLinkOrderInfo.do")
	public String getTbLinkOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request){
		GetTbLinkOrderInfoParam myParam=new GetTbLinkOrderInfoParam(userId,apiKey,request);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id.trim()));
		return new GetTbLinkOrderInfoLogic().process(myParam);
	}
	
	
	/**
	 * 统计该企业订单个数以及金额
	 */
	@POST
	@Path("getOrderSum.do")
	public String getBrandOrderSum(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@Context HttpServletRequest request) {
		GetOrderSumParam myParam = new GetOrderSumParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetOrderSumLogic().process(myParam);
	}
	
	/**
	 * 面向企业订单
	 * @param userId
	 * @param apiKey
	 * @param sellEnterpriseId 农资店企业id
	 * @param buyEnterpriseId 购买企业id
	 * @param orderInfo 订单详情
	 * @param idcard 购买人员身份证号
	 * @param name 购买人员姓名
	 * @param sex 购买人员性别
	 * @param nation 购买人员民族
	 * @param address 购买人员地址
	 * @param request
	 * @return
	 */
	@POST
	@Path("addToBOrder.do")
	public String addToBOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("sellEnterpriseId") String sellEnterpriseId,
			@FormParam("buyEnterpriseId") String buyEnterpriseId,
			@FormParam("orderInfo") String orderInfo,
			@FormParam("idcard") String idcard,
			@FormParam("name") String name,
			@FormParam("sex") String sex,
			@FormParam("nation") String nation,
			@FormParam("address") String address,
			@FormParam("price") String price,
			@FormParam("phone") String phone,
			@Context HttpServletRequest request) {
		ICache linkInfoLock = ZCacheManager.getInstance("linkInfoLock");
		if (!StringUtils.isEmpty(idcard)) {
			while("1".equals(linkInfoLock.get(idcard))) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			linkInfoLock.add(idcard, "1");
		}
		AddToBOrderParam myParam = new AddToBOrderParam(userId,apiKey,request);
		myParam.setSellEnterpriseId(sellEnterpriseId);
		myParam.setBuyEnterpriseId(buyEnterpriseId);
		myParam.setOrderInfo(orderInfo);
		myParam.setIdcard(idcard);
		myParam.setName(name);
		myParam.setSex(sex);
		myParam.setNation(nation);
		myParam.setAddress(address);
		myParam.setPrice(price);
		myParam.setPhone(phone);
		String result = new AddToBOrderLogic().process(myParam);
		if (!StringUtils.isEmpty(idcard)) {
			linkInfoLock.remove(idcard);
		}
		return result;
	}
	
	@POST
	@Path("listToolOrderByBuyEnterpriseId.do")
	public String listToolOrderByBuyEnterpriseId(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("buyEnterpriseId") String buyEnterpriseId,
			@FormParam("toolName") String toolName,
			@FormParam("productionUnit") String productionUnit,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("sellEnterpriseName") String sellEnterpriseName,
			@FormParam("orderNumber") String orderNumber,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		ListToolOrderByBuyEnterpriseIdParam myParam = new ListToolOrderByBuyEnterpriseIdParam(userId,apiKey,request);
		myParam.setBuyEnterpriseId(StringUtils.isEmpty(buyEnterpriseId) ? null : Integer.valueOf(buyEnterpriseId));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setToolName(toolName);
		myParam.setProductionUnit(productionUnit);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseName(sellEnterpriseName);
		myParam.setOrderNumber(orderNumber);
		return new ListToolOrderByBuyEnterpriseIdLogic().process(myParam);
	}
	
	@POST
	@Path("statisticToolOrderByBuyEnterpriseId.do")
	public String statisticToolOrderByBuyEnterpriseId(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("buyEnterpriseId") String buyEnterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		StatisticToolOrderByBuyEnterpriseIdParam myParam = new StatisticToolOrderByBuyEnterpriseIdParam(userId,apiKey,request);
		myParam.setBuyEnterpriseId(StringUtils.isEmpty(buyEnterpriseId) ? null : Integer.valueOf(buyEnterpriseId));
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new StatisticToolOrderByBuyEnterpriseIdLogic().process(myParam);
	}
	
	/**
	 * 	企业购买记录详情
	 * @param userId
	 * @param apiKey
	 * @param id
	 * @return
	 */
	@POST
	@Path("detailToBOrderInfo.do")
	public String detailToBOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DetailToBOrderInfoParam myParam = new DetailToBOrderInfoParam(userId,apiKey,request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new DetailToBOrderInfoLogic().process(myParam);
	}
	
}
