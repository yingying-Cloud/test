package com.jinpinghu.logic.user;


import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.ForgetPwdValidateUtil;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.ForgetPwdChangePwdParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ForgetPwdChangePwdLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ForgetPwdChangePwdParam myParam = (ForgetPwdChangePwdParam)logicParam;
		String phone = myParam.getPhone();
		String tempTicket = myParam.getTempTicket();
		String newPwd = myParam.getNewPwd();
		
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser tbUser = tbUserDao.findByPhone(phone);
		if(tbUser==null){
			res.add("status", -1);
			res.add("msg", "该手机号未绑定账户");
			return true;
		}
		
		boolean b = ForgetPwdValidateUtil.validateTempTicketInfo(phone, tempTicket);
		if(b){
			tbUser.setPassword(newPwd);
			tbUserDao.update(tbUser);
			res.add("status", 1);
			res.add("msg", "修改成功");
		}else{
			res.add("status", -1);
			res.add("msg", "临时凭证验证不通过");
		}
		
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ForgetPwdChangePwdParam myParam = (ForgetPwdChangePwdParam)logicParam;
		String phone = myParam.getPhone();
		String tempTicket = myParam.getTempTicket();
		String newPwd = myParam.getNewPwd();
		if(StringUtil.isNullOrEmpty(phone)
				||StringUtil.isNullOrEmpty(tempTicket)
				||StringUtil.isNullOrEmpty(newPwd)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		return true;
	}
}
