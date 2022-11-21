package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.plant.AddFarmingToolLogic;
import com.jinpinghu.logic.plant.AddOrUpdateBrandLogic;
import com.jinpinghu.logic.plant.AddOrUpdateCheckLogic;
import com.jinpinghu.logic.plant.AddOrUpdateEnterpriseUserinfoLogic;
import com.jinpinghu.logic.plant.AddOrUpdateFarmingLogic;
import com.jinpinghu.logic.plant.AddOrUpdateWorkLogic;
import com.jinpinghu.logic.plant.AuditBrandLogic;
import com.jinpinghu.logic.plant.DelBrandLogic;
import com.jinpinghu.logic.plant.DelCheckLogic;
import com.jinpinghu.logic.plant.DelEnterpriseUserinfoLogic;
import com.jinpinghu.logic.plant.DelFarmingLogic;
import com.jinpinghu.logic.plant.DelFarmingToolLogic;
import com.jinpinghu.logic.plant.DelWorkLogic;
import com.jinpinghu.logic.plant.GetAllBrandListLogic;
import com.jinpinghu.logic.plant.GetBrandInfoLogic;
import com.jinpinghu.logic.plant.GetBrandList2Logic;
import com.jinpinghu.logic.plant.GetBrandListLogic;
import com.jinpinghu.logic.plant.GetCheckInfoLogic;
import com.jinpinghu.logic.plant.GetCheckListLogic;
import com.jinpinghu.logic.plant.GetEnterpriseUserinfoInfoLogic;
import com.jinpinghu.logic.plant.GetEnterpriseUserinfoListLogic;
import com.jinpinghu.logic.plant.GetFarmingInfoLogic;
import com.jinpinghu.logic.plant.GetFarmingListLogic;
import com.jinpinghu.logic.plant.GetFarmingToolInfoLogic;
import com.jinpinghu.logic.plant.GetFarmingToolListLogic;
import com.jinpinghu.logic.plant.GetWorkInfoLogic;
import com.jinpinghu.logic.plant.GetWorkListLogic;
import com.jinpinghu.logic.plant.GetWorkSumLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateBrandParam;
import com.jinpinghu.logic.plant.param.AddOrUpdateCheckParam;
import com.jinpinghu.logic.plant.param.AddOrUpdateEnterpriseUserinfoParam;
import com.jinpinghu.logic.plant.param.AddOrUpdateFarmingParam;
import com.jinpinghu.logic.plant.param.AddOrUpdateFarmingToolParam;
import com.jinpinghu.logic.plant.param.AddOrUpdateWorkParam;
import com.jinpinghu.logic.plant.param.AuditBrandParam;
import com.jinpinghu.logic.plant.param.GetBrandList2Param;
import com.jinpinghu.logic.plant.param.GetBrandListParam;
import com.jinpinghu.logic.plant.param.GetCheckListParam;
import com.jinpinghu.logic.plant.param.GetEnterpriseUserinfoListParam;
import com.jinpinghu.logic.plant.param.GetFarmingListParam;
import com.jinpinghu.logic.plant.param.GetFarmingToolListParam;
import com.jinpinghu.logic.plant.param.GetWorkListParam;
import com.jinpinghu.logic.plant.param.GetWorkSumParam;
import com.jinpinghu.logic.plant.param.SimpleParam;
import com.jinpinghu.logic.tool.GetToolList2Logic;
import com.jinpinghu.logic.tool.param.GetToolList2Param;
import com.jinpinghu.logic.work.GetAllWorkInfoLogic;
import com.jinpinghu.logic.work.param.GetAllWorkInfoParam;

@Path("plant")
@Produces("application/json;charset=UTF-8")
public class PlantAction extends BaseZAction{
	
	/*
	 * 添加或删除品牌
	 * */
	@POST
	@Path("addOrUpdateBrand.do")
	public String addOrUpdateBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("productName")  String productName,
			@FormParam("model")  String model,
			@FormParam("registeredTrademark")  String registeredTrademark,
			@FormParam("authorizationCategory") String authorizationCategory,
			@FormParam("json") String json,
			@FormParam("type") String type,
			@FormParam("price") String price,
			@FormParam("unit") String unit,
			@FormParam("spec") String spec,
			@FormParam("describe") String describe,
			@FormParam("status") String status,
			@FormParam("upStatus") String upStatus,
			@Context HttpServletRequest request) {
		AddOrUpdateBrandParam myParam = new AddOrUpdateBrandParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setProductName(productName);
		myParam.setModel(model);
		myParam.setId(id);
		myParam.setRegisteredTrademark(registeredTrademark);
		myParam.setAuthorizationCategory(authorizationCategory);
		myParam.setJson(json);
		myParam.setType(type);
		myParam.setPrice(price);
		myParam.setUnit(unit);
		myParam.setSpec(spec);
		myParam.setDescribe(describe);
		myParam.setStatus(status);
		myParam.setUpStatus(upStatus);
		return new AddOrUpdateBrandLogic().process(myParam);
	}
	
	/*
	 * 获取品牌列表
	 * */
	@POST
	@Path("getBrandList.do")
	public String getBrandList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("productName")  String productName,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("status")  String status,
			@FormParam("type")  String type,
			@FormParam("isSale")  String isSale,
			@FormParam("upStatus")  String upStatus,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetBrandListParam myParam = new GetBrandListParam(userId,apiKey, request);
		myParam.setProductName(productName);
		myParam.setStatus(status);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setIsSale(isSale);
		myParam.setUpStatus(upStatus);
		return new GetBrandListLogic().process(myParam);
	}
	
	/*
	 * 获取全部品牌列表
	 * */
	@POST
	@Path("getAllBrandList.do")
	public String getAllBrandList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("productName")  String productName,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("status")  String status,
			@FormParam("type")  String type,
			@FormParam("isSale")  String isSale,
			@FormParam("upStatus")  String upStatus,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetBrandListParam myParam = new GetBrandListParam(userId,apiKey, request);
		myParam.setProductName(productName);
		myParam.setStatus(status);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setIsSale(isSale);
		myParam.setUpStatus(upStatus);
		return new GetAllBrandListLogic().process(myParam);
	}
	
	/*
	 * 删除品牌
	 * */
	@POST
	@Path("DelBrand.do")
	public String DelBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelBrandLogic().process(myParam);
	}
	
	/*
	 *获取品牌详情
	 * */
	@POST
	@Path("getBrandInfo.do")
	public String getBrandInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetBrandInfoLogic().process(myParam);
	}
	
	/*
	 *审核品牌
	 * */
	@POST
	@Path("auditBrand.do")
	public String auditBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		AuditBrandParam myParam = new AuditBrandParam(userId,apiKey, request);
		myParam.setIds(ids);
		myParam.setStatus(status);
		return new AuditBrandLogic().process(myParam);
	}
//	/*
//	 * 上架
//	 * */
//	@POST
//	@Path("auditBrandUpStatus.do")
//	public String auditBrandUpStatus(
//			@FormParam("userId") String userId,
//			@FormParam("apiKey") String apiKey,
//			@FormParam("ids")  String id,
//			@FormParam("status")  String status,
//			@Context HttpServletRequest request) {
//		AuditBrandParam myParam = new AuditBrandParam(userId,apiKey, request);
//		myParam.setIds(id);
//		myParam.setStatus(status);
//		return new AuditBrandUpStatusLogic().process(myParam);
//	}
	
	
	
	
	
	
	
	/*
	 * 添加或更新种植计划
	 * */
	@POST
	@Path("addOrUpdateWork.do")
	public String addOrUpdateWork(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("workName")  String workName,
			@FormParam("landBlockSn") String landBlockSn,
			@FormParam("area")  String area,
			@FormParam("crop")  String crop,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("recoveryTime")  String recoveryTime,
			@FormParam("expectedProduction") String expectedProduction,
			@FormParam("json") String json,
			@FormParam("purchaseSource") String purchaseSource,
			@FormParam("purchasePeople") String purchasePeople,
			@FormParam("purchaseTime") String purchaseTime,
			@FormParam("greenhousesId") String greenhousesId,
			@Context HttpServletRequest request) {
		AddOrUpdateWorkParam myParam = new AddOrUpdateWorkParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setWorkName(workName);
		myParam.setId(id);
		myParam.setLandBlockSn(landBlockSn);
		myParam.setArea(area);
		myParam.setCrop(crop);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setRecoveryTime(recoveryTime);
		myParam.setExpectedProduction(expectedProduction);
		myParam.setJson(json);
		myParam.setPurchasePeople(purchasePeople);
		myParam.setPurchaseSource(purchaseSource);
		myParam.setPurchaseTime(purchaseTime);
		myParam.setGreenhousesId(greenhousesId);
		return new AddOrUpdateWorkLogic().process(myParam);
	}
	
	/*
	 * 获取种植计划列表
	 * */
	@POST
	@Path("getWorkList.do")
	public String getWorkList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("search")  String search,
			@FormParam("workSn")  String workSn,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("crop")  String crop,
			@FormParam("brandId")  String brandId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("status") String status,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetWorkListParam myParam = new GetWorkListParam(userId,apiKey, request);
		myParam.setSearch(search);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setCrop(crop);
		myParam.setWorkSn(workSn);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setStatus(status);
		myParam.setBrandId(brandId);
		return new GetWorkListLogic().process(myParam);
	}
	
	/*
	 * 删除种植计划
	 * */
	@POST
	@Path("DelWork.do")
	public String DelWork(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelWorkLogic().process(myParam);
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
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetWorkInfoLogic().process(myParam);
	}
	
	
	
	
	


	
	/*
	 * 添加或更新农事
	 * */
	@POST
	@Path("addOrUpdateFarming.do")
	public String addOrUpdateFarming(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("workId")  Integer workId,
			@FormParam("describe")  String describe,
			@FormParam("inputTime")  String inputTime,
			@FormParam("json") String json,
			@Context HttpServletRequest request) {
		AddOrUpdateFarmingParam myParam = new AddOrUpdateFarmingParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setId(id);
		myParam.setWorkId(workId);
		myParam.setDescribe(describe);
		myParam.setInputTime(inputTime);
		myParam.setJson(json);
		return new AddOrUpdateFarmingLogic().process(myParam);
	}
	
	/*
	 * 获取农事列表
	 * */
	@POST
	@Path("getFarmingList.do")
	public String getFarmingList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workSn")  String workSn,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetFarmingListParam myParam = new GetFarmingListParam(userId,apiKey, request);
		myParam.setWorkSn(workSn);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetFarmingListLogic().process(myParam);
	}
	
	/*
	 *删除农事
	 * */
	@POST
	@Path("DelFarming.do")
	public String DelFarming(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelFarmingLogic().process(myParam);
	}
	
	/*
	 * 获取农事详情
	 * */
	@POST
	@Path("getFarmingInfo.do")
	public String getFarmingInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetFarmingInfoLogic().process(myParam);
	}
	
	
	
	
	
	/*
	 * 批量添加农事农资
	 * */
	@POST
	@Path("addFarmingTool.do")
	public String addFarmingTool(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workId")  Integer workId,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("inputTime")  String inputTime,
			@FormParam("json") String json,
			@Context HttpServletRequest request) {
		AddOrUpdateFarmingToolParam myParam = new AddOrUpdateFarmingToolParam(userId,apiKey, request);
		myParam.setWorkId(workId);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setInputTime(inputTime);
		myParam.setJson(json);
		return new AddFarmingToolLogic().process(myParam);
	}
	
	/*
	 * ��ӻ����ũ��ũ��
	 * ͣ��
	 * */
	/*@POST
	@Path("addOrUpdateFarmingTool.do")
	public String addOrUpdateFarmingTool(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("workId")  Integer workId,
			@FormParam("addPeople")  String addPeople,
			@FormParam("toolId")  Integer toolId,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("specification")  String specification,
			@FormParam("unit")  String unit,
			@FormParam("num") String num,
			@Context HttpServletRequest request) {
		AddOrUpdateFarmingToolParam myParam = new AddOrUpdateFarmingToolParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setWorkId(workId);
		myParam.setAddPeople(addPeople);
		myParam.setId(id);
		myParam.setToolId(toolId);
		myParam.setNum(num);
		myParam.setSpecification(specification);
		myParam.setUnit(unit);
		return new AddOrUpdateFarmingToolLogic().process(myParam);
	}*/
	
	/*
	 *获取农事农资列表
	 * */
	@POST
	@Path("getFarmingToolList.do")
	public String getFarmingToolList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolName")  String toolName,
			@FormParam("code")  String code,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("workSn")  String workSn,
			@FormParam("type")  String type,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("isEffective") String isEffective,
			@Context HttpServletRequest request) {
		GetFarmingToolListParam myParam = new GetFarmingToolListParam(userId,apiKey, request);
		myParam.setToolName(toolName);
		myParam.setCode(code);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setWorkSn(workSn);
		myParam.setType(type);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setIsEffective(isEffective);
		return new GetFarmingToolListLogic().process(myParam);
	}
	
	/*
	 * 删除农事农资
	 * */
	@POST
	@Path("DelFarmingTool.do")
	public String DelFarmingTool(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelFarmingToolLogic().process(myParam);
	}
	
	/*
	 * 获取农事农资详情
	 * */
	@POST
	@Path("getFarmingToolInfo.do")
	public String getFarmingToolInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetFarmingToolInfoLogic().process(myParam);
	}
	/*
	 * 获取农事农资详情
	 * */
	@POST
	@Path("getToolList.do")
	public String getToolList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("id")  String toolIds,
			@FormParam("type")  Integer type,
			@FormParam("toolName")  String toolName,
			@FormParam("nowPage")  Integer nowPage,
			@FormParam("pageCount") Integer pageCount,
			@Context HttpServletRequest request) {
		GetToolList2Param myParam = new GetToolList2Param(userId,apiKey, request);
		myParam.setToolIds(toolIds);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setToolName(toolName);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetToolList2Logic().process(myParam);
	}

	
	
	
	
	
	
	/*
	 * 添加或更新人员
	 * */
	@POST
	@Path("addOrUpdateUser.do")
	public String addOrUpdateUser(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("name")  String name,
			@FormParam("mobile")  String mobile,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("idCard")  String idCard,
			@FormParam("type")  String type,
			@FormParam("address") String address,
			@FormParam("sex") String sex,
			@FormParam("json") String json,
			@FormParam("major") String major,
			@FormParam("education") String education,
			@FormParam("company") String company,
			@FormParam("title") String title,
			@Context HttpServletRequest request) {
		AddOrUpdateEnterpriseUserinfoParam myParam = new AddOrUpdateEnterpriseUserinfoParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setName(name);
		myParam.setId(id);
		myParam.setMobile(mobile);
		myParam.setAddress(address);
		myParam.setIdCard(idCard);
		myParam.setType(type);
		myParam.setSex(sex);
		myParam.setJson(json);
		myParam.setMajor(major);
		myParam.setEducation(education);
		myParam.setCompany(company);
		myParam.setTitle(title);
		return new AddOrUpdateEnterpriseUserinfoLogic().process(myParam);
	}
	
	/*
	 *获取人员列表
	 * */
	@POST
	@Path("getUserList.do")
	public String getUserList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("type")  String type,
			@FormParam("sex")  String sex,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetEnterpriseUserinfoListParam myParam = new GetEnterpriseUserinfoListParam(userId,apiKey, request);
		myParam.setName(name);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setSex(sex);
		return new GetEnterpriseUserinfoListLogic().process(myParam);
	}
	
	/*
	 * 删除人员
	 * */
	@POST
	@Path("DelUser.do")
	public String DelUserinfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelEnterpriseUserinfoLogic().process(myParam);
	}
	
	/*
	 * 获取人员详情
	 * */
	@POST
	@Path("getUserInfo.do")
	public String getUserInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetEnterpriseUserinfoInfoLogic().process(myParam);
	}
	
	

	
	
	
	
	
	
	
	/*
	 * 添加或更新检查
	 * */
	@POST
	@Path("addOrUpdateCheck.do")
	public String addOrUpdateCheck(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("workId")  String workId,
			@FormParam("checkTime")  String checkTime,
			@FormParam("people")  String people,
			@FormParam("unit")  String unit,
			@FormParam("content")  String content,
			@FormParam("inputTime") String inputTime,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("json") String json,
			@Context HttpServletRequest request) {
		AddOrUpdateCheckParam myParam = new AddOrUpdateCheckParam(userId,apiKey, request);
		myParam.setId(id);
		myParam.setWorkId(workId);
		myParam.setCheckTime(checkTime);
		myParam.setPeople(people);
		myParam.setUnit(unit);
		myParam.setContent(content);
		myParam.setInputTime(inputTime);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setJson(json);
		return new AddOrUpdateCheckLogic().process(myParam);
	}
	
	/*
	 *获取检查列表
	 * */
	@POST
	@Path("getCheckList.do")
	public String getCheckList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workSn")  String workSn,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetCheckListParam myParam = new GetCheckListParam(userId,apiKey, request);
		myParam.setWorkSn(workSn);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetCheckListLogic().process(myParam);
	}
	
	/*
	 * 删除检查
	 * */
	@POST
	@Path("DelCheck.do")
	public String DelCheck(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelCheckLogic().process(myParam);
	}
	
	/*
	 * 获取检查详情
	 * */
	@POST
	@Path("getCheckInfo.do")
	public String getCheckInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetCheckInfoLogic().process(myParam);
	}
	
	/*
	 * 获取农事农资详情
	 * */
	@POST
	@Path("getBrandList2.do")
	public String getBrandList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("id")  String toolIds,
			@Context HttpServletRequest request) {
		GetBrandList2Param myParam = new GetBrandList2Param(userId,apiKey, request);
		myParam.setToolIds(toolIds);
		myParam.setEnterpriseId(enterpriseId);
		return new GetBrandList2Logic().process(myParam);
	}
	
	/*
	 * 获取农事农资详情
	 * */
	@POST
	@Path("getWorkSum.do")
	public String getWorkSum(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetWorkSumParam myParam = new GetWorkSumParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetWorkSumLogic().process(myParam);
	}
	
	/*
	 * 获取任务全部详情
	 * */
	@POST
	@Path("getAllWorkInfo.do")
	public String getAllWorkInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("workSn")  String workSn,
			@FormParam("workId")  String workId,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetAllWorkInfoParam myParam = new GetAllWorkInfoParam(userId,apiKey, request);
		myParam.setWorkId(workId);
		myParam.setWorkSn(workSn);
		myParam.setEnterpriseId(enterpriseId);
		return new GetAllWorkInfoLogic().process(myParam);
	}
	
}
