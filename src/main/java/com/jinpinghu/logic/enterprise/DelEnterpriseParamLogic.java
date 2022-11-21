package com.jinpinghu.logic.enterprise;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.DelEnterpriseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelEnterpriseParamLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelEnterpriseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelEnterpriseParam myParam=(DelEnterpriseParam)DelEnterpriseParam;
		List<String> ids=Arrays.asList(myParam.getId().split(","));
		
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbResUserEnterpriseDao tbResUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbEnterpriseUserProductionInfoDao userProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		
		for(String id:ids){
			TbEnterprise tbEnterprise=enterpriseDao.findById(Integer.valueOf(id));
			tbEnterprise.setDelFlag(1);
			enterpriseDao.update(tbEnterprise);
			tbResUserEnterpriseDao.delByEnterpriseId(Integer.valueOf(id));
			userProductionInfoDao.delUser(tbEnterprise.getId());
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
