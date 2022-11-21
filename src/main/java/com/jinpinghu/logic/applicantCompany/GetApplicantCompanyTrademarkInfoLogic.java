package com.jinpinghu.logic.applicantCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbApplicantCompanyTrademarkDao;
import com.jinpinghu.db.dao.TbResApplicantCompanyTrademarkFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.applicantCompany.param.GetApplicantCompanyTrademarkInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetApplicantCompanyTrademarkInfoLogic extends BaseZLogic {

	public GetApplicantCompanyTrademarkInfoLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetApplicantCompanyTrademarkInfoParam myParam = (GetApplicantCompanyTrademarkInfoParam)logicParam;
		Integer id = myParam.getId();
		
		TbApplicantCompanyTrademarkDao applicantCompanyTrademarkDao = new TbApplicantCompanyTrademarkDao(em);
		TbResApplicantCompanyTrademarkFileDao resApplicantCompanyTrademarkFileDao = new TbResApplicantCompanyTrademarkFileDao(em);
		
		Map<String, Object> resultMap = applicantCompanyTrademarkDao.getInfoById(id);
		if(resultMap != null) {
			List<Map<String, Object>> trademarkFiles = resApplicantCompanyTrademarkFileDao.getApplicantCompanyTrademarkFileList((Integer)resultMap.get("id"));
			resultMap.put("files", trademarkFiles == null ? new ArrayList<>() : trademarkFiles);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("resultMap", resultMap);
		return true;
	}

}
