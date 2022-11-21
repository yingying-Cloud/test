package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.applicantCompany.AddOrUpdateApplicantCompanyLogic;
import com.jinpinghu.logic.applicantCompany.DelApplicantCompanyLogic;
import com.jinpinghu.logic.applicantCompany.ExportByCompanyLogic;
import com.jinpinghu.logic.applicantCompany.GetApplicantCompanyInfo2Logic;
import com.jinpinghu.logic.applicantCompany.GetApplicantCompanyInfoLogic;
import com.jinpinghu.logic.applicantCompany.GetApplicantCompanyListLogic;
import com.jinpinghu.logic.applicantCompany.GetApplicantCompanyTrademarkInfoLogic;
import com.jinpinghu.logic.applicantCompany.UpdateStatusLogic;
import com.jinpinghu.logic.applicantCompany.param.AddOrUpdateApplicantCompanyParam;
import com.jinpinghu.logic.applicantCompany.param.DelApplicantCompanyParam;
import com.jinpinghu.logic.applicantCompany.param.ExportByCompanyParam;
import com.jinpinghu.logic.applicantCompany.param.GetApplicantCompanyInfo2Param;
import com.jinpinghu.logic.applicantCompany.param.GetApplicantCompanyInfoParam;
import com.jinpinghu.logic.applicantCompany.param.GetApplicantCompanyListParam;
import com.jinpinghu.logic.applicantCompany.param.GetApplicantCompanyTrademarkInfoParam;
import com.jinpinghu.logic.applicantCompany.param.UpdateStatusParam;

@Path("applicantCompany")
@Produces("application/json;charset=UTF-8")
public class ApplicantCompanyAction extends BaseZAction{

	@POST
	@Path("addOrUpdateApplicantCompany.do")
	public String addOrUpdateApplicantCompany(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("name") String name,
			@FormParam("address") String address,
			@FormParam("zipCode") String zipCode,
			@FormParam("legalPerson") String legalPerson,
			@FormParam("legalContact") String legalContact,
			@FormParam("legalMobile") String legalMobile,
			@FormParam("agent") String agent,
			@FormParam("agentContact") String agentContact,
			@FormParam("agentMobile") String agentMobile,
			@FormParam("fax") String fax,
			@FormParam("email") String email,
			@FormParam("registeredTrademark") String registeredTrademark,
			@FormParam("trademarkServiceStartTime") String trademarkServiceStartTime,
			@FormParam("trademarkServiceEndTime") String trademarkServiceEndTime,
			@FormParam("type") String type,
			@FormParam("productionSituation") String productionSituation,
			@FormParam("productQualityAttestation") String productQualityAttestation,
			@FormParam("machiningInfo") String machiningInfo,
			@FormParam("productSales") String productSales,
			@FormParam("packageSourceManager") String packageSourceManager,
			@FormParam("otherNt") String otherNt,
			@FormParam("applicantCompanyProducts") String applicantCompanyProducts,
			@FormParam("applicantCompanyCredentials") String applicantCompanyCredentials,
			@FormParam("x") String x,
			@FormParam("y") String y,
			@FormParam("profile") String profile,
			@FormParam("uniformSocialCreditCode") String uniformSocialCreditCode,
			@FormParam("operationPeriodStartTime") String operationPeriodStartTime,
			@FormParam("operationPeriodEndTime") String operationPeriodEndTime,
			@FormParam("spybName") String spybName,
			@FormParam("spybProduct") String spybProduct,
			@FormParam("spybStartTime") String spybStartTime,
			@FormParam("spybEndTime") String spybEndTime,
			@FormParam("trademarks") String trademarks,
			@FormParam("files") String files,
			@Context HttpServletRequest request) {
		AddOrUpdateApplicantCompanyParam myParam = new AddOrUpdateApplicantCompanyParam(userId,apiKey,request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setName(name);
		myParam.setAddress(address);
		myParam.setZipCode(zipCode);
		myParam.setLegalPerson(legalPerson);
		myParam.setLegalContact(legalContact);
		myParam.setLegalMobile(legalMobile);
		myParam.setAgent(agent);
		myParam.setAgentContact(agentContact);
		myParam.setAgentMobile(agentMobile);
		myParam.setFax(fax);
		myParam.setEmail(email);
		myParam.setRegisteredTrademark(registeredTrademark);
		myParam.setTrademarkServiceStartTime(trademarkServiceStartTime);
		myParam.setTrademarkServiceEndTime(trademarkServiceEndTime);
		myParam.setType(type);
		myParam.setProductionSituation(productionSituation);
		myParam.setProductQualityAttestation(productQualityAttestation);
		myParam.setMachiningInfo(machiningInfo);
		myParam.setProductSales(productSales);
		myParam.setPackageSourceManager(packageSourceManager);
		myParam.setOtherNt(otherNt);
		myParam.setApplicantCompanyCredentials(applicantCompanyCredentials);
		myParam.setApplicantCompanyProducts(applicantCompanyProducts);
		myParam.setX(StringUtils.isEmpty(x) ? null : Double.valueOf(x));
		myParam.setY(StringUtils.isEmpty(y) ? null : Double.valueOf(y));
		myParam.setProfile(profile);
		myParam.setUniformSocialCreditCode(uniformSocialCreditCode);
		myParam.setOperationPeriodStartTime(operationPeriodStartTime);
		myParam.setOperationPeriodEndTime(operationPeriodEndTime);
		myParam.setSpybName(spybName);
		myParam.setSpybProduct(spybProduct);
		myParam.setSpybStartTime(spybStartTime);
		myParam.setSpybEndTime(spybEndTime);
		myParam.setTrademarks(trademarks);
		myParam.setFiles(files);
		return new AddOrUpdateApplicantCompanyLogic().process(myParam);
	}
	
	@POST
	@Path("getApplicantCompanyList.do")
	public String getApplicantCompanyList(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("name") String name,
			@FormParam("status") String status,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetApplicantCompanyListParam myParam = new GetApplicantCompanyListParam(userId,apiKey,request);
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setName(name);
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		return new GetApplicantCompanyListLogic().process(myParam);
	}
	
	@POST
	@Path("getApplicantCompanyInfo.do")
	public String getApplicantCompanyInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetApplicantCompanyInfoParam myParam = new GetApplicantCompanyInfoParam(userId,apiKey,request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new GetApplicantCompanyInfoLogic().process(myParam);
	}
	

	@POST
	@Path("delApplicantCompany.do")
	public String delApplicantCompany(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelApplicantCompanyParam myParam = new DelApplicantCompanyParam(userId,apiKey,request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new DelApplicantCompanyLogic().process(myParam);
	}
	
	@POST
	@Path("updateStatus.do")
	public String updateStatus(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@FormParam("status") String status,
			@Context HttpServletRequest request) {
		UpdateStatusParam myParam = new UpdateStatusParam(userId,apiKey,request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		return new UpdateStatusLogic().process(myParam);
	}
	
	@POST
	@Path("exportByAppliactionCompany.do")
	public String exportByAppliactionCompany(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("companyId") String companyId,
			@Context HttpServletRequest request) {
		ExportByCompanyParam myParam = new ExportByCompanyParam(userId, apiKey, request);
		myParam.setCompanyId(companyId);
		return new ExportByCompanyLogic().process(myParam);
	}
	
	@POST
	@Path("getApplicantCompanyInfo2.do")
	public String getApplicantCompanyInfo2(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		GetApplicantCompanyInfo2Param myParam = new GetApplicantCompanyInfo2Param(userId,apiKey,request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		return new GetApplicantCompanyInfo2Logic().process(myParam);
	}
	
	@POST
	@Path("getApplicantCompanyTrademarkInfo.do")
	public String getApplicantCompanyTrademarkInfo(
			@FormParam("apiKey") String apiKey,
			@FormParam("userId") String userId,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetApplicantCompanyTrademarkInfoParam myParam = new GetApplicantCompanyTrademarkInfoParam(userId,apiKey,request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new GetApplicantCompanyTrademarkInfoLogic().process(myParam);
	}
	
}
