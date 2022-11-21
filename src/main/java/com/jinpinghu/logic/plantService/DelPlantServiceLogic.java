package com.jinpinghu.logic.plantService;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbPlantService;
import com.jinpinghu.db.dao.TbPlantServiceDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantService.param.DelPlantServiceParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelPlantServiceLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DelPlantServiceParam myParam = (DelPlantServiceParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		if(enterprise == null) {
			res.add("status", -1).add("msg", "您不是企业用户");
			return false;
		}
		TbPlantServiceDao plantServiceDao = new TbPlantServiceDao(em);
		
		TbPlantService plantService = plantServiceDao.findById(id);
		
		if(!plantService.getEnterpriseId().equals(enterprise.getId())) {
			res.add("status", 1).add("msg", "该劳务方案不属于您企业，不能删除");
			return false;
		}else {
			plantService.setDelFlag(1);
			plantServiceDao.update(plantService);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
