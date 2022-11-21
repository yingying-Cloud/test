package com.jinpinghu.logic.shoppingCar;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.shoppingCar.param.GetMyShoppingCarListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMyShoppingCarListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMyShoppingCarListParam myParam = (GetMyShoppingCarListParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		
		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
		Integer count = tbShoppingCarDao.findByCarCount(enterpriseId);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = tbShoppingCarDao.findByEnterpriseId(enterpriseId);
		
		res.add("allCounts", count);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
}
