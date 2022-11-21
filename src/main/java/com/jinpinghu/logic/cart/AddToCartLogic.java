package com.jinpinghu.logic.cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbCart;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbCartDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cart.param.AddToCartParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddToCartLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToCartParam myParam = (AddToCartParam)logicParam;
		Integer toolId = myParam.getToolId();
		BigDecimal toolNum = StringUtil.isNullOrEmpty(myParam.getToolNum())?null:new BigDecimal(myParam.getToolNum());
		String userId = myParam.getUserId();
		TbUserDao uDao = new TbUserDao(em);
		TbUser user = uDao.checkLogin2(userId);
		//判断用户是否已实名认证
		if(user != null){
			TbLinkOrderInfoDao linkDao = new TbLinkOrderInfoDao(em);
			TbLinkOrderInfo info = linkDao.findByIdcard(user.getExpertIdcard());
			if(info == null){
				res.add("status", 0);
				res.add("msg", "您还没有在农资店实名认证！");
				return false;
			}
		}
		
		TbCart cart = new TbCart();
		TbCartDao cartDao = new TbCartDao(em);
		TbToolDao tbToolDao = new TbToolDao(em);
		TbTool tool = tbToolDao.findById(toolId);
		
		List<TbCart> cartList = cartDao.findByUserIdAndToolId(userId, toolId);
		
		//获取购物车中商品已有数量
		BigDecimal oldToolNum = BigDecimal.ZERO;							 
		if(cartList != null && cartList.get(0) != null)
			oldToolNum = new BigDecimal(cartList.get(0).getCartNum());
		
		//购物车总数量大于库存
		if(oldToolNum.add(toolNum).compareTo( new BigDecimal( tool.getNumber() ) ) == 1){
			res.add("status", 0);
			res.add("msg", "超出商品库存，请调整加购数量！");
			return false;
		}
	
		//判断购物车中是否已存在该记录
		if(cartList == null || cartList.get(0) == null){	//不存在	
			cart.setUserid(myParam.getUserId());
			cart.setToolId(toolId);
			cart.setCartNum(toolNum.toString());
			cart.setStatus(0);
			cart.setInputTime(new Date());
			cart.setUpdateTime(new Date());
			cartDao.save(cart);
		}else{												//已存在
			cart = cartList.get(0);
			cart.setCartNum(new BigDecimal(cart.getCartNum()).add(toolNum).toString());
			cartDao.update(cart);
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
