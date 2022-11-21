package com.jinpinghu.logic.address;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbReceivingAddress;
import com.jinpinghu.db.dao.TbAddressDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.address.param.AddressParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelAddressLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddressParam param = (AddressParam)logicParam;
		Integer id = param.getId();
		
		TbAddressDao dao = new TbAddressDao(em);
		TbReceivingAddress tbReceivingAddress = dao.findById(id);
		tbReceivingAddress.setDelFlag(1);
		dao.update(tbReceivingAddress);
		
		res.add("status", 1);
		res.add("msg", "成功");	
		return true;
	}

}
