package com.jinpinghu.action;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.tool.AddOrUpdateToolLogic;
import com.jinpinghu.logic.tool.AddToolFileLogic;
import com.jinpinghu.logic.tool.DelToolLogic;
import com.jinpinghu.logic.tool.GetToolInfoLogic;
import com.jinpinghu.logic.tool.GetToolListLogic;
import com.jinpinghu.logic.tool.GetWorkToolSumListLogic;
import com.jinpinghu.logic.tool.ImportToolLogic;
import com.jinpinghu.logic.tool.ImportToolNumberLogic;
import com.jinpinghu.logic.tool.param.AddOrUpdateToolParam;
import com.jinpinghu.logic.tool.param.DelToolParam;
import com.jinpinghu.logic.tool.param.GetToolInfoParam;
import com.jinpinghu.logic.tool.param.GetToolListParam;
import com.jinpinghu.logic.tool.param.GetWorkToolSumListParam;
import com.jinpinghu.logic.tool.param.ImportToolParam;



@Path("tool")
@Produces("application/json;charset=UTF-8")
public class ToolAction extends BaseZAction {

	@POST
	@Path("addOrUpdateTool.do")
	public String addTool(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("enterpriseId") String enterpriseId,
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
			@FormParam("dnm") String dnm,
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
		AddOrUpdateToolParam myParam = new AddOrUpdateToolParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setDescribe(describe);
		myParam.setModel(model);
		myParam.setName(name);
		myParam.setNumber(number);
		myParam.setPrice(price);
		myParam.setSpecification(specification);
		myParam.setUnit(unit);
		myParam.setType(type);
		myParam.setEnterpriseId(enterpriseId);
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
		myParam.setDnm(dnm);
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
		myParam.setNysx(nysx);
		return new AddOrUpdateToolLogic().process(myParam);
	}
	
	
	@POST
	@Path("delTool.do")
	public String delTool(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelToolParam myParam = new DelToolParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelToolLogic().process(myParam);
	}
	
	@POST
	@Path("getToolInfo.do")
	public String getToolInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		GetToolInfoParam myParam = new GetToolInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetToolInfoLogic().process(myParam);
	}
	
	@POST
	@Path("getToolList.do")
	public String getToolList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("code")  String code ,
			@FormParam("name")  String name ,
			@FormParam("enterpriseName")  String enterpriseName,
			@FormParam("enterpriseType")  String enterpriseType,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("supplierName")  String supplierName,
			@FormParam("type")  String type,
			@FormParam("unit")  String unit,
			@FormParam("dnm")  String dnm,
			@FormParam("ids")  String ids,
			@FormParam("uniformPrice")  String uniformPrice,
			@FormParam("orderby")  String orderby,
			@FormParam("sortDirection")  String sortDirection,
			@FormParam("nowPage")  String nowPage,
			@FormParam("pageCount")  String pageCount,
			@FormParam("selectAll")  String selectAll,
			@FormParam("recordNo")  String recordNo,
			@FormParam("productAttributes")  String productAttributes,
			@FormParam("dscd")  String dscd,
			@FormParam("lowPrice")  String lowPrice,
			@FormParam("highPrice")  String highPrice,
			@FormParam("etName")  String etName,
			@Context HttpServletRequest request) {
		GetToolListParam myParam = new GetToolListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setEnterpriseName(enterpriseName);
		myParam.setEnterpriseType(enterpriseType);
		myParam.setSupplierName(supplierName);
		myParam.setName(name);
		myParam.setType(type);
		myParam.setUnit(unit);
		myParam.setIds(ids);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setCode(code);
		myParam.setDnm(dnm);
		myParam.setOrderby(orderby);
		myParam.setSortDirection(sortDirection);
		myParam.setSelectAll(selectAll);
		myParam.setRecordNo(recordNo);
		myParam.setUniformPrice(uniformPrice);
		myParam.setDscd(dscd);
		myParam.setProductAttributes(productAttributes);
		myParam.setLowPrice(lowPrice);
		myParam.setHighPrice(highPrice);
		myParam.setEtName(etName);
		return new GetToolListLogic().process(myParam);
	}
	@POST
	@Path("getWorkToolSumList.do")
	public String getWorkToolSumList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@Context HttpServletRequest request) {
		GetWorkToolSumListParam myParam = new GetWorkToolSumListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		return new GetWorkToolSumListLogic().process(myParam);
	}
	//导入商品价格
	@POST
	@Path("importTool.do")
	public String importGoods(
			@Context HttpServletRequest request) {
		ImportToolParam myParam = new ImportToolParam(null,null,request);
		return new ImportToolLogic().process(myParam);
	}
	//导入商品数量
	@POST
	@Path("importToolNumber.do")
	public String importToolNumber(
			@Context HttpServletRequest request) {
		ImportToolParam myParam = new ImportToolParam(null,null,request);
		return new ImportToolNumberLogic().process(myParam);
	}
	//添加农资图片
	@POST
	@Path("addToolFile.do")
	public String addToolRecoveryRecord(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("file")  String file,
			@Context HttpServletRequest request) {
		AddOrUpdateToolParam myParam = new AddOrUpdateToolParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setFile(file);
		return new AddToolFileLogic().process(myParam);
	}
}
