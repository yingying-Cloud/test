package com.jinpinghu.logic.enterpriseCertificate;

import com.jinpinghu.db.bean.TbEnterpriseCertificate;
import com.jinpinghu.db.dao.TbEnterpriseCertificateDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterpriseCertificate.param.DelEnterpriseCertificateParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class DelEnterpriseCertificateParamLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelEnterpriseCertificateParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelEnterpriseCertificateParam myParam=(DelEnterpriseCertificateParam)DelEnterpriseCertificateParam;
		List<String> ids=Arrays.asList(myParam.getId().split(","));
		TbEnterpriseCertificateDao enterpriseCertificateDao = new TbEnterpriseCertificateDao(em);
		if(ids!=null) {
			for (String id : ids) {
				TbEnterpriseCertificate tbEnterpriseCertificate = enterpriseCertificateDao.findById(Integer.valueOf(id));
				tbEnterpriseCertificate.setDelFlag(1);
				enterpriseCertificateDao.update(tbEnterpriseCertificate);
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
