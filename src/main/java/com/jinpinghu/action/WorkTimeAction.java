package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.workTime.AddOrUpdateWorkTimeLogic;
import com.jinpinghu.logic.workTime.DelWorkTimeLogic;
import com.jinpinghu.logic.workTime.GetWorkTimeInfoLogic;
import com.jinpinghu.logic.workTime.GetWorkTimeListLogic;
import com.jinpinghu.logic.workTime.StatisticalWorkingHoursLogic;
import com.jinpinghu.logic.workTime.param.AddOrUpdateWorkTimeParam;
import com.jinpinghu.logic.workTime.param.GetWorkTimeListParam;
import com.jinpinghu.logic.workTime.param.StatisticalWorkingHoursParam;

@Path("workTime")
@Produces("application/json;charset=UTF-8")
public class WorkTimeAction extends BaseZAction{
	
	/**
	 *获取时长列表
	 */
	@POST
	@Path("getWorkTimeList.do")
	public String getWorkTimeList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("roleId") String roleId,
			@FormParam("workId") String workId,
			@FormParam("workSn") String workSn,
			@FormParam("name") String name,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetWorkTimeListParam myParam = new GetWorkTimeListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setRoleId(roleId);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setWorkId(workId);
		myParam.setWorkSn(workSn);
		myParam.setName(name);
		return new GetWorkTimeListLogic().process(myParam);
	}
	
	/**
	 *添加/更新时长
	 */
	@POST
	@Path("addOrUpdateWorkTime.do")
	public String addOrUpdateWorkTime(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("workId") String workId,
			@FormParam("time") String time,
			@FormParam("json") String json,
			@FormParam("workTime") String workTime,
			@FormParam("userTabId") String userTabId ,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		AddOrUpdateWorkTimeParam myParam = new AddOrUpdateWorkTimeParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setJson(json);
		myParam.setWorkId(workId);
		myParam.setWorkTime(workTime);
		myParam.setUserTabId(userTabId);
		myParam.setId(id);
		return new AddOrUpdateWorkTimeLogic().process(myParam);
	}
	
	
	/**
	 *获取时长列表
	 */
	@POST
	@Path("StatisticalWorkingHours.do")
	public String StatisticalWorkingHours(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("roleId") String roleId,
			@FormParam("nickName") String nickName,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		StatisticalWorkingHoursParam myParam = new StatisticalWorkingHoursParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNickName(nickName);
		myParam.setRoleId(roleId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new StatisticalWorkingHoursLogic().process(myParam);
	}
	
	@POST
	@Path("delWorkTime.do")
	public String delWorkTime(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		AddOrUpdateWorkTimeParam myParam = new AddOrUpdateWorkTimeParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelWorkTimeLogic().process(myParam);
	}
	
	@POST
	@Path("getWorkTimeInfo.do")
	public String getWorkTimeInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		AddOrUpdateWorkTimeParam myParam = new AddOrUpdateWorkTimeParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetWorkTimeInfoLogic().process(myParam);
	}
	
	
}
