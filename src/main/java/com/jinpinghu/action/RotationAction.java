package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.rotation.AddRotationLogic;
import com.jinpinghu.logic.rotation.DelRotationLogic;
import com.jinpinghu.logic.rotation.GetEnterpriseZeroListLogic;
import com.jinpinghu.logic.rotation.GetRotationAdviceInfoLogic;
import com.jinpinghu.logic.rotation.GetRotationInfoLogic;
import com.jinpinghu.logic.rotation.GetRotationLogic;
import com.jinpinghu.logic.rotation.GetRotationTimeListLogic;
import com.jinpinghu.logic.rotation.param.AddRotationParam;
import com.jinpinghu.logic.rotation.param.DelRotationParam;
import com.jinpinghu.logic.rotation.param.GetRotationParam;
import com.jinpinghu.logic.rotation.param.GetRotationTimeListParam;
import com.jinpinghu.logic.rotation.param.GetToolZeroListParam;

@Path("rotation")
@Produces("application/json;charset=UTF-8")
public class RotationAction extends BaseZAction{

	/*
	 * 添加
	 * */
	@POST
	@Path("addRotation.do")
	public String addRotation(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("rotationId") String rotationId,
			@FormParam("advice") String advice,
			@FormParam("enterprise") String enterprise,
			@FormParam("tool") String tool,
			@FormParam("rotationTime") String rotationTime,
			@Context HttpServletRequest request) {
		AddRotationParam myParam = new AddRotationParam(userId, apiKey, request);
		myParam.setAdvice(advice);
		myParam.setEnterprise(enterprise);
		myParam.setTool(tool);
		myParam.setRotationTime(rotationTime);
		myParam.setRotationId(rotationId);
		return new AddRotationLogic().process(myParam);
	}
	
	/*
	 * 获取零差价企业
	 * */
	@POST
	@Path("getEnterpriseZeroList.do")
	public String getEnterpriseZeroList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids") String ids,
			@FormParam("enterpriseName") String enterpriseName,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetToolZeroListParam myParam = new GetToolZeroListParam(userId, apiKey, request);
		myParam.setIds(ids);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetEnterpriseZeroListLogic().process(myParam);
	}
	
	/*
	 * 获取轮播内容
	 * */
	@POST
	@Path("getRotation.do")
	public String getRotation(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("code") String code,
			@Context HttpServletRequest request) {
		GetRotationParam myParam = new GetRotationParam(userId, apiKey, request);
		myParam.setCode(code);
		return new GetRotationLogic().process(myParam);
	}
	
	@POST
	@Path("getRotationInfo.do")
	public String getRotationInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("rotationId") String rotationId,
			@Context HttpServletRequest request) {
		GetRotationParam myParam = new GetRotationParam(userId, apiKey, request);
		myParam.setCode(rotationId);
		return new GetRotationInfoLogic().process(myParam);
	}
	
	/*
	 * 获取记录
	 * */
	@POST
	@Path("getRotationTimeList.do")
	public String getRotationTimeList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetRotationTimeListParam myParam = new GetRotationTimeListParam(userId, apiKey, request);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetRotationTimeListLogic().process(myParam);
	}
	
	@POST
	@Path("delRotation.do")
	public String delRotation(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("rotationIds") String rotationIds,
			@Context HttpServletRequest request) {
		DelRotationParam myParam = new DelRotationParam(userId, apiKey, request);
		myParam.setRotationIds(rotationIds);
		return new DelRotationLogic().process(myParam);
	}
	/*
	 * 获取轮播建议详情
	 * */
	@POST
	@Path("getRotationAdviceInfo.do")
	public String getRotationAdviceInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetRotationParam myParam = new GetRotationParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetRotationAdviceInfoLogic().process(myParam);
	}
}
