package com.jinpinghu.action;

import com.jinpinghu.logic.brandShoppingCar.*;
import com.jinpinghu.logic.brandShoppingCar.param.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("brandShoppingCar")
@Produces("application/json;charset=UTF-8")
public class BrandShoppingCarAction extends BaseZAction{

	/**
	 * 获取我的购物车列表
	 */
	@POST
	@Path("getMyBrandShoppingCarList.do")
	public String getMyBrandShoppingCarList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetMyBrandShoppingCarListParam myParam = new GetMyBrandShoppingCarListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetMyBrandShoppingCarListLogic().process(myParam);
	}
	/**
	 * 获取我的购物车商品个数
	 */
	@POST
	@Path("getMyBrandShoppingCarCount.do")
	public String getMyBrandShoppingCarCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetMyBrandShoppingCarCountParam myParam = new GetMyBrandShoppingCarCountParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetMyBrandShoppingCarCountLogic().process(myParam);
	}
	/**
	 * 添加至购物车
	 */
	@POST
	@Path("addToBrandShoppingCar.do")
	public String addToBrandShoppingCar(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("num") String num,
			@FormParam("brandId") String brandId,
			@FormParam("type") String type,
			@FormParam("brandSaleId") String brandSaleId,
			@FormParam("sellId") String sellId,
			@Context HttpServletRequest request) {
		AddToBrandShoppingCarParam myParam = new AddToBrandShoppingCarParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNum(num);
		myParam.setBrandId(brandId);
		myParam.setType(type);
		myParam.setBrandSaleId(brandSaleId);
		myParam.setSellId(sellId);
		return new AddToBrandShoppingCarLogic().process(myParam);
	}
	
	/**
	 * 修改购物车商品数量
	 */
	@POST
	@Path("changeBrandShoppingCarGoodsNumber.do")
	public String ChangeBrandShoppingCarGoodsNumber(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("brandShoppingCarId") String brandShoppingCarId,
			@FormParam("num") Integer number,
			@Context HttpServletRequest request) {
		ChangeBrandShoppingCarGoodsNumberParam myParam = new ChangeBrandShoppingCarGoodsNumberParam(userId, apiKey, request);
		myParam.setBrandShoppingCarId(brandShoppingCarId);
		myParam.setNumber(number);
		return new ChangeBrandShoppingCarGoodsNumberLogic().process(myParam);
	}
	/**
	 * 删除购物车商品
	 */
	@POST
	@Path("deleteBrandShoppingCarGoods.do")
	public String deleteBrandShoppingCarGoods(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("brandShoppingCarIds") String brandShoppingCarIds,
			@Context HttpServletRequest request) {
		DeleteBrandShoppingCarGoodsParam myParam = new DeleteBrandShoppingCarGoodsParam(userId, apiKey, request);
		myParam.setBrandShoppingCarIds(brandShoppingCarIds);
		return new DeleteBrandShoppingCarGoodsLogic().process(myParam);
	}
	
}
