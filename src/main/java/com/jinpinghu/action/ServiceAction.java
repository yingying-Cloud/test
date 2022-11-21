package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.service.GetServiceInfoLogic;
import com.jinpinghu.logic.service.GetServiceListLogic;
import com.jinpinghu.logic.service.param.GetServiceInfoParam;
import com.jinpinghu.logic.service.param.GetServiceListParam;



@Path("service")
@Produces("application/json;charset=UTF-8")
public class ServiceAction extends BaseZAction{
	
	/*
	 * 根据type获取列表信息
	 * */
	@POST
	@Path("getServiceList.do")
	public String getServiceList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type") String type,
			@Context HttpServletRequest request) {
		GetServiceListParam myParam = new GetServiceListParam(userId, apiKey, request);
		myParam.setType(type);
		return new GetServiceListLogic().process(myParam);
	}
	
	/*
	 * 根据id获取详情信息
	 * */
	@POST
	@Path("getServiceInfo.do")
	public String getServiceInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetServiceInfoParam myParam = new GetServiceInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetServiceInfoLogic().process(myParam);
	}
}
