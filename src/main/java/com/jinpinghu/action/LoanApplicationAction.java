package com.jinpinghu.action;

import com.jinpinghu.logic.enterpriseLoanApplication.*;
import com.jinpinghu.logic.enterpriseLoanApplication.param.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("loanApplication")
@Produces("application/json;charset=UTF-8")
public class LoanApplicationAction extends BaseZAction {

    @POST
    @Path("addEnterpriseLoanApplication.do")
    public String addEnterpriseLoanApplication(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("id") String id,
        	@FormParam("enterpriseId") String enterpriseId,
        	@FormParam("loanAmount") String loanAmount,
        	@FormParam("startTime") String startTime,
        	@FormParam("endTime") String endTime,
        	@FormParam("useInstruction") String useInstruction,
        	@FormParam("file") String file,
        	@FormParam("bankBorrow") String bankBorrow,
        	@FormParam("businessIncome") String businessIncome,
        	@FormParam("currentAssets") String currentAssets,
        	@FormParam("customerName") String customerName,
        	@FormParam("financialStatement") String financialStatement,
        	@FormParam("fixedAssets") String fixedAssets,
        	@FormParam("guarantyStyle") String guarantyStyle,
        	@FormParam("guarantyUnit") String guarantyUnit,
        	@FormParam("handleBank") String handleBank,
        	@FormParam("idNumber") String idNumber,
        	@FormParam("idType") String idType,
        	@FormParam("loanTime") String loanTime,
        	@FormParam("ownershipInterest") String ownershipInterest,
        	@FormParam("receivables") String receivables,
        	@FormParam("totalAssets") String totalAssets,
        	@FormParam("totalIndebtedness") String totalIndebtedness,
        	@FormParam("totalProfit") String totalProfit,
            @Context HttpServletRequest request) {
        AddEnterpriseLoanApplicationParam myParam = new AddEnterpriseLoanApplicationParam(userId, apiKey, request, id, enterpriseId, 
        		loanAmount, startTime, endTime, useInstruction, file, bankBorrow, businessIncome, currentAssets, customerName, financialStatement, 
        		fixedAssets, guarantyStyle, guarantyUnit, handleBank, idNumber, idType, loanTime, ownershipInterest, receivables, totalAssets, 
        		totalIndebtedness, totalProfit);
        return new AddEnterpriseLoanApplicationLogic().process(myParam);
    }
    @POST
    @Path("getEnterpriseLoanApplicationInfo.do")
    public String getEnterpriseLoanApplicationInfo(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("id")  String id,
            @Context HttpServletRequest request) {
        GetEnterpriseLoanApplicationInfoParam myParam = new GetEnterpriseLoanApplicationInfoParam(userId, apiKey, request);
        myParam.setId(id);
        return new GetEnterpriseLoanApplicationInfoLogic().process(myParam);
    }

    @POST
    @Path("getEnterpriseLoanApplicationList.do")
    public String getEnterpriseLoanApplicationList(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("enterpriseId")  String enterpriseId,
            @FormParam("enterpriseName")  String enterpriseName,
            @FormParam("status")  String status,
            @FormParam("startTime")  String startTime,
            @FormParam("endTime")  String endTime,
            @FormParam("nowPage")  String nowPage,
            @FormParam("pageCount")  String pageCount,
            @Context HttpServletRequest request) {
        GetEnterpriseLoanApplicationListParam myParam = new GetEnterpriseLoanApplicationListParam(userId, apiKey, request);
        myParam.setEnterpriseName(enterpriseName);
        myParam.setStatus(status);
        myParam.setStartTime(startTime);
        myParam.setEndTime(endTime);
        myParam.setEnterpriseId(enterpriseId);
        myParam.setNowPage(nowPage);
        myParam.setPageCount(pageCount);
        return new GetEnterpriseLoanApplicationListLogic().process(myParam);
    }
    @POST
    @Path("delEnterpriseLoanApplication.do")
    public String delEnterpriseLoanApplication(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("ids")  String ids,
            @Context HttpServletRequest request) {
        DelEnterpriseLoanApplicationParam myParam = new DelEnterpriseLoanApplicationParam(userId, apiKey, request);
        myParam.setId(ids);
        return new DelEnterpriseLoanApplicationParamLogic().process(myParam);
    }

    @POST
    @Path("changeEnterpriseLoanApplicationStatus.do")
    public String changeEnterpriseLoanApplicationStatus(
            @FormParam("userId") String userId,
            @FormParam("apiKey") String apiKey,
            @FormParam("ids")  String ids,
            @FormParam("status")  String status,
            @FormParam("remark")  String remark,
            @Context HttpServletRequest request) {
        ChangeEnterpriseLoanApplicationStatusParam myParam = new ChangeEnterpriseLoanApplicationStatusParam(userId, apiKey, request);
        myParam.setIds(ids);
        myParam.setStatus(status);
        myParam.setRemark(remark);
        return new ChangeEnterpriseLoanApplicationStatusLogic().process(myParam);
    }

}
