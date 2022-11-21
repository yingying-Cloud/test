package com.jinpinghu.logic.shoppingCar;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.shoppingCar.param.ChangeShoppingCarGoodsNumberParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangeShoppingCarGoodsNumberLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangeShoppingCarGoodsNumberParam myParam = (ChangeShoppingCarGoodsNumberParam)logicParam;
		Integer shoppingCarId = StringUtils.isEmpty(myParam.getShoppingCarId())?null:Integer.valueOf(myParam.getShoppingCarId());
		String number = myParam.getNumber()+"";
		
		if(new BigDecimal(number)==BigDecimal.ZERO){
			res.add("status", -1);
			res.add("msg", "无法再减少数量，若要删除，请点击编辑后删除该商品");
			return true;
		}
		
		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		TbShoppingCar car = tbShoppingCarDao.findById(shoppingCarId);
		if(car==null){
			res.add("status", -1);
			res.add("msg", "未知错误，购物车商品信息获取失败");
			return true;
		}
		TbTool tool = toolDao.findById(car.getToolId());
		if(tool == null || tool.getDelFlag() == 1) {
			res.add("status", -1);
			res.add("msg", "该商品已删除");
			return false;
		}
		car.setNum(number);
//		if(new BigDecimal(car.getNum()).compareTo(new BigDecimal(tool.getNumber()))==1) {
//			res.add("status", -1);
//			res.add("msg", "库存不足");
//			return false;
//		}
		tbShoppingCarDao.update(car);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangeShoppingCarGoodsNumberParam myParam = (ChangeShoppingCarGoodsNumberParam)logicParam;
		String shoppingCarId = myParam.getShoppingCarId();
		Integer number = myParam.getNumber();
		if(StringUtils.isEmpty(shoppingCarId)||number==null){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
