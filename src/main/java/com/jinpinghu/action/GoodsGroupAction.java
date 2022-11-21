package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.offlineStore.ListGoodsGroupByIdTypeLogic;
import com.jinpinghu.logic.offlineStore.param.ListGoodsGroupByIdTypeParam;


@Path("goodsGroup")
@Produces("application/json;charset=UTF-8")
public class GoodsGroupAction extends BaseZAction{
	/*
	 * 根据id和goodsType获取分组
	 */
	@POST
	@Path("/listGoodsGroupByIdType.do")
	public String listGoodsGroupByIdType(
			@FormParam("apiKey")String apiKey,
			@FormParam("userId")String userId,
			@FormParam("id")String id,
			@FormParam("goodsType")String goodsType,
			@Context HttpServletRequest request) {
		ListGoodsGroupByIdTypeParam myParam = new ListGoodsGroupByIdTypeParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setGoodsType(goodsType);
		return new ListGoodsGroupByIdTypeLogic().process(myParam);
	}
	
}
