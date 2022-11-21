package com.jinpinghu.logic.user;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.RegisteredPhoneSmsUtil;
import com.jinpinghu.common.tools.SendSmsUtil;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbSmsInfoDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.RegisteredPhoneParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RegisteredPhoneLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RegisteredPhoneParam myParam = (RegisteredPhoneParam) logicParam;
		String mobile = myParam.getMobile();
				
		TbUserDao tbUserDao = new TbUserDao(em);
		TbSmsInfoDao tbSmsInfoDao = new TbSmsInfoDao(em);
		
		TbUser user = tbUserDao.findByPhone(mobile);
		if(user!=null){
			res.add("msg", "该手机号已被注册！");		
			res.add("status", -1);
			return true;
		}
		
		String code = RegisteredPhoneSmsUtil.getCode();
		String[] codeInfo = new String[]{code,System.currentTimeMillis()+""};
		boolean b = RegisteredPhoneSmsUtil.setCodeInfoToMap(mobile, codeInfo);
		if(!b){
			res.add("status", -1);
			res.add("msg", "获取验证码太过频繁，请稍后再试");	
			return true;
		}
		
		String[] s = new String[]{code,"三"};
		SendSmsUtil.sendSms(mobile, "431221", s,tbSmsInfoDao);
		
		res.add("status", 1);
		res.add("msg", "成功");	
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RegisteredPhoneParam myParam = (RegisteredPhoneParam) logicParam;
		String mobile = myParam.getMobile();
		if (StringUtil.isNullOrEmpty(mobile)) {
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
