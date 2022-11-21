package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.plantService.AddOrUpdatePlantServiceLogic;
import com.jinpinghu.logic.plantService.ChangePlantServiceStatusLogic;
import com.jinpinghu.logic.plantService.DelPlantServiceLogic;
import com.jinpinghu.logic.plantService.GetPlantServiceInfoLogic;
import com.jinpinghu.logic.plantService.GetPlantServiceListLogic;
import com.jinpinghu.logic.plantService.param.AddOrUpdatePlantServiceParam;
import com.jinpinghu.logic.plantService.param.ChangePlantServiceStatusParam;
import com.jinpinghu.logic.plantService.param.DelPlantServiceParam;
import com.jinpinghu.logic.plantService.param.GetPlantServiceInfoParam;
import com.jinpinghu.logic.plantService.param.GetPlantServiceListParam;

@Produces("application/json;charset=UTF-8")
@Path("plantService")
public class PlantServiceAction extends BaseZAction{

	@POST
	@Path("addOrUpdatePlantService.do")
	public String addOrUpdatePlantService(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("content")  String content,
			@FormParam("name")  String name,
			@FormParam("orderDescribe")  String orderDescribe,
			@FormParam("price")  String price,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("serverType")  String serverType,
			@FormParam("json")  String json,
			@FormParam("serverTime")  String serverTime,
			@Context HttpServletRequest request) {
		AddOrUpdatePlantServiceParam myParam = new AddOrUpdatePlantServiceParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setContent(content);
		myParam.setName(name);
		myParam.setOrderDescribe(orderDescribe);
		myParam.setPrice(price);
		myParam.setServerType(serverType);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setJson(json);
		myParam.setServerTime(serverTime);
		return new AddOrUpdatePlantServiceLogic().process(myParam);
	}
	
	
	@POST
	@Path("delPlantService.do")
	public String delPlantService(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		DelPlantServiceParam myParam = new DelPlantServiceParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelPlantServiceLogic().process(myParam);
	}
	
	@POST
	@Path("getPlantServiceInfo.do")
	public String getPlantServiceInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetPlantServiceInfoParam myParam = new GetPlantServiceInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetPlantServiceInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getPlantServiceList.do")
	public String getPlantServiceList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name ,
			@FormParam("enterpriseName")  String enterpriseName,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("dscd")  String dscd,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("lowPrice")  String lowPrice,
			@FormParam("highPrice")  String highPrice,
			@FormParam("etName")  String etName,
			@FormParam("serverType")  String serverType,
			@FormParam("orderby")  String orderby,
			@FormParam("sortDirection")  String sortDirection,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		GetPlantServiceListParam myParam = new GetPlantServiceListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setLowPrice(lowPrice);
		myParam.setHighPrice(highPrice);
		myParam.setEtName(etName);
		myParam.setServerType(serverType);
		myParam.setOrderby(orderby);
		myParam.setSortDirection(sortDirection);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setStatus(status);
		return new GetPlantServiceListLogic().process(myParam);
	}
	
	@POST
	@Path("changePlantServiceStatus.do")
	public String changePlantServiceStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		ChangePlantServiceStatusParam myParam = new ChangePlantServiceStatusParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new ChangePlantServiceStatusLogic().process(myParam);
	}
	
}
