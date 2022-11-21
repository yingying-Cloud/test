package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.sellBrandRecord.AddOrUpdateSellBrandRecordLogic;
import com.jinpinghu.logic.sellBrandRecord.DelSellBrandRecordLogic;
import com.jinpinghu.logic.sellBrandRecord.GetSellBrandRecordInfoLogic;
import com.jinpinghu.logic.sellBrandRecord.GetSellBrandRecordListLogic;
import com.jinpinghu.logic.sellBrandRecord.param.AddOrUpdateSellBrandRecordParam;
import com.jinpinghu.logic.sellBrandRecord.param.DelSellBrandRecordParam;
import com.jinpinghu.logic.sellBrandRecord.param.GetSellBrandRecordInfoParam;
import com.jinpinghu.logic.sellBrandRecord.param.GetSellBrandRecordListParam;


@Path("sellBrandRecord")
@Produces("application/json;charset=UTF-8")
public class SellBrandRecordAction extends BaseZAction {

	@POST
	@Path("getSellBrandRecordList.do")
	public String getSellBrandRecordRecordList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("sellBrandId") String sellBrandId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("recordType") String recordType,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("type")  String type,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetSellBrandRecordListParam myParam = new GetSellBrandRecordListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);;
		myParam.setName(name);
		myParam.setSellBrandId(sellBrandId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setRecordType(recordType);
		myParam.setType(type);
		return new GetSellBrandRecordListLogic().process(myParam);
	}
	
	
	@POST
	@Path("addOrUpdateSellBrandRecord.do")
	public String addSellBrandRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("sellBrandId")  String sellBrandId,
			@FormParam("recordType")  String recordType,
			@FormParam("number")  String number,
			@FormParam("useName")  String useName,
			@FormParam("useTime")  String useTime,
			@FormParam("file")  String file,
			@FormParam("outName")  String outName,
			@FormParam("outMobile")  String outMobile,
			@Context HttpServletRequest request) {
		AddOrUpdateSellBrandRecordParam myParam = new AddOrUpdateSellBrandRecordParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setSellBrandId(sellBrandId);
		myParam.setNumber(number);
		myParam.setRecordType(recordType);
		myParam.setFile(file);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setUseName(useName);
		myParam.setUseTime(useTime);
		myParam.setOutMobile(outMobile);
		myParam.setOutName(outName);
		return new AddOrUpdateSellBrandRecordLogic().process(myParam);
	}
	
	
	@POST
	@Path("delSellBrandRecord.do")
	public String delSellBrandRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelSellBrandRecordParam myParam = new DelSellBrandRecordParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelSellBrandRecordLogic().process(myParam);
	}
	
	@POST
	@Path("getSellBrandRecordInfo.do")
	public String getSellBrandRecordInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetSellBrandRecordInfoParam myParam = new GetSellBrandRecordInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetSellBrandRecordInfoLogic().process(myParam);
	}
	
	
}
