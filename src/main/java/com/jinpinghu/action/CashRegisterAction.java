package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.logic.cashRegister.AddOrUpdateCashRegisterLogic;
import com.jinpinghu.logic.cashRegister.DelCashRegisterLogic;
import com.jinpinghu.logic.cashRegister.DetailCashRegisterLogic;
import com.jinpinghu.logic.cashRegister.ListCashRegisterLogic;
import com.jinpinghu.logic.cashRegister.param.AddOrUpdateCashRegisterParam;
import com.jinpinghu.logic.cashRegister.param.DelCashRegisterParam;
import com.jinpinghu.logic.cashRegister.param.DetailCashRegisterParam;
import com.jinpinghu.logic.cashRegister.param.ListCashRegisterParam;


@Path("cashRegister")
@Produces("application/json;charset=UTF-8")
public class CashRegisterAction extends BaseZAction{

	/*
	 * 增加修改农资收银设备
	 */
	@POST
	@Path("addOrUpdateCashRegister.do")
	public String addOrUpdateCashRegister(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("cashRegisterId") String cashRegisterId,
			@FormParam("baiduAifaceSn") String baiduAifaceSn,
			@Context HttpServletRequest request) {
		AddOrUpdateCashRegisterParam myParam = new AddOrUpdateCashRegisterParam(userId, apiKey, request);
		myParam.setCashRegisterId(cashRegisterId);
		myParam.setBaiduAifaceSn(baiduAifaceSn);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new AddOrUpdateCashRegisterLogic().process(myParam);
	}
	/*
	 * 删除农资收银设备
	 */
	@POST
	@Path("delCashRegister.do")
	public String delCashRegister(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelCashRegisterParam myParam = new DelCashRegisterParam(userId, apiKey, request);
		myParam.setIds(id);
		return new DelCashRegisterLogic().process(myParam);
	}
	/*
	 * 农资收银设备详情
	 */
	@POST
	@Path("detailCashRegister.do")
	public String detailCashRegister(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DetailCashRegisterParam myParam = new DetailCashRegisterParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new DetailCashRegisterLogic().process(myParam);
	}
	/*
	 * 农资收银设备列表
	 */
	@POST
	@Path("listCashRegister.do")
	public String listlCashRegister(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("baiduAifaceSn") String baiduAifaceSn,
			@FormParam("cashRegisterId") String cashRegisterId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		ListCashRegisterParam myParam = new ListCashRegisterParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setBaiduAifaceSn(baiduAifaceSn);
		myParam.setCashRegisterId(cashRegisterId);
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		return new ListCashRegisterLogic().process(myParam);
	}
	
}
