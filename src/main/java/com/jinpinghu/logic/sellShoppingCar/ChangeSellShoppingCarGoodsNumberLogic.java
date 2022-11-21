package com.jinpinghu.logic.sellShoppingCar;

import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbSellBrand;
import com.jinpinghu.db.bean.TbSellShoppingCar;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellShoppingCar.param.ChangeSellShoppingCarGoodsNumberParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class ChangeSellShoppingCarGoodsNumberLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangeSellShoppingCarGoodsNumberParam myParam = (ChangeSellShoppingCarGoodsNumberParam)logicParam;
		Integer shoppingCarId = StringUtils.isEmpty(myParam.getSellShoppingCarId())?null:Integer.valueOf(myParam.getSellShoppingCarId());
		String number = myParam.getNumber()+"";
		
		if(new BigDecimal(number)==BigDecimal.ZERO){
			res.add("status", -1);
			res.add("msg", "无法再减少数量，若要删除，请点击编辑后删除该商品");
			return true;
		}
		
		TbSellShoppingCarDao tbSellShoppingCarDao = new TbSellShoppingCarDao(em);
		TbSellBrandDao sellDao = new TbSellBrandDao(em);
		TbSellShoppingCar car = tbSellShoppingCarDao.findById(shoppingCarId);
		if(car==null){
			res.add("status", -1);
			res.add("msg", "未知错误，购物车商品信息获取失败");
			return true;
		}
		TbSellBrand sell = sellDao.findById(car.getBrandId());
		if(sell == null || sell.getDelFlag() == 1) {
			res.add("status", -1);
			res.add("msg", "该商品已删除");
			return false;
		}
		car.setNum(number);
		car.setPrice(new BigDecimal(sell.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		tbSellShoppingCarDao.update(car);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeSellShoppingCarGoodsNumberParam myParam = (ChangeSellShoppingCarGoodsNumberParam)logicParam;
		String shoppingCarId = myParam.getSellShoppingCarId();
		Integer number = myParam.getNumber();
		if(StringUtils.isEmpty(shoppingCarId)||number==null){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
