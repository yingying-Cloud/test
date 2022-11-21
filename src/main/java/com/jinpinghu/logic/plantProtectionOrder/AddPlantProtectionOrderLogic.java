package com.jinpinghu.logic.plantProtectionOrder;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbPlantProtection;
import com.jinpinghu.db.bean.TbPlantProtectionOrder;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.dao.TbPlantProtectionDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantProtectionOrder.param.AddPlantProtectionOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddPlantProtectionOrderLogic extends BaseZLogic {

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
		AddPlantProtectionOrderParam myParam = (AddPlantProtectionOrderParam)logicParam;
		Integer plantProtectionId = StringUtils.isEmpty(myParam.getPlantProtectionId()) ? null : Integer.valueOf(myParam.getPlantProtectionId());
		String area = myParam.getArea();
		String name = myParam.getName();
		String address = myParam.getAddress();
		String contact = myParam.getContact();
		String phone = myParam.getPhone();
		Date serviceStartTime = StringUtils.isEmpty(myParam.getServiceStartTime()) ? null : DateTimeUtil.formatTime(myParam.getServiceStartTime());
		Date serviceEndTime = StringUtils.isEmpty(myParam.getServiceEndTime()) ? null : DateTimeUtil.formatTime(myParam.getServiceEndTime());
		
		TbPlantProtectionOrderDao plantProtectionOrderDao = new TbPlantProtectionOrderDao(em);
		TbPlantProtectionDao plantProtectionDao = new TbPlantProtectionDao(em);
		
		TbPlantProtection plantProtection = plantProtectionDao.findById(plantProtectionId);
		
		if(plantProtection == null) {
			res.add("status", -1).add("msg", "该植保计划不存在或已删除");
			return false;
		}
		String orderNumber = DateTimeUtil.formatSelf(new Date(),"yyyyMMddHHmmssSSS");
		
		BigDecimal price = new BigDecimal(area).multiply(new BigDecimal(plantProtection.getPrice()));
		
		TbPlantProtectionOrder plantProtectionOrder = new TbPlantProtectionOrder(plantProtection.getEnterpriseId(),enterprise.getId(),name,
				plantProtectionId, loginUser.getName(), orderNumber, price.setScale(2,BigDecimal.ROUND_HALF_UP).toString(), area, 1,
				null, serviceStartTime, new Date(), null, null,null, 0,serviceEndTime);
		plantProtectionOrder.setAddress(address);
		plantProtectionOrder.setPhone(phone);
		plantProtectionOrder.setContact(contact);
		plantProtectionOrderDao.save(plantProtectionOrder);
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddPlantProtectionOrderParam myParam = (AddPlantProtectionOrderParam)logicParam;
		try {
			Integer.valueOf(myParam.getPlantProtectionId());
			new BigDecimal(myParam.getArea());
		}catch (Exception e) {
			// TODO: handle exception
			res.add("status", -1).add("msg", "传入参数格式有误");
			return false;
		}
		return true;
	}

}
