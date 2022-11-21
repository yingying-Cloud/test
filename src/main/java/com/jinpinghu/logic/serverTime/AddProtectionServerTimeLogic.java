package com.jinpinghu.logic.serverTime;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbPlantProtectionServerTime;
import com.jinpinghu.db.dao.TbPlantProtectionServerTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.serverTime.param.AddProtectionServerTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddProtectionServerTimeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddProtectionServerTimeParam myParam = (AddProtectionServerTimeParam)logicParam;
		Integer protectionId = myParam.getProtectionId();
		String serverTime = myParam.getServerTime();
		
		TbPlantProtectionServerTimeDao serverTimeDao = new TbPlantProtectionServerTimeDao(em);
		
		TbPlantProtectionServerTime protectionServerTime = new TbPlantProtectionServerTime();
		protectionServerTime.setProtectionId(protectionId);
		protectionServerTime.setServerTime(DateTimeUtil.formatTime(serverTime));
		serverTimeDao.save(protectionServerTime);
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		
		return true;
	}

}
