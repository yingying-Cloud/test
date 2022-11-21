package com.jinpinghu.logic.sellShoppingCar;

import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellShoppingCar.param.GetMySellShoppingCarCountParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

public class GetMySellShoppingCarCountLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMySellShoppingCarCountParam myParam = (GetMySellShoppingCarCountParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;		
		TbSellShoppingCarDao tbSellShoppingCarDao = new TbSellShoppingCarDao(em);
		
		String count = tbSellShoppingCarDao.findByEnterpriseIdCount(enterpriseId);
		
		res.add("allCounts", count==null?0:count);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	/*@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetMySellShoppingCarCountParam myParam = (GetMySellShoppingCarCountParam)logicParam;
		String shopId = myParam.getShopId();
		if(StringUtil.isNullOrEmpty(shopId)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}*/
	
}
