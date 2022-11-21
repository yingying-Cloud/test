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

public class StatisticValidationDscdOrderLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticValidationParam myParam = (StatisticValidationParam)logicParam;
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		StatisticDao statisticDao = new StatisticDao(em);
		List<Map<String, Object>> resultList = statisticDao.statisticValidationDscdOrder(selectAll,permissionEnterpriseIdList);
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return true;
	}

}
