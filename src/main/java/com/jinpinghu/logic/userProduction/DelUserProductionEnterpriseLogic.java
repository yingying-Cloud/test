package com.jinpinghu.logic.userProduction;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.DelEnterpriseParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelUserProductionEnterpriseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelEnterpriseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelEnterpriseParam myParam=(DelEnterpriseParam)DelEnterpriseParam;
		String id = myParam.getId();
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterprise enterprise = null;
		if(!StringUtils.isNullOrEmpty(id)) {
			enterprise=enterpriseDao.findById(Integer.valueOf(id));
			TbEnterpriseUserProductionInfoDao userProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
			userProductionInfoDao.delUser(enterprise.getId());
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
