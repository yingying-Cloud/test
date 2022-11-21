package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.shoppingCar.AddToShoppingCarLogic;
import com.jinpinghu.logic.shoppingCar.ChangeShoppingCarGoodsNumberLogic;
import com.jinpinghu.logic.shoppingCar.DeleteShoppingCarGoodsLogic;
import com.jinpinghu.logic.shoppingCar.GetMyShoppingCarCountLogic;
import com.jinpinghu.logic.shoppingCar.GetMyShoppingCarListLogic;
import com.jinpinghu.logic.shoppingCar.param.AddToShoppingCarParam;
import com.jinpinghu.logic.shoppingCar.param.ChangeShoppingCarGoodsNumberParam;
import com.jinpinghu.logic.shoppingCar.param.DeleteShoppingCarGoodsParam;
import com.jinpinghu.logic.shoppingCar.param.GetMyShoppingCarCountParam;
import com.jinpinghu.logic.shoppingCar.param.GetMyShoppingCarListParam;

@Path("shoppingCar")
@Produces("application/json;charset=UTF-8")
public class ShoppingCarAction extends BaseZAction{

	/**
	 * 获取我的购物车列表
	 */
	@POST
	@Path("getMyShoppingCarList.do")
	public String getMyShoppingCarList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetMyShoppingCarListParam myParam = new GetMyShoppingCarListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetMyShoppingCarListLogic().process(myParam);
	}
	/**
	 * 获取我的购物车商品个数
	 */
	@POST
	@Path("getMyShoppingCarCount.do")
	public String getMyShoppingCarCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetMyShoppingCarCountParam myParam = new GetMyShoppingCarCountParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetMyShoppingCarCountLogic().process(myParam);
	}
	/**
	 * 添加至购物车
	 */
	@POST
	@Path("addToShoppingCar.do")
	public String addToShoppingCar(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("num") String num,
			@FormParam("toolId") String toolId,
			@Context HttpServletRequest request) {
		AddToShoppingCarParam myParam = new AddToShoppingCarParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNum(num);
		myParam.setToolId(toolId);
		return new AddToShoppingCarLogic().process(myParam);
	}
	
	/**
	 * 修改购物车商品数量
	 */
	@POST
	@Path("changeShoppingCarGoodsNumber.do")
	public String ChangeShoppingCarGoodsNumber(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("shoppingCarId") String shoppingCarId,
			@FormParam("number") Integer number,
			@Context HttpServletRequest request) {
		ChangeShoppingCarGoodsNumberParam myParam = new ChangeShoppingCarGoodsNumberParam(userId, apiKey, request);
		myParam.setShoppingCarId(shoppingCarId);
		myParam.setNumber(number);
		return new ChangeShoppingCarGoodsNumberLogic().process(myParam);
	}
	/**
	 * 删除购物车商品
	 */
	@POST
	@Path("deleteShoppingCarGoods.do")
	public String deleteShoppingCarGoods(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("shoppingCarIds") String shoppingCarIds,
			@Context HttpServletRequest request) {
		DeleteShoppingCarGoodsParam myParam = new DeleteShoppingCarGoodsParam(userId, apiKey, request);
		myParam.setShoppingCarIds(shoppingCarIds);
		return new DeleteShoppingCarGoodsLogic().process(myParam);
	}
	
}
