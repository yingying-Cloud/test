package com.jinpinghu.logic.user;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.ForgetPwdSmsUtil;
import com.jinpinghu.common.tools.ForgetPwdValidateUtil;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.ForgetPwdSetCodeParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ForgetPwdSetCodeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ForgetPwdSetCodeParam myParam = (ForgetPwdSetCodeParam)logicParam;
		String phone = myParam.getPhone();
		String code = myParam.getCode();
		
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser tbUser = tbUserDao.findByPhone(phone);
		if(tbUser==null){
			res.add("status", -1);
			res.add("msg", "该手机号未绑定账户");
			return true;
		}
		
		boolean b = ForgetPwdSmsUtil.validateCodeInfo(phone, code);
		if(b){
			String tempTicket = ForgetPwdValidateUtil.getTempTicket();
			String[] tempTicketInfo = new String[]{tempTicket,System.currentTimeMillis()+""};
			ForgetPwdValidateUtil.setCodeInfoToMap(phone, tempTicketInfo);
			res.add("tempTicket", tempTicket);
			res.add("status", 1);
			res.add("msg", "成功");
		}else{
			res.add("status", -1);
			res.add("msg", "验证码错误");
		}
		
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ForgetPwdSetCodeParam myParam = (ForgetPwdSetCodeParam)logicParam;
		String phone = myParam.getPhone();
		String code = myParam.getCode();
		if(StringUtil.isNullOrEmpty(phone)
				||StringUtil.isNullOrEmpty(code)){
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
