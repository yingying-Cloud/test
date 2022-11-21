package com.jinpinghu.logic.agriculturalAdvice;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbAgriculturalAdvice;
import com.jinpinghu.db.dao.TbAgriculturalAdviceDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalAdvice.param.GetAgriculturalAdviceInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelAgriculturalAdviceLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetAgriculturalAdviceInfoParam myParam = (GetAgriculturalAdviceInfoParam)logicParam;
		Integer id = myParam.getId();
		TbAgriculturalAdviceDao tbagriculturalAdviceDao = new TbAgriculturalAdviceDao(em);
		TbAgriculturalAdvice agriculturalAdvice = tbagriculturalAdviceDao.findById(id);
		if(agriculturalAdvice!=null) {
			tbagriculturalAdviceDao.delete(agriculturalAdvice);
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
