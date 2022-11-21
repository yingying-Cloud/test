package com.jinpinghu.logic.applicantCompany;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbApplicantCompany;
import com.jinpinghu.db.dao.TbApplicantCompanyDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.applicantCompany.param.DelApplicantCompanyParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelApplicantCompanyLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DelApplicantCompanyParam myParam = (DelApplicantCompanyParam)logicParam;
		Integer id = myParam.getId();
		
		TbApplicantCompanyDao applicantCompanyDao = new TbApplicantCompanyDao(em);
		
		TbApplicantCompany applicantCompany = applicantCompanyDao.findById(id);
		
		if (applicantCompany != null) {
			applicantCompany.setDelFlag(1);
			applicantCompanyDao.update(applicantCompany);
			
			applicantCompanyDao.delApplicantCompanyCredentials(id);
			applicantCompanyDao.delApplicantCompanyProduct(id);
			applicantCompanyDao.delApplicantCompanyTrademark(id);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
