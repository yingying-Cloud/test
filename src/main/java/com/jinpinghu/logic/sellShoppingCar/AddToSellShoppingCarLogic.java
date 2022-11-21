package com.jinpinghu.logic.sellShoppingCar;

import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.bean.TbSellBrand;
import com.jinpinghu.db.bean.TbSellShoppingCar;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellShoppingCar.param.AddToSellShoppingCarParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;

public class AddToSellShoppingCarLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToSellShoppingCarParam myParam = (AddToSellShoppingCarParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		Integer sellId =StringUtils.isNullOrEmpty(myParam.getSellId())?null:Integer.valueOf(myParam.getSellId()) ;
		String num = myParam.getNum();
		Integer brandId =StringUtils.isNullOrEmpty(myParam.getBrandId())?null:Integer.valueOf(myParam.getBrandId()) ;
		
		Date now = new Date();
		TbSellShoppingCarDao tbSellShoppingCarDao = new TbSellShoppingCarDao(em);
		TbSellBrandDao brandDao = new TbSellBrandDao(em);
		TbSellBrand sell = brandDao.findById(brandId);
		if(sell==null){
			res.add("status", -1);
			res.add("msg", "未知错误，获取商品信息失败");
			return true;
		}
		TbSellShoppingCar car = tbSellShoppingCarDao.findInBySellId(brandId,enterpriseId,sellId);
		
		if(car==null){
			//购物车无该商品，添加
			car = new TbSellShoppingCar();
			car.setEnterpriseId(enterpriseId);
			car.setIsPay(0);
			car.setDelFlag(0);
			car.setStatus(1);
			car.setNum(num);
			car.setBrandPrice(sell.getPrice());
			car.setSellId(sellId);
			car.setBrandId(brandId);
			car.setPrice(new BigDecimal(sell.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			tbSellShoppingCarDao.save(car);
		}else{
			//购物车有该商品，修改数量
			car.setNum(new BigDecimal(car.getNum()).add(new BigDecimal(num)).toString());
			car.setPrice(new BigDecimal(sell.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			tbSellShoppingCarDao.update(car);
		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddToSellShoppingCarParam myParam = (AddToSellShoppingCarParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		Integer brandId =StringUtils.isNullOrEmpty(myParam.getBrandId())?null:Integer.valueOf(myParam.getBrandId()) ;
		if(brandId==null||enterpriseId==null){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
