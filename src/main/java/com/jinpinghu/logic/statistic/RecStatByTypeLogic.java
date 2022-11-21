package com.jinpinghu.logic.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.RecStatByTypeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RecStatByTypeLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam RecStatByTypeParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RecStatByTypeParam myParam=(RecStatByTypeParam)RecStatByTypeParam;
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		Integer type =StringUtils.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String name = myParam.getName();
		StatisticDao statisticDao=new StatisticDao(em);
		
		Integer count = statisticDao.recStatByTypeCount( startTime, endTime, type,name);
		if(count==null||count==0) {
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Object[]> obList=statisticDao.recStatByType( startTime, endTime, type,nowPage,pageCount,name);
		if(obList!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(Object[] ob: obList){
				map=new HashMap<String,Object>();
				map.put("name", ob[0]);
				map.put("number", ob[1]);
				result.add(map);
			}
			res.add("status", 1).add("result", result).add("msg", "操作成功");res.add("allCounts", count);
		}
		return true;
	}

}

