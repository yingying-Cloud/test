package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.plantProtect.AddOrUpdatePlantProtectLogic;
import com.jinpinghu.logic.plantProtect.ChangePlantProtectStatusLogic;
import com.jinpinghu.logic.plantProtect.DelPlantProtectLogic;
import com.jinpinghu.logic.plantProtect.GetPlantProtectInfoLogic;
import com.jinpinghu.logic.plantProtect.GetPlantProtectionListLogic;
import com.jinpinghu.logic.plantProtect.param.AddOrUpdatePlantProtectParam;
import com.jinpinghu.logic.plantProtect.param.ChangePlantProtectStatusParam;
import com.jinpinghu.logic.plantProtect.param.DelPlantProtectParam;
import com.jinpinghu.logic.plantProtect.param.GetPlantProtectInfoParam;
import com.jinpinghu.logic.plantProtect.param.GetPlantProtectionListParam;

@Produces("application/json;charset=UTF-8")
@Path("plantProtection")
public class PlantProtectionAction extends BaseZAction{

	@POST
	@Path("addOrUpdatePlantProtection.do")
	public String addOrUpdatePlantProtection(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("content")  String content,
			@FormParam("name")  String name,
			@FormParam("orderDescribe")  String orderDescribe,
			@FormParam("price")  String price,
			@FormParam("machine")  String machine,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("serverType")  String serverType,
			@FormParam("json") String json,
			@FormParam("serverTime")  String serverTime,
			@Context HttpServletRequest request) {
		AddOrUpdatePlantProtectParam myParam = new AddOrUpdatePlantProtectParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setContent(content);
		myParam.setName(name);
		myParam.setOrderDescribe(orderDescribe);
		myParam.setPrice(price);
		myParam.setMachine(machine);
		myParam.setServerType(serverType);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setJson(json);
		myParam.setServerTime(serverTime);
		return new AddOrUpdatePlantProtectLogic().process(myParam);
	}
	
	
	@POST
	@Path("delPlantProtection.do")
	public String delPlantProtection(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		DelPlantProtectParam myParam = new DelPlantProtectParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelPlantProtectLogic().process(myParam);
	}
	
	@POST
	@Path("getPlantProtectionInfo.do")
	public String getPlantProtectionInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetPlantProtectInfoParam myParam = new GetPlantProtectInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetPlantProtectInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getPlantProtectionList.do")
	public String getPlantProtectionList(
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
			@FormParam("type")  String type,
			@FormParam("brand")  String brand,
			@FormParam("serverType")  String serverType,
			@FormParam("orderby")  String orderby,
			@FormParam("sortDirection")  String sortDirection,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		GetPlantProtectionListParam myParam = new GetPlantProtectionListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setLowPrice(lowPrice);
		myParam.setHighPrice(highPrice);
		myParam.setEtName(etName);
		myParam.setType(type);
		myParam.setBrand(brand);
		myParam.setServerType(serverType);
		myParam.setOrderby(orderby);
		myParam.setSortDirection(sortDirection);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setStatus(status);
		return new GetPlantProtectionListLogic().process(myParam);
	}
	
	@POST
	@Path("changePlantProtectionStatus.do")
	public String changePlantProtectionStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		ChangePlantProtectStatusParam myParam = new ChangePlantProtectStatusParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new ChangePlantProtectStatusLogic().process(myParam);
	}
	
}
