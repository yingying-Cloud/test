package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.agriculturalGreenhouses.AddAgriculturalGreenhousesLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.DelAgriculturalGreenhousesLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.GetAgriculturalGreenhousesInfoLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.GetAgriculturalGreenhousesListLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.GetGreenhouseGeomListLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.UpdateAgriculturalGreenhousesLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.AddAgriculturalGreenhousesParam;
import com.jinpinghu.logic.agriculturalGreenhouses.param.DelAgriculturalGreenhousesParam;
import com.jinpinghu.logic.agriculturalGreenhouses.param.GetAgriculturalGreenhousesInfoParam;
import com.jinpinghu.logic.agriculturalGreenhouses.param.GetAgriculturalGreenhousesListParam;
import com.jinpinghu.logic.agriculturalGreenhouses.param.GetGreenhouseGeomListParam;
import com.jinpinghu.logic.agriculturalGreenhouses.param.UpdateAgriculturalGreenhousesParam;

@Path("agriculturalGreenhouses")
@Produces("application/json;charset=UTF-8")
public class AgriculturalGreenhousesAction extends BaseZAction {

	@POST
	@Path("addAgriculturalGreenhouses.do")
	public String AddAgriculturalGreenhouses(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("greenhousesName")  String greenhousesName,
			@FormParam("mu")  String mu,
			@FormParam("file")  String file,
			@FormParam("classify")  String classify,
			@Context HttpServletRequest request) {
		AddAgriculturalGreenhousesParam myParam = new AddAgriculturalGreenhousesParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setGreenhousesName(greenhousesName);
		myParam.setMu(mu);
		myParam.setFile(file);
		myParam.setClassify(classify);
		return new AddAgriculturalGreenhousesLogic().process(myParam);
	}
	
	@POST
	@Path("updateAgriculturalGreenhouses.do")
	public String updateAgriculturalGreenhouses(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  Integer id,
			@FormParam("enterpriseId")  Integer enterpriseId,
			@FormParam("greenhousesName")  String greenhousesName,
//			@FormParam("area")  String area,
//			@FormParam("center")  String center,
			@FormParam("mu")  String mu,
			@FormParam("file")  String file,
			@FormParam("classify")  String classify,
			@FormParam("greenhousesModel")  String greenhousesModel,
			@Context HttpServletRequest request) {
		UpdateAgriculturalGreenhousesParam myParam = new UpdateAgriculturalGreenhousesParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setGreenhousesName(greenhousesName);
//		myParam.setArea(area);
//		myParam.setCenter(center);
		myParam.setMu(mu);
		myParam.setFile(file);
		myParam.setClassify(classify);
		return new UpdateAgriculturalGreenhousesLogic().process(myParam);
	}
	
	@POST
	@Path("delAgriculturalGreenhouses.do")
	public String delAgriculturalGreenhouses(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelAgriculturalGreenhousesParam myParam = new DelAgriculturalGreenhousesParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelAgriculturalGreenhousesLogic().process(myParam);
	}
	
	@POST
	@Path("getAgriculturalGreenhousesInfo.do")
	public String getAgriculturalGreenhousesInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetAgriculturalGreenhousesInfoParam myParam = new GetAgriculturalGreenhousesInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetAgriculturalGreenhousesInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getAgriculturalGreenhousesList.do")
	public String getAgriculturalGreenhousesList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("greenhousesName")  String greenhousesName,
			@FormParam("type")  String type,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetAgriculturalGreenhousesListParam myParam = new GetAgriculturalGreenhousesListParam(userId, apiKey, request);
		myParam.setGreenhousesName(greenhousesName);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setType(type);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetAgriculturalGreenhousesListLogic().process(myParam);
	}
	
	@POST
	@Path("getGreenhouseGeomList.do")
	public String getGreenhouseGeomList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetGreenhouseGeomListParam myParam = new GetGreenhouseGeomListParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		return new GetGreenhouseGeomListLogic().process(myParam);
	}
}
