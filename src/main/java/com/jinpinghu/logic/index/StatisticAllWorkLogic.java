package com.jinpinghu.logic.index;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.IndexDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.index.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticAllWorkLogic extends  BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam = (SimpleParam)logicParam;
		IndexDao indexDao = new IndexDao(em);
		Map<String, Object> list = indexDao.statisticAllWork();
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
