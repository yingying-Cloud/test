package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.business.GetBusinessInfoLogic;
import com.jinpinghu.logic.business.GetBusinessListLogic;
import com.jinpinghu.logic.business.param.GetBusinessInfoParam;
import com.jinpinghu.logic.business.param.GetBusinessListParam;

@Path("business")
@Produces("application/json;charset=UTF-8")
public class BusinessAction extends BaseZAction{

	@POST
	@Path("getBusinessList.do")
	public String getBusinessList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("title") String title,
			@FormParam("author") String author,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetBusinessListParam myParam = new GetBusinessListParam(userId,apiKey,request);
		myParam.setAuthor(author);
		myParam.setTitle(title);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		return new GetBusinessListLogic().process(myParam);
	}
	
	@POST
	@Path("getBusinessInfo.do")
	public String getBusinessInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetBusinessInfoParam myParam = new GetBusinessInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new GetBusinessInfoLogic().process(myParam);
	}
	
}
