package com.jinpinghu.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.nhl.ListNhlLogic;
import com.jinpinghu.logic.nhl.param.ListNhlParam;

@Path("nhl")
@Produces("application/json;charset=UTF-8")
public class NhlAction extends BaseZAction{
	
	@POST
	@Path("listNhl.do")
	public String listNhl(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		ListNhlParam myParam = new ListNhlParam(userId,apiKey, request);
		return new ListNhlLogic().process(myParam);
	}
}
