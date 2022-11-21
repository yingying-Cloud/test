package com.jinpinghu.logic.nhl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbNhlDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListNhlLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		TbNhlDao nhlDao = new TbNhlDao(em);
		
		List<Map<String, Object>> resultList = nhlDao.listNhl();
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return true;
	}

}
