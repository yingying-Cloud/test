package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.advertisement.AddOrUpdateAdvertisementLogic;
import com.jinpinghu.logic.advertisement.DelAdvertisementLogic;
import com.jinpinghu.logic.advertisement.DetailAdvertisementLogic;
import com.jinpinghu.logic.advertisement.GetShowAdvertisementLogic;
import com.jinpinghu.logic.advertisement.ListAdvertisementLogic;
import com.jinpinghu.logic.advertisement.param.AddOrUpdateAdvertisementParam;
import com.jinpinghu.logic.advertisement.param.DelAdvertisementParam;
import com.jinpinghu.logic.advertisement.param.DetailAdvertisementParam;
import com.jinpinghu.logic.advertisement.param.ListAdvertisementParam;


@Path("advertisement")
@Produces("application/json;charset=UTF-8")
public class AdvertisementAction extends BaseZAction{

	/*
	 * 增加修改广告
	 */
	@POST
	@Path("addOrUpdateAdvertisement.do")
	public String addOrUpdateAdvertisement(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("title") String title,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("visible") String visible,
			@FormParam("file") String file,
			@FormParam("enterpriseJson") String enterpriseJson,
			@FormParam("type") String type,
			@Context HttpServletRequest request) {
		AddOrUpdateAdvertisementParam myParam = new AddOrUpdateAdvertisementParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setTitle(title);
		myParam.setFileUrls(file);
		myParam.setVisible(visible);
		myParam.setType(type);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseJson(enterpriseJson);
	return new AddOrUpdateAdvertisementLogic().process(myParam);
	}
	/*
	 * 删除广告
	 */
	@POST
	@Path("delAdvertisement.do")
	public String delAdvertisement(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelAdvertisementParam myParam = new DelAdvertisementParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelAdvertisementLogic().process(myParam);
	}
	/*
	 * 广告详情
	 */
	@POST
	@Path("detailAdvertisement.do")
	public String detailAdvertisement(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DetailAdvertisementParam myParam = new DetailAdvertisementParam(userId, apiKey, request);
		myParam.setId(id);
		return new DetailAdvertisementLogic().process(myParam);
	}
	/*
	 * 广告列表
	 */
	@POST
	@Path("listAdvertisement.do")
	public String listlAdvertisement(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("title") String title,
			@FormParam("visible") String visible,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@FormParam("type") String type,
			@FormParam("status") String status,
			@Context HttpServletRequest request) {
		ListAdvertisementParam myParam = new ListAdvertisementParam(userId, apiKey, request);
		myParam.setTitle(title);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setVisible(visible);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setType(type);
		myParam.setStatus(status);
		return new ListAdvertisementLogic().process(myParam);
	}
	
	@POST
	@Path("getShowAdvertisement.do")
	public String addTagSort(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("code") String code,
			@Context HttpServletRequest request) {
		DetailAdvertisementParam myParam = new DetailAdvertisementParam(userId,apiKey,request);
		myParam.setCode(code);
		return new GetShowAdvertisementLogic().process(myParam);
	}
	
}
