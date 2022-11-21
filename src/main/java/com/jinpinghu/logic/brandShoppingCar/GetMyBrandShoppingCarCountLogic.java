package com.jinpinghu.logic.brandShoppingCar;

import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandShoppingCar.param.GetMyBrandShoppingCarCountParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

public class GetMyBrandShoppingCarCountLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMyBrandShoppingCarCountParam myParam = (GetMyBrandShoppingCarCountParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;		
		TbBrandShoppingCarDao tbBrandShoppingCarDao = new TbBrandShoppingCarDao(em);
		
		String count = tbBrandShoppingCarDao.findByEnterpriseIdCount(enterpriseId);
		
		res.add("allCounts", count==null?0:count);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	/*@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetMyBrandShoppingCarCountParam myParam = (GetMyBrandShoppingCarCountParam)logicParam;
		String shopId = myParam.getShopId();
		if(StringUtil.isNullOrEmpty(shopId)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}*/
	
}
