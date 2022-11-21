package com.jinpinghu.logic.statistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.AllRecoveryStatisticParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AllRecoveryStatisticLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AllRecoveryStatisticParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AllRecoveryStatisticParam myParam=(AllRecoveryStatisticParam)AllRecoveryStatisticParam;
		
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		StatisticDao statisticDao=new StatisticDao(em);
		
		Object[] ob=statisticDao.allRecoveryStatistic(enterpriseId, startTime, endTime,selectAll,permissionEnterpriseIdList);
		if(ob!=null){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("number", ob[0]==null?0:ob[0]);
			map.put("price", ob[1]==null?0:ob[1]);
			map.put("area", ob[2]);
			map.put("otherNumber", ob[3]==null?0:ob[3]);
			res.add("status", 1).add("result", map).add("msg", "操作成功");
		}else{
			res.add("status", 1).add("msg", "无数据");
		}
		return true;
	}

}
