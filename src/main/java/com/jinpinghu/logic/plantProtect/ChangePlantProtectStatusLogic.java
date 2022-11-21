package com.jinpinghu.logic.plantProtect;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPlantProtection;
import com.jinpinghu.db.dao.TbPlantProtectionDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantProtect.param.ChangePlantProtectStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangePlantProtectStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ChangePlantProtectStatusParam myParam = (ChangePlantProtectStatusParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		String status = myParam.getStatus();
		
		TbPlantProtectionDao plantProtectionDao = new TbPlantProtectionDao(em);
		TbPlantProtection plantProtection = plantProtectionDao.findById(id);
		plantProtection.setStatus(status);
		plantProtectionDao.update(plantProtection);
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
