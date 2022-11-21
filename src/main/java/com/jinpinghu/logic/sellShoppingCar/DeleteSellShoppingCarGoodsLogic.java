package com.jinpinghu.logic.sellShoppingCar;

import com.jinpinghu.db.bean.TbSellShoppingCar;
import com.jinpinghu.db.dao.TbSellShoppingCarDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellShoppingCar.param.DeleteSellShoppingCarGoodsParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import org.apache.commons.lang.StringUtils;

import javax.persistence.EntityManager;

public class DeleteSellShoppingCarGoodsLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DeleteSellShoppingCarGoodsParam myParam = (DeleteSellShoppingCarGoodsParam)logicParam;
		String shoppingCarIds = myParam.getSellShoppingCarIds();
		
		TbSellShoppingCarDao tbSellShoppingCarDao = new TbSellShoppingCarDao(em);
		
		String[] shoppingCarId = shoppingCarIds.split(",");
		int count = 0;
		for (int i = 0; i < shoppingCarId.length; i++) {
			TbSellShoppingCar car = tbSellShoppingCarDao.findById(Integer.valueOf(shoppingCarId[i]));
			if(car!=null){
				tbSellShoppingCarDao.delete(car);
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
		DeleteSellShoppingCarGoodsParam myParam = (DeleteSellShoppingCarGoodsParam)logicParam;
		String shoppingCarIds = myParam.getSellShoppingCarIds();
		if(StringUtils.isEmpty(shoppingCarIds)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
