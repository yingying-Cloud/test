package com.jinpinghu.logic.business;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbBusinessDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.arableLand.param.AddOrUpdateArableLandParam;
import com.jinpinghu.logic.business.param.GetBusinessInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetBusinessInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetBusinessInfoParam myParam = (GetBusinessInfoParam)logicParam;
		Integer id = myParam.getId();
		TbBusinessDao tbBusinessDao = new TbBusinessDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Map<String, Object> result = tbBusinessDao.findMapById(id);
		List<Map<String, Object>> tfs =tfDao.getListByBusinessId(id);
		result.put("file", tfs);
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
