package com.jinpinghu.logic.statistic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.RecStatByKindsParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RecStatByKindsLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam RecStatByKindsParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RecStatByKindsParam myParam=(RecStatByKindsParam)RecStatByKindsParam;
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		
		StatisticDao statisticDao=new StatisticDao(em);
		
		List<Object[]> obList=statisticDao.recStatByKinds(enterpriseId, startTime, endTime);
		
		
		if(obList!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(Object[] ob: obList){
				map=new HashMap<String,Object>();
				map.put("number", ob[0]==null?0:ob[0]);
				map.put("name", ob[1]);
				result.add(map);
				
			}
			res.add("status", 1).add("result", result).add("msg", "操作成功");
		}else{
			res.add("status", 1).add("msg", "无数据");
		}
		return true;
	}

}
