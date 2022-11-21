package com.jinpinghu.logic.statistic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.RecStatByEnterpriseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RecStatByEnterpriseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam RecStatByEnterpriseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RecStatByEnterpriseParam myParam=(RecStatByEnterpriseParam)RecStatByEnterpriseParam;
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String name = myParam.getName();
		StatisticDao statisticDao=new StatisticDao(em);
		
		Integer count=statisticDao.recStatByEnterpriseCount( startTime, endTime,name);
		DecimalFormat df=new DecimalFormat("0.00");

		List<Object[]> obList=statisticDao.recStatByEnterprise( startTime, endTime, nowPage, pageCount,name);
		if(count!=null){

			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			int No=(nowPage-1)*pageCount+1;
			for(Object[] ob: obList){
				map=new HashMap<String,Object>();
				map.put("No", No++);
				map.put("name", ob[0]);
				map.put("number", ob[1]);
				map.put("price",ob[2]==null?0:new BigDecimal(ob[2].toString()));
				map.put("area", ob[3]);
				map.put("ratio", ob[4]==null?"0%":ob[4].toString()+'%');
				result.add(map);
			}
			res.add("status", 1).add("result", result).add("msg", "操作成功").add("allCounts", count);
		}else{
			res.add("status", 1).add("msg", "无数据");
		}
		return true;
	}

}
