package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.offlineStore.GetToolListLogic;
import com.jinpinghu.logic.offlineStore.param.GetToolListParam;


@Produces("application/json;charset=UTF-8")
@Path("storeInventory")
public class StoreInventoryAction extends BaseZAction{
	
	@POST
	@Path("getStoreInventoryAllGoodsList.do")
	public String getToolList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("shopId") String enterpriseId,
			@FormParam("groupId") String groupId,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetToolListParam myParam = new GetToolListParam(userId,apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setType(groupId);
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		return new GetToolListLogic().process(myParam);
	}
	
}
