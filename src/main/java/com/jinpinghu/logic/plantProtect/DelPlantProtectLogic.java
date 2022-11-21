package com.jinpinghu.logic.plantProtect;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbPlantProtection;
import com.jinpinghu.db.dao.TbPlantProtectionDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantProtect.param.DelPlantProtectParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelPlantProtectLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DelPlantProtectParam myParam = (DelPlantProtectParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		if(enterprise == null) {
			res.add("status", -1).add("msg", "您不是企业用户");
			return false;
		}
		TbPlantProtectionDao plantProtectionDao = new TbPlantProtectionDao(em);
		
		TbPlantProtection plantProtection = plantProtectionDao.findById(id);
		
		if(plantProtection.getEnterpriseId() != enterprise.getId()) {
			res.add("status", 1).add("msg", "该植保方案不属于您企业，不能删除");
			return false;
		}else {
			plantProtection.setDelFlag(1);
			plantProtectionDao.update(plantProtection);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
