package com.jinpinghu.logic.statistic;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.StatisticValidationParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticValidationLogic extends BaseZLogic {

	public StatisticValidationLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticValidationParam myParam = (StatisticValidationParam)logicParam;
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		StatisticDao statisticDao = new StatisticDao(em);
		Map<String, Object> resultList = statisticDao.statisticValidation(selectAll,permissionEnterpriseIdList,startTime,endTime);
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return true;
	}

}
