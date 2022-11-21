package com.jinpinghu.logic.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.ListToolOrderByBuyEnterpriseIdParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListToolOrderByBuyEnterpriseIdLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ListToolOrderByBuyEnterpriseIdParam myParam = (ListToolOrderByBuyEnterpriseIdParam)logicParam;
		Integer buyEnterpriseId = myParam.getBuyEnterpriseId();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		String toolName = myParam.getToolName();
		String productionUnit = myParam.getProductionUnit();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String enterpriseName = myParam.getEnterpriseName();
		String orderNumber = myParam.getOrderNumber();
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		
		Integer maxCount = toolOrderDao.listToolOrderByBuyEnterpriseIdCount(buyEnterpriseId, toolName, productionUnit, startTime, endTime,enterpriseName,orderNumber);
		
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
		
		resultList = toolOrderDao.listToolOrderByBuyEnterpriseId(buyEnterpriseId, toolName, productionUnit, startTime, endTime, nowPage, pageCount,enterpriseName,orderNumber);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
