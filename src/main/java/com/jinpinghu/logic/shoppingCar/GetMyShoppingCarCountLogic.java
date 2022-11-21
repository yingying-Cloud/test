package com.jinpinghu.logic.shoppingCar;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.shoppingCar.param.GetMyShoppingCarCountParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMyShoppingCarCountLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMyShoppingCarCountParam myParam = (GetMyShoppingCarCountParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;		
		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
		
		String count = tbShoppingCarDao.findByEnterpriseIdCount(enterpriseId);
		
		res.add("allCounts", count==null?0:count);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	/*@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetMyShoppingCarCountParam myParam = (GetMyShoppingCarCountParam)logicParam;
		String shopId = myParam.getShopId();
		if(StringUtil.isNullOrEmpty(shopId)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}*/
	
}
