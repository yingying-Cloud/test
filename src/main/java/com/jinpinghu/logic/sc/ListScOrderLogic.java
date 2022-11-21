package com.jinpinghu.logic.sc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbScOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.ListScOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListScOrderLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ListScOrderParam myParam = (ListScOrderParam)logicParam;
		String orderNumber = myParam.getOrderNumber();
		String productName = myParam.getProductName();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		String type = myParam.getType();
		Integer enterpriseId = myParam.getEnterpriseId();
		String status = myParam.getStatus();
		
		TbScOrderDao scOrderDao = new TbScOrderDao(em);
		
		Integer maxCount = 0;
		if ("1".equals(type)) {//供应订单
			maxCount = scOrderDao.listScOrderCount(orderNumber, productName, startTime, endTime, enterpriseId, null,null,status);
		}else if ("2".equals(type)) {//购买订单
			maxCount = scOrderDao.listScOrderCount(orderNumber, productName, startTime, endTime, null, enterpriseId,null,status);
		}
		
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
		
		if ("1".equals(type)) {
			resultList = scOrderDao.listScOrder(orderNumber, productName, startTime, endTime, enterpriseId, null,null, nowPage, pageCount,status);
		}else if ("2".equals(type)) {
			resultList = scOrderDao.listScOrder(orderNumber, productName, startTime, endTime, null, enterpriseId, null, nowPage, pageCount,status);
		}
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
