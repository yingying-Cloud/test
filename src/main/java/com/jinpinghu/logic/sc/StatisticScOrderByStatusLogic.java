package com.jinpinghu.logic.sc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbScOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.StatisticScOrderByStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticScOrderByStatusLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticScOrderByStatusParam myParam = (StatisticScOrderByStatusParam)logicParam;
		String type = myParam.getType();
		Integer enterpriseId = myParam.getEnterpriseId();
		
		List<Map<String, Object>> resultList = new ArrayList<>(0);
		TbScOrderDao scOrderDao = new TbScOrderDao(em);
		if ("1".equals(type)) {
			resultList = scOrderDao.groupByStatus(enterpriseId, null);
		}else if ("2".equals(type)) {
			resultList = scOrderDao.groupByStatus(null, enterpriseId);
		}
		
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
