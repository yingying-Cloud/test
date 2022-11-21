package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.topFile.GetTopFileListLogic;
import com.jinpinghu.logic.topFile.param.GetTopFileListParam;

@Path("topFile")
@Produces("application/json;charset=UTF-8")
public class TopFileAction {
	@POST
	@Path("getTopFileList.do")
	public String getTypeList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetTopFileListParam myParam = new GetTopFileListParam(userId,apiKey, request);
		myParam.setType(type);
		return new GetTopFileListLogic().process(myParam);
	}
}
