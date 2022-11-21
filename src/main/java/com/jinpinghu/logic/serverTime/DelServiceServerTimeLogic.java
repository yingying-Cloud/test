package com.jinpinghu.logic.serverTime;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbPlantProtectionServerTime;
import com.jinpinghu.db.dao.TbPlantProtectionServerTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.serverTime.param.AddProtectionServerTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelServiceServerTimeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddProtectionServerTimeParam myParam = (AddProtectionServerTimeParam)logicParam;
		Integer id = myParam.getId();
		
		TbPlantProtectionServerTimeDao serverTimeDao = new TbPlantProtectionServerTimeDao(em);
		TbPlantProtectionServerTime serverTime = serverTimeDao.findById(id);
		if(serverTime!=null) {
			serverTimeDao.delete(serverTime);
		}
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		
		return true;
	}

}
