package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.mechine.AddOrUpdateMechineLogic;
import com.jinpinghu.logic.mechine.DelMechineLogic;
import com.jinpinghu.logic.mechine.GetExpertTypeListLogic;
import com.jinpinghu.logic.mechine.GetMechineInfoLogic;
import com.jinpinghu.logic.mechine.GetMechineListLogic;
import com.jinpinghu.logic.mechine.GetMechineTypeListLogic;
import com.jinpinghu.logic.mechine.GetTypeListLogic;
import com.jinpinghu.logic.mechine.MechineTypeCountLogic;
import com.jinpinghu.logic.mechine.param.AddOrUpdateMechineParam;
import com.jinpinghu.logic.mechine.param.GetMechineListParam;
import com.jinpinghu.logic.mechine.param.GetMechineTypeListParam;
import com.jinpinghu.logic.mechine.param.GetTypeListParam;
import com.jinpinghu.logic.mechine.param.MechineTypeCountParam;
import com.jinpinghu.logic.mechine.param.SimpleParam;


@Path("mechine")
@Produces("application/json;charset=UTF-8")
public class MechineAction extends BaseZAction{
	/*
	 * 添加或删除品牌
	 * */
	@POST
	@Path("addOrUpdateMechine.do")
	public String addOrUpdateMechine(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("describe")  String describe,
			@FormParam("name")  String name,
			@FormParam("model")  String model,
			@FormParam("type")  String type,
			@FormParam("brand") String brand,
			@FormParam("json") String json,
			@Context HttpServletRequest request) {
		AddOrUpdateMechineParam myParam = new AddOrUpdateMechineParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setName(name);
		myParam.setDescribe(describe);
		myParam.setModel(model);
		myParam.setId(id);
		myParam.setType(type);
		myParam.setBrand(brand);
		myParam.setJson(json);
		return new AddOrUpdateMechineLogic().process(myParam);
	}
	
	/*
	 * 获取品牌列表
	 * */
	@POST
	@Path("getMechineList.do")
	public String getMechineList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("type")  String type,
			@FormParam("brand")  String brand,
			@FormParam("model")  String model,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetMechineListParam myParam = new GetMechineListParam(userId,apiKey, request);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setBrand(brand);
		myParam.setModel(model);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetMechineListLogic().process(myParam);
	}
	
	/*
	 * 删除品牌
	 * */
	@POST
	@Path("DelMechine.do")
	public String DelMechine(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelMechineLogic().process(myParam);
	}
	
	/*
	 *获取品牌详情
	 * */
	@POST
	@Path("getMechineInfo.do")
	public String getMechineInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetMechineInfoLogic().process(myParam);
	}
	
	
	/*
	 *获取品牌详情
	 * */
	@POST
	@Path("getTypeList.do")
	public String getTypeList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetTypeListParam myParam = new GetTypeListParam(userId,apiKey, request);
		myParam.setType(type);
		return new GetTypeListLogic().process(myParam);
	}
	
	/*
	 *获取品牌详情
	 * */
	@POST
	@Path("getMechineTypeList.do")
	public String getMechineTypeList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("parentId")  String parentId,
			@Context HttpServletRequest request) {
		GetMechineTypeListParam myParam = new GetMechineTypeListParam(userId,apiKey, request);
		myParam.setParentId(parentId);
		return new GetMechineTypeListLogic().process(myParam);
	}
	
	
	
	
	@POST
	@Path("getMechineTypeCount.do")
	public String getMechineTypeCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		MechineTypeCountParam myParam = new MechineTypeCountParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new MechineTypeCountLogic().process(myParam);
	}
	
	/*
	 *获取品牌详情
	 * */
	@POST
	@Path("getExpertTypeList.do")
	public String getExpertTypeList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetTypeListParam myParam = new GetTypeListParam(userId,apiKey, request);
		myParam.setType(type);
		return new GetExpertTypeListLogic().process(myParam);
	}
	
	
}
