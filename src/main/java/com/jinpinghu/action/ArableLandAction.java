package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.arableLand.AddOrUpdateArableLandLogic;
import com.jinpinghu.logic.arableLand.DelArableLandLogic;
import com.jinpinghu.logic.arableLand.GetArableLandInfoByIdLogic;
import com.jinpinghu.logic.arableLand.GetArableLandInfoLogic;
import com.jinpinghu.logic.arableLand.GetArableLandListLogic;
import com.jinpinghu.logic.arableLand.param.AddOrUpdateArableLandParam;
import com.jinpinghu.logic.arableLand.param.GetArableLandListParam;

@Path("arableLand")
@Produces("application/json;charset=UTF-8")
public class ArableLandAction extends BaseZAction{

	@POST
	@Path("addOrUpdateArableLand.do")
	public String addOrUpdateArableLand(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@FormParam("villageGroup")  String villageGroup,
			@FormParam("userName")  String userName,
			@FormParam("idCard")  String idCard,
			@FormParam("area")  String area,
			@FormParam("riceArea")  String riceArea,
			@FormParam("wheatArea")  String wheatArea,
			@FormParam("vegetablesArea")  String vegetablesArea,
			@FormParam("fruitsArea")  String fruitsArea,
			@FormParam("otherArea")  String otherArea,
			@FormParam("remark")  String remark,
			@FormParam("town")  String town,
			@Context HttpServletRequest request) {
		AddOrUpdateArableLandParam myParam = new AddOrUpdateArableLandParam(userId,apiKey,request);
		myParam.setId(id);
		myParam.setArea(area);
		myParam.setFruitsArea(fruitsArea);
		myParam.setIdCard(idCard);
		myParam.setOtherArea(otherArea);
		myParam.setRemark(remark);
		myParam.setRiceArea(riceArea);
		myParam.setTown(town);
		myParam.setUserName(userName);
		myParam.setVegetablesArea(vegetablesArea);
		myParam.setVillageGroup(villageGroup);
		myParam.setWheatArea(wheatArea);
		return new AddOrUpdateArableLandLogic().process(myParam);
	}
	
	@POST
	@Path("getArableLandList.do")
	public String getArableLandList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("idCard") String idCard,
			@FormParam("userName") String userName,
			@FormParam("dscd") String dscd,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetArableLandListParam myParam = new GetArableLandListParam(userId,apiKey,request);
		myParam.setUserName(userName);
		myParam.setDscd(dscd);
		myParam.setIdCard(idCard);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		return new GetArableLandListLogic().process(myParam);
	}
	
	@POST
	@Path("getArableLandInfo.do")
	public String getArableLandInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		AddOrUpdateArableLandParam myParam = new AddOrUpdateArableLandParam(userId,apiKey,request);
		myParam.setId(id);
		return new GetArableLandInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getArableLandInfoById.do")
	public String getArableLandInfoById(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		AddOrUpdateArableLandParam myParam = new AddOrUpdateArableLandParam(userId,apiKey,request);
		myParam.setId(id);
		return new GetArableLandInfoByIdLogic().process(myParam);
	}
	

	@POST
	@Path("delArableLand.do")
	public String delArableLand(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		AddOrUpdateArableLandParam myParam = new AddOrUpdateArableLandParam(userId,apiKey,request);
		myParam.setId(id);
		return new DelArableLandLogic().process(myParam);
	}
	
	
	
}
