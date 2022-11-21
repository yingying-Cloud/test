package com.jinpinghu.logic.cart;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbCart;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbCartDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateToolNumCartLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam myParam = (AddToCartParam)logicParam;
		Integer cartId = StringUtil.isNullOrEmpty(myParam.getCartId()) ? null : Integer.valueOf(myParam.getCartId());
		BigDecimal toolNum = StringUtil.isNullOrEmpty(myParam.getToolNum())?null:new BigDecimal(myParam.getToolNum());
		
		TbCartDao cartDao = new TbCartDao(em);
		TbToolDao tbToolDao = new TbToolDao(em);
		
		TbCart cart = cartDao.findById(cartId);
		Integer toolId = cart.getToolId();
		TbTool tool = tbToolDao.findById(toolId);
		
		
		if(toolNum.compareTo( new BigDecimal( tool.getNumber() ) ) == 1){//判断购物车总数量是否大于库存  *传入的toolNum是最终数量
			res.add("status", 0);
			res.add("msg", "超出商品库存，请调整加购数量！");
			return false;
		}
												
		cart.setCartNum(toolNum.toString());
		if(toolNum.compareTo(BigDecimal.ZERO) == 0)						//判断购物车商品数量为0时，删除该记录
			cart.setStatus(2);
		cartDao.update(cart);
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
