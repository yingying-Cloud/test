package com.jinpinghu.logic.agriculturalAdvice;

import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbAgriculturalAdviceDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalAdvice.param.GetAgriculturalAdviceInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAgriculturalAdviceInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetAgriculturalAdviceInfoParam myParam = (GetAgriculturalAdviceInfoParam)logicParam;
		Integer id = myParam.getId();
		TbAgriculturalAdviceDao tbagriculturalAdviceDao = new TbAgriculturalAdviceDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Map<String, Object> result = tbagriculturalAdviceDao.findMapById(id);
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
