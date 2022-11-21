package com.jinpinghu.logic.user;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.RegisteredPhoneSmsUtil;
import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.RegisteredParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.common.util.EncryptTool;
import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RegisteredLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RegisteredParam myParam = (RegisteredParam) logicParam;
		String username = myParam.getUsername();
		String password = myParam.getPassword();
		String mobile = myParam.getMobile();
		String code = myParam.getCode();
		String role = myParam.getRole();
		String wxId = myParam.getWxId();
		String headPic = myParam.getHeadPic();
		
		boolean b = RegisteredPhoneSmsUtil.validateCodeInfo(mobile, code);
		if(!b){
			res.add("status", -1);
			res.add("msg", "验证码过期或有误！");	
			return true;
		}
				
		TbUserDao tbUserDao = new TbUserDao(em);
		
		TbUser user = new TbUser();
		
		String userId = ZDao.genPkId();
		String apiKey = _blow.encryptString(username + password+ System.currentTimeMillis()).substring(0, 100);
		user = new TbUser(null,userId, apiKey, mobile, password, new Date());
		user.setDelFlag(0);
		user.setName(username);
		user.setWxId(wxId);
		user.setHeadPic(headPic);
		tbUserDao.save(user);
		
		TbResUserRoleDao userRoleDao = new TbResUserRoleDao(em);
		if(!StringUtils.isNullOrEmpty(role)) {
			String[] split = role.split(",");
			for(String roleId:split) {
				TbResUserRole userRole = new TbResUserRole();
				userRole.setDelFlag(0);
				userRole.setInputTime(new Date());
				userRole.setRoleId(Integer.valueOf(roleId));
				userRole.setUserTabId(user.getId());
				userRoleDao.save(userRole);
			}
		}
		
		
		res.add("status", 1);
		res.add("msg", "注册成功！");	
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RegisteredParam myParam = (RegisteredParam) logicParam;
		String username = myParam.getUsername();
		String password = myParam.getPassword();
		String mobile = myParam.getMobile();
		String code = myParam.getCode();
		if (StringUtil.isNullOrEmpty(username)
				||StringUtil.isNullOrEmpty(password)
				||StringUtil.isNullOrEmpty(mobile)
				||StringUtil.isNullOrEmpty(code)
				) {
			res.add("msg", "必填参数不能为空");
			res.add("status", -2);
			return false;
		}
		return true;
	}

	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
	
}
