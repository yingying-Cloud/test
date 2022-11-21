package com.jinpinghu.logic.enterpriseLoanApplication;

import com.jinpinghu.db.bean.TbEnterpriseLoanApplication;
import com.jinpinghu.db.dao.TbEnterpriseLoanApplicationDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseLoanApplication.param.DelEnterpriseLoanApplicationParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class DelEnterpriseLoanApplicationParamLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelEnterpriseLoanApplicationParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelEnterpriseLoanApplicationParam myParam=(DelEnterpriseLoanApplicationParam)DelEnterpriseLoanApplicationParam;
		List<String> ids=Arrays.asList(myParam.getId().split(","));
		TbEnterpriseLoanApplicationDao enterpriseLoanApplicationDao = new TbEnterpriseLoanApplicationDao(em);
		if(ids!=null) {
			for (String id : ids) {
				TbEnterpriseLoanApplication tbEnterpriseLoanApplication = enterpriseLoanApplicationDao.findById(Integer.valueOf(id));
				tbEnterpriseLoanApplication.setDelFlag(1);
				enterpriseLoanApplicationDao.update(tbEnterpriseLoanApplication);
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
