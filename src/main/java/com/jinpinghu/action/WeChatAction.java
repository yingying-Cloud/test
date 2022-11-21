package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.common.tools.WeChatUtil;
import com.jinpinghu.logic.weChat.GetAccessTokenLogic;
import com.jinpinghu.logic.weChat.GetWeChatOpenAndAccessTokenLogic;
import com.jinpinghu.logic.weChat.GetWeChatUserInfo2Logic;
import com.jinpinghu.logic.weChat.GetWeChatUserInfoLogic;
import com.jinpinghu.logic.weChat.param.GetAccessTokenParam;
import com.jinpinghu.logic.weChat.param.GetWeChatOpenAndAccessTokenParam;
import com.jinpinghu.logic.weChat.param.GetWeChatUserInfo2Param;
import com.jinpinghu.logic.weChat.param.GetWeChatUserInfoParam;
import com.jinpinghu.logic.weChat.GetJSAPITicketLogic;
import com.jinpinghu.logic.weChat.param.GetJSAPITicketParam;

/** 微信 */
@Path("weChat")
@Produces("application/json;charset=UTF-8")
public class WeChatAction extends BaseZAction {
	
	/**
	 * 获取AccessToken
	 * @author Ejectam719
	 * @createTime 2017年5月15日 下午1:40:56
	 * @updater Ejectam719
	 * @updateTime 2017年5月15日 下午1:40:56
	 */
	@POST
	@Path("getAccessToken.do")
	public String getAccessToken(
			@FormParam("appid") String appid,
			@FormParam("secret") String secret,
			@Context HttpServletRequest request) {
		String msg = WeChatUtil.getMsg();
		if(WeChatUtil.getMsg()!=null){
			return msg;
		}
		GetAccessTokenParam myParam = new GetAccessTokenParam(null, null, request);
		myParam.setAppid(appid);
		myParam.setSecret(secret);
		return new GetAccessTokenLogic().process(myParam);
	}
	
	@POST
	@Path("getJSAPITicket.do")
	public String getJSAPITicket(
			@FormParam("access_token") String access_token,
			@Context HttpServletRequest request) {
		GetJSAPITicketParam myParam = new GetJSAPITicketParam(null, null, request);
		myParam.setAccess_token(access_token);
		return new GetJSAPITicketLogic().process(myParam);
	}
	
	
	/**
	 * 获取微信openId与accessToken
	 */
	@POST
	@Path("getWeChatOpenAndAccessToken.do")
	public String getWeChatOpenAndAccessToken(
			@FormParam("appid") String appid,
			@FormParam("secret") String secret,
			@FormParam("code") String code,
			@Context HttpServletRequest request) {
		GetWeChatOpenAndAccessTokenParam myParam = new GetWeChatOpenAndAccessTokenParam(null, null, request);
		myParam.setAppid(appid);
		myParam.setSecret(secret);
		myParam.setCode(code);
		return new GetWeChatOpenAndAccessTokenLogic().process(myParam);
	}
	
	/**
	 * 获取微信用户信息
	 */
	@POST
	@Path("getWeChatUserInfo.do")
	public String getWeChatUserInfo(
			@FormParam("openId") String openId,
			@FormParam("accessToken") String accessToken,
			@Context HttpServletRequest request) {
		GetWeChatUserInfoParam myParam = new GetWeChatUserInfoParam(null, null, request);
		myParam.setOpenId(openId);
		myParam.setAccessToken(accessToken);
		return new GetWeChatUserInfoLogic().process(myParam);
	}
	
	/**
	 * 获取微信用户信息2
	 */
	@POST
	@Path("getWeChatUserInfo2.do")
	public String getWeChatUserInfo2(
			@FormParam("openId") String openId,
			@FormParam("accessToken") String accessToken,
			@Context HttpServletRequest request) {
		GetWeChatUserInfo2Param myParam = new GetWeChatUserInfo2Param(null, null, request);
		myParam.setOpenId(openId);
		myParam.setAccessToken(accessToken);
		return new GetWeChatUserInfo2Logic().process(myParam);
	}
	
	
	
}
