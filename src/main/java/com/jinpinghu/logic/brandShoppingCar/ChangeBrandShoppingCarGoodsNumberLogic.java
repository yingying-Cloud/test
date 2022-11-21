package com.jinpinghu.logic.brandShoppingCar;

import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbBrandSale;
import com.jinpinghu.db.bean.TbBrandShoppingCar;
import com.jinpinghu.db.bean.TbResBrandCarNum;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbBrandSaleDao;
import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.db.dao.TbResBrandCarNumDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandShoppingCar.param.ChangeBrandShoppingCarGoodsNumberParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class ChangeBrandShoppingCarGoodsNumberLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangeBrandShoppingCarGoodsNumberParam myParam = (ChangeBrandShoppingCarGoodsNumberParam)logicParam;
		Integer shoppingCarId = StringUtils.isEmpty(myParam.getBrandShoppingCarId())?null:Integer.valueOf(myParam.getBrandShoppingCarId());
		String number = myParam.getNumber()+"";
		
		if(new BigDecimal(number)==BigDecimal.ZERO){
			res.add("status", -1);
			res.add("msg", "无法再减少数量，若要删除，请点击编辑后删除该商品");
			return true;
		}
		
		TbBrandShoppingCarDao tbBrandShoppingCarDao = new TbBrandShoppingCarDao(em);
		TbBrandDao brandDao = new TbBrandDao(em);
		TbBrandShoppingCar car = tbBrandShoppingCarDao.findById(shoppingCarId);
		if(car==null){
			res.add("status", -1);
			res.add("msg", "未知错误，购物车商品信息获取失败");
			return true;
		}
		TbBrand brand = brandDao.findById(car.getBrandId());
		if(brand == null || brand.getDelFlag() == 1) {
			res.add("status", -1);
			res.add("msg", "该商品已删除");
			return false;
		}
		car.setNum(number);
		if(car.getType()==null||car.getType()==1) {
			car.setPrice(new BigDecimal(brand.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		}
		tbBrandShoppingCarDao.update(car);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeBrandShoppingCarGoodsNumberParam myParam = (ChangeBrandShoppingCarGoodsNumberParam)logicParam;
		String shoppingCarId = myParam.getBrandShoppingCarId();
		Integer number = myParam.getNumber();
		if(StringUtils.isEmpty(shoppingCarId)||number==null){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
