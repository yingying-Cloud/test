package com.jinpinghu.logic.index;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.IndexDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class CreditLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		IndexDao indexDao=new IndexDao(em);

		Object allRequest=indexDao.statisticAllLoanApplication();
		Object totalPrice=indexDao.statisticTotalLoanApplication();
		Object requestPass=indexDao.statisticPassLoanApplication();//通过数
		Object count=indexDao.statisticAllLoanApplicationCount();
		
		res.add("status", 1)
		.add("allRequest", allRequest)
		.add("totalPrice", totalPrice)
		.add("requestPass", requestPass)
		.add("count", count)
		.add("msg", "操作成功");
		
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}

	