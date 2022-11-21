package com.jinpinghu.logic.log;

import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbLogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.log.param.GetLogTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLogTimeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetLogTimeParam myParam = (GetLogTimeParam)logicParam;
		TbLogDao logDao = new TbLogDao(em);
		Map<String, Object> logTime = logDao.getLogTime();
		res.add("result", logTime);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
