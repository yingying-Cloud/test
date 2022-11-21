package com.jinpinghu.logic.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.RecStatByToolParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RecStatByToolLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam RecStatByToolParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RecStatByToolParam myParam=(RecStatByToolParam)RecStatByToolParam;
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		String recoveryId =myParam.getRecoveryId();
		
		StatisticDao statisticDao=new StatisticDao(em);
		
		List<Object[]> obList=statisticDao.recStatByTool( startTime, endTime, recoveryId);
		
		if(obList!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(Object[] ob: obList){
				map=new HashMap<String,Object>();
				map.put("name", ob[0]);
				map.put("number",ob[1]);
				result.add(map);
			}
			res.add("status", 1).add("result", result).add("msg", "操作成功");
		}else{
			res.add("status", 1).add("msg", "无数据");
		}
		return true;
	}

}

