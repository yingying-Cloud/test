package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.login.LoginLogic;
import com.jinpinghu.logic.login.param.LoginParam;
import com.jinpinghu.logic.login.RegisteredLogic;
import com.jinpinghu.logic.login.param.RegisteredParam;

/** 登陆 */
@Path("login")
@Produces("application/json;charset=UTF-8")
public class LoginAction extends BaseZAction {
	
	/**
	 * 登陆
	 */
	@POST
	@Path("login.do")
	public String login(
			@FormParam("usernameOrMobile") String usernameOrMobile,
			@FormParam("password") String password,
			@FormParam("wxId") String wxId,
			@FormParam("nickname") String nickname,
			@FormParam("sex") String sex,
			@FormParam("headPic") String headPic,
			@FormParam("cashRegisterId") String cashRegisterId,
			@FormParam("cashRegisterVersion") String cashRegisterVersion,
			@Context HttpServletRequest request) {
		LoginParam myParam = new LoginParam(null, null, request);
		myParam.setAccount(usernameOrMobile);
		myParam.setPassword(password);
		myParam.setWxId(wxId);
		myParam.setHeadPic(headPic);
		myParam.setCashRegisterId(cashRegisterId);
		myParam.setCashRegisterVersion(cashRegisterVersion);
		return new LoginLogic().process(myParam);
	}
	
	/**
	 * 注册
	 */
	@POST
	@Path("registered.do")
	public String registered(
			@FormParam("name") String name,
			@FormParam("idCode") String idCode,
			@FormParam("mobile") String mobile,
			@Context HttpServletRequest request) {
		RegisteredParam myParam = new RegisteredParam(null, null, request);
		myParam.setName(name);
		myParam.setIdCode(idCode);
		myParam.setMobile(mobile);
		return new RegisteredLogic().process(myParam);
	}
}
