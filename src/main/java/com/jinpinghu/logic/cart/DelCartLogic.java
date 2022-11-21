package com.jinpinghu.logic.cart;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbCartDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelCartLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam myParam = (AddToCartParam)logicParam;
		String cartIds = StringUtil.isNullOrEmpty(myParam.getCartIds())? null : myParam.getCartIds();
		
		if(cartIds != null){
			List<String> cartIdList = Arrays.asList( cartIds.split(",") );
			TbCartDao cartDao = new TbCartDao(em);
			Integer result = cartDao.delCart(cartIdList);
			res.add("result", result);
		}
		
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
