package com.jinpinghu.logic.cashRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbAdvertisementDao;
import com.jinpinghu.db.dao.TbCashRegisterDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.advertisement.param.ListAdvertisementParam;
import com.jinpinghu.logic.cashRegister.param.ListCashRegisterParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListCashRegisterLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ListCashRegisterParam myParam = (ListCashRegisterParam)logicParam;
		String name = myParam.getName();
		String canshRegisterId = myParam.getCashRegisterId();
		String baiduAifaceSn = myParam.getBaiduAifaceSn();
		Integer nowPage = myParam.getNowPage();
		Integer pageCount = myParam.getPageCount();
		
		TbCashRegisterDao cashRegisterDao = new TbCashRegisterDao(em);
		Integer maxCount = cashRegisterDao.getCashRegisterCount(name,canshRegisterId,baiduAifaceSn);
		
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
		
		resultList = cashRegisterDao.getCashRegisterList(name,canshRegisterId,baiduAifaceSn, nowPage, pageCount);
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("msg", "成功！");
		res.add("status", 1);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
}
