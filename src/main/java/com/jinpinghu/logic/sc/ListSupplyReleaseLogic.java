package com.jinpinghu.logic.sc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbSupplyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.ListSupplyReleaseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListSupplyReleaseLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ListSupplyReleaseParam myParam = (ListSupplyReleaseParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		String productName = myParam.getProductName();
		Integer type = myParam.getType();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		Integer status = myParam.getStatus();
		String dscd = myParam.getDscd();
		
		TbSupplyReleaseDao buyReleaseDao = new TbSupplyReleaseDao(em);
		
		Integer maxCount = buyReleaseDao.listSupplyReleaseCount(enterpriseId, productName, type, status,dscd);
		
		List<Map<String, Object>> resultList = new ArrayList<>(0);
		
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
		
		resultList = buyReleaseDao.listSupplyRelease(enterpriseId, productName, type, status, dscd, nowPage, pageCount);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
