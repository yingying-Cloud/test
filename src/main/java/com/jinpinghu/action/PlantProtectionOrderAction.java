package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.brandOrder.GetBrandOrderSumLogic;
import com.jinpinghu.logic.brandOrder.param.GetBrandOrderSumParam;
import com.jinpinghu.logic.order.param.DelOrderParam;
import com.jinpinghu.logic.plantProtectionOrder.AddPlantProtectionOrderLogic;
import com.jinpinghu.logic.plantProtectionOrder.CancelOrderLogic;
import com.jinpinghu.logic.plantProtectionOrder.CompleteOrderLogic;
import com.jinpinghu.logic.plantProtectionOrder.DelOrderLogic;
import com.jinpinghu.logic.plantProtectionOrder.GetOrderInfoLogic;
import com.jinpinghu.logic.plantProtectionOrder.GetPlantOrderSumLogic;
import com.jinpinghu.logic.plantProtectionOrder.GetPlantProtectionOrderListLogic;
import com.jinpinghu.logic.plantProtectionOrder.UpdateStatusLogic;
import com.jinpinghu.logic.plantProtectionOrder.param.AddPlantProtectionOrderParam;
import com.jinpinghu.logic.plantProtectionOrder.param.CancelOrderParam;
import com.jinpinghu.logic.plantProtectionOrder.param.CompleteOrderParam;
import com.jinpinghu.logic.plantProtectionOrder.param.GetOrderInfoParam;
import com.jinpinghu.logic.plantProtectionOrder.param.GetPlantOrderSumParam;
import com.jinpinghu.logic.plantProtectionOrder.param.GetPlantProtectionOrderListParam;
import com.jinpinghu.logic.plantProtectionOrder.param.UpdateStatusParam;

@Produces("application/json;charset=UTF-8")
@Path("plantProtectionOrder")
public class PlantProtectionOrderAction extends BaseZAction{

	/**
	 * 	添加植保订单
	 * @createTime:2019-05-20 16:07:29
	 * @author:harrychao
	 */
	@POST
	@Path("addPlantProtectionOrder.do")
	public String addPlantProtectionOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("plantProtectionId")  String plantProtectionId,
			@FormParam("area")  String area,
			@FormParam("name")  String name,
			@FormParam("serviceStartTime")  String serviceStartTime,
			@FormParam("serviceEndTime")  String serviceEndTime,
			@FormParam("address") String address,
			@FormParam("contact") String contact,
			@FormParam("phone") String phone,
			@Context HttpServletRequest request) {
		AddPlantProtectionOrderParam myParam = new AddPlantProtectionOrderParam(userId, apiKey, request);
		myParam.setPlantProtectionId(plantProtectionId);
		myParam.setArea(area);
		myParam.setName(name);
		myParam.setServiceStartTime(serviceStartTime);
		myParam.setServiceEndTime(serviceEndTime);
		myParam.setAddress(address);
		myParam.setContact(contact);
		myParam.setPhone(phone);
		return new AddPlantProtectionOrderLogic().process(myParam);
	}
	
	/**
	 * 	取消订单
	 * @createTime:2019-05-20 15:55:39
	 * @author:harrychao
	 */
	@POST
	@Path("cancelOrder.do")
	public String CancelOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("cancelInfo")  String cancelInfo,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		CancelOrderParam myParam = new CancelOrderParam(userId, apiKey, request);
		myParam.setCancelInfo(cancelInfo);
		myParam.setId(id);
		return new CancelOrderLogic().process(myParam);
	}
	
	/**
	 * 	完成订单
	 * @createTime:2019-05-20 15:56:09
	 * @author:harrychao
	 */
	@POST
	@Path("completeOrder.do")
	public String completeOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("area")  String area,
			@FormParam("id")  String id,
			@FormParam("completeTime")  String completeTime,
			@FormParam("content")  String content,
			@FormParam("file")  String file,
			@Context HttpServletRequest request) {
		CompleteOrderParam myParam = new CompleteOrderParam(userId, apiKey, request);
		myParam.setArea(area);
		myParam.setCompleteTime(completeTime);
		myParam.setContent(content);
		myParam.setId(id);
		myParam.setFile(file);
		return new CompleteOrderLogic().process(myParam);
	}
	
	/**
	 * 	删除订单
	 * @createTime:2019-05-20 15:59:20
	 * @author:harrychao
	 */
	@POST
	@Path("delOrder.do")
	public String delOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		DelOrderParam myParam = new DelOrderParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelOrderLogic().process(myParam);
	}
	
	/**
	 * 	获取订单详情
	 * @createTime:2019-05-20 16:00:21
	 * @author:harrychao
	 */
	@POST
	@Path("getOrderInfo.do")
	public String getOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetOrderInfoParam myParam = new GetOrderInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetOrderInfoLogic().process(myParam);
	}
	
	/**
	 * 	获取订单列表
	 * @createTime:2019-05-20 16:04:05
	 * @author:harrychao
	 */
	@POST
	@Path("getOrderList.do")
	public String getOrderList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("name")  String name,
			@FormParam("status")  String status,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("type")  String type,
			@FormParam("pageCount")  String pageCount,
			@FormParam("nowPage")  String nowPage,
			@Context HttpServletRequest request) {
		GetPlantProtectionOrderListParam myParam = new GetPlantProtectionOrderListParam(userId, apiKey, request);
		myParam.setEndTime(endTime);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setStartTime(startTime);
		myParam.setStatus(status);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setType(type);
		return new GetPlantProtectionOrderListLogic().process(myParam);
	}
	
	/**
	 * 	更新状态
	 * @createTime:2019-05-20 16:05:57
	 * @author:harrychao
	 */
	@POST
	@Path("updateStatus.do")
	public String updateStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		UpdateStatusParam myParam = new UpdateStatusParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new UpdateStatusLogic().process(myParam);
	}
	
	/**
	 * 统计该企业订单个数以及金额
	 */
	@POST
	@Path("getPlantOrderSum.do")
	public String getPlantOrderSum(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@Context HttpServletRequest request) {
		GetPlantOrderSumParam myParam = new GetPlantOrderSumParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetPlantOrderSumLogic().process(myParam);
	}
	
}
