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
import com.jinpinghu.logic.brandShoppingCar.param.AddToBrandShoppingCarParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;

public class AddToBrandShoppingCarLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToBrandShoppingCarParam myParam = (AddToBrandShoppingCarParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		Integer brandId =StringUtils.isNullOrEmpty(myParam.getBrandId())?null:Integer.valueOf(myParam.getBrandId()) ;
		String num = myParam.getNum();
		Integer type =StringUtils.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType()) ;
		Integer brandSaleId =StringUtils.isNullOrEmpty(myParam.getBrandSaleId())?null:Integer.valueOf(myParam.getBrandSaleId()) ;
		Integer sellId =StringUtils.isNullOrEmpty(myParam.getSellId())?null:Integer.valueOf(myParam.getSellId()) ;
		
		Date now = new Date();
		TbBrandShoppingCarDao tbBrandShoppingCarDao = new TbBrandShoppingCarDao(em);
		TbBrandDao brandDao = new TbBrandDao(em);
		TbBrand brand = brandDao.findById(brandId);
		if(brand==null){
			res.add("status", -1);
			res.add("msg", "未知错误，获取商品信息失败");
			return true;
		}
		TbBrandShoppingCar car = tbBrandShoppingCarDao.findInByBrandId(brandId,enterpriseId,type,brandSaleId,sellId);
		
		if(car==null){
			//购物车无该商品，添加
			car = new TbBrandShoppingCar();
			car.setEnterpriseId(enterpriseId);
			car.setBrandId(brandId);
			car.setIsPay(0);
			car.setDelFlag(0);
			car.setStatus(1);
			car.setNum(num);
			car.setType(type);
			car.setBrandSaleId(brandSaleId);
			car.setBrandPrice(brand.getPrice());
			car.setSellId(sellId);
			if(type==null||type==1) {
				car.setPrice(new BigDecimal(brand.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			tbBrandShoppingCarDao.save(car);
		}else{
			//购物车有该商品，修改数量
			car.setNum(new BigDecimal(car.getNum()).add(new BigDecimal(num)).toString());
			if(type==null||type==1) {
				car.setPrice(new BigDecimal(brand.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			tbBrandShoppingCarDao.update(car);
		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddToBrandShoppingCarParam myParam = (AddToBrandShoppingCarParam)logicParam;
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
