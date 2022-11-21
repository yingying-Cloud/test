package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.log.GetLogTimeDayLogic;
import com.jinpinghu.logic.log.GetLogTimeLogic;
import com.jinpinghu.logic.log.GetLogTimePeopleLogic;
import com.jinpinghu.logic.log.GetLogTimeRoleLogic;
import com.jinpinghu.logic.log.param.GetLogTimeParam;
@Path("log")
@Produces("application/json;charset=UTF-8")
public class LogAction extends BaseZAction{
	
	/**
	 *  统计登陆次数和人数
	 */
	@POST
	@Path("getLogTimeDay.do")
	public String getLogTimeDay(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		GetLogTimeParam myParam = new GetLogTimeParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetLogTimeDayLogic().process(myParam);
	}
	
	/**
	 *  统计登陆次数和人数
	 */
	@POST
	@Path("getLogTime.do")
	public String getLogTime(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		GetLogTimeParam myParam = new GetLogTimeParam(userId, apiKey, request);
		return new GetLogTimeLogic().process(myParam);
	}
	
	/**
	 *  统计登陆次数和人数
	 */
	@POST
	@Path("getLogTimePeople.do")
	public String getLogTimePeople(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("name") String name,
			@FormParam("endTime") String endTime,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetLogTimeParam myParam = new GetLogTimeParam(userId, apiKey, request);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setName(name);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetLogTimePeopleLogic().process(myParam);
	}
	
	/**
	 *  统计登陆次数和人数
	 */
	@POST
	@Path("getLogTimeRole.do")
	public String getLogTimeRole(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@Context HttpServletRequest request) {
		GetLogTimeParam myParam = new GetLogTimeParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		return new GetLogTimeRoleLogic().process(myParam);
	}
	
	

}
