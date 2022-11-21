package com.jinpinghu.logic.sc;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbSupplyRelease;
import com.jinpinghu.db.dao.TbSupplyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.UpdateSupplyReleaseStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateSupplyReleaseStatusLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateSupplyReleaseStatusParam myParam = (UpdateSupplyReleaseStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		TbSupplyReleaseDao supplyReleaseDao = new TbSupplyReleaseDao(em);
		
		TbSupplyRelease supplyRelease = supplyReleaseDao.findById(id);
		
		if (supplyRelease != null) {
			supplyRelease.setStatus(status);
			supplyReleaseDao.update(supplyRelease);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateSupplyReleaseStatusParam myParam = (UpdateSupplyReleaseStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		if (status == null || id == null) {
			res.add("status", -1).add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}

}
