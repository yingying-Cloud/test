package com.jinpinghu.logic.weChat;

import javax.persistence.EntityManager;

import net.sf.json.JSONObject;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.common.tools.WeChatUtil;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weChat.param.GetAccessTokenParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAccessTokenLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAccessTokenParam myParam = (GetAccessTokenParam) logicParam;
		String appid = myParam.getAppid();
		String secret = myParam.getSecret();
		
		JSONObject tokenJO = HttpRequestUtil.getAccessToken(appid, secret);
		if(tokenJO.has("errcode")){
			res.add("status", -3);
			res.add("msg", "验证失败！");
			res.add("result", tokenJO);
			return false;
		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		res.add("result", tokenJO);
		WeChatUtil.setMsg(res.toString());
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) {
		GetAccessTokenParam myParam = (GetAccessTokenParam) logicParam;
		String appid = myParam.getAppid();
		String secret = myParam.getSecret();
		
		if(StringUtil.isNullOrEmpty(appid)
				|| StringUtil.isNullOrEmpty(secret)) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空！");
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
