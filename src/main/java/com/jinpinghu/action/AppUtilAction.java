package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.appUtil.AddAppUtilLogic;
import com.jinpinghu.logic.appUtil.GetAppUtilListLogic;
import com.jinpinghu.logic.appUtil.param.AddAppUtilParam;
import com.jinpinghu.logic.appUtil.param.GetAppUtilListParam;

@Path("appUtil")
@Produces("application/json;charset=UTF-8")
public class AppUtilAction extends BaseZAction {
	
	@POST
	@Path("getAppUtilList.do")
	public String getEnterpriseUserProductionList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		GetAppUtilListParam myParam = new GetAppUtilListParam(userId, apiKey, request);
		return new GetAppUtilListLogic().process(myParam);
	}
	
	@POST
	@Path("addAppUtil.do")
	public String addAppUtil(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("utilJa") String utilJa,
			@Context HttpServletRequest request) {
		AddAppUtilParam myParam = new AddAppUtilParam(userId, apiKey, request);
		myParam.setUtilJa(utilJa);
		return new AddAppUtilLogic().process(myParam);
	}
	

}
