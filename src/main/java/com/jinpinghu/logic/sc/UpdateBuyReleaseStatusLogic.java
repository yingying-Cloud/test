package com.jinpinghu.logic.sc;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbBuyRelease;
import com.jinpinghu.db.dao.TbBuyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.UpdateBuyReleaseStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateBuyReleaseStatusLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateBuyReleaseStatusParam myParam = (UpdateBuyReleaseStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		TbBuyReleaseDao buyReleaseDao = new TbBuyReleaseDao(em);
		
		TbBuyRelease buyRelease = buyReleaseDao.findById(id);
		
		if (buyRelease != null) {
			buyRelease.setStatus(status);
			buyReleaseDao.update(buyRelease);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateBuyReleaseStatusParam myParam = (UpdateBuyReleaseStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		if (status == null || id == null) {
			res.add("status", -1).add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}

}
