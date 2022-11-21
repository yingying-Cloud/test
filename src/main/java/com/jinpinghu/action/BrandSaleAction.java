package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.brandSale.AddOrUpdateBrandSaleLogic;
import com.jinpinghu.logic.brandSale.DelBrandSaleInfoLogic;
import com.jinpinghu.logic.brandSale.GetBrandSaleInfoLogic;
import com.jinpinghu.logic.brandSale.GetBrandSaleListLogic;
import com.jinpinghu.logic.brandSale.UpdateBrandSaleStatusLogic;
import com.jinpinghu.logic.brandSale.UpdateBrandSaleUpStatusLogic;
import com.jinpinghu.logic.brandSale.param.AddOrUpdateBrandSaleParam;
import com.jinpinghu.logic.brandSale.param.DelBrandSaleParam;
import com.jinpinghu.logic.brandSale.param.GetBrandSaleInfoParam;
import com.jinpinghu.logic.brandSale.param.GetBrandSaleListParam;
import com.jinpinghu.logic.brandSale.param.UpdateBrandSaleStatusParam;

@Path("brandSale")
@Produces("application/json;charset=UTF-8")
public class BrandSaleAction extends BaseZAction{

	/*
	 * 添加或删除预售商品
	 * */
	@POST
	@Path("addOrUpdateBrandSale.do")
	public String addOrUpdateBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("brandId")  String brandId,
			@FormParam("inputTime")  String inputTime,
			@FormParam("price") String price,
			@FormParam("num") String num,
			@FormParam("file") String file,
			@FormParam("describe") String describe,
			@Context HttpServletRequest request) {
		AddOrUpdateBrandSaleParam myParam = new AddOrUpdateBrandSaleParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setId(id);
		myParam.setPrice(price);
		myParam.setBrandId(brandId);
		myParam.setInputTime(inputTime);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNum(num);
		myParam.setFile(file);
		myParam.setDescribe(describe);
		return new AddOrUpdateBrandSaleLogic().process(myParam);
	}
	
	/*
	 * 获取品牌列表
	 * */
	@POST
	@Path("getBrandSaleList.do")
	public String getBrandSaleList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("status") String status,
			@FormParam("name") String name,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetBrandSaleListParam myParam = new GetBrandSaleListParam(userId,apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStatus(status);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetBrandSaleListLogic().process(myParam);
	}
	
	/*
	 * 删除品牌
	 * */
	@POST
	@Path("DelBrandSale.do")
	public String DelBrandSale(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		DelBrandSaleParam myParam = new DelBrandSaleParam(userId,apiKey, request);
		myParam.setId(id);
		return new DelBrandSaleInfoLogic().process(myParam);
	}
	
	/*
	 *获取品牌详情
	 * */
	@POST
	@Path("getBrandSaleInfo.do")
	public String getBrandInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetBrandSaleInfoParam myParam = new GetBrandSaleInfoParam(userId,apiKey, request);
		myParam.setId(id);
		return new GetBrandSaleInfoLogic().process(myParam);
	}
	/*
	 * 审核
	 * */
	@POST
	@Path("updateBrandSaleStatus.do")
	public String updateBrandSaleStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		UpdateBrandSaleStatusParam myParam = new UpdateBrandSaleStatusParam(userId,apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new UpdateBrandSaleStatusLogic().process(myParam);
	}
	/*
	 * 上架
	 * */
	@POST
	@Path("updateBrandSaleUpStatus.do")
	public String updateBrandSaleUpStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		UpdateBrandSaleStatusParam myParam = new UpdateBrandSaleStatusParam(userId,apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new UpdateBrandSaleUpStatusLogic().process(myParam);
	}
}
