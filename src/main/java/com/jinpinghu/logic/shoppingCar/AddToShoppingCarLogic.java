package com.jinpinghu.logic.shoppingCar;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;


import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.shoppingCar.param.AddToShoppingCarParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddToShoppingCarLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddToShoppingCarParam myParam = (AddToShoppingCarParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		Integer toolId =StringUtils.isNullOrEmpty(myParam.getToolId())?null:Integer.valueOf(myParam.getToolId()) ;
		String num = myParam.getNum();
		
		
		Date now = new Date();
		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		TbTool tool = toolDao.findById(toolId);
		if(tool==null){
			res.add("status", -1);
			res.add("msg", "未知错误，获取商品信息失败");
			return true;
		}
		String price = StringUtils.isNullOrEmpty(tool.getPrice())?"0":tool.getPrice();
		TbShoppingCar car = tbShoppingCarDao.findInByToolId(toolId,enterpriseId);
		
		if(car==null){
			//购物车无该商品，添加
			
			car = new TbShoppingCar();
			car.setToolId(toolId);
			car.setEnterpriseId(enterpriseId);
			car.setIsPay(0);
			car.setDelFlag(0);
			car.setStatus(1);
			car.setNum(num);
//			if(new BigDecimal(car.getNum()).compareTo(new BigDecimal(StringUtils.isNullOrEmpty(tool.getNumber())?"0":tool.getNumber()))==1) {
//				res.add("status", -1);
//				res.add("msg", "库存不足");
//				return false;
//			}
			car.setPrice(new BigDecimal(price).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			tbShoppingCarDao.save(car);
		}else{
			//购物车有该商品，修改数量
//			if(new BigDecimal(car.getNum()).compareTo(new BigDecimal(tool.getNumber()))==1) {
//				res.add("status", -1);
//				res.add("msg", "库存不足");
//				return false;
//			}
			car.setNum(new BigDecimal(car.getNum()).add(new BigDecimal(num)).toString());
			car.setPrice(new BigDecimal(price).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			tbShoppingCarDao.update(car);
		}
			
//		if(new BigDecimal(car.getNum()).compareTo(new BigDecimal(tool.getNumber()))==1) {
//			res.add("status", -1);
//			res.add("msg", "库存不足");
//			return false;
//		}
//		car.setPrice(new BigDecimal(tool.getPrice()).multiply(new BigDecimal(car.getNum())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
//		tbShoppingCarDao.update(car);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddToShoppingCarParam myParam = (AddToShoppingCarParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId()) ;
		Integer toolId =StringUtils.isNullOrEmpty(myParam.getToolId())?null:Integer.valueOf(myParam.getToolId()) ;
		if(toolId==null||enterpriseId==null){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
