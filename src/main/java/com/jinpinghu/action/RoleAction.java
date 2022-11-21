package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.role.GetRoleLIstLogic;
import com.jinpinghu.logic.role.param.GetRoleLIstParam;

@Path("role")
@Produces("application/json;charset=UTF-8")
public class RoleAction extends BaseZAction{
	
	/*
	 * 获取role列表
	 * */
	@POST
	@Path("getRoleList.do")
	public String getRoleList(
		@FormParam("userId") String userId,
		@FormParam("apiKey") String apiKey,
		@FormParam("id") String id,
		@FormParam("roleName") String roleName,
		@FormParam("pageCount") String pageCount,
		@FormParam("nowPage") String nowPage,
		@Context HttpServletRequest request) {
		GetRoleLIstParam myParam = new GetRoleLIstParam(userId,apiKey,request); 
		myParam.setId(id);
		myParam.setRoleName(roleName);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		return new GetRoleLIstLogic().process(myParam);
	}
}
