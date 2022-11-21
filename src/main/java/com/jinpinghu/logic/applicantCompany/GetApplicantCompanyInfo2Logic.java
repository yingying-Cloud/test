package com.jinpinghu.logic.applicantCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbApplicantCompanyDao;
import com.jinpinghu.db.dao.TbApplicantCompanyTrademarkDao;
import com.jinpinghu.db.dao.TbResApplicantCompanyFileDao;
import com.jinpinghu.db.dao.TbResApplicantCompanyTrademarkFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.applicantCompany.param.GetApplicantCompanyInfo2Param;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetApplicantCompanyInfo2Logic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetApplicantCompanyInfo2Param myParam = (GetApplicantCompanyInfo2Param)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		
		TbApplicantCompanyDao applicantCompanyDao = new TbApplicantCompanyDao(em);
		TbResApplicantCompanyFileDao resApplicantCompanyFileDao = new TbResApplicantCompanyFileDao(em);
		TbApplicantCompanyTrademarkDao applicantCompanyTrademarkDao = new TbApplicantCompanyTrademarkDao(em);
		TbResApplicantCompanyTrademarkFileDao resApplicantCompanyTrademarkFileDao = new TbResApplicantCompanyTrademarkFileDao(em);
		
		Map<String, Object> applicantCompany = applicantCompanyDao.getApplicantCompanyInfoByEnterpriseId(enterpriseId);
		
		if(applicantCompany != null) {
			List<Map<String,Object>> applicantCompanyProducts = applicantCompanyDao.findApplicantCompanyProductByApplicantCompanyId((Integer)applicantCompany.get("id"));
			List<Map<String,Object>> applicantCompanyCredentials = applicantCompanyDao.findApplicantCompanyCredentialsByApplicantCompanyId((Integer)applicantCompany.get("id"));
			applicantCompany.put("applicantCompanyProducts", applicantCompanyProducts == null ? new ArrayList<>() : applicantCompanyProducts);
			applicantCompany.put("applicantCompanyCredentials", applicantCompanyCredentials == null ? new ArrayList<>() : applicantCompanyCredentials);
			List<Map<String, Object>> files = resApplicantCompanyFileDao.getApplicantCompanyFileList((Integer)applicantCompany.get("id"));
			applicantCompany.put("files", files == null ? new ArrayList<>() : files);
			List<Map<String, Object>> applicantCompanyTrademarks = applicantCompanyTrademarkDao.getApplicantCompanyTrademarkList((Integer)applicantCompany.get("id"));
			if (applicantCompanyTrademarks != null) {
				for (Map<String, Object> map : applicantCompanyTrademarks) {
					List<Map<String, Object>> trademarkFiles = resApplicantCompanyTrademarkFileDao.getApplicantCompanyTrademarkFileList((Integer)map.get("id"));
					map.put("files", trademarkFiles == null ? new ArrayList<>() : trademarkFiles);
				}
			}
			applicantCompany.put("applicantCompanyTrademarks", applicantCompanyTrademarks == null ? new ArrayList<>() : applicantCompanyTrademarks);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", applicantCompany);
		return true;
	}

}
