package com.jinpinghu.logic.statistic;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.GetAllToolOrderStatisticParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAllToolCatalogStatisticLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAllToolOrderStatisticParam myParam = (GetAllToolOrderStatisticParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer type = StringUtils.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		StatisticDao statisticDao = new StatisticDao(em);
		Integer allCount = statisticDao.getAllToolCatalogStatistic(enterpriseId);
		List<Map<String, Object>> map = statisticDao.getAllToolCatalogTypeStatistic(enterpriseId,type);
		res.add("result", map);
		res.add("allCounts", allCount);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
