package com.jinpinghu.logic.user;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserExpertDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.WechatLoginParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class WechatLoginLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		WechatLoginParam myParam = (WechatLoginParam)logicParam;
		String wxId = myParam.getWxId();

		TbUserDao tbUserDao = new TbUserDao(em);
		
		TbResUserRoleDao resUserRoleDao = new TbResUserRoleDao(em);
		
		TbUser tbUser = tbUserDao.wechatLogin(wxId);
		if(tbUser==null) {
			res.add("status", 2);
			res.add("msg", "用户名或密码错误");
			return true;
		}
		
		tbUser.setLastTime(new Date());
		Map<String,Object> resultMap = new HashMap<>();
		//用户基础信息
		resultMap.put("userTabId", tbUser.getId());
		resultMap.put("userId", tbUser.getUserId());
		resultMap.put("apiKey", tbUser.getApiKey());
		resultMap.put("name", tbUser.getName());
		resultMap.put("mobile", tbUser.getMobile());
		resultMap.put("headPic", tbUser.getHeadPic());
		resultMap.put("wxId", tbUser.getWxId());
		
		//获取用户角色信息
		TbRole role = resUserRoleDao.findByUserTabId(tbUser.getId());
		resultMap.put("roleId", role == null ? "" : role.getId());
		resultMap.put("roleName", role == null ? "" : role.getRoleName());
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbResUserExpertDao resUserExpertDao = new TbResUserExpertDao(em);
		TbExpert expert = resUserExpertDao.findByUserTabId(tbUser.getId());
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		resultMap.put("enterpriseId", enterprise == null ? "" : enterprise.getId());
		resultMap.put("enterpriseName", enterprise == null ? "" : enterprise.getName());
		resultMap.put("status", enterprise == null ? "" : enterprise.getStatus());
		if(expert != null) {
			resultMap.put("enterpriseId", expert == null ? "" : expert.getId());
			resultMap.put("enterpriseName", expert == null ? "" : tbUser.getName());
			resultMap.put("status", expert == null ? "" : expert.getStatus());
		}
		
		res.add("result", resultMap);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		WechatLoginParam myParam = (WechatLoginParam)logicParam;
		String wxId = myParam.getWxId();
		if(StringUtils.isEmpty(wxId)) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}
}
