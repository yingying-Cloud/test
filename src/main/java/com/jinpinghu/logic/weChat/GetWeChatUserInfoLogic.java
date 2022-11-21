package com.jinpinghu.logic.weChat;

import javax.persistence.EntityManager;

import net.sf.json.JSONObject;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weChat.param.GetWeChatUserInfoParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWeChatUserInfoLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam getUserListParam,
			ZSimpleJsonObject res, EntityManager em) throws Exception {
		
		GetWeChatUserInfoParam myParam = (GetWeChatUserInfoParam) getUserListParam;
		String openId = myParam.getOpenId();
		String accessToken = myParam.getAccessToken();
		//https://api.weixin.qq.com/sns/userinfo?access_token=
		//https://api.weixin.qq.com/cgi-bin/user/info?access_token=
		String urlNameString = "https://api.weixin.qq.com/sns/userinfo?access_token="
					+accessToken+"&openid="+openId+"&lang=zh_CN";
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
		GetWeChatUserInfoParam myParam = (GetWeChatUserInfoParam) getUserListParam;
		String openId = myParam.getOpenId();
		String accessToken = myParam.getAccessToken();
		if(StringUtil.isNullOrEmpty(openId)
				||StringUtil.isNullOrEmpty(accessToken)){
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
