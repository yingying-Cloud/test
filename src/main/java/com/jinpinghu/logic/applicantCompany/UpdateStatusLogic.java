package com.jinpinghu.logic.applicantCompany;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbApplicantCompany;
import com.jinpinghu.db.bean.TbApplicantCompanyCheckRecord;
import com.jinpinghu.db.dao.TbApplicantCompanyDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.applicantCompany.param.UpdateStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateStatusParam myParam = (UpdateStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		TbApplicantCompanyDao applicantCompanyDao = new TbApplicantCompanyDao(em);
		
		TbApplicantCompany applicantCompany = applicantCompanyDao.findById(id);
		
		if (applicantCompany != null) {
			applicantCompany.setStatus(status);
			applicantCompanyDao.update(applicantCompany);
			TbApplicantCompanyCheckRecord applicantCompanyCheckRecord = new TbApplicantCompanyCheckRecord();
			applicantCompanyCheckRecord.setApplicantCompanyId(id);
			applicantCompanyCheckRecord.setInputTime(new Date());
			applicantCompanyCheckRecord.setStatus(status);
			applicantCompanyDao.save(applicantCompanyCheckRecord);
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateStatusParam myParam = (UpdateStatusParam)logicParam;
		Integer id = myParam.getId();
		Integer status = myParam.getStatus();
		
		if(id == null || status == null) {
			res.add("status", -1).add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}

}
