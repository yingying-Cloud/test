package com.jinpinghu.logic.shoppingCar;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbShoppingCar;
import com.jinpinghu.db.dao.TbShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.shoppingCar.param.DeleteShoppingCarGoodsParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DeleteShoppingCarGoodsLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DeleteShoppingCarGoodsParam myParam = (DeleteShoppingCarGoodsParam)logicParam;
		String shoppingCarIds = myParam.getShoppingCarIds();
		
		TbShoppingCarDao tbShoppingCarDao = new TbShoppingCarDao(em);
		
		String[] shoppingCarId = shoppingCarIds.split(",");
		int count = 0;
		for (int i = 0; i < shoppingCarId.length; i++) {
			TbShoppingCar car = tbShoppingCarDao.findById(Integer.valueOf(shoppingCarId[i]));
			if(car!=null){
				tbShoppingCarDao.delete(car);
				count++;
			}
		}
		
		res.add("status", 1);
		res.add("msg", "成功删除"+count+"个");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DeleteShoppingCarGoodsParam myParam = (DeleteShoppingCarGoodsParam)logicParam;
		String shoppingCarIds = myParam.getShoppingCarIds();
		if(StringUtils.isEmpty(shoppingCarIds)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
