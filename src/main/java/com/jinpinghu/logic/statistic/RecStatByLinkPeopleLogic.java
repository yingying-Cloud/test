package com.jinpinghu.logic.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.RecStatByStreetParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RecStatByLinkPeopleLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RecStatByStreetParam myParam=(RecStatByStreetParam)logicParam;
		String name = myParam.getName();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		StatisticDao statisticDao=new StatisticDao(em);
		Integer count = statisticDao.recStatByLinkPeopleCount(enterpriseId, startTime, endTime, selectAll, permissionEnterpriseIdList, name);
		List<Object[]> obList=statisticDao.recStatByLinkPeople(enterpriseId, startTime, endTime, selectAll, permissionEnterpriseIdList, name, nowPage, pageCount);
		
		if(obList!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(Object[] ob: obList){
				map=new HashMap<String,Object>();
				map.put("id", ob[0]);
				map.put("name", ob[1]);
				map.put("nyfqbzwNum", ob[2]);
				map.put("fqnmNum", ob[3] == null ? 0 :ob[3]);
				result.add(map);
			}
			res.add("status", 1).add("result", result).add("msg", "操作成功").add("allCounts", count);
		}else{
			res.add("status", 1).add("msg", "无数据");
		}
		return true;
	}

}
