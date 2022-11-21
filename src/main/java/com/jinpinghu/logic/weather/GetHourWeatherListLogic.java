package com.jinpinghu.logic.weather;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbHourWeatherDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weather.param.GetHourWeatherListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetHourWeatherListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetHourWeatherListParam myParam = (GetHourWeatherListParam)logicParam;
		String area = myParam.getArea();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String type =myParam.getType();
		TbHourWeatherDao weatherDao = new TbHourWeatherDao(em);
		List<Map<String,Object>> list = null;
		if(!StringUtils.isNullOrEmpty(type)&&type.equals("1")) {
			list = weatherDao.findHourWeatherListByTime(startTime, endTime, area);
		}else {
			Date day = new Date();
			Date addDay = DateTimeUtil.addDay(day,1);
			list = weatherDao.findHourWeatherListByTime(DateTimeUtil.formatTime9(day), DateTimeUtil.formatTime9(addDay), area);
		}
		res.add("result", list);
		res.add("msg", "操作成功");
		res.add("status", 1);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
			return true;
		}
}
