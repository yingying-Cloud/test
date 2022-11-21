package com.jinpinghu.logic.weChat;

import javax.persistence.EntityManager;

import net.sf.json.JSONObject;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weChat.param.GetWeChatOpenAndAccessTokenParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWeChatOpenAndAccessTokenLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam getUserListParam,
			ZSimpleJsonObject res, EntityManager em) throws Exception {
		
		GetWeChatOpenAndAccessTokenParam myParam = (GetWeChatOpenAndAccessTokenParam) getUserListParam;
		String appid = myParam.getAppid();
		String secret = myParam.getSecret();
		String code = myParam.getCode();
		String urlNameString = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+
				appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		
		String result = HttpRequestUtil.sendGet(urlNameString, null);
		JSONObject jsonObejct = JSONObject.fromObject(result);
		res.add("result", jsonObejct);
		res.add("status", 1);
		res.add("msg", "成功");
	    return true;
	}


	@Override
	protected boolean validate(ZLogicParam getUserListParam,
			ZSimpleJsonObject res, EntityManager em) {
		GetWeChatOpenAndAccessTokenParam myParam = (GetWeChatOpenAndAccessTokenParam) getUserListParam;
		String appid = myParam.getAppid();
		String secret = myParam.getSecret();
		String code = myParam.getCode();
		if(StringUtil.isNullOrEmpty(appid)
				||StringUtil.isNullOrEmpty(secret)
				||StringUtil.isNullOrEmpty(code)){
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam objParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		return true;
	}

}
