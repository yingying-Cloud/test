package com.jinpinghu.logic.cart;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolOrderBookDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetOrderListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam param = (AddToCartParam)logicParam;
		String name = param.getName();
		Integer enterpriseId = param.getEnterpriseId();
		Integer nowPage = param.getNowPage();
		Integer pageCount = param.getPageCount();
		Integer status = param.getStatus();
		
		TbToolOrderBookDao dao = new TbToolOrderBookDao(em);
		List<Map<String, Object>> result = null;
		
		Integer allCounts = dao.getOrderBookCount(param.getUserId(), enterpriseId, name, status);
		if(allCounts > 0){
			result = dao.getOrderList(param.getUserId(), enterpriseId, name, status, nowPage, pageCount);
			for(Map<String, Object> map :result){
				List<Map<String, Object>> tool = dao.getShopCarBookListByOrderId(Integer.valueOf(map.get("id").toString()));
				map.put("tool", tool);
			}
			
		}
		
					
		res.add("allCounts", allCounts);
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
