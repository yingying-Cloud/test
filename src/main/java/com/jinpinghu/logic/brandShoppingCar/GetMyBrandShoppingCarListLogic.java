package com.jinpinghu.logic.brandShoppingCar;

import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandShoppingCar.param.GetMyBrandShoppingCarListParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class GetMyBrandShoppingCarListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMyBrandShoppingCarListParam myParam = (GetMyBrandShoppingCarListParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		
		TbBrandShoppingCarDao tbBrandShoppingCarDao = new TbBrandShoppingCarDao(em);
		Integer count = tbBrandShoppingCarDao.findByCarCount(enterpriseId);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = tbBrandShoppingCarDao.findByEnterpriseId(enterpriseId);
		
		res.add("allCounts", count);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
}
