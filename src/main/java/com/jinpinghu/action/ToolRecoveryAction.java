package com.jinpinghu.action;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.toolRecovery.AddOrUpdateToolRecoveryLogic;
import com.jinpinghu.logic.toolRecovery.DelToolRecoveryLogic;
import com.jinpinghu.logic.toolRecovery.GetToolRecoveryInfoLogic;
import com.jinpinghu.logic.toolRecovery.GetToolRecoveryListLogic;
import com.jinpinghu.logic.toolRecovery.param.AddOrUpdateToolRecoveryParam;
import com.jinpinghu.logic.toolRecovery.param.DelToolRecoveryParam;
import com.jinpinghu.logic.toolRecovery.param.GetToolRecoveryInfoParam;
import com.jinpinghu.logic.toolRecovery.param.GetToolRecoveryListParam;


@Path("toolRecovery")
@Produces("application/json;charset=UTF-8")
public class ToolRecoveryAction extends BaseZAction {

	@POST
	@Path("addOrUpdateToolRecovery.do")
	public String addToolRecovery(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("name") String name,
			@FormParam("model") String model,
			@FormParam("specification") String specification,
			@FormParam("unit") String unit,
			@FormParam("type") String type,
			@FormParam("file") String file,
			@FormParam("describe") String describe,
			@FormParam("price") String price,
			@Context HttpServletRequest request) {
		AddOrUpdateToolRecoveryParam myParam = new AddOrUpdateToolRecoveryParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setDescribe(describe);
		myParam.setModel(model);
		myParam.setName(name);
		myParam.setSpecification(specification);
		myParam.setUnit(unit);
		myParam.setType(type);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setFile(file);
		myParam.setPrice(price);
		return new AddOrUpdateToolRecoveryLogic().process(myParam);
	}
	
	
	@POST
	@Path("delToolRecovery.do")
	public String delToolRecovery(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelToolRecoveryParam myParam = new DelToolRecoveryParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelToolRecoveryLogic().process(myParam);
	}
	
	@POST
	@Path("getToolRecoveryInfo.do")
	public String getToolRecoveryInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetToolRecoveryInfoParam myParam = new GetToolRecoveryInfoParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		return new GetToolRecoveryInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getToolRecoveryList.do")
	public String getToolRecoveryList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name ,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("dscd")  String dscd ,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetToolRecoveryListParam myParam = new GetToolRecoveryListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setName(name);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setSelectAll(selectAll);
		return new GetToolRecoveryListLogic().process(myParam);
	}
	
	
}
