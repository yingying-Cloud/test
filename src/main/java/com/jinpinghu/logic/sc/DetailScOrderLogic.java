package com.jinpinghu.logic.sc;

import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbScOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.DetailScOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DetailScOrderLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DetailScOrderParam myParam = (DetailScOrderParam)logicParam;
		Integer id = myParam.getId();
		
		TbScOrderDao scOrderDaoDao = new TbScOrderDao(em);
		
		Map<String, Object> result = scOrderDaoDao.detailScOrder(id);
		
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
