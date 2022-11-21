package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.logic.device.GetDeviceDataListLogic;
import com.jinpinghu.logic.device.GetDeviceInfoLogic;
import com.jinpinghu.logic.device.GetDeviceListLogic;
import com.jinpinghu.logic.device.param.GetDeviceDataListParam;
import com.jinpinghu.logic.device.param.GetDeviceListParam;
import com.jinpinghu.logic.device.param.SimpleParam;

@Path("device")
@Produces("application/json;charset=UTF-8")
public class DeviceAction extends BaseZAction{
	
	/**
	 *获取设备列表
	 */
	@POST
	@Path("getDeviceList.do")
	public String getDeviceList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String baseId,
			@FormParam("deviceName") String deviceName,
			@FormParam("greenhousesName") String greenhousesName,
			@FormParam("greenhousesId") String greenhousesId,
			@FormParam("classify") String classify,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetDeviceListParam myParam = new GetDeviceListParam(userId, apiKey, request);
		myParam.setBaseId(StringUtil.isNullOrEmpty(baseId)?null:baseId);
		myParam.setDeviceName(StringUtil.isNullOrEmpty(deviceName)?null:deviceName);
		myParam.setGreenhousesName(StringUtil.isNullOrEmpty(greenhousesName)?null:greenhousesName);
		myParam.setNowPage(StringUtil.isNullOrEmpty(nowPage)?null:Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtil.isNullOrEmpty(pageCount)?null:Integer.valueOf(pageCount));
		myParam.setGreenhousesId(greenhousesId);
		myParam.setClassify(StringUtil.isNullOrEmpty(classify)?null:Integer.valueOf(classify));
		return new GetDeviceListLogic().process(myParam);
	}
	
	/**
	 *获取设备详情
	 */
	@POST
	@Path("getDeviceInfo.do")
	public String getDeviceInfo(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		SimpleParam myParam = new SimpleParam(userId, apiKey, request);
		myParam.setIds(StringUtil.isNullOrEmpty(id)?null:id);
		return new GetDeviceInfoLogic().process(myParam);
	}
	
	
	@POST
	@Path("getDeviceDataList.do")
	public String getDeviceDataList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("deviceId") String deviceId,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("pageCount") String pageCount,
			@FormParam("nowPage") String nowPage,
			@Context HttpServletRequest request) {
		GetDeviceDataListParam myParam = new GetDeviceDataListParam(userId, apiKey, request);
		myParam.setDeviceId(StringUtil.isNullOrEmpty(deviceId) ? null : Integer.valueOf(deviceId));
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setPageCount(StringUtil.isNullOrEmpty(pageCount) ? null : Integer.valueOf(pageCount));
		myParam.setNowPage(StringUtil.isNullOrEmpty(nowPage) ? null : Integer.valueOf(nowPage));
		return new GetDeviceDataListLogic().process(myParam);
	}
	
	
}
