package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.inspection.AddCompleteLogic;
import com.jinpinghu.logic.inspection.AddInspectionLogic;
import com.jinpinghu.logic.inspection.AddOrUpItem;
import com.jinpinghu.logic.inspection.AddOrUpType;
import com.jinpinghu.logic.inspection.DelInspectionLogic;
import com.jinpinghu.logic.inspection.DelItemLogic;
import com.jinpinghu.logic.inspection.DelTypeLogic;
import com.jinpinghu.logic.inspection.GetCompleteListLogic;
import com.jinpinghu.logic.inspection.GetInspectionInfoLogic;
import com.jinpinghu.logic.inspection.GetInspectionListLogic;
import com.jinpinghu.logic.inspection.GetItemInfo;
import com.jinpinghu.logic.inspection.GetItemListLogic;
import com.jinpinghu.logic.inspection.GetPointListLogic;
import com.jinpinghu.logic.inspection.GetShopInfoLogic;
import com.jinpinghu.logic.inspection.GetShopListLogic;
import com.jinpinghu.logic.inspection.GetTypeInfo;
import com.jinpinghu.logic.inspection.GetTypeListLogic;
import com.jinpinghu.logic.inspection.InspectionParam;


@Produces("application/json;charset=UTF-8")
@Path("inspection")
public class InspectionAction {

	/*
	 * 新增监管记录
	 * */
	@POST
	@Path("addInspection.do")
	public String addInspection(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("remark")  String remark,
			@FormParam("problem")  Integer problem,
			@FormParam("time")  String time,
			@FormParam("file")  String file,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setRemark(remark);
		myParam.setItemId(problem);
		myParam.setTime(time);
		myParam.setFile(file);
		return new AddInspectionLogic().process(myParam);
	}
	
	
	/*
	 * 获取监管记录列表
	 * */
	@POST
	@Path("getInspectionList.do")
	public String getInspectionList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("status")  Integer status,
			@FormParam("enterpriseName")  String enterpriseName,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStatus(status);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetInspectionListLogic().process(myParam);
	}
	
	/*
	 * 获取监管记录详情
	 * */
	@POST
	@Path("getInspectionInfo.do")
	public String getInspectionInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  Integer id,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetInspectionInfoLogic().process(myParam);
	}
	
	/*
	 *删除监管记录
	 * */
	@POST
	@Path("delInspection.do")
	public String delInspection(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelInspectionLogic().process(myParam);
	}
	
	
	/*
	 * 获取店铺列表
	 * */
	@POST
	@Path("getShopList.do")
	public String getShopList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("dscd")  String dscd,
			@FormParam("enterpriseName")  String enterpriseName,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setDscd(dscd);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetShopListLogic().process(myParam);
	}
	
	/*
	 * 获取问题类型列表
	 * */
	@POST
	@Path("getItemList.do")
	public String getItemList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("type") Integer type,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetItemListLogic().process(myParam);
	}
	
	/*
	 * 获取问题类型详情
	 * */
	@POST
	@Path("getItemInfo.do")
	public String getItemInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,			
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetItemInfo().process(myParam);
	}
	
	/*
	 * 添加或更新问题类型
	 * */
	@POST
	@Path("addOrUpItem.do")
	public String addOrUpItem(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,		
			@FormParam("name") String name,
			@FormParam("type") Integer type,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setName(name);
		myParam.setType(type);
		return new AddOrUpItem().process(myParam);
	}
	
	/*
	 * 删除问题类型
	 * */
	@POST
	@Path("delItem.do")
	public String delItem(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids") String ids,		
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelItemLogic().process(myParam);
	}
	
	/*
	 * 新增反馈记录
	 * */
	@POST
	@Path("addComplete.do")
	public String addComplete(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("inspectionId")  Integer inspectionId,
			@FormParam("remark")  String remark,
			@FormParam("type")  Integer type,
			@FormParam("file")  String file,
			@FormParam("status")  Integer status,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setInspectionId(inspectionId);
		myParam.setRemark(remark);
		myParam.setStatus(status);
		myParam.setFile(file);
		myParam.setType(type);
		return new AddCompleteLogic().process(myParam);
	}
	
	/*
	 * 获取复核/整改列表
	 * */
	@POST
	@Path("getCompleteList.do")
	public String getCompleteList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("inspectionId")  Integer inspectionId,
			@FormParam("type")  Integer type,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setInspectionId(inspectionId);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetCompleteListLogic().process(myParam);
	}
	
	/*
	 * 获取店铺详情
	 * */
	@POST
	@Path("getShopInfo.do")
	public String getShopInfo(
			@FormParam("code")  String code,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(null, null, request);
		myParam.setCode(code);
		return new GetShopInfoLogic().process(myParam);
	}
	
	/*
	 * 获取店铺使用率 及 排行
	 * */
	@POST
	@Path("getShopSort.do")
	public String getShopSort(
			@FormParam("code")  String code,
			@FormParam("month")  String month,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(null, null, request);
		myParam.setCode(code);
		myParam.setMonth(month);
		return new GetShopSortLogic().process(myParam);
	}
	
	
	/*
	 * 获取问题级别列表
	 * */
	@POST
	@Path("getTypeList.do")
	public String getTypeList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("point") Integer point,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setPoint(point);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetTypeListLogic().process(myParam);
	}
	
	/*
	 * 获取问题级别详情
	 * */
	@POST
	@Path("getTypeInfo.do")
	public String getTypeInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,			
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetTypeInfo().process(myParam);
	}
	
	/*
	 * 添加或更新级别类型
	 * */
	@POST
	@Path("addOrUpType.do")
	public String addOrUpType(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,		
			@FormParam("name") String name,
			@FormParam("point") Integer point,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setName(name);
		myParam.setPoint(point);
		return new AddOrUpType().process(myParam);
	}
	
	/*
	 * 删除问题级别
	 * */
	@POST
	@Path("delType.do")
	public String delType(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids") String ids,		
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelTypeLogic().process(myParam);
	}
	
	
	/*
	 * 获取监管分数列表
	 * */
	@POST
	@Path("getPointList.do")
	public String getPointList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("year") String year,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("isExport")  Integer isExport,
			@Context HttpServletRequest request) {
		InspectionParam myParam = new InspectionParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setYear(year);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setIsExport(isExport);
		return new GetPointListLogic().process(myParam);
	}
}
