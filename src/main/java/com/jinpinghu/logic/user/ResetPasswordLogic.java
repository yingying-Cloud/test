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

public class ResetPasswordLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangePasswordParam myParam = (ChangePasswordParam)logicParam;
		Integer id = myParam.getId();
		TbUserDao userDao = new TbUserDao(em);
		TbUser tbUser = userDao.findById(id);
		if(tbUser==null){
			res.add("status", -1);
			res.add("msg", "未知错误获取用户失败");
			return true;
		}
		
		tbUser.setPassword(EncryptTool.md5("123456"));
		tbUser.setApiKey(_blow.encryptString(tbUser.getMobile() + EncryptTool.md5("123456") 
				+ System.currentTimeMillis()).substring(0, 100));
		userDao.update(tbUser);
		res.add("status", 1);
		res.add("msg", "修改成功");
		
		return true;
	}

}
