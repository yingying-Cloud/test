package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.util.FaceRecognitionLogic;
import com.jinpinghu.logic.util.UploadPicLogic;
import com.jinpinghu.logic.util.param.FaceRecognitionParam;
import com.jinpinghu.logic.util.param.GetVersionParam;
import com.jinpinghu.logic.util.param.UploadPicParam;
import com.jinpinghu.logic.util.GetVersionLogic;


/** 通用工具 */
@Path("util")
@Produces("application/json;charset=UTF-8")
public class UtilAction extends BaseZAction {
	
	
	
	/**
	 * 上传图片
	 * 微信转oss
	 */
	@POST
	@Path("uploadPic.do")
	public String uploadPic(
			@FormParam("token") String token,
			@FormParam("mediaId") String mediaId,
			@FormParam("fileName") String fileName,
			@Context HttpServletRequest request) {
		UploadPicParam myParam = new UploadPicParam(null, null, request);
		myParam.setToken(token);
		myParam.setMediaId(mediaId);
		myParam.setFileName(fileName);
		return new UploadPicLogic().process(myParam);
	}
	
	/**
	 * 人脸识别
	 */
	@POST
	@Path("faceRecognition.do")
	public String faceRecognition(
			@FormParam("imageA") String imageA,
			@FormParam("imageB") String imageB,
			@Context HttpServletRequest request) {
		FaceRecognitionParam myParam = new FaceRecognitionParam(null, null, request);
		myParam.setImageA(imageA);
		myParam.setImageB(imageB);
		return new FaceRecognitionLogic().process(myParam);
	}
	
	/**
	 * 获取系统各版本号
	 */
	@POST
	@Path("getVersion.do")
	public String getVersion(
			@FormParam("appVersion") String appVersion,
			@Context HttpServletRequest request) {
		GetVersionParam myParam = new GetVersionParam(null, null, request);
		myParam.setAppVersion(appVersion);
		return new GetVersionLogic().process(myParam);
	}
	
	
	
}
