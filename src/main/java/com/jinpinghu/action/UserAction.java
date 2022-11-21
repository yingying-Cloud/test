package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.logic.user.AddOrUpdUserLogic;
import com.jinpinghu.logic.user.AddUserEnterpriseRelLogic;
import com.jinpinghu.logic.user.ChangePasswordLogic;
import com.jinpinghu.logic.user.DelUserLogic;
import com.jinpinghu.logic.user.FindUserListLogic;
import com.jinpinghu.logic.user.ForgetPwdChangePwdLogic;
import com.jinpinghu.logic.user.ForgetPwdGetCodeLogic;
import com.jinpinghu.logic.user.ForgetPwdSetCodeLogic;
import com.jinpinghu.logic.user.GetMenuListByRoleLogic;
import com.jinpinghu.logic.user.GetUserEnterpriseRelListLogic;
import com.jinpinghu.logic.user.GetUserInfoByMobileLogic;
import com.jinpinghu.logic.user.GetUserInfoByUsername2Logic;
import com.jinpinghu.logic.user.GetUserIntegralLogic;
import com.jinpinghu.logic.user.LoginLogic;
import com.jinpinghu.logic.user.RegisteredLogic;
import com.jinpinghu.logic.user.RegisteredPhoneLogic;
import com.jinpinghu.logic.user.ResetPasswordLogic;
import com.jinpinghu.logic.user.WechatLoginLogic;
import com.jinpinghu.logic.user.param.AddOrUpdUserParam;
import com.jinpinghu.logic.user.param.AddUserEnterpriseRelParam;
import com.jinpinghu.logic.user.param.ChangePasswordParam;
import com.jinpinghu.logic.user.param.DelUserParam;
import com.jinpinghu.logic.user.param.FindUserListParam;
import com.jinpinghu.logic.user.param.ForgetPwdChangePwdParam;
import com.jinpinghu.logic.user.param.ForgetPwdGetCodeParam;
import com.jinpinghu.logic.user.param.ForgetPwdSetCodeParam;
import com.jinpinghu.logic.user.param.GetMenuListByRoleParam;
import com.jinpinghu.logic.user.param.GetUserEnterpriseRelListParam;
import com.jinpinghu.logic.user.param.GetUserInfoByMobileParam;
import com.jinpinghu.logic.user.param.LoginParam;
import com.jinpinghu.logic.user.param.RegisteredParam;
import com.jinpinghu.logic.user.param.RegisteredPhoneParam;
import com.jinpinghu.logic.user.param.WechatLoginParam;

/** 用户 */
@Path("user")
@Produces("application/json;charset=UTF-8")
public class UserAction extends BaseZAction {
	

	/**
	 * �ֻ���֤�뷢��
	 */
	@POST
	@Path("registeredPhone.do")
	public String registeredPhone(
			@FormParam("mobile") String mobile,
			@Context HttpServletRequest request) {
		RegisteredPhoneParam myParam = new RegisteredPhoneParam(null, null, request);
		myParam.setMobile(mobile);
		return new RegisteredPhoneLogic().process(myParam);
	}
	
	/**
	 * ע��
	 */
	@POST
	@Path("registered.do")
	public String registered(
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("mobile") String mobile,
			@FormParam("code") String code,
			@FormParam("role") String role,
			@FormParam("wxId") String wxId,
			@FormParam("headPic") String headPic,
			@Context HttpServletRequest request) {
		RegisteredParam myParam = new RegisteredParam(null, null, request);
		myParam.setUsername(username);
		myParam.setPassword(password);
		myParam.setMobile(mobile);
		myParam.setCode(code);
		myParam.setRole(role);
		myParam.setWxId(wxId);
		myParam.setHeadPic(headPic);
		return new RegisteredLogic().process(myParam);
	}
	
	/**
	 * 根据角色获取菜单列表
	 */
	@POST
	@Path("getMenuListByRole.do")
	public String getMenuListByRole(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("roleId") Integer roleId,
			@FormParam("parentId") String parentId,
			@Context HttpServletRequest request) {
		GetMenuListByRoleParam myParam = new GetMenuListByRoleParam(userId, apiKey, request);
		myParam.setRoleId(roleId);
		myParam.setParentId(parentId);
		return new GetMenuListByRoleLogic().process(myParam);
	}
	
	/**
	 * 登录
	 */
	@POST
	@Path("login.do")
	public String login(
			@FormParam("account") String account,
			@FormParam("password") String password,
			@FormParam("wxId") String wxId,
			@FormParam("headPic") String headPic,
			@Context HttpServletRequest request) {
		LoginParam myParam = new LoginParam(null, null, request);
		myParam.setAccount(account);
		myParam.setPassword(password);
		myParam.setWxId(wxId);
		myParam.setHeadPic(headPic);
		return new LoginLogic().process(myParam);
	}
	
	@POST
	@Path("wechatLogin.do")
	public String wechatLogin(
			@FormParam("wxId") String wxId,
			@Context HttpServletRequest request) {
		WechatLoginParam myParam = new WechatLoginParam(null, null, request);
		myParam.setWxId(wxId);
		return new WechatLoginLogic().process(myParam);
	}
	
	/*
	 * 修改密码
	 */
	
	@POST
	@Path("changePassword.do")
	public String changePassword(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("oldPassword") String oldPassword,
			@FormParam("newPassword") String newPassword,
			@Context HttpServletRequest request) {
		ChangePasswordParam myParam = new ChangePasswordParam(userId, apiKey, request);
		myParam.setOldPassword(oldPassword);
		myParam.setNewPassword(newPassword);
		return new ChangePasswordLogic().process(myParam);
	}
	
	/**
	 * 忘记密码-获取验证码
	 */
	@POST
	@Path("forgetPwdGetCode.do")
	public String forgetPwdGetCode(
			@FormParam("phone") String phone,
			@Context HttpServletRequest request) {
		ForgetPwdGetCodeParam myParam = new ForgetPwdGetCodeParam(null, null, request);
		myParam.setPhone(phone);
		return new ForgetPwdGetCodeLogic().process(myParam);
	}
	
	/**
	 * 忘记密码-提交验证码
	 */
	@POST
	@Path("forgetPwdSetCode.do")
	public String forgetPwdSetCode(
			@FormParam("phone") String phone,
			@FormParam("code") String code,
			@Context HttpServletRequest request) {
		ForgetPwdSetCodeParam myParam = new ForgetPwdSetCodeParam(null, null, request);
		myParam.setPhone(phone);
		myParam.setCode(code);
		return new ForgetPwdSetCodeLogic().process(myParam);
	}
	
	/**
	 * 忘记密码-修改密码
	 */
	@POST
	@Path("forgetPwdChangePwd.do")
	public String forgetPwdChangePwd(
			@FormParam("phone") String phone,
			@FormParam("tempTicket") String tempTicket,
			@FormParam("newPwd") String newPwd,
			@Context HttpServletRequest request) {
		ForgetPwdChangePwdParam myParam = new ForgetPwdChangePwdParam(null, null, request);
		myParam.setPhone(phone);
		myParam.setTempTicket(tempTicket);
		myParam.setNewPwd(newPwd);
		return new ForgetPwdChangePwdLogic().process(myParam);
	}

	/**
	 * 增加或修改用户信息
	 * @author yanchengjie
	 * @date 2019/8/28 9:45
	 */
	@POST
	@Path("addOrUpdUser.do")
	public String addOrUpdUser(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("mobile") String mobile,
			@FormParam("password") String password,
			@FormParam("name") String name,
			@FormParam("id") String id,  //更新需要,添加不需要
			@FormParam("roleId") String roleId,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("dscd") String dscd,
			@Context HttpServletRequest request) {
		AddOrUpdUserParam myParam=new AddOrUpdUserParam(userId,apiKey,request);
		myParam.setMobile(StringUtil.isNullOrEmpty(mobile)?null:mobile);
		myParam.setPassword(StringUtil.isNullOrEmpty(password)?null:password);
		myParam.setName(StringUtil.isNullOrEmpty(name)?null:name);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
		myParam.setRoleId(StringUtil.isNullOrEmpty(roleId)?null:Integer.valueOf(roleId));
		myParam.setEnterpriseId(StringUtil.isNullOrEmpty(enterpriseId)?null:Integer.valueOf(enterpriseId));
		myParam.setDscd(dscd);
		return new AddOrUpdUserLogic().process(myParam);
	}

	/**
	 * 删除用户信息
	 * @author yanchengjie
	 * @date 2019/8/28 10:47
	 */
	@POST
	@Path("delUser.do")
	public String delUser(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelUserParam myParam=new DelUserParam(userId,apiKey,request);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
		return new DelUserLogic().process(myParam);
	}

	/**
	 * 获取用户信息列表
	 * @author yanchengjie
	 * @date 2019/8/28 11:04
	 */
	@POST
	@Path("findUserList.do")
	public String findUserList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("name") String name,
			@FormParam("mobile") String mobile,
			@FormParam("roleId") String roleId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@FormParam("dscd") String dscd,
			@Context HttpServletRequest request) {
		FindUserListParam myParam=new FindUserListParam(userId,apiKey,request);
		myParam.setName(StringUtil.isNullOrEmpty(name)?null:name);
		myParam.setMobile(mobile);
		myParam.setRoleId(roleId);
		myParam.setNowPage(StringUtil.isNullOrEmpty(nowPage)?null:Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtil.isNullOrEmpty(pageCount)?null:Integer.valueOf(pageCount));
		myParam.setDscd(dscd);
		return new FindUserListLogic().process(myParam);

	}
	/**
	 * 获取用户详情
	 * @param userId
	 * @param apiKey
	 * @param id
	 * @param request
	 * @return
	 */
	@POST
	@Path("getUserInfoByMobile.do")
	public String getUserInfoByMobile(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("mobile") String mobile,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetUserInfoByMobileParam myParam=new GetUserInfoByMobileParam(userId,apiKey,request);
		myParam.setMobile(mobile);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
		return new GetUserInfoByMobileLogic().process(myParam);
	}
	
	/*
	 * 重置密码
	 */
	
	@POST
	@Path("resetPassword.do")
	public String resetPassword(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		ChangePasswordParam myParam = new ChangePasswordParam(userId, apiKey, request);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
		return new ResetPasswordLogic().process(myParam);
	}
	
	@POST
	@Path("addUserEnterpriseRel.do")
	public String addUserEnterpriseRel(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("userTabId") Integer userTabId,
			@Context HttpServletRequest request) {
		AddUserEnterpriseRelParam myParam = new AddUserEnterpriseRelParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setUserTabId(userTabId);
		return new AddUserEnterpriseRelLogic().process(myParam);
	}
	
	@POST
	@Path("getUserEnterpriseRelList.do")
	public String getUserEnterpriseRelList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("userTabId") String userTabId,
			@FormParam("name") String name,
			@FormParam("isIn") String isIn,
			@FormParam("dscd") String dscd,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetUserEnterpriseRelListParam myParam = new GetUserEnterpriseRelListParam(userId, apiKey, request);
		myParam.setUserTabId(userTabId);
		myParam.setName(name);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		myParam.setIsIn(isIn);
		myParam.setDscd(dscd);
		return new GetUserEnterpriseRelListLogic().process(myParam);
	}
	
	@POST
	@Path("getUserIntegral.do")
	public String getUserIntegral(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		GetUserInfoByMobileParam myParam=new GetUserInfoByMobileParam(userId,apiKey,request);
		myParam.setId(StringUtil.isNullOrEmpty(id)?null:Integer.valueOf(id));
		return new GetUserIntegralLogic().process(myParam);
	}
	/**
	 * 获取用户详情
	 * @param userId
	 * @param apiKey
	 * @param id
	 * @param request
	 * @return
	 */
	@POST
	@Path("getUserInfoByUsername2.do")
	public String getUserInfoByUsername2(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("username2") String username2,
			@Context HttpServletRequest request) {
		GetUserInfoByMobileParam myParam=new GetUserInfoByMobileParam(userId,apiKey,request);
		myParam.setUsername2(username2);
		return new GetUserInfoByUsername2Logic().process(myParam);
	}
}
