package com.jinpinghu.logic.weather;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbDayWeatherDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weather.param.GetDayWeatherListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetDayWeatherListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetDayWeatherListParam myParam = (GetDayWeatherListParam)logicParam;
		String area = myParam.getArea();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		TbDayWeatherDao weatherDao = new TbDayWeatherDao(em);
		List<Map<String,Object>> list = weatherDao.findDayWeatherListByTime(startTime, endTime, area);
		res.add("result", list);
		res.add("msg", "操作成功");
		res.add("status", 1);
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
