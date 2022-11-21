package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.pests.AddPestsLogic;
import com.jinpinghu.logic.pests.DelPestsLogic;
import com.jinpinghu.logic.pests.GetPestsInfoLogic;
import com.jinpinghu.logic.pests.GetPestsListLogic;
import com.jinpinghu.logic.pests.param.AddPestsParam;
import com.jinpinghu.logic.pests.param.GetPestsInfoParam;
import com.jinpinghu.logic.pests.param.GetPestsListParam;

@Path("pests")
@Produces("application/json;charset=UTF-8")
public class PestsAction extends BaseZAction{

	@POST
	@Path("getPestsList.do")
	public String getPestsList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("type") String type,
			@FormParam("title") String title,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetPestsListParam myParam = new GetPestsListParam(userId,apiKey,request);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setTitle(title);
		myParam.setType(type);
		return new GetPestsListLogic().process(myParam);
	}
	
	@POST
	@Path("getPestsInfo.do")
	public String getPestsInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetPestsInfoParam myParam = new GetPestsInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new GetPestsInfoLogic().process(myParam);
	}
	@POST
	@Path("delPests.do")
	public String delPests(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetPestsInfoParam myParam = new GetPestsInfoParam(userId,apiKey,request);
		myParam.setId(id);
		return new DelPestsLogic().process(myParam);
	}
	
	@POST
	@Path("addPests.do")
	public String addPests(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@FormParam("type") String type,
			@FormParam("title") String title,
			@FormParam("features") String features,
			@FormParam("happen") String happen,
			@FormParam("agriculturalControl") String agriculturalControl,
			@FormParam("greenControl") String greenControl,
			@FormParam("organicControl") String organicControl,
			@FormParam("allControl") String allControl,
			@FormParam("greenControlMedicine") String greenControlMedicine,
			@FormParam("organicControlMedicine") String organicControlMedicine,
			@FormParam("allControlMedicine") String allControlMedicine,
			@FormParam("file") String file,
			@Context HttpServletRequest request) {
		AddPestsParam myParam = new AddPestsParam(userId,apiKey,request);
		myParam.setId(id);
		myParam.setTitle(title);
		myParam.setType(type);
		myParam.setAgriculturalControl(agriculturalControl);
		myParam.setAllControl(allControl);
		myParam.setAllControlMedicine(allControlMedicine);
		myParam.setFeatures(features);
		myParam.setGreenControl(greenControl);
		myParam.setGreenControlMedicine(greenControlMedicine);
		myParam.setHappen(happen);
		myParam.setOrganicControl(organicControl);
		myParam.setOrganicControlMedicine(organicControlMedicine);
		myParam.setFile(file);
		return new AddPestsLogic().process(myParam);
	}
	
}
