package com.jinpinghu.logic.index;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.IndexDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.index.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticWeekOrderLogic extends  BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam = (SimpleParam)logicParam;
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		IndexDao indexDao = new IndexDao(em);
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Integer count = indexDao.statisticWeekOrder(startTime,endTime);
		List<Map<String,Object>> list = indexDao.statisticWeekOrderInfo(startTime,endTime,nowPage,pageCount);
		if(list!=null) {
			for(Map<String,Object> map:list) {
				if(map.containsKey("inputTime")) {
					String inputTime = map.get("inputTime").toString();
					String smartFormat = DateTimeUtil.smartFormat(DateTimeUtil.formatTime2(inputTime));
					map.put("time", smartFormat);
				}
			}
		}
		res.add("allCounts", count);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
