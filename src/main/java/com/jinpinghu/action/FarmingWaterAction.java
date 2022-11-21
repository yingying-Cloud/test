package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.farmingWater.AddFarmingWaterLogic;
import com.jinpinghu.logic.farmingWater.DelFarmingWaterLogic;
import com.jinpinghu.logic.farmingWater.GetFarmingWaterInfoLogic;
import com.jinpinghu.logic.farmingWater.GetFarmingWaterListLogic;
import com.jinpinghu.logic.farmingWater.param.AddOrUpdateFarmingWaterParam;
import com.jinpinghu.logic.farmingWater.param.GetFarmingWaterListParam;
import com.jinpinghu.logic.plant.param.SimpleParam;


@Path("farmingWater")
@Produces("application/json;charset=UTF-8")
public class FarmingWaterAction extends BaseZAction {
	
	@POST
	@Path("addOrUpdateFarmingWater.do")
	public String addOrUpdateFarmingWater(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("workId") String workId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("traffic") String traffic,
			@FormParam("startIrrigationTime") String startIrrigationTime,
			@FormParam("waterAmount")  String waterAmount,
			@FormParam("endIrrigationTime")  String endIrrigationTime,
			@FormParam("describe")  String describe,
			@FormParam("file")  String file,
			@Context HttpServletRequest request) {
		AddOrUpdateFarmingWaterParam myParam = new AddOrUpdateFarmingWaterParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setWorkId(workId);
		myParam.setId(id);
		myParam.setFile(file);
		myParam.setDescribe(describe);
		myParam.setEndIrrigationTime(endIrrigationTime);
		myParam.setWaterAmount(waterAmount);
		myParam.setStartIrrigationTime(startIrrigationTime);
		myParam.setTraffic(traffic);
		myParam.setId(id);
		return new AddFarmingWaterLogic().process(myParam);
	}
	
	/*
	 *获取农事水量列表
	 * */
	@POST
	@Path("getFarmingWaterList.do")
	public String getFarmingWaterList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("addPeople")  String addPeople,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("workSn")  String workSn,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetFarmingWaterListParam myParam = new GetFarmingWaterListParam(userId,apiKey, request);
		myParam.setAddPeople(addPeople);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setWorkSn(workSn);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetFarmingWaterListLogic().process(myParam);
	}
	
	/*
	 * 删除农事水量
	 * */
	@POST
	@Path("DelFarmingWater.do")
	public String DelFarmingWater(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelFarmingWaterLogic().process(myParam);
	}
	
	/*
	 * 获取农事水量详情
	 * */
	@POST
	@Path("getFarmingWaterInfo.do")
	public String getFarmingWaterInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetFarmingWaterInfoLogic().process(myParam);
	}
	
}
