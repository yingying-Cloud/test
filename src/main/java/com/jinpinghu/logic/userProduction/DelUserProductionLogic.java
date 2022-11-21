package com.jinpinghu.logic.userProduction;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterpriseUserProductionInfo;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.userProduction.param.DelUserProductionParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelUserProductionLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelUserProductionParam myParam = (DelUserProductionParam)logicParam;
		Integer id = myParam.getId();
		TbEnterpriseUserProductionInfoDao userProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		TbEnterpriseUserProductionInfo userProductionInfo = userProductionInfoDao.getById(id);
		if(userProductionInfo!=null) {
			userProductionInfoDao.delete(userProductionInfo);
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
