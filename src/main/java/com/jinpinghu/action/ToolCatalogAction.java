package com.jinpinghu.action;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.logic.tool.AddToolFromToolCatalogLogic;
import com.jinpinghu.logic.tool.param.AddToolFromToolCatalogParam;
import com.jinpinghu.logic.toolCatalog.AddOrUpdateToolCatalogLogic;
import com.jinpinghu.logic.toolCatalog.AuditToolCatalogStatusLogic;
import com.jinpinghu.logic.toolCatalog.ChangeToolCatalogReamrkLogic;
import com.jinpinghu.logic.toolCatalog.DelToolCatalogLogic;
import com.jinpinghu.logic.toolCatalog.GetMiniToolCatalogListLogic;
import com.jinpinghu.logic.toolCatalog.GetSellRecordByToolCatalogIdLogic;
import com.jinpinghu.logic.toolCatalog.GetToolCatalogInfoLogic;
import com.jinpinghu.logic.toolCatalog.GetToolCatalogListLogic;
import com.jinpinghu.logic.toolCatalog.GetUserToolCatalogListLogic;
import com.jinpinghu.logic.toolCatalog.StatisticMiniToolCatalogNyFlLogic;
import com.jinpinghu.logic.toolCatalog.StatisticSellRecordByToolCatalogIdLogic;
import com.jinpinghu.logic.toolCatalog.SyncToolUniformPriceLogic;
import com.jinpinghu.logic.toolCatalog.param.AddOrUpdateToolCatalogParam;
import com.jinpinghu.logic.toolCatalog.param.AuditToolCatalogStatusParam;
import com.jinpinghu.logic.toolCatalog.param.ChangeToolCatalogReamrkParam;
import com.jinpinghu.logic.toolCatalog.param.DelToolCatalogParam;
import com.jinpinghu.logic.toolCatalog.param.GetMiniToolCatalogListParam;
import com.jinpinghu.logic.toolCatalog.param.GetSellRecordByToolCatalogIdParam;
import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogInfoParam;
import com.jinpinghu.logic.toolCatalog.param.GetToolCatalogListParam;


@Path("toolCatalog")
@Produces("application/json;charset=UTF-8")
public class ToolCatalogAction extends BaseZAction {

	@POST
	@Path("addOrUpdateToolCatalog.do")
	public String addToolCatalog(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("name") String name,
			@FormParam("supplierName") String supplierName,
			@FormParam("model") String model,
			@FormParam("specification") String specification,
			@FormParam("unit") String unit,
			@FormParam("type") String type,
			@FormParam("price") String price,
			@FormParam("file") String file,
			@FormParam("number") String number,
			@FormParam("describe") String describe,
			@FormParam("productionUnits") String productionUnits,
			@FormParam("registrationCertificateNumber") String registrationCertificateNumber,
			@FormParam("explicitIngredients") String explicitIngredients,
			@FormParam("effectiveIngredients") String effectiveIngredients,
			@FormParam("implementationStandards") String implementationStandards,
			@FormParam("dosageForms") String dosageForms,
			@FormParam("productAttributes") String productAttributes,
			@FormParam("toxicity") String toxicity,
			@FormParam("qualityGrade") String qualityGrade,
			@FormParam("uniformPrice") String uniformPrice,
			@FormParam("code") String code,
			@FormParam("wholesalePrice") String wholesalePrice,
			@FormParam("hfzc") String hfzc,
			@FormParam("approvalEndDate") String approvalEndDate,
			@FormParam("approvalNo") String approvalNo,
			@FormParam("approveNo") String approveNo,
			@FormParam("certificateNo") String certificateNo,
			@FormParam("checkHealthNo") String checkHealthNo,
			@FormParam("healthNo") String healthNo,
			@FormParam("limitDate") String limitDate,
			@FormParam("produced") String produced,
			@FormParam("productionNo") String productionNo,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("n") String n,
			@FormParam("p") String p,
			@FormParam("k") String k,
			@FormParam("qt") String qt,
			@FormParam("qrCode") String qrCode,
			@FormParam("npk")  String NPK,
			@FormParam("np")  String NP,
			@FormParam("nk")  String NK,
			@FormParam("pk")  String PK,
			@FormParam("yxcfUnit")  String yxcfUnit,
			@FormParam("zjzl")  String zjzl,
			@FormParam("yxcfJa")  String yxcfJa,
			@FormParam("nysx")  String nysx,
			@Context HttpServletRequest request) {
		AddOrUpdateToolCatalogParam myParam = new AddOrUpdateToolCatalogParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setDescribe(describe);
		myParam.setModel(model);
		myParam.setName(name);
		myParam.setNumber(number);
		myParam.setPrice(price);
		myParam.setSpecification(specification);
		myParam.setUnit(unit);
		myParam.setType(type);
		myParam.setFile(file);
		myParam.setSupplierName(supplierName);
		myParam.setProductionUnits(productionUnits);
		myParam.setRegistrationCertificateNumber(registrationCertificateNumber);
		myParam.setExplicitIngredients(explicitIngredients);
		myParam.setEffectiveIngredients(effectiveIngredients);			
		myParam.setImplementationStandards(implementationStandards);
		myParam.setDosageForms(dosageForms);
		myParam.setProductAttributes(productAttributes);
		myParam.setToxicity(toxicity);
		myParam.setQualityGrade(qualityGrade);
		myParam.setUniformPrice(uniformPrice);
		myParam.setCode(code);
		myParam.setWholesalePrice(wholesalePrice);
		myParam.setHfzc(hfzc);
		myParam.setApprovalEndDate(approvalEndDate);
		myParam.setApprovalNo(approvalNo);
		myParam.setApproveNo(approveNo);
		myParam.setCertificateNo(certificateNo);
		myParam.setCheckHealthNo(checkHealthNo);
		myParam.setHealthNo(healthNo);
		myParam.setLimitDate(limitDate);
		myParam.setProduced(produced);
		myParam.setProductionNo(productionNo);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setN(n);
		myParam.setP(p);
		myParam.setK(k);
		myParam.setQt(qt);
		myParam.setQrCode(qrCode);
		myParam.setNPK(NPK);
		myParam.setNK(NK);
		myParam.setNP(NP);
		myParam.setPK(PK);
		myParam.setYxcfJa(yxcfJa);
		myParam.setYxcfUnit(yxcfUnit);
		myParam.setZjzl(zjzl);
		//nysx字段
		myParam.setNysx(nysx);
		return new AddOrUpdateToolCatalogLogic().process(myParam);
	}
	
	
	@POST
	@Path("delToolCatalog.do")
	public String delToolCatalog(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelToolCatalogParam myParam = new DelToolCatalogParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelToolCatalogLogic().process(myParam);
	}
	
	@POST
	@Path("getToolCatalogInfo.do")
	public String getToolCatalogInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetToolCatalogInfoParam myParam = new GetToolCatalogInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetToolCatalogInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getToolCatalogList.do")
	public String getToolCatalogList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name ,
			@FormParam("supplierName")  String supplierName,
			@FormParam("type")  String type,
			@FormParam("unit")  String unit,
			@FormParam("code")  String code,
			@FormParam("uniformPrice")  String uniformPrice ,
			@FormParam("ids")  String ids,
			@FormParam("status")  String status,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetToolCatalogListParam myParam = new GetToolCatalogListParam(userId, apiKey, request);
		myParam.setSupplierName(supplierName);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setUnit(unit);
		myParam.setCode(code);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setUniformPrice(uniformPrice);
		myParam.setIds(ids);
		myParam.setStatus(status);
		return new GetToolCatalogListLogic().process(myParam);
	}
	
	@POST
	@Path("addToolFromToolCatalog.do")
	public String addToolFromToolCatalog(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		AddToolFromToolCatalogParam myParam = new AddToolFromToolCatalogParam(userId, apiKey, request);
		myParam.setIds(id);
		myParam.setEnterpriseId(enterpriseId);
		return new AddToolFromToolCatalogLogic().process(myParam);
	}
	
	@POST
	@Path("getMiniToolCatalogList.do")
	public String getMiniToolCatalogList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("toolName")  String toolName ,
			@FormParam("productionUnits")  String productionUnits,
			@FormParam("type")  String type,
			@FormParam("uniformPrice")  String uniformPrice,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll") String selectAll,
			@FormParam("productAttributes") String productAttributes,
			@Context HttpServletRequest request) {
		GetMiniToolCatalogListParam myParam = new GetMiniToolCatalogListParam(userId, apiKey, request);
		myParam.setToolName(toolName);
		myParam.setProductionUnits(productionUnits);
		myParam.setType(type);
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setSelectAll(selectAll);
		myParam.setUniformPrice(uniformPrice);
		myParam.setProductAttributes(productAttributes);
		return new GetMiniToolCatalogListLogic().process(myParam);
	}
	
	@POST
	@Path("getToolCatalogOrderList.do")
	public String getToolCatalogOrderList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId ,
			@FormParam("seller")  String seller ,
			@FormParam("buyer")  String buyer,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("toolCatalogId")  String toolCatalogId,
			@FormParam("code")  String code,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetSellRecordByToolCatalogIdParam myParam = new GetSellRecordByToolCatalogIdParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setCode(code);
		myParam.setSeller(seller);
		myParam.setBuyer(buyer);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setToolCatalogId(StringUtils.isEmpty(toolCatalogId) ? null : Integer.valueOf(toolCatalogId));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setSelectAll(selectAll);
		return new GetSellRecordByToolCatalogIdLogic().process(myParam);
	}
	
	/*
	 *审核品牌
	 * */
	@POST
	@Path("auditToolCatalog.do")
	public String auditSellBrand(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		AuditToolCatalogStatusParam myParam = new AuditToolCatalogStatusParam(userId,apiKey, request);
		myParam.setIds(ids);
		myParam.setStatus(status);
		return new AuditToolCatalogStatusLogic().process(myParam);
	}
	
	/*
	 *修改备注
	 * */
	@POST
	@Path("changeToolCatalogReamrk.do")
	public String changeToolCatalogReamrk(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("remark")  String remark,
			@Context HttpServletRequest request) {
		ChangeToolCatalogReamrkParam myParam = new ChangeToolCatalogReamrkParam(userId,apiKey, request);
		myParam.setId(id);
		myParam.setRemark(remark);
		return new ChangeToolCatalogReamrkLogic().process(myParam);
	}
	/*
	 * 获取该用户添加农资信息
	 */
	
	@POST
	@Path("getUserToolCatalogList.do")
	public String getUserToolCatalogList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name")  String name ,
			@FormParam("supplierName")  String supplierName,
			@FormParam("type")  String type,
			@FormParam("unit")  String unit,
			@FormParam("code")  String code,
			@FormParam("uniformPrice")  String uniformPrice ,
			@FormParam("ids")  String ids,
			@FormParam("status")  String status,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@Context HttpServletRequest request) {
		GetToolCatalogListParam myParam = new GetToolCatalogListParam(userId, apiKey, request);
		myParam.setSupplierName(supplierName);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setUnit(unit);
		myParam.setCode(code);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setUniformPrice(uniformPrice);
		myParam.setIds(ids);
		myParam.setStatus(status);
		return new GetUserToolCatalogListLogic().process(myParam);
	}
	@POST
	@Path("statisticToolCatalogOrderList.do")
	public String statisticToolCatalogOrderList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId ,
			@FormParam("seller")  String seller ,
			@FormParam("buyer")  String buyer,
			@FormParam("startTime")  String startTime,
			@FormParam("endTime")  String endTime,
			@FormParam("toolCatalogId")  String toolCatalogId,
			@FormParam("code")  String code,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll") String selectAll,
			@Context HttpServletRequest request) {
		GetSellRecordByToolCatalogIdParam myParam = new GetSellRecordByToolCatalogIdParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setCode(code);
		myParam.setSeller(seller);
		myParam.setBuyer(buyer);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setToolCatalogId(StringUtils.isEmpty(toolCatalogId) ? null : Integer.valueOf(toolCatalogId));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setSelectAll(selectAll);
		return new StatisticSellRecordByToolCatalogIdLogic().process(myParam);
	}
	
	@POST
	@Path("statisticMiniToolCatalogNyFl.do")
	public String statisticMiniToolCatalogNyFl(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		GetMiniToolCatalogListParam myParam = new GetMiniToolCatalogListParam(userId, apiKey, request);
		return new StatisticMiniToolCatalogNyFlLogic().process(myParam);
	}
	
	@POST
	@Path("syncToolUniformPrice.do")
	public String syncToolUniformPrice(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@Context HttpServletRequest request) {
		GetMiniToolCatalogListParam myParam = new GetMiniToolCatalogListParam(userId, apiKey, request);
		return new SyncToolUniformPriceLogic().process(myParam);
	}
	
}
