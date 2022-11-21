package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.agriculturalAdvice.AddAgriculturalAdviceLogic;
import com.jinpinghu.logic.agriculturalAdvice.DelAgriculturalAdviceLogic;
import com.jinpinghu.logic.agriculturalAdvice.GetAgriculturalAdviceInfoLogic;
import com.jinpinghu.logic.agriculturalAdvice.GetAgriculturalAdviceListLogic;
import com.jinpinghu.logic.agriculturalAdvice.param.AddAgriculturalAdviceParam;
import com.jinpinghu.logic.agriculturalAdvice.param.GetAgriculturalAdviceInfoParam;
import com.jinpinghu.logic.agriculturalAdvice.param.GetAgriculturalAdviceListParam;

@Path("agriculturalAdvice")
@Produces("application/json;charset=UTF-8")
public class AgriculturalAdviceAction extends BaseZAction{

	@POST
	@Path("getAgriculturalAdviceList.do")
	public String getAgriculturalAdviceList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("type") String type,
			@FormParam("title") String title,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetAgriculturalAdviceListParam myParam = new GetAgriculturalAdviceListParam(userId,apiKey,request);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setTitle(title);
		myParam.setType(type);
		return new GetAgriculturalAdviceListLogic().process(myParam);
	}
	
	@POST
	@Path("getAgriculturalAdviceInfo.do")
	public String getAgriculturalAdviceInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetAgriculturalAdviceInfoParam myParam = new GetAgriculturalAdviceInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new GetAgriculturalAdviceInfoLogic().process(myParam);
	}
	@POST
	@Path("delAgriculturalAdvice.do")
	public String delAgriculturalAdvice(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetAgriculturalAdviceInfoParam myParam = new GetAgriculturalAdviceInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new DelAgriculturalAdviceLogic().process(myParam);
	}
	
	@POST
	@Path("addAgriculturalAdvice.do")
	public String addPests(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@FormParam("type") String type,
			@FormParam("title") String title,
			@FormParam("author") String author,
			@FormParam("adviceType") String adviceType,
			@FormParam("content") String content,
			@Context HttpServletRequest request) {
		AddAgriculturalAdviceParam myParam = new AddAgriculturalAdviceParam(userId,apiKey,request);
		myParam.setId(id);
		myParam.setTitle(title);
		myParam.setType(type);
		myParam.setAdviceType(adviceType);
		myParam.setAuthor(author);
		myParam.setContent(content);
		return new AddAgriculturalAdviceLogic().process(myParam);
	}
	
	
}
