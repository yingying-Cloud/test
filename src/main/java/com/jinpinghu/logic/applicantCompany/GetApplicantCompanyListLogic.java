package com.jinpinghu.logic.applicantCompany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbApplicantCompanyDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.applicantCompany.param.GetApplicantCompanyListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetApplicantCompanyListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetApplicantCompanyListParam myParam = (GetApplicantCompanyListParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		Integer status = myParam.getStatus();
		String name = myParam.getName();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		
		TbApplicantCompanyDao applicantCompanyDao = new TbApplicantCompanyDao(em);
		
		Integer maxCount = applicantCompanyDao.getApplicantCompanyListCount(name, enterpriseId, status);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		if(maxCount==null||maxCount==0){
			res.add("allCounts",0);
			res.add("maxPage",0);
			res.add("result", resultList);
			res.add("status", 1);
			res.add("msg", "无记录");
			return true;
		}
		int maxPage = 1;
		if(pageCount != null) {
			maxPage = maxCount/pageCount;
			if(maxCount%pageCount!=0){
				maxPage++;
			}
			if (pageCount * nowPage >= (maxCount + pageCount) && maxPage != 0) {
				nowPage = maxPage;
				res.add("allCounts",maxCount);
				res.add("maxPage",maxPage);
				res.add("result", resultList);
				res.add("status", 1);
				res.add("msg", "该页无记录");
				return true;
			}else if(maxPage == 0){
				nowPage = 1;
			}
		}
		
		resultList = applicantCompanyDao.getApplicantCompanyList(name, enterpriseId, status, nowPage, pageCount);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
