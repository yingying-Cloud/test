package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.agriculturalClassroom.AddAgriculturalClassroomLogic;
import com.jinpinghu.logic.agriculturalClassroom.DelAgriculturalClassroomLogic;
import com.jinpinghu.logic.agriculturalClassroom.GetAgriculturalClassroomInfoLogic;
import com.jinpinghu.logic.agriculturalClassroom.GetAgriculturalClassroomListLogic;
import com.jinpinghu.logic.agriculturalClassroom.param.AddAgriculturalClassroomParam;
import com.jinpinghu.logic.agriculturalClassroom.param.GetAgriculturalClassroomInfoParam;
import com.jinpinghu.logic.agriculturalClassroom.param.GetAgriculturalClassroomListParam;

@Path("agriculturalClassroom")
@Produces("application/json;charset=UTF-8")
public class AgriculturalClassroomAction extends BaseZAction{

	@POST
	@Path("getAgriculturalClassroomList.do")
	public String getAgriculturalClassroomList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("type") String type,
			@FormParam("title") String title,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetAgriculturalClassroomListParam myParam = new GetAgriculturalClassroomListParam(userId,apiKey,request);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setTitle(title);
		myParam.setType(type);
		return new GetAgriculturalClassroomListLogic().process(myParam);
	}
	
	@POST
	@Path("getAgriculturalClassroomInfo.do")
	public String getAgriculturalClassroomInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetAgriculturalClassroomInfoParam myParam = new GetAgriculturalClassroomInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new GetAgriculturalClassroomInfoLogic().process(myParam);
	}
	@POST
	@Path("delAgriculturalClassroom.do")
	public String delAgriculturalClassroom(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetAgriculturalClassroomInfoParam myParam = new GetAgriculturalClassroomInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new DelAgriculturalClassroomLogic().process(myParam);
	}
	@POST
	@Path("addAgriculturalClassroom.do")
	public String addAgriculturalClassroom(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@FormParam("type") String type,
			@FormParam("title") String title,
			@FormParam("file") String file,
			@Context HttpServletRequest request) {
		AddAgriculturalClassroomParam myParam = new AddAgriculturalClassroomParam(userId,apiKey,request);
		myParam.setId(id);
		myParam.setTitle(title);
		myParam.setType(type);
		myParam.setFile(file);
		return new AddAgriculturalClassroomLogic().process(myParam);
	}
}
