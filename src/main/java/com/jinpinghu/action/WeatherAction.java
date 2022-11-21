package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.weather.GetAreaListLogic;
import com.jinpinghu.logic.weather.GetDayWeatherListLogic;
import com.jinpinghu.logic.weather.GetHourWeatherListLogic;
import com.jinpinghu.logic.weather.GetNowHourWeatherLogic;
import com.jinpinghu.logic.weather.GetWeatherAdviceLogic;
import com.jinpinghu.logic.weather.param.GetAreaListParam;
import com.jinpinghu.logic.weather.param.GetDayWeatherListParam;
import com.jinpinghu.logic.weather.param.GetHourWeatherListParam;
import com.jinpinghu.logic.weather.param.GetNowHourWeatherParam;
import com.jinpinghu.logic.weather.param.GetWeatherAdviceParam;

/** 天气 */
@Path("weather")
@Produces("application/json;charset=UTF-8")
public class WeatherAction extends BaseZAction{
	
	@POST
	@Path("getDayWeatherList.do")
	public String getDayWeatherList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("area")  String area,
			@FormParam("endTime")  String endTime,
			@FormParam("startTime")  String startTime,
			@Context HttpServletRequest request) {
		GetDayWeatherListParam myParam = new GetDayWeatherListParam(userId, apiKey, request);
		myParam.setArea(area);
		myParam.setEndTime(endTime);
		myParam.setStartTime(startTime);
		return new GetDayWeatherListLogic().process(myParam);
	}
	@POST
	@Path("getHourWeatherList.do")
	public String getHourWeatherList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("area")  String area,
			@FormParam("endTime")  String endTime,
			@FormParam("startTime")  String startTime,
			@FormParam("type")  String type,
			@Context HttpServletRequest request) {
		GetHourWeatherListParam myParam = new GetHourWeatherListParam(userId, apiKey, request);
		myParam.setArea(area);
		myParam.setEndTime(endTime);
		myParam.setStartTime(startTime);
		myParam.setType(type);
		return new GetHourWeatherListLogic().process(myParam);
	}
	@POST
	@Path("getNowHourWeather.do")
	public String getNowHourWeather(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("area")  String area,
			@FormParam("hour")  String hour,
			@Context HttpServletRequest request) {
		GetNowHourWeatherParam myParam = new GetNowHourWeatherParam(userId, apiKey, request);
		myParam.setArea(area);
		myParam.setHour(hour);
		return new GetNowHourWeatherLogic().process(myParam);
	}
	@POST
	@Path("getWeatherAdvice.do")
	public String getWeatherAdvice(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("area")  String area,
			@FormParam("day")  String day,
			@Context HttpServletRequest request) {
		GetWeatherAdviceParam myParam = new GetWeatherAdviceParam(userId, apiKey, request);
		myParam.setArea(area);
		myParam.setDay(day);
		return new GetWeatherAdviceLogic().process(myParam);
	}
	/*
	 * 获取地区列表
	 */
	@POST
	@Path("getAreaList.do")
	public String getAreaList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("dscd") String dscd,
			@FormParam("needSelf") String needSelf,
			@Context HttpServletRequest request) {
		GetAreaListParam myParam = new GetAreaListParam(userId, apiKey, request);
		myParam.setDscd(dscd);
		myParam.setNeedSelf(needSelf);
		return new GetAreaListLogic().process(myParam);
	}
	
}
