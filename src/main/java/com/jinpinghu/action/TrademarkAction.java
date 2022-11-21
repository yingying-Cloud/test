package com.jinpinghu.action;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.trademark.AddOrUpdateTrademarkLogic;
import com.jinpinghu.logic.trademark.DelTrademarkLogic;
import com.jinpinghu.logic.trademark.GetTrademarkInfoLogic;
import com.jinpinghu.logic.trademark.GetTrademarkList2Logic;
import com.jinpinghu.logic.trademark.GetTrademarkListLogic;
import com.jinpinghu.logic.trademark.param.AddOrUpdateTrademarkParam;
import com.jinpinghu.logic.trademark.param.DelTrademarkParam;
import com.jinpinghu.logic.trademark.param.GetTrademarkInfoParam;
import com.jinpinghu.logic.trademark.param.GetTrademarkList2Param;
import com.jinpinghu.logic.trademark.param.GetTrademarkListParam;



@Path("trademark")
@Produces("application/json;charset=UTF-8")
public class TrademarkAction extends BaseZAction {

	@POST
	@Path("addOrUpdateTrademark.do")
	public String addTrademark(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("address") String address,
			@FormParam("brandName") String brandName,
			@FormParam("contractNumber") String contractNumber,
			@FormParam("productCertification") String productCertification,
			@FormParam("source") String source,
			@FormParam("trademarkName") String trademarkName,
			@FormParam("x") String x,
			@FormParam("file") String file,
			@FormParam("y") String y,
			@FormParam("brand") String brand,
			@Context HttpServletRequest request) {
		AddOrUpdateTrademarkParam myParam = new AddOrUpdateTrademarkParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setAddress(address);
		myParam.setBrandName(brandName);
		myParam.setContractNumber(contractNumber);
		myParam.setProductCertification(productCertification);
		myParam.setSource(source);
		myParam.setTrademarkName(trademarkName);
		myParam.setX(x);
		myParam.setY(y);
		myParam.setBrand(brand);
		myParam.setFile(file);
		return new AddOrUpdateTrademarkLogic().process(myParam);
	}
	
	
	@POST
	@Path("delTrademark.do")
	public String delTrademark(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelTrademarkParam myParam = new DelTrademarkParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelTrademarkLogic().process(myParam);
	}
	
	@POST
	@Path("getTrademarkInfo.do")
	public String getTrademarkInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetTrademarkInfoParam myParam = new GetTrademarkInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetTrademarkInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getTrademarkList.do")
	public String getTrademarkList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name ,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetTrademarkListParam myParam = new GetTrademarkListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetTrademarkListLogic().process(myParam);
	}
	@POST
	@Path("getTrademarkBrandList.do")
	public String getTrademarkBrandList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("trademarkId")  String trademarkId ,
			@FormParam("brandId")  String brandId ,
			@FormParam("name")  String name ,
			@FormParam("productName")  String productName ,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetTrademarkList2Param myParam = new GetTrademarkList2Param(userId, apiKey, request);
		myParam.setBrandId(brandId);
		myParam.setTrademarkId(trademarkId);
		myParam.setName(name);
		myParam.setProductName(productName);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetTrademarkList2Logic().process(myParam);
	}
	
	
}
