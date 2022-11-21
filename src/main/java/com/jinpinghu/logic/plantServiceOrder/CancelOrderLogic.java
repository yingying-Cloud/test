package com.jinpinghu.logic.plantServiceOrder;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPlantServiceOrder;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantServiceOrder.param.CancelOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class CancelOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		CancelOrderParam myParam = (CancelOrderParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		String cancelInfo = myParam.getCancelInfo();
		
		TbPlantServiceOrderDao plantServiceOrderDao = new TbPlantServiceOrderDao(em);
		
		TbPlantServiceOrder order = plantServiceOrderDao.findById(id);
		
		if(order == null) {
			res.add("status", -1).add("msg", "订单不存在");
			return false;
		}else {
			order.setCancleInfo(cancelInfo);
			order.setStatus(99);
			order.setTimeCancle(new Date());
			plantServiceOrderDao.update(order);
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
