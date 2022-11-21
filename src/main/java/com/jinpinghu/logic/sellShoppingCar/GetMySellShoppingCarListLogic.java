package com.jinpinghu.logic.sellShoppingCar;

import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellShoppingCar.param.GetMySellShoppingCarListParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class GetMySellShoppingCarListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMySellShoppingCarListParam myParam = (GetMySellShoppingCarListParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		
		TbSellShoppingCarDao tbSellShoppingCarDao = new TbSellShoppingCarDao(em);
		Integer count = tbSellShoppingCarDao.findByCarCount(enterpriseId);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = tbSellShoppingCarDao.findByEnterpriseId(enterpriseId);
		
		res.add("allCounts", count);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
}
