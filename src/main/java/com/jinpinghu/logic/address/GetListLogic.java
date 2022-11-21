package com.jinpinghu.logic.address;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import com.jinpinghu.db.dao.TbAddressDao;
import com.jinpinghu.logic.BaseZLogic;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		TbAddressDao dao = new TbAddressDao(em);
		
		Integer count = dao.getAddressListCount(logicParam.getUserId());
		List<Map<String, Object>> result = null;
		if(count != null && count > 0)
			result = dao.findAddressList(logicParam.getUserId());
		
		
		res.add("allCounts", count);
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");	
		return true;
	}

}
