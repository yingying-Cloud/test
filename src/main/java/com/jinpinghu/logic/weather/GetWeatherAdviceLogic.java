package com.jinpinghu.logic.weather;

import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbWeatherAdviceDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weather.param.GetWeatherAdviceParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWeatherAdviceLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetWeatherAdviceParam myParam = (GetWeatherAdviceParam)logicParam;
		String area = myParam.getArea();
		String day = myParam.getDay();
		TbWeatherAdviceDao weatherAdviceDao = new TbWeatherAdviceDao(em);
		Map<String, Object> list = weatherAdviceDao.findDayWeatherAdviceByTime(day, area);
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
