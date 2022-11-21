package com.jinpinghu.logic.cart;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolOrderBookDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetOrderInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam param = (AddToCartParam)logicParam;
		Integer orderId = param.getOrderId();
		TbToolOrderBookDao dao = new TbToolOrderBookDao(em);
		Map<String, Object> result = dao.getOrderInfo(orderId);
		List<Map<String, Object>> tool = dao.getShopCarBookListByOrderId(Integer.valueOf(result.get("id").toString()));
		result.put("tool", tool);
		
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
