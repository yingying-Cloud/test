package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.enterpriseCertificate.AddEnterpriseCertificateLogic;
import com.jinpinghu.logic.enterpriseCertificate.DelEnterpriseCertificateParamLogic;
import com.jinpinghu.logic.enterpriseCertificate.GetEnterpriseCertificateInfoLogic;
import com.jinpinghu.logic.enterpriseCertificate.GetEnterpriseCertificateListLogic;
import com.jinpinghu.logic.enterpriseCertificate.param.AddEnterpriseCertificateParam;
import com.jinpinghu.logic.enterpriseCertificate.param.DelEnterpriseCertificateParam;
import com.jinpinghu.logic.enterpriseCertificate.param.GetEnterpriseCertificateInfoParam;
import com.jinpinghu.logic.enterpriseCertificate.param.GetEnterpriseCertificateListParam;

@Path("enterpriseCertificate")
@Produces("application/json;charset=UTF-8")
public class EnterpriseCertificateAction extends BaseZAction {

	
	 @POST
	    @Path("addEnterpriseCertificate.do")
	    public String addEnterpriseCertificate(
	            @FormParam("userId") String userId,
	            @FormParam("apiKey") String apiKey,
	            @FormParam("id") String id,
	            @FormParam("enterpriseId") String enterpriseId,
	            @FormParam("softwareName") String softwareName,
	            @FormParam("owner") String owner,
	            @FormParam("completeTime") String completeTime,
	            @FormParam("publishTime") String publishTime,
	            @FormParam("way") String way,
	            @FormParam("authority") String authority,
	            @FormParam("registrationNumber") String registrationNumber,
	            @FormParam("inputTime") String inputTime,
	            @FormParam("file") String file,
	            @FormParam("certificateType") String certificateType,
	            @Context HttpServletRequest request) {
	        AddEnterpriseCertificateParam myParam = new AddEnterpriseCertificateParam(userId, apiKey, request);
	        myParam.setId(id);
	        myParam.setEnterpriseId(enterpriseId);
			myParam.setAuthority(authority);
			myParam.setCompleteTime(completeTime);
			myParam.setInputTime(inputTime);
			myParam.setOwner(owner);
			myParam.setPublishTime(publishTime);
			myParam.setRegistrationNumber(registrationNumber);
			myParam.setSoftwareName(softwareName);
			myParam.setWay(way);
	        myParam.setFile(file);
	        myParam.setCertificateType(certificateType);
	        return new AddEnterpriseCertificateLogic().process(myParam);
	    }
	    @POST
	    @Path("getEnterpriseCertificateInfo.do")
	    public String getEnterpriseCertificateInfo(
	            @FormParam("userId") String userId,
	            @FormParam("apiKey") String apiKey,
	            @FormParam("id")  String id,
	            @Context HttpServletRequest request) {
	        GetEnterpriseCertificateInfoParam myParam = new GetEnterpriseCertificateInfoParam(userId, apiKey, request);
	        myParam.setId(id);
	        return new GetEnterpriseCertificateInfoLogic().process(myParam);
	    }

	    @POST
	    @Path("getEnterpriseCertificateList.do")
	    public String getEnterpriseCertificateList(
	            @FormParam("userId") String userId,
	            @FormParam("apiKey") String apiKey,
	            @FormParam("enterpriseId")  String enterpriseId,
	            @FormParam("name")  String name,
	            @FormParam("startTime")  String startTime,
	            @FormParam("endTime")  String endTime,
	            @FormParam("nowPage")  String nowPage,
	            @FormParam("pageCount")  String pageCount,
	            @Context HttpServletRequest request) {
	        GetEnterpriseCertificateListParam myParam = new GetEnterpriseCertificateListParam(userId, apiKey, request);
	        myParam.setName(name);
	        myParam.setStartTime(startTime);
	        myParam.setEndTime(endTime);
	        myParam.setEnterpriseId(enterpriseId);
	        myParam.setNowPage(nowPage);
	        myParam.setPageCount(pageCount);
	        return new GetEnterpriseCertificateListLogic().process(myParam);
	    }
	    @POST
	    @Path("delEnterpriseCertificate.do")
	    public String delEnterpriseCertificate(
	            @FormParam("userId") String userId,
	            @FormParam("apiKey") String apiKey,
	            @FormParam("ids")  String ids,
	            @Context HttpServletRequest request) {
	        DelEnterpriseCertificateParam myParam = new DelEnterpriseCertificateParam(userId, apiKey, request);
	        myParam.setId(ids);
	        return new DelEnterpriseCertificateParamLogic().process(myParam);
	    }
	
}
