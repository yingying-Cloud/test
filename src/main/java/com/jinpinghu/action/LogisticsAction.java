package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.logistics.GetLogisticsInfoLogic;
import com.jinpinghu.logic.logistics.GetLogisticsListLogic;
import com.jinpinghu.logic.logistics.param.GetLogisticsInfoParam;
import com.jinpinghu.logic.logistics.param.GetLogisticsListParam;

@Path("logistics")
@Produces("application/json;charset=UTF-8")
public class LogisticsAction extends BaseZAction{

	@POST
	@Path("getLogisticsList.do")
	public String getLogisticsList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetLogisticsListParam myParam = new GetLogisticsListParam(userId,apiKey,request);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		return new GetLogisticsListLogic().process(myParam);
	}
	
	@POST
	@Path("getLogisticsInfo.do")
	public String getLogisticsInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetLogisticsInfoParam myParam = new GetLogisticsInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new GetLogisticsInfoLogic().process(myParam);
	}
	
}
