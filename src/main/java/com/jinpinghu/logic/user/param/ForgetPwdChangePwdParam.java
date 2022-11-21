package com.jinpinghu.logic.user.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class ForgetPwdChangePwdParam extends BaseZLogicParam {

	public ForgetPwdChangePwdParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}

	private String phone;
	private String tempTicket;
	private String newPwd;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTempTicket() {
		return tempTicket;
	}

	public void setTempTicket(String tempTicket) {
		this.tempTicket = tempTicket;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}
