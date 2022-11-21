package com.jinpinghu.logic.cashRegister;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.bean.TbCashRegister;
import com.jinpinghu.db.dao.TbCashRegisterDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cashRegister.param.DelCashRegisterParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelCashRegisterLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DelCashRegisterParam myParam = (DelCashRegisterParam)logicParam;
		String ids = myParam.getIds();
		
		TbCashRegisterDao cashRegisterDao = new TbCashRegisterDao(em);
		if (!StringUtils.isEmpty(ids)) {
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				Integer id = Integer.valueOf(idArray[i]);
				TbCashRegister cashRegister = cashRegisterDao.findById(id);
				if (cashRegister != null) {
					cashRegister.setDelFlag(1);
					cashRegisterDao.update(cashRegister);
				}
			}
			
		}
		
		
		res.add("msg", "删除成功");
		res.add("status", 1);
		return true;
	}
}
