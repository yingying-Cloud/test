package com.jinpinghu.logic.enterprise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbResEnterpriseZoneDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterPriseZoneListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEnterPriseZoneListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetEnterPriseZoneListParam myParam = (GetEnterPriseZoneListParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		String type = myParam.getType();
		String name = myParam.getName();
		
		TbResEnterpriseZoneDao resEnterpriseZoneDao = new TbResEnterpriseZoneDao(em);
		
		Integer maxCount = resEnterpriseZoneDao.getEnterpriseZoneListCount(enterpriseId, type,name);
		
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
		
		resultList = resEnterpriseZoneDao.getEnterpriseZoneList(enterpriseId, type,name, nowPage, pageCount);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
