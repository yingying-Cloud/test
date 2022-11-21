package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.goodsGroup.GetGroupGoodsInfoLogic;
import com.jinpinghu.logic.goodsGroup.GetGroupListLogic;
import com.jinpinghu.logic.goodsGroup.param.GetGroupGoodsInfoParam;
import com.jinpinghu.logic.goodsGroup.param.GetGroupListParam;
import com.jinpinghu.logic.home.GetFamingListLogic;
import com.jinpinghu.logic.home.GetFarmingInfoLogic;
import com.jinpinghu.logic.home.GetFarmingPicLogic;
import com.jinpinghu.logic.home.GetUserTypeCountLogic;
import com.jinpinghu.logic.home.GetWorkFamingListLogic;
import com.jinpinghu.logic.home.GetWorkInfoLogic;
import com.jinpinghu.logic.home.PlantOrderStatusCountLogic;
import com.jinpinghu.logic.home.PlantServiceOrderStatusCountLogic;
import com.jinpinghu.logic.home.PostStatusCountLogic;
import com.jinpinghu.logic.home.StatisticalFarmingByWorkLogic;
import com.jinpinghu.logic.home.ToolOrderStatusCountLogic;
import com.jinpinghu.logic.home.param.GetFarmingInfoParam;
import com.jinpinghu.logic.home.param.GetFarmingListParam;
import com.jinpinghu.logic.home.param.GetFarmingPicParam;
import com.jinpinghu.logic.home.param.GetUserTypeCountParam;
import com.jinpinghu.logic.home.param.GetWorkFarmingListParam;
import com.jinpinghu.logic.home.param.GetWorkInfoParam;
import com.jinpinghu.logic.home.param.OrderStatusCountParam;
import com.jinpinghu.logic.home.param.PlantOrderStatusCountParam;
import com.jinpinghu.logic.home.param.PlantServiceOrderStatusCountParam;
import com.jinpinghu.logic.home.param.PostOrderStatusCountParam;
import com.jinpinghu.logic.home.param.StatisticalFarmingByWorkParam;

@Path("home")
@Produces("application/json;charset=UTF-8")
public class HomeAction extends BaseZAction{

	@POST
	@Path("getFarmingList.do")
	public String getFarmingList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workSn")  String workSn,
			@FormParam("id")  String workId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetFarmingListParam myParam = new GetFarmingListParam(userId, apiKey, request);
		myParam.setWorkSn(workSn);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setWorkId(StringUtils.isEmpty(workId) ? null : Integer.valueOf(workId));
		return new GetFamingListLogic().process(myParam);
	}
	
	@POST
	@Path("plantOrderStatusCount.do")
	public String plantOrderStatusCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("plantProtectionEnterpriseId")  String plantProtectionEnterpriseId,
			@FormParam("plantEnterpriseId")  String plantEnterpriseId,
			@Context HttpServletRequest request) {
		PlantOrderStatusCountParam myParam = new PlantOrderStatusCountParam(userId, apiKey, request);
		myParam.setPlantEnterpriseId(plantEnterpriseId);
		myParam.setPlantProtectionenterpriseId(plantProtectionEnterpriseId);
		return new PlantOrderStatusCountLogic().process(myParam);
	}
	
	@POST
	@Path("postOrderStatusCount.do")
	public String postOrderStatusCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("expertId")  String expertId,
			@Context HttpServletRequest request) {
		PostOrderStatusCountParam myParam = new PostOrderStatusCountParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setExpertId(expertId);
		return new PostStatusCountLogic().process(myParam);
	}
	
	@POST
	@Path("orderStatusCount.do")
	public String orderStatusCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolEnterpriseId")  String toolEnterpriseId,
			@FormParam("plantEnterpriseId")  String plantEnterpriseId,
			@Context HttpServletRequest request) {
		OrderStatusCountParam myParam = new OrderStatusCountParam(userId, apiKey, request);
		myParam.setToolEnterpriseId(toolEnterpriseId);
		myParam.setPlantEnterpriseId(plantEnterpriseId);
		return new ToolOrderStatusCountLogic().process(myParam);
	}
	@POST
	@Path("getWorkFarmingList.do")
	public String getWorkFarmingList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workSn")  String workSn,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetWorkFarmingListParam myParam = new GetWorkFarmingListParam(userId, apiKey, request);
		myParam.setWorkSn(workSn);
		myParam.setEnterpriseId(enterpriseId);
		return new GetWorkFamingListLogic().process(myParam);
	}
	
	@POST
	@Path("getUserTypeCount.do")
	public String getUserTypeCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetUserTypeCountParam myParam = new GetUserTypeCountParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetUserTypeCountLogic().process(myParam);
	}
	@POST
	@Path("getFarmingPic.do")
	public String getFarmingPic(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("workSn") String workSn,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		GetFarmingPicParam myParam = new GetFarmingPicParam(userId, apiKey, request);
		myParam.setWorkSn(workSn);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setName(name);
		return new GetFarmingPicLogic().process(myParam);
	}
	/*
	 * 获取种植计划详情
	 * */
	@POST
	@Path("getWorkInfo.do")
	public String getWorkInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetWorkInfoParam myParam = new GetWorkInfoParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetWorkInfoLogic().process(myParam);
	}
	
	@POST
	@Path("statisticsFarmingByWork.do")
	public String statisticsFarmingByWork(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workId") String workId,
			@Context HttpServletRequest request) {
		StatisticalFarmingByWorkParam myParam = new StatisticalFarmingByWorkParam(userId, apiKey, request);
		myParam.setWorkId(workId);
		return new StatisticalFarmingByWorkLogic().process(myParam);
	}
	
	@POST
	@Path("getFarmingInfo.do")
	public String getFarmingInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workId") String workId,
			@FormParam("time") String time,
			@Context HttpServletRequest request) {
		GetFarmingInfoParam myParam = new GetFarmingInfoParam(userId, apiKey, request);
		myParam.setWorkId(workId);
		myParam.setTime(time);
		return new GetFarmingInfoLogic().process(myParam);
	}
	
	@POST
	@Path("plantServiceOrderStatusCount.do")
	public String plantServiceOrderStatusCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("plantServiceEnterpriseId")  String plantServiceEnterpriseId,
			@FormParam("plantEnterpriseId")  String plantEnterpriseId,
			@Context HttpServletRequest request) {
		PlantServiceOrderStatusCountParam myParam = new PlantServiceOrderStatusCountParam(userId, apiKey, request);
		myParam.setPlantEnterpriseId(plantEnterpriseId);
		myParam.setPlantServiceenterpriseId(plantServiceEnterpriseId);
		return new PlantServiceOrderStatusCountLogic().process(myParam);
	}
	
}
