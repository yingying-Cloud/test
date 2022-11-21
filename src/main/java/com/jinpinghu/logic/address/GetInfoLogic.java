package com.jinpinghu.logic.address;

import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbAddressDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.address.param.AddressParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddressParam param = (AddressParam)logicParam;
		Integer id = param.getId();
		
		TbAddressDao dao = new TbAddressDao(em);
		
		Map<String, Object> result = dao.findAddressInfo(id);

		
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");	
		return true;
	}

}
