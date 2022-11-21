package com.jinpinghu.logic.user;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.ChangePasswordParam;

import fw.jbiz.common.util.EncryptTool;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangePasswordLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangePasswordParam myParam = (ChangePasswordParam)logicParam;
		String oldPassword = myParam.getOldPassword();
		String newPassword = myParam.getNewPassword();
		
		TbUserDao userDao = new TbUserDao(em);
		TbUser tbUser = userDao.checkLogin(logicParam.getUserId(), logicParam.getApiKey());
		if(tbUser==null){
			res.add("status", -1);
			res.add("msg", "未知错误获取用户失败");
			return true;
		}
		
		if(tbUser.getPassword().equals(oldPassword)){
			tbUser.setPassword(newPassword);
			tbUser.setApiKey(_blow.encryptString(tbUser.getMobile() + newPassword 
					+ System.currentTimeMillis()).substring(0, 100));
			userDao.update(tbUser);
			res.add("status", 1);
			res.add("msg", "修改成功");
		}else{
			res.add("status", -1);
			res.add("msg", "旧密码错误");
		}
		
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ChangePasswordParam myParam = (ChangePasswordParam)logicParam;
		String oldPassword = myParam.getOldPassword();
		String newPassword = myParam.getNewPassword();
		if(StringUtils.isEmpty(oldPassword)
				||StringUtils.isEmpty(newPassword)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}

}
