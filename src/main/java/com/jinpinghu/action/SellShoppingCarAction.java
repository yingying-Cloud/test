package com.jinpinghu.action;

import com.jinpinghu.logic.sellShoppingCar.*;
import com.jinpinghu.logic.sellShoppingCar.param.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("sellShoppingCar")
@Produces("application/json;charset=UTF-8")
public class SellShoppingCarAction extends BaseZAction{

	/**
	 * 获取我的购物车列表
	 */
	@POST
	@Path("getMySellShoppingCarList.do")
	public String getMySellShoppingCarList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetMySellShoppingCarListParam myParam = new GetMySellShoppingCarListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetMySellShoppingCarListLogic().process(myParam);
	}
	/**
	 * 获取我的购物车商品个数
	 */
	@POST
	@Path("getMySellShoppingCarCount.do")
	public String getMySellShoppingCarCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetMySellShoppingCarCountParam myParam = new GetMySellShoppingCarCountParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetMySellShoppingCarCountLogic().process(myParam);
	}
	/**
	 * 添加至购物车
	 */
	@POST
	@Path("addToSellShoppingCar.do")
	public String addToSellShoppingCar(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("num") String num,
			@FormParam("brandId") String brandId,
			@FormParam("sellId") String sellId,
			@Context HttpServletRequest request) {
		AddToSellShoppingCarParam myParam = new AddToSellShoppingCarParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNum(num);
		myParam.setSellId(sellId);
		myParam.setBrandId(brandId);
		return new AddToSellShoppingCarLogic().process(myParam);
	}
	
	/**
	 * 修改购物车商品数量
	 */
	@POST
	@Path("changeSellShoppingCarGoodsNumber.do")
	public String ChangeSellShoppingCarGoodsNumber(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("sellShoppingCarId") String sellShoppingCarId,
			@FormParam("num") Integer number,
			@Context HttpServletRequest request) {
		ChangeSellShoppingCarGoodsNumberParam myParam = new ChangeSellShoppingCarGoodsNumberParam(userId, apiKey, request);
		myParam.setSellShoppingCarId(sellShoppingCarId);
		myParam.setNumber(number);
		return new ChangeSellShoppingCarGoodsNumberLogic().process(myParam);
	}
	/**
	 * 删除购物车商品
	 */
	@POST
	@Path("deleteSellShoppingCarGoods.do")
	public String deleteSellShoppingCarGoods(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("sellShoppingCarIds") String sellShoppingCarIds,
			@Context HttpServletRequest request) {
		DeleteSellShoppingCarGoodsParam myParam = new DeleteSellShoppingCarGoodsParam(userId, apiKey, request);
		myParam.setSellShoppingCarIds(sellShoppingCarIds);
		return new DeleteSellShoppingCarGoodsLogic().process(myParam);
	}
	
}
