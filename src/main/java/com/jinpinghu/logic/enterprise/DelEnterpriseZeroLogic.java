package com.jinpinghu.logic.enterprise;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.DelEnterpriseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelEnterpriseZeroLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelEnterpriseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelEnterpriseParam myParam=(DelEnterpriseParam)DelEnterpriseParam;
		List<String> ids=Arrays.asList(myParam.getId().split(","));
		TbEnterpriseZeroDao enterpriseDao = new TbEnterpriseZeroDao(em);
		for(String id:ids){
			TbEnterpriseZero tbEnterprise=enterpriseDao.findById(Integer.valueOf(id));
			tbEnterprise.setDelFlag(1);
			enterpriseDao.update(tbEnterprise);
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
