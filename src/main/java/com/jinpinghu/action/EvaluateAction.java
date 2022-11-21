package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.evaluate.AddEvaluateLogic;
import com.jinpinghu.logic.evaluate.GetEvaluateListLogic;
import com.jinpinghu.logic.evaluate.StatisticEvaluateListLogic;
import com.jinpinghu.logic.evaluate.param.AddEvaluateParam;
import com.jinpinghu.logic.evaluate.param.GetEvaluateListParam;

@Path("evaluate")
@Produces("application/json;charset=UTF-8")
public class EvaluateAction extends BaseZAction{

	@POST
	@Path("getEvaluateList.do")
	public String getEvaluateList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("type") String type,
			@FormParam("searchId") String searchId,
			@FormParam("resId") String resId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("level") String level,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetEvaluateListParam myParam = new GetEvaluateListParam(userId,apiKey,request);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setSearchId(searchId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setResId(resId);
		myParam.setType(type);
		myParam.setLevel(level);
		return new GetEvaluateListLogic().process(myParam);
	}
	
	@POST
	@Path("addEvaluate.do")
	public String getEvaluateList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@FormParam("content") String content,
			@FormParam("level") String level,
			@FormParam("resId") String resId,
			@FormParam("type") String type,
			@FormParam("file") String file,
			@Context HttpServletRequest request) {
		AddEvaluateParam myParam = new AddEvaluateParam(userId,apiKey,request);
		myParam.setResId(resId);
		myParam.setType(type);
		myParam.setId(id);
		myParam.setContent(content);
		myParam.setType(type);
		myParam.setFile(file);
		myParam.setLevel(level);
		return new AddEvaluateLogic().process(myParam);
	}
	
	@POST
	@Path("statisticEvaluateList.do")
	public String statisticEvaluateList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("type") String type,
			@FormParam("searchId") String searchId,
			@FormParam("resId") String resId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		GetEvaluateListParam myParam = new GetEvaluateListParam(userId,apiKey,request);
		myParam.setSearchId(searchId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setResId(resId);
		myParam.setType(type);
		return new StatisticEvaluateListLogic().process(myParam);
	}
	
}
