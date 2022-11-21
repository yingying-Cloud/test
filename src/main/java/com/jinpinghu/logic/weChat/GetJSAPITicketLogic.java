package com.jinpinghu.logic.weChat;

import javax.persistence.EntityManager;

import net.sf.json.JSONObject;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.weChat.param.GetJSAPITicketParam;

import fw.jbiz.common.helper.StringUtil;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetJSAPITicketLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetJSAPITicketParam myParam = (GetJSAPITicketParam) logicParam;
		String access_token = myParam.getAccess_token();
		
		JSONObject ticketJO = HttpRequestUtil.getJSAPITicket(access_token);
		if(ticketJO.getInt("errcode") != 0){
			res.add("status", -3);
			res.add("msg", "验证失败！");
			res.add("result", ticketJO);
			return false;
		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		res.add("result", ticketJO);
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) {
		GetJSAPITicketParam myParam = (GetJSAPITicketParam) logicParam;
		String access_token = myParam.getAccess_token();
		
		if(StringUtil.isNullOrEmpty(access_token)) {
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
