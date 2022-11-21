package com.jinpinghu.logic.login;

import java.util.Date;
import javax.persistence.EntityManager;


import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbResUserEnterprise;
import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.login.param.RegisteredParam;
import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RegisteredLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RegisteredParam param = (RegisteredParam) logicParam;
		String name = param.getName();
		String idCode = param.getIdCode();
		String mobile = param.getMobile();
		
		TbUserDao tbUserDao = new TbUserDao(em);
		//判断电话号码是否已注册
		TbUser user = tbUserDao.findByPhone(mobile);
		if(user != null){
			res.add("msg", "改手机号已被注册");
			res.add("status", 1);
			return true;
		}
		//新增用户
		user = new TbUser();
		String password = "e10adc3949ba59abbe56e057f20f883e";
		String userId = ZDao.genPkId();
		String apiKey = _blow.encryptString(name + password+ System.currentTimeMillis()).substring(0, 100);
		user = new TbUser(null,userId, apiKey, mobile, password, new Date());
		user.setDelFlag(0);
		user.setExpertIdcard(idCode);
		user.setName(name);
		tbUserDao.save(user);
		//新增角色关联
		TbResUserRoleDao userRoleDao = new TbResUserRoleDao(em);
		TbResUserRole userRole = new TbResUserRole();
		userRole.setDelFlag(0);
		userRole.setInputTime(new Date());
		userRole.setRoleId(3);
		userRole.setUserTabId(user.getId());
		userRoleDao.save(userRole);
		//新增企业
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		TbEnterprise enterprise = new TbEnterprise();
		enterprise.setDelFlag(0);
		enterprise.setEnterpriseLegalPerson(name);
		enterprise.setEnterpriseLegalPersonIdcard(idCode);
		enterprise.setEnterpriseLinkMobile(mobile);
		enterprise.setEnterpriseLinkPeople(name);
		enterprise.setEnterpriseType(1);
		enterprise.setInputTime(new Date());
		enterprise.setName(name);
		enterprise.setStatus(1);
		enterprise.setDscd("330482000000");
		enterprise.setListOrder(0);
		enterprise.setType("1");
		enterprise.setIsSync(0);
		enterprise.setState(1);
		enterpriseDao.save(enterprise);
		//新增企业用户关联
		TbResUserEnterpriseDao tbResUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbResUserEnterprise checkIsExist = tbResUserEnterpriseDao.checkIsExist(user.getId(), enterprise.getId());
		if(checkIsExist==null) {
			TbResUserEnterprise tbResUserEnterprice = new TbResUserEnterprise();
			tbResUserEnterprice.setDelFlag(0);
			tbResUserEnterprice.setEnterpriseId(enterprise.getId());
			tbResUserEnterprice.setUserTabId(user.getId());
			tbResUserEnterpriseDao.save(tbResUserEnterprice);
		}
		
		res.add("status", 1).add("msg","操作成功").add("id", enterprise.getId()).add("enterpriseName", enterprise.getName());
		return true;
	}
	   // 身份认证
		@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
			return true;
	   }
}
