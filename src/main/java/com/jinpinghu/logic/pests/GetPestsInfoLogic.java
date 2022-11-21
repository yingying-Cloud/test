package com.jinpinghu.logic.pests;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPestsDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.pests.param.GetPestsInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPestsInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPestsInfoParam myParam = (GetPestsInfoParam)logicParam;
		Integer id = myParam.getId();
		TbPestsDao tbPestsDao = new TbPestsDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Map<String, Object> result = tbPestsDao.findMapById(id);
		List<Map<String, Object>> tfs =tfDao.getListByPestsId(id);
		result.put("file", tfs);
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
