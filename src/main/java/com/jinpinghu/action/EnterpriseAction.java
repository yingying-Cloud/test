package com.jinpinghu.action;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.agriculturalGreenhouses.param.GetGreenhouseGeomListParam;
import com.jinpinghu.logic.enterprise.AddEnterpriseBrandLogic;
import com.jinpinghu.logic.enterprise.AddEnterpriseFileLogic;
import com.jinpinghu.logic.enterprise.AddEnterpriseLogic;
import com.jinpinghu.logic.enterprise.AddOrRemoveEnterpriseZeroLogic;
import com.jinpinghu.logic.enterprise.AddOrRemoveEnterpriseZoneLogic;
import com.jinpinghu.logic.enterprise.AddOrUpdateEnterpriseLogic;
import com.jinpinghu.logic.enterprise.ChangeEnterpriseStateLogic;
import com.jinpinghu.logic.enterprise.ChangeEnterpriseStatusLogic;
import com.jinpinghu.logic.enterprise.DelEnterpriseFileLogic;
import com.jinpinghu.logic.enterprise.DelEnterpriseParamLogic;
import com.jinpinghu.logic.enterprise.GetEnterPriseZoneListLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseBrandListLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseFileCountLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseFileLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseGeomListLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseInfoLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseListLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseSouceInfoLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseSouceLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseSouceTopLogic;
import com.jinpinghu.logic.enterprise.GetEnterpriseZeroInfoLogic;
import com.jinpinghu.logic.enterprise.GetPlantEnterpriseListLogic;
import com.jinpinghu.logic.enterprise.GetStatusByTypeLogic;
import com.jinpinghu.logic.enterprise.GetToEnterpriseListLogic;
import com.jinpinghu.logic.enterprise.StatisticalEnterpriseCompleteOrderLogic;
import com.jinpinghu.logic.enterprise.StatisticalPlantEnterpriseCompleteOrderLogic;
import com.jinpinghu.logic.enterprise.UpdateEnterpriseListOrderLogic;
import com.jinpinghu.logic.enterprise.UpdateEnterpriseLogic;
import com.jinpinghu.logic.enterprise.param.AddEnterpriseBrandParam;
import com.jinpinghu.logic.enterprise.param.AddEnterpriseFileParam;
import com.jinpinghu.logic.enterprise.param.AddEnterpriseParam;
import com.jinpinghu.logic.enterprise.param.AddOrRemoveEnterpriseZeroParam;
import com.jinpinghu.logic.enterprise.param.AddOrRemoveEnterpriseZoneParam;
import com.jinpinghu.logic.enterprise.param.AddOrUpdateEnterpriseParam;
import com.jinpinghu.logic.enterprise.param.ChangeEnterpriseStatusParam;
import com.jinpinghu.logic.enterprise.param.DelEnterpriseFileParam;
import com.jinpinghu.logic.enterprise.param.DelEnterpriseParam;
import com.jinpinghu.logic.enterprise.param.GetEnterPriseZoneListParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseBrandListParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseFileParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseInfoParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseListParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseSouceInfoParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseSouceParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseZeroInfoParam;
import com.jinpinghu.logic.enterprise.param.GetPlantEnterpriseListParam;
import com.jinpinghu.logic.enterprise.param.GetStatusByTypeParam;
import com.jinpinghu.logic.enterprise.param.GetToEnterpriseListParam;
import com.jinpinghu.logic.enterprise.param.StatisticalEnterpriseCompleteOrderParam;
import com.jinpinghu.logic.enterprise.param.StatisticalPlantEnterpriseCompleteOrderParam;
import com.jinpinghu.logic.enterprise.param.UpdateEnterpriseListOrderParam;
import com.jinpinghu.logic.enterprise.param.UpdateEnterpriseParam;
import com.jinpinghu.logic.userProduction.AddEnterpriseUserProductionLogic;
import com.jinpinghu.logic.userProduction.AddUserProductionLogic;
import com.jinpinghu.logic.userProduction.DelUserProductionLogic;
import com.jinpinghu.logic.userProduction.GetEnterpriseUserProductionListLogic;
import com.jinpinghu.logic.userProduction.GetUserProductionEnterpriseListLogic;
import com.jinpinghu.logic.userProduction.GetUserProductionInfoLogic;
import com.jinpinghu.logic.userProduction.GetUserProductionListLogic;
import com.jinpinghu.logic.userProduction.param.AddEnterpriseUserProductionParam;
import com.jinpinghu.logic.userProduction.param.AddUserProductionParam;
import com.jinpinghu.logic.userProduction.param.DelUserProductionParam;
import com.jinpinghu.logic.userProduction.param.GetEnterpriseUserProductionListParam;
import com.jinpinghu.logic.userProduction.param.GetUserProductionListParam;

@Path("enterprise")
@Produces("application/json;charset=UTF-8")
public class EnterpriseAction extends BaseZAction {

	@POST
	@Path("addEnterprise.do")
	public String addEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("enterpriseCreditCode") String enterpriseCreditCode,
			@FormParam("enterpriseLegalPerson") String enterpriseLegalPerson,
			@FormParam("enterpriseLegalPersonIdcard") String enterpriseLegalPersonIdcard,
			@FormParam("enterpriseLinkPeople") String enterpriseLinkPeople,
			@FormParam("enterpriseLinkMobile") String enterpriseLinkMobile,
			@FormParam("enterpriseAddress") String enterpriseAddress,
			@FormParam("enterpriseType") String enterpriseType,
			@FormParam("file") String file,
			@FormParam("plantScope") String plantScope,
			@FormParam("x") String x,
			@FormParam("y") String y,
			@FormParam("plantName") String plantName,
			@FormParam("baseAddress") String baseAddress,
			@FormParam("dscd") String dscd,
			@FormParam("changes") String changes,
			@FormParam("brand") String brand,
			@FormParam("enterpriseNature") String enterpriseNature,
			@FormParam("registeredFunds") String registeredFunds,
			@FormParam("type") String type,
			@FormParam("businessScope") String businessScope,
			@FormParam("permitForoperationNum") String permitForoperationNum,
			@FormParam("operationMode") String operationMode,
			@Context HttpServletRequest request) {
		AddEnterpriseParam myParam = new AddEnterpriseParam(userId, apiKey, request);
		myParam.setEnterpriseAddress(enterpriseAddress);
		myParam.setEnterpriseCreditCode(enterpriseCreditCode);
		myParam.setEnterpriseLegalPerson(enterpriseLegalPerson);
		myParam.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
		myParam.setEnterpriseLinkMobile(enterpriseLinkMobile);
		myParam.setEnterpriseLinkPeople(enterpriseLinkPeople);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setName(name);
		myParam.setFile(file);
		myParam.setId(id);
		myParam.setX(x);
		myParam.setY(y);
		myParam.setPlantScope(plantScope);
		myParam.setBaseAddress(baseAddress);
		myParam.setPlantName(plantName);
		myParam.setDscd(dscd);
		myParam.setRegisteredFunds(registeredFunds);
		myParam.setChanges(changes);
		myParam.setEnterpriseNature(enterpriseNature);
		myParam.setBrand(brand);
		myParam.setType(type);
		myParam.setBusinessScope(businessScope);
		myParam.setPermitForoperationNum(permitForoperationNum);
		myParam.setOperationMode(operationMode);
		return new AddEnterpriseLogic().process(myParam);
	}
	@POST
	@Path("updateEnterprise.do")
	public String updateEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,
			@FormParam("name") String name,
			@FormParam("enterpriseCreditCode") String enterpriseCreditCode,
			@FormParam("enterpriseLegalPerson") String enterpriseLegalPerson,
			@FormParam("enterpriseLegalPersonIdcard") String enterpriseLegalPersonIdcard,
			@FormParam("enterpriseLinkPeople") String enterpriseLinkPeople,
			@FormParam("enterpriseLinkMobile") String enterpriseLinkMobile,
			@FormParam("enterpriseAddress") String enterpriseAddress,
			@FormParam("enterpriseType") String enterpriseType,
			@FormParam("file") String file,
			@FormParam("dscd") String dscd,
			@FormParam("changes") String changes,
			@FormParam("enterpriseNature") String enterpriseNature,
			@FormParam("registeredFunds") String registeredFunds,
			@FormParam("brand") String brand,
			@Context HttpServletRequest request) {
		UpdateEnterpriseParam myParam = new UpdateEnterpriseParam(userId, apiKey, request);
		myParam.setEnterpriseAddress(enterpriseAddress);
		myParam.setEnterpriseCreditCode(enterpriseCreditCode);
		myParam.setEnterpriseLegalPerson(enterpriseLegalPerson);
		myParam.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
		myParam.setEnterpriseLinkMobile(enterpriseLinkMobile);
		myParam.setEnterpriseLinkPeople(enterpriseLinkPeople);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setName(name);
		myParam.setFile(file);
		myParam.setId(id);
		myParam.setDscd(dscd);
		myParam.setRegisteredFunds(registeredFunds);
		myParam.setChanges(changes);
		myParam.setEnterpriseNature(enterpriseNature);
		myParam.setBrand(brand);
		return new UpdateEnterpriseLogic().process(myParam);
	}
	@POST
	@Path("getEnterpriseInfo.do")
	public String getEnterpriseInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetEnterpriseInfoParam myParam = new GetEnterpriseInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetEnterpriseInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getEnterpriseList.do")
	public String getEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseCreditCode")  String enterpriseCreditCode,
			@FormParam("enterpriseLegalPerson")  String enterpriseLegalPerson,
			@FormParam("enterpriseLegalPersonIdcard")  String enterpriseLegalPersonIdcard,
			@FormParam("enterpriseLinkPeople")  String enterpriseLinkPeople,
			@FormParam("enterpriseLinkMobile")  String enterpriseLinkMobile,
			@FormParam("enterpriseAddress")  String enterpriseAddress,
			@FormParam("enterpriseType")  String enterpriseType,
			@FormParam("status")  String status,
			@FormParam("dscd")  String dscd,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll")  String selectAll,
			@FormParam("state")  String state,
			@Context HttpServletRequest request) {
		GetEnterpriseListParam myParam = new GetEnterpriseListParam(userId, apiKey, request);
		myParam.setEnterpriseAddress(enterpriseAddress);
		myParam.setEnterpriseCreditCode(enterpriseCreditCode);
		myParam.setEnterpriseLegalPerson(enterpriseLegalPerson);
		myParam.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
		myParam.setEnterpriseLinkMobile(enterpriseLinkMobile);
		myParam.setEnterpriseLinkPeople(enterpriseLinkPeople);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setStatus(status);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setSelectAll(selectAll);
		myParam.setState(state);
		return new GetEnterpriseListLogic().process(myParam);
	}
	
	@POST
	@Path("changeEnterpriseStatus.do")
	public String changeEnterpriseStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		ChangeEnterpriseStatusParam myParam = new ChangeEnterpriseStatusParam(userId, apiKey, request);
		myParam.setIds(id);
		myParam.setStatus(status);
		return new ChangeEnterpriseStatusLogic().process(myParam);
	}
	
	@POST
	@Path("getStatusByType.do")
	public String getStatusByType(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetStatusByTypeParam myParam = new GetStatusByTypeParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setType(type);
		return new GetStatusByTypeLogic().process(myParam);
	}
	@POST
	@Path("statisticalEnterpriseCompleteOrder.do")
	public String getEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("dscd")  String dscd,
			@Context HttpServletRequest request) {
		StatisticalEnterpriseCompleteOrderParam myParam = new StatisticalEnterpriseCompleteOrderParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setName(name);
		myParam.setDscd(dscd);
		return new StatisticalEnterpriseCompleteOrderLogic().process(myParam);
	}
	@POST
	@Path("statisticalPlantEnterpriseCompleteOrder.do")
	public String statisticalPlantEnterpriseCompleteOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("dscd")  String dscd,
			@Context HttpServletRequest request) {
		StatisticalPlantEnterpriseCompleteOrderParam myParam = new StatisticalPlantEnterpriseCompleteOrderParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setName(name);
		myParam.setDscd(dscd);
		return new StatisticalPlantEnterpriseCompleteOrderLogic().process(myParam);
	}

	@POST
	@Path("getEnterpriseGradeList.do")
	public String getEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseCreditCode")  String enterpriseCreditCode,
			@FormParam("enterpriseType")  String enterpriseType,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetEnterpriseSouceParam myParam = new GetEnterpriseSouceParam(userId, apiKey, request);
		myParam.setEnterpriseCreditCode(enterpriseCreditCode);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetEnterpriseSouceLogic().process(myParam);
	}
	@POST
	@Path("getEnterpriseGradeInfo.do")
	public String getEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseCreditCode")  String enterpriseCreditCode,
			@Context HttpServletRequest request) {
		GetEnterpriseSouceInfoParam myParam = new GetEnterpriseSouceInfoParam(userId, apiKey, request);
		myParam.setEnterpriseCreditCode(enterpriseCreditCode);
		return new GetEnterpriseSouceInfoLogic().process(myParam);
	}
	@POST
	@Path("getPlantEnterpriseList.do")
	public String getPlantEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("time")  String time,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetPlantEnterpriseListParam myParam = new GetPlantEnterpriseListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setTime(time);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetPlantEnterpriseListLogic().process(myParam);
	}
	
	@POST
	@Path("addOrUpdateEnterprise.do")
	public String addOrUpdateEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@FormParam("name") String name,
			@FormParam("enterpriseCreditCode") String enterpriseCreditCode,
			@FormParam("enterpriseLegalPerson") String enterpriseLegalPerson,
			@FormParam("enterpriseLegalPersonIdcard") String enterpriseLegalPersonIdcard,
			@FormParam("enterpriseLinkPeople") String enterpriseLinkPeople,
			@FormParam("enterpriseLinkMobile") String enterpriseLinkMobile,
			@FormParam("enterpriseAddress") String enterpriseAddress,
			@FormParam("enterpriseType") String enterpriseType,
			@FormParam("file") String file,
			@FormParam("plantScope") String plantScope,
			@FormParam("x") String x,
			@FormParam("y") String y,
			@FormParam("plantName") String plantName,
			@FormParam("baseAddress") String baseAddress,
			@FormParam("dscd") String dscd,
			@FormParam("changes") String changes,
			@FormParam("enterpriseNature") String enterpriseNature,
			@FormParam("registeredFunds") String registeredFunds,
			@FormParam("brand") String brand,
			@FormParam("type") String type,
			@FormParam("businessScope") String businessScope,
			@FormParam("permitForoperationNum") String permitForoperationNum,
			@FormParam("operationMode") String operationMode,
			@Context HttpServletRequest request) {
		AddOrUpdateEnterpriseParam myParam = new AddOrUpdateEnterpriseParam(userId, apiKey, request);
		myParam.setEnterpriseAddress(enterpriseAddress);
		myParam.setEnterpriseCreditCode(enterpriseCreditCode);
		myParam.setEnterpriseLegalPerson(enterpriseLegalPerson);
		myParam.setEnterpriseLegalPersonIdcard(enterpriseLegalPersonIdcard);
		myParam.setEnterpriseLinkMobile(enterpriseLinkMobile);
		myParam.setEnterpriseLinkPeople(enterpriseLinkPeople);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setName(name);
		myParam.setFile(file);
		myParam.setId(id);
		myParam.setX(x);
		myParam.setY(y);
		myParam.setPlantScope(plantScope);
		myParam.setBaseAddress(baseAddress);
		myParam.setPlantName(plantName);
		myParam.setDscd(dscd);
		myParam.setRegisteredFunds(registeredFunds);
		myParam.setChanges(changes);
		myParam.setEnterpriseNature(enterpriseNature);
		myParam.setBrand(brand);
		myParam.setType(type);
		myParam.setBusinessScope(businessScope);
		myParam.setPermitForoperationNum(permitForoperationNum);
		myParam.setOperationMode(operationMode);
		return new AddOrUpdateEnterpriseLogic().process(myParam);
	}
	
	@POST
	@Path("delEnterprise.do")
	public String delEnterprise(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseIds")  String enterpriseIds,
			@Context HttpServletRequest request) {
		DelEnterpriseParam myParam = new DelEnterpriseParam(userId, apiKey, request);
		myParam.setId(enterpriseIds);
		return new DelEnterpriseParamLogic().process(myParam);
	}
	
	@POST
	@Path("getEnterPriseZoneList.do")
	public String getEnterPriseZoneList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("type")  String type,
			@FormParam("name")  String name,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetEnterPriseZoneListParam myParam = new GetEnterPriseZoneListParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setType(type);
		myParam.setName(name);
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		return new GetEnterPriseZoneListLogic().process(myParam);
	}
	
	@POST
	@Path("addOrRemoveEnterpriseZone.do")
	public String addOrRemoveEnterpriseZone(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("zoneIds")  String zoneIds,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		AddOrRemoveEnterpriseZoneParam myParam = new AddOrRemoveEnterpriseZoneParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setZoneIds(zoneIds);
		myParam.setType(type);
		return new AddOrRemoveEnterpriseZoneLogic().process(myParam);
	}
	
	@POST
	@Path("delEnterpriseFile.do")
	public String delEnterpriseFile(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("resEnterpriseFileId")  String resEnterpriseFileId,
			@Context HttpServletRequest request) {
		DelEnterpriseFileParam myParam = new DelEnterpriseFileParam(userId, apiKey, request);
		myParam.setId(resEnterpriseFileId);
		return new DelEnterpriseFileLogic().process(myParam);
	}
	
	@POST
	@Path("addEnterpriseFile.do")
	public String addEnterpriseFile(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("file")  String file,
			@Context HttpServletRequest request) {
		AddEnterpriseFileParam myParam = new AddEnterpriseFileParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setFile(file);
		return new AddEnterpriseFileLogic().process(myParam);
	}
	
	@POST
	@Path("getEnterpriseFile.do")
	public String getEnterpriseFile(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetEnterpriseFileParam myParam = new GetEnterpriseFileParam(userId, apiKey, request);
		myParam.setId(enterpriseId);
		myParam.setType(type);
		return new GetEnterpriseFileLogic().process(myParam);
	}
	@POST
	@Path("getEnterpriseFileCount.do")
	public String getEnterpriseFile(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetEnterpriseFileParam myParam = new GetEnterpriseFileParam(userId, apiKey, request);
		myParam.setId(enterpriseId);
		return new GetEnterpriseFileCountLogic().process(myParam);
	}

	@POST
	@Path("getEnterpriseGradeTop.do")
	public String getEnterpriseGradeTop(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		GetEnterpriseSouceParam myParam = new GetEnterpriseSouceParam(userId, apiKey, request);
		return new GetEnterpriseSouceTopLogic().process(myParam);
	}
	
	@POST
	@Path("addOrRemoveEnterpriseZero.do")
	public String addOrRemoveEnterpriseZero(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseIds") String ids,
			@FormParam("type") String type,
			@Context HttpServletRequest request) {
		AddOrRemoveEnterpriseZeroParam myParam = new AddOrRemoveEnterpriseZeroParam(userId, apiKey, request);
		myParam.setId(ids);
		myParam.setType(type);
		return new AddOrRemoveEnterpriseZeroLogic().process(myParam);
	}
	@POST
	@Path("updateEnterpriseListOrder.do")
	public String updateEnterpriseListOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("listOrderJa")  String listOrderJa,
			@Context HttpServletRequest request) {
		UpdateEnterpriseListOrderParam myParam = new UpdateEnterpriseListOrderParam(userId, apiKey, request);
		myParam.setListOrderJa(listOrderJa);
		return new UpdateEnterpriseListOrderLogic().process(myParam);
	}
	/*
	 * 企业开启关闭
	 */
	@POST
	@Path("changeEnterpriseState.do")
	public String changeEnterpriseState(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String id,
			@FormParam("state")  String state,
			@Context HttpServletRequest request) {
		ChangeEnterpriseStatusParam myParam = new ChangeEnterpriseStatusParam(userId, apiKey, request);
		myParam.setIds(id);
		myParam.setStatus(state);
		return new ChangeEnterpriseStateLogic().process(myParam);
	}
	
	@POST
	@Path("addEnterpriseBrand.do")
	public String addEnterpriseBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("brandId")  Integer brandId,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@Context HttpServletRequest request) {
		AddEnterpriseBrandParam myParam = new AddEnterpriseBrandParam(userId, apiKey, request);
		myParam.setBrandId(brandId);
		myParam.setEnterpriseId(enterpriseId);
		return new AddEnterpriseBrandLogic().process(myParam);
	}
	
	@POST
	@Path("getEnterpriseBrandList.do")
	public String getEnterpriseBrandList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetEnterpriseBrandListParam myParam = new GetEnterpriseBrandListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetEnterpriseBrandListLogic().process(myParam);
	}
	
	@POST
	@Path("getEnterpriseGeomList.do")
	public String getGreenhouseGeomList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetGreenhouseGeomListParam myParam = new GetGreenhouseGeomListParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		return new GetEnterpriseGeomListLogic().process(myParam);
	}
	@POST
	@Path("getToEnterpriseList.do")
	public String getToEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("enterpriseType")  String enterpriseType,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("dscd")  String dscd,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetToEnterpriseListParam myParam = new GetToEnterpriseListParam(userId, apiKey, request);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetToEnterpriseListLogic().process(myParam);
	}
	/**
	 * 获取零售价企业能否添加零售价商品
	 * @param userId
	 * @param apiKey
	 * @param enterpriseIds
	 * @param request
	 * @return
	 */
	@POST
	@Path("getEnterpriseZeroFlag.do")
	public String getEnterpriseZeroFlag(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@Context HttpServletRequest request) {
		GetEnterpriseZeroInfoParam myParam = new GetEnterpriseZeroInfoParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetEnterpriseZeroInfoLogic().process(myParam);
	}
	/**
	 * 添加userProductionnInfo用户信息
	 * @param userId
	 * @param apiKey
	 * @param enterpriseIds
	 * @param request
	 * @return
	 */
	@POST
	@Path("addEnterpriseUserProduction.do")
	public String addEnterpriseUserProduction(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("name") String name,
			@FormParam("dscd") String dscd,
			@FormParam("village") String village,
			@FormParam("area") String area,
			@FormParam("confirmArea") String confirmArea,
			@FormParam("inflowArea") String inflowArea,
			@FormParam("outflowArea") String outflowArea,
			@FormParam("userJa") String userJa,
			@FormParam("type") String type,
			@FormParam("nyLimitAmount") String nyLimitAmount,
			@FormParam("nmLimitAmount") String nmLimitAmount,
			@Context HttpServletRequest request) {
		AddEnterpriseUserProductionParam myParam = new AddEnterpriseUserProductionParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setArea(area);
		myParam.setConfirmArea(confirmArea);
		myParam.setInflowArea(inflowArea);
		myParam.setOutflowArea(outflowArea);
		myParam.setDscd(dscd);
		myParam.setVillage(village);
		myParam.setUserJa(userJa);
		myParam.setName(name);
		myParam.setEnterpriseType(type);
		myParam.setNmLimitAmount(nmLimitAmount);
		myParam.setNyLimitAmount(nyLimitAmount);
		return new AddEnterpriseUserProductionLogic().process(myParam);
	}
	/**
	 * 获取企业userProductionnInfo用户信息
	 * @param userId
	 * @param apiKey
	 * @param enterpriseIds
	 * @param request
	 * @return
	 */
	@POST
	@Path("getEnterpriseUserProductionList.do")
	public String getEnterpriseUserProductionList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetEnterpriseUserProductionListParam myParam = new GetEnterpriseUserProductionListParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetEnterpriseUserProductionListLogic().process(myParam);
	}
	/**
	 * 获取耕地企业列表 类型-1 存在user_production_info 用户 
	 * @param userId
	 * @param apiKey
	 * @param name
	 * @param dscd
	 * @param nowPage
	 * @param pageCount
	 * @param request
	 * @return
	 */
	@POST
	@Path("getUserProductionEnterpriseList.do")
	public String getUserProductionEnterpriseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name,
			@FormParam("dscd")  String dscd,
			@FormParam("userName")  String userName,
			@FormParam("userIdCard")  String userIdCard,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetEnterpriseListParam myParam = new GetEnterpriseListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setUserName(userName);
		myParam.setUserIdCard(userIdCard);
		myParam.setEnterpriseType(type);
		return new GetUserProductionEnterpriseListLogic().process(myParam);
	}
	/**
	 * 获取某企业员工列表
	 * @param userId
	 * @param apiKey
	 * @param enterpriseId
	 * @param name
	 * @param dscd
	 * @param nowPage
	 * @param pageCount
	 * @param request
	 * @return
	 */
	@POST
	@Path("getUserProductionList.do")
	public String getUserProductionList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("name")  String name,
			@FormParam("dscd")  String dscd,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetUserProductionListParam myParam = new GetUserProductionListParam(userId, apiKey, request);
		myParam.setName(name);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setDscd(dscd);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetUserProductionListLogic().process(myParam);
	}
	/**
	 * 添加员工
	 * @param userId
	 * @param apiKey
	 * @param id
	 * @param enterpriseId
	 * @param userIdCard
	 * @param userName
	 * @param request
	 * @return
	 */
	@POST
	@Path("addUserProduction.do")
	public String addUserProduction(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("userIdCard")  String userIdCard,
			@FormParam("userName")  String userName,
			@Context HttpServletRequest request) {
		AddUserProductionParam myParam = new AddUserProductionParam(userId, apiKey, request);
		myParam.setUserName(userName);
		myParam.setUserIdCard(userIdCard);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setId(id);
		return new AddUserProductionLogic().process(myParam);
	}
	/**
	 * 删除员工
	 * @param userId
	 * @param apiKey
	 * @param id
	 * @param request
	 * @return
	 */
	@POST
	@Path("delUserProduction.do")
	public String delUserProduction(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  Integer id,
			@Context HttpServletRequest request) {
		DelUserProductionParam myParam = new DelUserProductionParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelUserProductionLogic().process(myParam);
	}
	@POST
	@Path("getUserProductionInfo.do")
	public String getUserProductionInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  Integer id,
			@Context HttpServletRequest request) {
		DelUserProductionParam myParam = new DelUserProductionParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetUserProductionInfoLogic().process(myParam);
	}
	
}
