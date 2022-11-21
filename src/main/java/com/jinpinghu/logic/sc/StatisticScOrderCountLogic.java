package com.jinpinghu.logic.sc;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbScOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.StatisticScOrderCountParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticScOrderCountLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticScOrderCountParam myParam = (StatisticScOrderCountParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		
		TbScOrderDao tbScOrderDao = new TbScOrderDao(em);
		Map<String, Object> list = tbScOrderDao.statisticScOrderCount(enterpriseId);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
