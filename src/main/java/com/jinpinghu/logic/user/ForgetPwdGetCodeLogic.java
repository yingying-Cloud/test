package com.jinpinghu.logic.user;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.ForgetPwdSmsUtil;
import com.jinpinghu.common.tools.SendSmsUtil;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbSmsInfoDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.ForgetPwdGetCodeParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ForgetPwdGetCodeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ForgetPwdGetCodeParam myParam = (ForgetPwdGetCodeParam)logicParam;
		String phone = myParam.getPhone();
		
		TbUserDao tbUserDao = new TbUserDao(em);
		TbSmsInfoDao tbSmsInfoDao = new TbSmsInfoDao(em);
		
		TbUser tbUser = tbUserDao.findByPhone(phone);
		if(tbUser==null){
			res.add("status", -1);
			res.add("msg", "该手机号未绑定账户");
			return true;
		}
		
		String code = ForgetPwdSmsUtil.getCode();
		String[] codeInfo = new String[]{code,System.currentTimeMillis()+""};
		boolean b = ForgetPwdSmsUtil.setCodeInfoToMap(phone, codeInfo);
		if(!b){
			res.add("status", -1);
			res.add("msg", "获取验证码太过频繁，请稍后再试");	
			return true;
		}
		
		String[] s = new String[]{code,"三"};
		SendSmsUtil.sendSms(phone, "431221", s,tbSmsInfoDao);
		
		res.add("status", 1);
		res.add("msg", "短信发送成功");
		
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		ForgetPwdGetCodeParam myParam = (ForgetPwdGetCodeParam)logicParam;
		String phone = myParam.getPhone();
		if(StringUtil.isNullOrEmpty(phone)){
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
