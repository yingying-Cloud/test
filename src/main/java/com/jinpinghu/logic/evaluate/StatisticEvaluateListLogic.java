package com.jinpinghu.logic.evaluate;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbEvaluateDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.evaluate.param.GetEvaluateListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticEvaluateListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetEvaluateListParam myParam = (GetEvaluateListParam)logicParam;
		String type = myParam.getType();
		String searchId = myParam.getSearchId();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String resId = myParam.getResId();
	
		TbEvaluateDao tbEvaluateDao = new TbEvaluateDao(em);
		
		List<Map<String, Object>> resultList = tbEvaluateDao.statisticFindByAll(resId, startTime, endTime, searchId, type);

		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
