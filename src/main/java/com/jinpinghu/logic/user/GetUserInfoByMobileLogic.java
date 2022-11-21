package com.jinpinghu.logic.user;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbArea;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbAreaDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserExpertDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.GetUserInfoByMobileParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetUserInfoByMobileLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetUserInfoByMobileParam myParam = (GetUserInfoByMobileParam)logicParam;
		String mobile = myParam.getMobile();
		Integer id = myParam.getId();
		
		TbUserDao userDao = new TbUserDao(em);
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbAreaDao areaDao = new TbAreaDao(em);
		TbUser tbUser = null;
		if(id!=null) {
			tbUser = userDao.findById(id);
		}
		if(!StringUtils.isEmpty(mobile)) {
			tbUser = userDao.findByPhone(mobile);
		}
		
		if(tbUser==null) {
			res.add("status", 2);
			res.add("msg", "该账户不存在！");
			return true;
		}
		
		Map<String,Object> resultMap = new HashMap<>();
		
		//获取用户角色信息
		TbRole role = resUserRoleDao.findByUserTabId(tbUser.getId());
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		if (role.getId() == 5) {
			if(enterprise != null && enterprise.getState() ==0) {
				res.add("status", -1);
				res.add("msg", "此账号已停用");
				return false;
			}
		}
		
		resultMap.put("userTabId", tbUser.getId());
		resultMap.put("userId", tbUser.getUserId());
		resultMap.put("apiKey", tbUser.getApiKey());
		resultMap.put("name", tbUser.getName());
		resultMap.put("mobile", tbUser.getMobile());
		resultMap.put("headPic", tbUser.getHeadPic());
		resultMap.put("wxId", tbUser.getWxId());
		resultMap.put("dscd", tbUser.getDscd());
		
		TbArea area = areaDao.findById(tbUser.getDscd());
		resultMap.put("dsnm", area == null ? "" : area.getName());
		
		resultMap.put("roleId", role == null ? "" : role.getId());
		resultMap.put("roleName", role == null ? "" : role.getRoleName());
		
		
		TbResUserExpertDao resUserExpertDao = new TbResUserExpertDao(em);
		TbExpert expert = resUserExpertDao.findByUserTabId(tbUser.getId());
		
		resultMap.put("enterpriseId", enterprise == null ? "" : enterprise.getId());
		resultMap.put("enterpriseName", enterprise == null ? "" : enterprise.getName());
		resultMap.put("status", enterprise == null ? "" : enterprise.getStatus());
		resultMap.put("industrial", enterprise == null ? "" : enterprise.getIndustrial());
		if(expert != null) {
			resultMap.put("enterpriseId", expert == null ? "" : expert.getId());
			resultMap.put("enterpriseName", expert == null ? "" : tbUser.getName());
			resultMap.put("status", expert == null ? "" : expert.getStatus());
		}
		TbEnterpriseZeroDao tbEnterpriseZeroDap = new TbEnterpriseZeroDao(em);
		if(enterprise!=null) {
			TbEnterpriseZero zero = tbEnterpriseZeroDap.findByEnterpriseId(enterprise.getId());
			resultMap.put("flag", zero == null ? 0 : zero.getFlag());
		}
		
		
		res.add("result", resultMap);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

}
