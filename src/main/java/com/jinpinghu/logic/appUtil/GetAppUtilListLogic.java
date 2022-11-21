package com.jinpinghu.logic.appUtil;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.AppUtilDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.appUtil.param.GetAppUtilListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAppUtilListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAppUtilListParam myParam = (GetAppUtilListParam)logicParam;
		AppUtilDao appUtilDao = new AppUtilDao(em);
		List<Map<String,Object>> mapList = appUtilDao.getMapList();
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", mapList);
		return true;
	}

}
