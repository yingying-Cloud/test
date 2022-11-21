package com.jinpinghu.logic.cashRegister;


import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbCashRegisterDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cashRegister.param.DetailCashRegisterParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DetailCashRegisterLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DetailCashRegisterParam myParam = (DetailCashRegisterParam)logicParam;
		Integer id = myParam.getId();
		
		TbCashRegisterDao cashRegisterDao = new TbCashRegisterDao(em);
		
		Map<String, Object> resultMap = cashRegisterDao.getCashRegisterInfo(id);
		
		res.add("result", resultMap);
		res.add("msg", "成功！");
		res.add("status", 1);
		return true;
	}
}
