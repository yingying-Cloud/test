package com.jinpinghu.logic.statistic;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.GetAllToolOrderStatisticParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAllToolOrderToolTypeStatisticLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAllToolOrderStatisticParam myParam = (GetAllToolOrderStatisticParam)logicParam;
		String endTime = myParam.getEndTime();
		String startTime = myParam.getStartTime();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer toolType = StringUtils.isNullOrEmpty(myParam.getToolType())?null:Integer.valueOf(myParam.getToolType());
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		StatisticDao statisticDao = new StatisticDao(em);
		List<Map<String, Object>> map = statisticDao.getAllOrderToolTypeCount(enterpriseId, startTime, endTime,toolType,selectAll,permissionEnterpriseIdList);
		
		res.add("result", map);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
