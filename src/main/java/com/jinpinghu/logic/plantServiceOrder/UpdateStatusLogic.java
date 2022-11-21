package com.jinpinghu.logic.plantServiceOrder;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPlantServiceOrder;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantServiceOrder.param.UpdateStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateStatusParam myParam = (UpdateStatusParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		Integer status = StringUtils.isEmpty(myParam.getStatus()) ? null : Integer.valueOf(myParam.getStatus());
		
		TbPlantServiceOrderDao orderDao = new TbPlantServiceOrderDao(em);
		
		TbPlantServiceOrder order = orderDao.findById(id);
		
		if(order == null) {
			res.add("status", 1).add("msg", "操作成功");
			return false;
		}
		
		order.setStatus(status);
		
		Date now = new Date();
		if(status == 2 ) {
			order.setTimeReceive(now);
		}else if(status == 3) {
			order.setTimeConfirm(now);
		}else if(status == 99) {
			order.setTimeCancle(now);
		}
		
		orderDao.update(order);
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
