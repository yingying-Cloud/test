package com.jinpinghu.logic.weather;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbHourWeatherDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weather.param.GetNowHourWeatherParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetNowHourWeatherLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetNowHourWeatherParam myParam = (GetNowHourWeatherParam)logicParam;
		String area = myParam.getArea();
		String hour = StringUtils.isNullOrEmpty(myParam.getHour())?DateTimeUtil.formatTime9(new Date()):myParam.getHour();
		TbHourWeatherDao weatherDao = new TbHourWeatherDao(em);
		Map<String, Object> list = weatherDao.findNowHourWeatherByTime(hour, area);
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
