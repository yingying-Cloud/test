package com.jinpinghu.logic.plantServiceOrder;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbPlantService;
import com.jinpinghu.db.bean.TbPlantServiceOrder;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.dao.TbPlantServiceDao;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantServiceOrder.param.AddPlantServiceOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddPlantServiceOrderLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbRole role = resUserRoleDao.findByUserTabId(loginUser.getId());
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		if (role == null || role.getId() != 3 || enterprise == null) {
			res.add("status", -1).add("msg", "权限错误");
			return false;
		}
		// TODO Auto-generated method stub
		AddPlantServiceOrderParam myParam = (AddPlantServiceOrderParam)logicParam;
		Integer plantServiceId = StringUtils.isEmpty(myParam.getPlantServiceId()) ? null : Integer.valueOf(myParam.getPlantServiceId());
		String name = myParam.getName();
		String address = myParam.getAddress();
		String contact = myParam.getContact();
		String phone = myParam.getPhone();
		String day = myParam.getDay();
		Date serviceStartTime = StringUtils.isEmpty(myParam.getServiceStartTime()) ? null : DateTimeUtil.formatTime(myParam.getServiceStartTime());
		Date serviceEndTime = StringUtils.isEmpty(myParam.getServiceEndTime()) ? null : DateTimeUtil.formatTime(myParam.getServiceEndTime());
		
		TbPlantServiceOrderDao plantServiceOrderDao = new TbPlantServiceOrderDao(em);
		TbPlantServiceDao plantServiceDao = new TbPlantServiceDao(em);
		
		TbPlantService plantService = plantServiceDao.findById(plantServiceId);
		
		if(plantService == null) {
			res.add("status", -1).add("msg", "该劳务计划不存在或已删除");
			return false;
		}
		String orderNumber = DateTimeUtil.formatSelf(new Date(),"yyyyMMddHHmmssSSS");
		
		BigDecimal price = new BigDecimal(plantService.getPrice());
		
		TbPlantServiceOrder plantServiceOrder = new TbPlantServiceOrder(plantService.getEnterpriseId(),enterprise.getId(),name,
				plantServiceId, loginUser.getName(), orderNumber, price.setScale(2,BigDecimal.ROUND_HALF_UP).toString(), null, 1,
				null, serviceStartTime, new Date(), null, null,null, 0,serviceEndTime);
		plantServiceOrder.setAddress(address);
		plantServiceOrder.setPhone(phone);
		plantServiceOrder.setContact(contact);
		plantServiceOrder.setDay(day);
		plantServiceOrderDao.save(plantServiceOrder);
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddPlantServiceOrderParam myParam = (AddPlantServiceOrderParam)logicParam;
		try {
			Integer.valueOf(myParam.getPlantServiceId());
//			new BigDecimal(myParam.getArea());
		}catch (Exception e) {
			// TODO: handle exception
			res.add("status", -1).add("msg", "传入参数格式有误");
			return false;
		}
		return true;
	}

}
