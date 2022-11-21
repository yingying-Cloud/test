package com.jinpinghu.logic.order;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.DetailToBOrderInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DetailToBOrderInfoLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DetailToBOrderInfoParam myParam = (DetailToBOrderInfoParam)logicParam;
		Integer id = myParam.getId();
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbShoppingCarDao carDao = new TbShoppingCarDao(em);
		
		
		Map<String, Object> order = toolOrderDao.getOrderInfoByCarId(id);
		
		if (order != null) {
			TbEnterprise buyEnterprise = enterpriseDao.findByToolOrderId((Integer)order.get("id"));
			if(buyEnterprise!=null) {
				order.put("buyEnterpriseName",buyEnterprise.getName() );
			}
			
			Map<String, Object> orderInfo = carDao.getCarInfoById(id);
			BigDecimal carTotalPrice = BigDecimal.ZERO;
			if (orderInfo != null) {
				BigDecimal price = BigDecimal.ZERO;
				BigDecimal num = BigDecimal.ZERO;
				try {
					price = new BigDecimal(orderInfo.get("price").toString());
					num = new BigDecimal(orderInfo.get("num").toString());
				} catch (Exception e) {
					// TODO: handle exception
				}
				carTotalPrice = price.multiply(num);
			}
			order.put("price", carTotalPrice);
			order.put("orderInfo", orderInfo);
		}
		res.add("msg", "查询成功");
		res.add("status", 1).add("result", order);
		return true;
	}

}
