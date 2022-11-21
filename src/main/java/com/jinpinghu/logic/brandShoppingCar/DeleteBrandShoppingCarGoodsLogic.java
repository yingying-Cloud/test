package com.jinpinghu.logic.brandShoppingCar;

import com.jinpinghu.db.bean.TbBrandShoppingCar;
import com.jinpinghu.db.dao.TbBrandShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandShoppingCar.param.DeleteBrandShoppingCarGoodsParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;

public class DeleteBrandShoppingCarGoodsLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DeleteBrandShoppingCarGoodsParam myParam = (DeleteBrandShoppingCarGoodsParam)logicParam;
		String shoppingCarIds = myParam.getBrandShoppingCarIds();
		
		TbBrandShoppingCarDao tbBrandShoppingCarDao = new TbBrandShoppingCarDao(em);
		
		String[] shoppingCarId = shoppingCarIds.split(",");
		int count = 0;
		for (int i = 0; i < shoppingCarId.length; i++) {
			TbBrandShoppingCar car = tbBrandShoppingCarDao.findById(Integer.valueOf(shoppingCarId[i]));
			if(car!=null){
				tbBrandShoppingCarDao.delete(car);
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
		DeleteBrandShoppingCarGoodsParam myParam = (DeleteBrandShoppingCarGoodsParam)logicParam;
		String shoppingCarIds = myParam.getBrandShoppingCarIds();
		if(StringUtils.isEmpty(shoppingCarIds)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
