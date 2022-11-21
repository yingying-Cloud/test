package com.jinpinghu.logic.order;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.StatisticToolOrderByBuyEnterpriseIdParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticToolOrderByBuyEnterpriseIdLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticToolOrderByBuyEnterpriseIdParam myParam = (StatisticToolOrderByBuyEnterpriseIdParam)logicParam;
		Integer buyEnterpriseId = myParam.getBuyEnterpriseId();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		
		List<Map<String, Object>> resultList = toolOrderDao.statisticToolOrderByBuyEnterpriseId(buyEnterpriseId, startTime, endTime);
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return false;
	}

}
