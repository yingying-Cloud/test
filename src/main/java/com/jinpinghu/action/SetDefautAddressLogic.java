package com.jinpinghu.action;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbAddressDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.address.param.AddressParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class SetDefautAddressLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddressParam param = (AddressParam)logicParam;
		Integer id = param.getId();
		String userId = param.getUserId();
		

		TbAddressDao dao = new TbAddressDao(em);
		
		dao.setDefaultAddress(userId, id);
		
		
		res.add("status", 1);
		res.add("msg", "成功");	
		return true;
	}

}
