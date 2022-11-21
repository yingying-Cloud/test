package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.facedata.AddFaceDataLogic;
import com.jinpinghu.logic.facedata.DetailFaceDataLogic;
import com.jinpinghu.logic.facedata.param.AddFaceDataParam;
import com.jinpinghu.logic.facedata.param.DetailFaceDataParam;

@Path("facedata")
@Produces("application/json;charset=UTF-8")
public class FaceDataAction extends BaseZAction {
	
	@POST
	@Path("addFaceData.do")
	public String addFaceData(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("idcard")  String idcard ,
			@FormParam("facePic")  String facePic,
			@FormParam("name")  String name,
			@FormParam("faceData")  String faceData,
			@Context HttpServletRequest request) {
		AddFaceDataParam myParam = new AddFaceDataParam(userId, apiKey, request);
		myParam.setIdcard(idcard);
		myParam.setFaceData(faceData);
		myParam.setName(name);
		myParam.setFacePic(facePic);
		return new AddFaceDataLogic().process(myParam);
	}
	
	@POST
	@Path("detailFaceData.do")
	public String detailFaceData(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("idcard")  String idcard ,
			@Context HttpServletRequest request) {
		DetailFaceDataParam myParam = new DetailFaceDataParam(userId, apiKey, request);
		myParam.setIdcard(idcard);
		return new DetailFaceDataLogic().process(myParam);
	}
}
