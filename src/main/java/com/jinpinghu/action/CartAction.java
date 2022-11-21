package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.cart.AddToCartLogic;
import com.jinpinghu.logic.cart.ChangeOrderStatusLogic;
import com.jinpinghu.logic.cart.DelCartLogic;
import com.jinpinghu.logic.cart.GetMyCartListLogic;
import com.jinpinghu.logic.cart.GetOrderInfoLogic;
import com.jinpinghu.logic.cart.GetOrderListLogic;
import com.jinpinghu.logic.cart.HistoryOrderLogic;
import com.jinpinghu.logic.cart.SubmitOrderLogic;
import com.jinpinghu.logic.cart.UpdateToolNumCartLogic;
import com.jinpinghu.logic.cart.param.AddOrderParam;
import com.jinpinghu.logic.cart.param.AddToCartParam;

@Path("cart")
@Produces("application/json;charset=UTF-8")
public class CartAction extends BaseZAction{

	/**
	 * 添加商品到购物车
	 */
	@POST
	@Path("addToCart.do")
	public String addToCart(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolId") Integer toolId,
			@FormParam("toolNum") String toolNum,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		myParam.setToolId(toolId);
		myParam.setToolNum(toolNum);
		return new AddToCartLogic().process(myParam);
	}

	/**
	 * 更新购物车内商品数量
	 */
	@POST
	@Path("updateToolNumCart.do")
	public String updateToolNumCart(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("cartId") String cartId,
			@FormParam("toolNum") String toolNum,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		myParam.setToolNum(toolNum);
		myParam.setCartId(cartId);
		return new UpdateToolNumCartLogic().process(myParam);
	}
	
	/**
	 * 获取购物车列表
	 */
	@POST
	@Path("getMyCartList.do")
	public String getMyCartList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		return new GetMyCartListLogic().process(myParam);
	}
	
	/**
	 * 删除购物车
	 */
	@POST
	@Path("delCart.do")
	public String delCart(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("cartIds") String cartIds,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		myParam.setCartIds(cartIds);
		return new DelCartLogic().process(myParam);
	}
	
	/**
	 * 提交订单
	 */
	@POST
	@Path("submitOrder.do")
	public String submitOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("cartJson") String cartJson,
			@FormParam("linkInfoId") String linkInfoId,
			@FormParam("addressId") Integer addressId,
			@Context HttpServletRequest request) {
		AddOrderParam myParam = new AddOrderParam(userId, apiKey, request);
		myParam.setCartJson(cartJson);
		myParam.setLinkInfoId(linkInfoId);
		myParam.setAddressId(addressId);
		return new SubmitOrderLogic().process(myParam);
	}
	
	/**
	 * 查看账单记录
	 */
	@POST
	@Path("historyOrder.do")
	public String historyOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("year") String year,
			@FormParam("name") String name,
			@FormParam("nowPage") Integer nowPage,
			@FormParam("status") Integer status,
			@FormParam("pageCount") Integer pageCount,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		myParam.setYear(year);
		myParam.setName(name);
		myParam.setStatus(status);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new HistoryOrderLogic().process(myParam);
	}
	
	
	/**
	 * 改变订单状态
	 */
	@POST
	@Path("changeOrderStatus.do")
	public String changeOrderStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("orderId") Integer orderId,
			@FormParam("status") Integer status,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		myParam.setOrderId(orderId);
		myParam.setStatus(status);
		return new ChangeOrderStatusLogic().process(myParam);
	}
	
	
	/**
	 * 查看订单记录
	 */
	@POST
	@Path("getOrderList.do")
	public String getOrderList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("enterpriseId") Integer enterpriseId,
			@FormParam("status") Integer status,
			@FormParam("nowPage") Integer nowPage,
			@FormParam("pageCount") Integer pageCount,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setStatus(status);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetOrderListLogic().process(myParam);
	}
	
	
	/**
	 * 查看订单详情
	 */
	@POST
	@Path("getOrderInfo.do")
	public String getOrderInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("orderId") Integer orderId,
			@Context HttpServletRequest request) {
		AddToCartParam myParam = new AddToCartParam(userId, apiKey, request);
		myParam.setOrderId(orderId);
		return new GetOrderInfoLogic().process(myParam);
	}
	
}
