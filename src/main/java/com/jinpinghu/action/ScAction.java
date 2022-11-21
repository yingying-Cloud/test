package com.jinpinghu.action;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbBuyRelease;
import com.jinpinghu.db.bean.TbSupplyRelease;
import com.jinpinghu.logic.sc.AddBuyReleaseLogic;
import com.jinpinghu.logic.sc.AddScOrderLogic;
import com.jinpinghu.logic.sc.AddSupplyReleaseLogic;
import com.jinpinghu.logic.sc.DelBuyReleaseLogic;
import com.jinpinghu.logic.sc.DelSupplyReleaseLogic;
import com.jinpinghu.logic.sc.DetailBuyReleaseLogic;
import com.jinpinghu.logic.sc.DetailScOrderLogic;
import com.jinpinghu.logic.sc.DetailSupplyReleaseLogic;
import com.jinpinghu.logic.sc.ListBuyReleaseLogic;
import com.jinpinghu.logic.sc.ListScOrderLogic;
import com.jinpinghu.logic.sc.ListSupplyReleaseLogic;
import com.jinpinghu.logic.sc.ListUnionSupplyAndBuyLogic;
import com.jinpinghu.logic.sc.StatisticScOrderByStatusLogic;
import com.jinpinghu.logic.sc.StatisticScOrderCountLogic;
import com.jinpinghu.logic.sc.UpdateBuyReleaseStatusLogic;
import com.jinpinghu.logic.sc.UpdateScOrderStatusLogic;
import com.jinpinghu.logic.sc.UpdateSupplyReleaseStatusLogic;
import com.jinpinghu.logic.sc.param.AddBuyReleaseParam;
import com.jinpinghu.logic.sc.param.AddScOrderParam;
import com.jinpinghu.logic.sc.param.AddSupplyReleaseParam;
import com.jinpinghu.logic.sc.param.DelBuyReleaseParam;
import com.jinpinghu.logic.sc.param.DelSupplyReleaseParam;
import com.jinpinghu.logic.sc.param.DetailBuyReleaseParam;
import com.jinpinghu.logic.sc.param.DetailScOrderParam;
import com.jinpinghu.logic.sc.param.DetailSupplyReleaseParam;
import com.jinpinghu.logic.sc.param.ListBuyReleaseParam;
import com.jinpinghu.logic.sc.param.ListScOrderParam;
import com.jinpinghu.logic.sc.param.ListSupplyReleaseParam;
import com.jinpinghu.logic.sc.param.ListUnionSupplyAndBuyParam;
import com.jinpinghu.logic.sc.param.StatisticScOrderByStatusParam;
import com.jinpinghu.logic.sc.param.StatisticScOrderCountParam;
import com.jinpinghu.logic.sc.param.UpdateBuyReleaseStatusParam;
import com.jinpinghu.logic.sc.param.UpdateScOrderStatusParam;
import com.jinpinghu.logic.sc.param.UpdateSupplyReleaseStatusParam;

@Path("sc")
@Produces("application/json;charset=UTF-8")
public class ScAction extends BaseZAction {

	@POST
	@Path("addSupplyRelease.do")
	public String addSupplyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String idStr,
			@FormParam("brandId") Integer brandId,
			@FormParam("workId") Integer workId,
			@FormParam("specification") String specification,
			@FormParam("num") Integer num,
			@FormParam("price") BigDecimal price,
			@FormParam("type") Integer type,
			@FormParam("contactPerson") String contactPerson,
			@FormParam("contactPhone") String contactPhone,
			@FormParam("contactAddress") String contactAddress,
			@FormParam("enterpriseId") Integer enterpriseId,
			@FormParam("dscd") String dscd,
			@FormParam("estimateOutput") String estimateOutput,
			@FormParam("fileArray") String fileArray,
			@Context HttpServletRequest request) {
		Integer id = StringUtils.isEmpty(idStr) ? null : Integer.valueOf(idStr);
		AddSupplyReleaseParam myParam = new AddSupplyReleaseParam(userId, apiKey, request);
		TbSupplyRelease supplyRelease = new TbSupplyRelease(id,brandId, contactAddress, contactPerson, contactPhone, enterpriseId, 
				num, price, specification, type, workId,dscd,estimateOutput);
		myParam.setSupplyRelease(supplyRelease);
		myParam.setFileArray(fileArray);
		return new AddSupplyReleaseLogic().process(myParam);
	}
	
	
	@POST
	@Path("delSupplyRelease.do")
	public String delSupplyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelSupplyReleaseParam myParam = new DelSupplyReleaseParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelSupplyReleaseLogic().process(myParam);
	}
	
	@POST
	@Path("detailSupplyRelease.do")
	public String detailSupplyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		DetailSupplyReleaseParam myParam = new DetailSupplyReleaseParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new DetailSupplyReleaseLogic().process(myParam);
	}
	
	@POST
	@Path("listSupplyRelease.do")
	public String listSupplyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("productName") String productName,
			@FormParam("type") String type,
			@FormParam("status") String status,
			@FormParam("dscd") String dscd,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		ListSupplyReleaseParam myParam = new ListSupplyReleaseParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setProductName(productName);
		myParam.setDscd(dscd);
		myParam.setType(StringUtils.isEmpty(type) ? null : Integer.valueOf(type));
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		return new ListSupplyReleaseLogic().process(myParam);
	}
	
	@POST
	@Path("updateSupplyReleaseStatus.do")
	public String updateSupplyReleaseStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		UpdateSupplyReleaseStatusParam myParam = new UpdateSupplyReleaseStatusParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		return new UpdateSupplyReleaseStatusLogic().process(myParam);
	}
	
	
	
	
	
	@POST
	@Path("addBuyRelease.do")
	public String addBuyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String idStr,
			@FormParam("contactAddress") String contactAddress,
			@FormParam("contactPerson") String contactPerson,
			@FormParam("contactPhone") String contactPhone,
			@FormParam("startTime") String startTimeStr,
			@FormParam("endTime") String endTimeStr,
			@FormParam("productName") String productName,
			@FormParam("enterpriseId") Integer enterpriseId,
			@FormParam("num") Integer num,
			@FormParam("type") Integer type,
			@FormParam("price") BigDecimal price,
			@FormParam("dscd") String dscd,
			@FormParam("specification") String specification,
			@FormParam("fileArray") String fileArray,
			@Context HttpServletRequest request) {
		Integer id = StringUtils.isEmpty(idStr) ? null : Integer.valueOf(idStr);
		Date startTime = StringUtils.isEmpty(startTimeStr) ? null : DateTimeUtil.formatTime(startTimeStr);
		Date endTime = StringUtils.isEmpty(endTimeStr) ? null : DateTimeUtil.formatTime(endTimeStr);
		AddBuyReleaseParam myParam = new AddBuyReleaseParam(userId, apiKey, request);
		TbBuyRelease buyRelease = new TbBuyRelease(id,contactAddress, contactPerson, contactPhone, endTime, enterpriseId, num, price, 
				productName, startTime, type,dscd,specification);
		myParam.setBuyRelease(buyRelease);
		myParam.setFileArray(fileArray);
		return new AddBuyReleaseLogic().process(myParam);
	}
	
	
	@POST
	@Path("delBuyRelease.do")
	public String delBuyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("ids")  String ids,
			@Context HttpServletRequest request) {
		DelBuyReleaseParam myParam = new DelBuyReleaseParam(userId, apiKey, request);
		myParam.setIds(ids);
		return new DelBuyReleaseLogic().process(myParam);
	}
	
	@POST
	@Path("detailBuyRelease.do")
	public String detailBuyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		DetailBuyReleaseParam myParam = new DetailBuyReleaseParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new DetailBuyReleaseLogic().process(myParam);
	}
	
	@POST
	@Path("listBuyRelease.do")
	public String listBuyRelease(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("productName") String productName,
			@FormParam("type") String type,
			@FormParam("dscd") String dscd,
			@FormParam("status") String status,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		ListBuyReleaseParam myParam = new ListBuyReleaseParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setProductName(productName);
		myParam.setDscd(dscd);
		myParam.setType(StringUtils.isEmpty(type) ? null : Integer.valueOf(type));
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		return new ListBuyReleaseLogic().process(myParam);
	}
	
	
	@POST
	@Path("updateBuyReleaseStatus.do")
	public String updateBuyReleaseStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		UpdateBuyReleaseStatusParam myParam = new UpdateBuyReleaseStatusParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		return new UpdateBuyReleaseStatusLogic().process(myParam);
	}
	
	
	
	
	@POST
	@Path("addScOrder.do")
	public String addScOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("contactPerson") String contactPerson,
			@FormParam("contactPhone") String contactPhone,
			@FormParam("contactAddress") String contactAddress,
			@FormParam("workId") String workId,
			@FormParam("remark") String remark,
			@FormParam("supplyReleaseId") String supplyReleaseId,
			@FormParam("buyReleaseId") String buyReleaseId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("num") String num,
			@FormParam("price") String price,
			@FormParam("productName") String productName,
			@Context HttpServletRequest request) {
		AddScOrderParam myParam = new AddScOrderParam(userId, apiKey, request);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setContactAddress(contactAddress);
		myParam.setContactPerson(contactPerson);
		myParam.setContactPhone(contactPhone);
		myParam.setWorkId(workId);
		myParam.setRemark(remark);
		myParam.setSupplyReleaseId(supplyReleaseId);
		myParam.setBuyReleaseId(buyReleaseId);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNum(num);
		myParam.setPrice(price);
		myParam.setProductName(productName);
		return new AddScOrderLogic().process(myParam);
	}
	
	
	@POST
	@Path("detailScOrder.do")
	public String detailScOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@Context HttpServletRequest request) {
		DetailScOrderParam myParam = new DetailScOrderParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		return new DetailScOrderLogic().process(myParam);
	}
	
	@POST
	@Path("listScOrder.do")
	public String listScOrder(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("productName") String productName,
			@FormParam("type") String type,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("orderNumber") String orderNumber,
			@FormParam("status") String status,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		ListScOrderParam myParam = new ListScOrderParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		myParam.setProductName(productName);
		myParam.setType(type);
		myParam.setNowPage(StringUtils.isEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setOrderNumber(orderNumber);
		myParam.setStatus(status);
		return new ListScOrderLogic().process(myParam);
	}
	
	@POST
	@Path("updateScOrderStatus.do")
	public String updateScOrderStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("status")  String status,
			@Context HttpServletRequest request) {
		UpdateScOrderStatusParam myParam = new UpdateScOrderStatusParam(userId, apiKey, request);
		myParam.setId(StringUtils.isEmpty(id) ? null : Integer.valueOf(id));
		myParam.setStatus(StringUtils.isEmpty(status) ? null : Integer.valueOf(status));
		return new UpdateScOrderStatusLogic().process(myParam);
	}
	
	@POST
	@Path("statisticScOrderByStatus.do")
	public String statisticScOrderByStatus(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId")  String enterpriseId,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		StatisticScOrderByStatusParam myParam = new StatisticScOrderByStatusParam(userId, apiKey, request);
		myParam.setType(type);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		return new StatisticScOrderByStatusLogic().process(myParam);
	}
	
	@POST
	@Path("listUnionSupplyAndBuy.do")
	public String listUnionSupplyAndBuy(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("dscd") String dscd,
			@FormParam("type") String type,
			@FormParam("name") String name,
			@FormParam("startPrice") String startPrice,
			@FormParam("endPrice") String endPrice,
			@FormParam("releaseType") String releaseType,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("orderby")  String orderby,
			@FormParam("sortDirection")  String sortDirection,
			@Context HttpServletRequest request) {
		ListUnionSupplyAndBuyParam myParam = new ListUnionSupplyAndBuyParam(userId,apiKey,request);
		myParam.setDscd(dscd);
		myParam.setReleaseType(releaseType);
		myParam.setType(type);
		myParam.setStartPrice(startPrice);
		myParam.setEndPrice(endPrice);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setName(name);
		myParam.setOrderby(orderby);
		myParam.setSortDirection(sortDirection);
		return new ListUnionSupplyAndBuyLogic().process(myParam);
	}
	
	@POST
	@Path("statisticScOrderCount.do")
	public String statisticScOrderCount(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@Context HttpServletRequest request) {
		StatisticScOrderCountParam myParam = new StatisticScOrderCountParam(userId, apiKey, request);
		myParam.setEnterpriseId(StringUtils.isEmpty(enterpriseId) ? null : Integer.valueOf(enterpriseId));
		return new StatisticScOrderCountLogic().process(myParam);
	}
	
	
}
