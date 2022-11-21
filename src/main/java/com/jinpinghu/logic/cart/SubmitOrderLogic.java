package com.jinpinghu.logic.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jinpinghu.db.bean.TbCart;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbReceivingAddress;
import com.jinpinghu.db.bean.TbShoppingCarBook;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbToolOrderBook;
import com.jinpinghu.db.dao.TbAddressDao;
import com.jinpinghu.db.dao.TbCartDao;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbShoppingCarBookDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderBookDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.AddOrderLogic;
import com.jinpinghu.logic.cart.param.AddOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class SubmitOrderLogic extends BaseZLogic{

	static Logger log = Logger.getLogger(AddOrderLogic.class);
	
	public static final BigDecimal HUNDRED = new BigDecimal("100");
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrderParam myParam = (AddOrderParam)logicParam;
		String userId = myParam.getUserId();
		JSONArray cartJson = JSONArray.parseArray( myParam.getCartJson()) ;		
		Integer addressId = myParam.getAddressId();
		BigDecimal totalPrice = BigDecimal.ZERO;
		
		TbAddressDao adDao = new TbAddressDao(em);
		TbReceivingAddress addresss = adDao.findById(addressId);
		String add = addresss.getProvince()+addresss.getCity()+addresss.getCounty()+addresss.getAddress();
		
		if(cartJson.size() > 0){
			JSONObject cartJo = null;

			
			TbToolOrderBookDao dao = new TbToolOrderBookDao(em);
			TbShoppingCarBookDao carDao = new TbShoppingCarBookDao(em);
			TbCartDao cartDao = new TbCartDao(em);
			TbToolDao toolDao = new TbToolDao(em);
			
			for(int i = 0; i < cartJson.size(); i++){
				BigDecimal orderPrice = BigDecimal.ZERO;
				Integer enterpriseId = null;
				JSONArray cartList = null;
				JSONObject cart = null;
				
				String orderNumber ="1001"+String.format("%011d", Math.abs(UUID.randomUUID().hashCode()))+6;
				cartJo = cartJson.getJSONObject(i);
				enterpriseId = cartJo.getInteger("enterpriseId");
				cartList = cartJo.getJSONArray("cart");
				//主表
				TbToolOrderBook tbToolOrderBook = new TbToolOrderBook(enterpriseId, userId, orderNumber, null, 0, 
						addresss.getLinkPeople(), addresss.getLinkMobile(), add, new Date(), 0);
				dao.save(tbToolOrderBook);
				for(int ii = 0; ii < cartList.size(); ii++){
					
					Integer toolId = null;
					String cartNum = null;
					String toolPrice = null;
					cart = cartList.getJSONObject(ii);
					toolId = cart.getInteger("toolId");
					cartNum = cart.getString("cartNum");
					toolPrice = cart.getString("price");
					TbTool tool = toolDao.findById(toolId);
					if(tool == null || tool.getDelFlag().equals(1)){
						res.add("status", 0);
						res.add("msg", "该商品已下架！");
						return false;
					}
					if(new BigDecimal(cartNum).compareTo( new BigDecimal( tool.getNumber() ) ) == 1){//判断购物车总数量是否大于库存  *传入的toolNum是最终数量
						res.add("status", 0);
						res.add("msg", "超出商品库存，请调整加购数量！");
						return false;
					}
					if(new BigDecimal(toolPrice).compareTo( new BigDecimal( tool.getPrice()) ) != 0){//判断购物车商品价格是否与库中价格相同
						res.add("status", 0);
						res.add("msg", "该商品价格已改变，请重新加购！");
						return false;
					}
					TbShoppingCarBook tbShoppingCarBook = new TbShoppingCarBook(tbToolOrderBook.getId() ,toolId, cartNum, 1, toolPrice, new Date(), 0);
					
					carDao.save(tbShoppingCarBook);
					//更改购物车状态
					TbCart tbCart = cartDao.findById(cart.getInteger("cartId"));
					tbCart.setStatus(1);
					cartDao.update(tbCart);
					orderPrice = orderPrice.add(new BigDecimal(toolPrice).multiply(new BigDecimal(cartNum)));
				}
				//存入订单价格
				tbToolOrderBook.setPrice(orderPrice.toString());
				dao.update(tbToolOrderBook);
				//计算购物车结算价
				totalPrice = totalPrice.add(orderPrice);
			}

		}
		res.add("totalPrice", totalPrice);
		res.add("status", 1);
		res.add("msg", "下单成功");
		return true;
	}
	
}
