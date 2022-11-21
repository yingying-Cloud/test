package com.jinpinghu.logic.cashRegister;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.bean.TbCashRegister;
import com.jinpinghu.db.dao.TbCashRegisterDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.cashRegister.param.AddOrUpdateCashRegisterParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrUpdateCashRegisterLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddOrUpdateCashRegisterParam myParam = (AddOrUpdateCashRegisterParam)logicParam;
		String cashRegisterId = myParam.getCashRegisterId();
		String baiduAifaceSn = myParam.getBaiduAifaceSn();
		Integer id = myParam.getId();
		
		TbCashRegisterDao cashRegisterDao = new TbCashRegisterDao(em);
		
		TbCashRegister cashRegister = cashRegisterDao.findById(id);
		
		TbCashRegister checkCashRegister = cashRegisterDao.findByCashRegisterId(cashRegisterId);
		
		if (cashRegister != null) {
			if (checkCashRegister!= null && checkCashRegister.getId() != cashRegister.getId()) {
				res.add("msg", "此收银机编码已存在");
				res.add("status", -1);
				return false;
			}
			cashRegister.setBaiduAifaceSn(baiduAifaceSn);
			cashRegister.setCashRegisterId(cashRegisterId);
			cashRegisterDao.update(cashRegister);
		}else {
			if (checkCashRegister!= null) {
				res.add("msg", "此收银机编码已存在");
				res.add("status", -1);
				return false;
			}
			Date now = new Date();
			cashRegister = new TbCashRegister(cashRegisterId, baiduAifaceSn,now,now,0);
			cashRegisterDao.save(cashRegister);
		}
		
		res.add("msg", "成功！");
		res.add("status", 1);
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		AddOrUpdateCashRegisterParam myParam = (AddOrUpdateCashRegisterParam)logicParam;
		String cashRegisterId = myParam.getCashRegisterId();
		if (StringUtils.isEmpty(cashRegisterId)) {
			res.add("status", -1).add("msg", "机器id不能为空");
			return false;
		}
		return true;
	}
}
