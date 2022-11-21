package com.jinpinghu.action;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.enterprise.DelEnterpriseParamLogic;
import com.jinpinghu.logic.enterprise.param.DelEnterpriseParam;
import com.jinpinghu.logic.expert.AddExpertLogic;
import com.jinpinghu.logic.expert.AddOrUpdateExpertLogic;
import com.jinpinghu.logic.expert.ChangeExpertStatusLogic;
import com.jinpinghu.logic.expert.DelExpertLogic;
import com.jinpinghu.logic.expert.GetExpertInfoLogic;
import com.jinpinghu.logic.expert.GetExpertListLogic;
import com.jinpinghu.logic.expert.param.AddExpertParam;
import com.jinpinghu.logic.expert.param.AddOrUpdateExpertParam;
import com.jinpinghu.logic.expert.param.ChangeExpertStatusParam;
import com.jinpinghu.logic.expert.param.DelExpertParam;
import com.jinpinghu.logic.expert.param.GetExpertInfoParam;
import com.jinpinghu.logic.expert.param.GetExpertListParam;



@Path("expert")
@Produces("application/json;charset=UTF-8")
public class ExpertAction extends BaseZAction {

	@POST
	@Path("addOrUpdateExpert.do")
	public String addExpert(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("address")  String address,
			@FormParam("goodsField")  String goodsField,
			@FormParam("idcard")  String idcard,
			@FormParam("synopsis")  String synopsis,
			@FormParam("file")  String file,
			@FormParam("type")  String type,
			@FormParam("adnm")  String adnm,
			@FormParam("productTeam")  String productTeam,
			@Context HttpServletRequest request) {
		AddExpertParam myParam = new AddExpertParam(userId, apiKey, request);
		myParam.setFile(file);
		myParam.setId(id);
		myParam.setGoodsField(goodsField);
		myParam.setAddress(address);
		myParam.setIdcard(idcard);
		myParam.setSynopsis(synopsis);
		myParam.setType(type);
		myParam.setAdnm(adnm);
		myParam.setProductTeam(productTeam);
		return new AddExpertLogic().process(myParam);
	}
	@POST
	@Path("getExpertInfo.do")
	public String getExpertInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("uid")  String uid,
			@Context HttpServletRequest request) {
		GetExpertInfoParam myParam = new GetExpertInfoParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setUid(uid);
		return new GetExpertInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getExpertList.do")
	public String getExpertList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("status")  String status,
			@FormParam("type")  String type,
			@FormParam("lowIntegal")  String lowIntegral,
			@FormParam("highIntegal")  String highIntegral,
			@FormParam("adnm")  String adnm,
			@FormParam("productTeam")  String productTeam,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("orderby")  String orderby,
			@FormParam("sortDirection")  String sortDirection,
			@Context HttpServletRequest request) {
		GetExpertListParam myParam = new GetExpertListParam(userId, apiKey, request);
		myParam.setStatus(status);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setLowIntegral(lowIntegral);
		myParam.setHighIntegral(highIntegral);
		myParam.setAdnm(adnm);
		myParam.setProductTeam(productTeam);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setOrderby(orderby);
		myParam.setSortDirection(sortDirection);
		return new GetExpertListLogic().process(myParam);
	}
	
	@POST
	@Path("changeExpertStatus.do")
	public String changeExpertStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		ChangeExpertStatusParam myParam = new ChangeExpertStatusParam(userId, apiKey, request);
		myParam.setIds(id);
		myParam.setStatus(status);
		return new ChangeExpertStatusLogic().process(myParam);
	}
	
	@POST
	@Path("addOrUpdateExpert2.do")
	public String addOrUpdateExpert2(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("address")  String address,
			@FormParam("goodsField")  String goodsField,
			@FormParam("idcard")  String idcard,
			@FormParam("mobile")  String mobile,
			@FormParam("name")  String name,
			@FormParam("synopsis")  String synopsis,
			@FormParam("file")  String file,
			@FormParam("type")  String type,
			@FormParam("adnm")  String adnm,
			@FormParam("productTeam")  String productTeam,
			@Context HttpServletRequest request) {
		AddOrUpdateExpertParam myParam = new AddOrUpdateExpertParam(userId, apiKey, request);
		myParam.setFile(file);
		myParam.setId(id);
		myParam.setGoodsField(goodsField);
		myParam.setAddress(address);
		myParam.setMobile(mobile);
		myParam.setName(name);
		myParam.setIdcard(idcard);
		myParam.setSynopsis(synopsis);
		myParam.setType(type);
		myParam.setAdnm(adnm);
		myParam.setProductTeam(productTeam);
		return new AddOrUpdateExpertLogic().process(myParam);
	}
	
	@POST
	@Path("delExpert.do")
	public String delExpert(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("expertIds")  String expertIds,
			@Context HttpServletRequest request) {
		DelExpertParam myParam = new DelExpertParam(userId, apiKey, request);
		myParam.setId(expertIds);
		return new DelExpertLogic().process(myParam);
	}
}
