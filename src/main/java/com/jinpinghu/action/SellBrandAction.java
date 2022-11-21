package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.plant.param.SimpleParam;
import com.jinpinghu.logic.sellBrand.AddOrUpdateSellBrandLogic;
import com.jinpinghu.logic.sellBrand.AddSellBrandFromBrandLogic;
import com.jinpinghu.logic.sellBrand.AuditSellBrandUpStatusLogic;
import com.jinpinghu.logic.sellBrand.DelSellBrandLogic;
import com.jinpinghu.logic.sellBrand.GetBrandList2Logic;
import com.jinpinghu.logic.sellBrand.GetSellBrandInfoLogic;
import com.jinpinghu.logic.sellBrand.GetSellBrandListLogic;
import com.jinpinghu.logic.sellBrand.param.AddOrUpdateSellBrandParam;
import com.jinpinghu.logic.sellBrand.param.AddSellBrandFromBrandParam;
import com.jinpinghu.logic.sellBrand.param.AuditSellBrandParam;
import com.jinpinghu.logic.sellBrand.param.GetBrandList2Param;
import com.jinpinghu.logic.sellBrand.param.GetSellBrandListParam;

@Path("sellBrand")
@Produces("application/json;charset=UTF-8")
public class SellBrandAction extends BaseZAction{
	
	/*
	 * 添加或删除品牌
	 * */
	@POST
	@Path("addOrUpdateSellBrand.do")
	public String addOrUpdateSellBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("productName")  String productName,
			@FormParam("model")  String model,
			@FormParam("registeredTrademark")  String registeredTrademark,
			@FormParam("authorizationCategory") String authorizationCategory,
			@FormParam("json") String json,
			@FormParam("type") String type,
			@FormParam("price") String price,
			@FormParam("unit") String unit,
			@FormParam("spec") String spec,
			@FormParam("describe") String describe,
			@FormParam("status") String status,
			@FormParam("upStatus") String upStatus,
			@Context HttpServletRequest request) {
		AddOrUpdateSellBrandParam myParam = new AddOrUpdateSellBrandParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setProductName(productName);
		myParam.setId(id);
		myParam.setJson(json);
		myParam.setType(type);
		myParam.setPrice(price);
		myParam.setUnit(unit);
		myParam.setSpec(spec);
		myParam.setDescribe(describe);
		myParam.setStatus(status);
		myParam.setUpStatus(upStatus);
		return new AddOrUpdateSellBrandLogic().process(myParam);
	}
	
	/*
	 * 获取品牌列表
	 * */
	@POST
	@Path("getSellBrandList.do")
	public String getSellBrandList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("productName")  String productName,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("status")  String status,
			@FormParam("type")  String type,
			@FormParam("upStatus")  String upStatus,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetSellBrandListParam myParam = new GetSellBrandListParam(userId,apiKey, request);
		myParam.setProductName(productName);
		myParam.setStatus(status);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setUpStatus(upStatus);
		return new GetSellBrandListLogic().process(myParam);
	}
	
	/*
	 * 删除品牌
	 * */
	@POST
	@Path("DelSellBrand.do")
	public String DelSellBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelSellBrandLogic().process(myParam);
	}
	
	/*
	 *获取品牌详情
	 * */
	@POST
	@Path("getSellBrandInfo.do")
	public String getSellBrandInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetSellBrandInfoLogic().process(myParam);
	}
	
	/*
	 *审核品牌
	 * */
	@POST
	@Path("auditSellBrand.do")
	public String auditSellBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		AuditSellBrandParam myParam = new AuditSellBrandParam(userId,apiKey, request);
		myParam.setIds(ids);
		myParam.setStatus(status);
		return new AuditSellBrandUpStatusLogic().process(myParam);
	}
	
	/*
	 *批量添加
	 * */
	@POST
	@Path("addSellBrandFromBrand.do")
	public String addSellBrandFromBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		AddSellBrandFromBrandParam myParam = new AddSellBrandFromBrandParam(userId,apiKey, request);
		myParam.setIds(ids);
		myParam.setEnterpriseId(enterpriseId);
		return new AddSellBrandFromBrandLogic().process(myParam);
	}
	
	/*
	 *批量添加
	 * */
	@POST
	@Path("getBrandList.do")
	public String getBrandList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetBrandList2Param myParam = new GetBrandList2Param(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetBrandList2Logic().process(myParam);
	}
	
	
	
}
