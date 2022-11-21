package com.jinpinghu.logic.logistics;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbLogisticsDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.logistics.param.GetLogisticsInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLogisticsInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetLogisticsInfoParam myParam = (GetLogisticsInfoParam)logicParam;
		Integer id = myParam.getId();
		TbLogisticsDao tbBusinessDao = new TbLogisticsDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Map<String, Object> result = tbBusinessDao.findMapById(id);
		List<Map<String, Object>> tfs =tfDao.getListByLogisticsId(id);
		result.put("file", tfs);
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
