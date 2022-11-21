package com.jinpinghu.logic.serverTime;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbPlantServiceServerTime;
import com.jinpinghu.db.dao.TbPlantServiceServerTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.serverTime.param.AddProtectionServerTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddServiceServerTimeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddProtectionServerTimeParam myParam = (AddProtectionServerTimeParam)logicParam;
		Integer serviceId = myParam.getProtectionId();
		String serverTime = myParam.getServerTime();
		
		TbPlantServiceServerTimeDao serverTimeDao = new TbPlantServiceServerTimeDao(em);
		
		TbPlantServiceServerTime protectionServerTime = new TbPlantServiceServerTime();
		protectionServerTime.setServiceId(serviceId);
		protectionServerTime.setServerTime(DateTimeUtil.formatTime(serverTime));
		serverTimeDao.save(protectionServerTime);
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		
		return true;
	}

}
