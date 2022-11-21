package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.goodsGroup.GetGroupGoodsInfoLogic;
import com.jinpinghu.logic.goodsGroup.GetGroupGoodsListLogic;
import com.jinpinghu.logic.goodsGroup.GetGroupInGoodsListLogic;
import com.jinpinghu.logic.goodsGroup.GetGroupListLogic;
import com.jinpinghu.logic.goodsGroup.param.GetGroupGoodsInfoParam;
import com.jinpinghu.logic.goodsGroup.param.GetGroupGoodsListParam;
import com.jinpinghu.logic.goodsGroup.param.GetGroupInGoodsListParam;
import com.jinpinghu.logic.goodsGroup.param.GetGroupListParam;
@Path("groupGoods")
@Produces("application/json;charset=UTF-8")
public class GroupGoodsAction extends BaseZAction{
	@POST
	@Path("getGroupGoodsInfo.do")
	public String getGroupGoodsInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("isIn") String isIn,
			@Context HttpServletRequest request) {
		GetGroupGoodsInfoParam myParam = new GetGroupGoodsInfoParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setIsIn(isIn);
		return new GetGroupGoodsInfoLogic().process(myParam);
	}
	@POST
	@Path("getGroupGoodsList.do")
	public String getGroupGoodsList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("groupId") String groupId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetGroupGoodsListParam myParam = new GetGroupGoodsListParam(userId, apiKey, request);
		myParam.setGroupId(groupId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetGroupGoodsListLogic().process(myParam);
	}
	@POST
	@Path("getGroupList.do")
	public String getGroupList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		GetGroupListParam myParam = new GetGroupListParam(userId, apiKey, request);
		return new GetGroupListLogic().process(myParam);
	}
	@POST
	@Path("getGroupInGoodsList.do")
	public String getGroupInGoodsList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("groupId") String groupId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetGroupInGoodsListParam myParam = new GetGroupInGoodsListParam(userId, apiKey, request);
		myParam.setGroupId(groupId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetGroupInGoodsListLogic().process(myParam);
	}
}
