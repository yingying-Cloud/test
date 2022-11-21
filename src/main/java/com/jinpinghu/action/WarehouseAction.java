package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.warehouse.AddOrUpdateWareHouseLogic;
import com.jinpinghu.logic.warehouse.GetPlantWarehouseInfoLogic;
import com.jinpinghu.logic.warehouse.GetPlantWarehouseListLogic;
import com.jinpinghu.logic.warehouse.param.AddOrUpdateWareHouseParam;
import com.jinpinghu.logic.warehouse.param.GetPlantWarehouseInfoParam;
import com.jinpinghu.logic.warehouse.param.GetPlantWarehouseListParam;

@Path("warehouse")
@Produces("application/json;charset=UTF-8")
public class WarehouseAction extends BaseZAction {
	


	@POST
	@Path("addWareHouse.do")
	public String addOrUpdateWareHouse(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("brandId") String brandId,
			@FormParam("recordType") String recordType,
			@FormParam("outDirection") String outDirection,
			@FormParam("logisticsInfo") String logisticsInfo,
			@FormParam("linkPeople") String linkPeople,
			@FormParam("linkMobile") String linkMobile,
			@FormParam("number") String number,
			@FormParam("file") String file,
			@FormParam("workId") String workId,
			@FormParam("oddNumber") String oddNumber,
			@FormParam("price") String price,
			@FormParam("wrapper") String wrapper,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("inputTime") String inputTime,
			@Context HttpServletRequest request) {
		AddOrUpdateWareHouseParam myParam = new AddOrUpdateWareHouseParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setBrandId(brandId);
		myParam.setWorkId(workId);
		myParam.setRecordType(recordType);
		myParam.setOutDirection(outDirection);
		myParam.setLogisticsInfo(logisticsInfo);
		myParam.setLinkMobile(linkMobile);
		myParam.setLinkPeople(linkPeople);
		myParam.setNumber(number);
		myParam.setInputTime(inputTime);
		myParam.setOddNumber(oddNumber);
		myParam.setWrapper(wrapper);
		myParam.setPrice(price);
		myParam.setFile(file);
		return new AddOrUpdateWareHouseLogic().process(myParam);
	}
	
	@POST
	@Path("getPlantWarehouseList.do")
	public String getPlantWarehouseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("workSn") String workSn,
			@FormParam("recordType") String recordType,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetPlantWarehouseListParam myParam = new GetPlantWarehouseListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setWarehouseType(recordType);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setWorkSn(workSn);
		return new GetPlantWarehouseListLogic().process(myParam);
	}
	@POST
	@Path("getPlantWarehouseInfo.do")
	public String getPlantWarehouseList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetPlantWarehouseInfoParam myParam = new GetPlantWarehouseInfoParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetPlantWarehouseInfoLogic().process(myParam);
	}
}
