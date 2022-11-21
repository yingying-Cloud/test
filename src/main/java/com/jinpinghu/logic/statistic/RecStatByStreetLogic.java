package com.jinpinghu.logic.statistic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

public class RecStatByStreetLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam RecStatByStreetParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RecStatByStreetParam myParam=(RecStatByStreetParam)RecStatByStreetParam;
		
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		Integer type=StringUtil.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		StatisticDao statisticDao=new StatisticDao(em);
		
		List<Object[]> obList=statisticDao.recStatByStreet(enterpriseId, startTime, endTime, type,selectAll,permissionEnterpriseIdList);
		
		if(obList!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(Object[] ob: obList){
				map=new HashMap<String,Object>();
				map.put("name", ob[0]);
				map.put("value", ob[1]);
				map.put("id", ob[2]);

				result.add(map);
			}
			res.add("status", 1).add("result", result).add("msg", "操作成功");
		}else{
			res.add("status", 1).add("msg", "无数据");
		}
		return true;
	}

}
