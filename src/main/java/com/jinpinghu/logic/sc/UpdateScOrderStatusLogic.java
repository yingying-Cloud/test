package com.jinpinghu.logic.sc;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbScOrder;
import com.jinpinghu.db.dao.TbScOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.UpdateScOrderStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateScOrderStatusLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateScOrderStatusParam myParam = (UpdateScOrderStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		TbScOrderDao scOrderDao = new TbScOrderDao(em);
		
		TbScOrder scOrder = scOrderDao.findById(id);
		
		if (scOrder != null) {
			scOrder.setStatus(status);
			scOrderDao.update(scOrder);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateScOrderStatusParam myParam = (UpdateScOrderStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		if (status == null || id == null) {
			res.add("status", -1).add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}

}
