package com.jinpinghu.logic.login.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class LoginParam extends BaseZLogicParam {

	public LoginParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}

	private String account;
	private String password;
	private String wxId;
	private String headPic;
	private String cashRegisterId;
	private String cashRegisterVersion;

	public String getCashRegisterId() {
		return cashRegisterId;
	}

	public void setCashRegisterId(String cashRegisterId) {
		this.cashRegisterId = cashRegisterId;
	}

	public String getCashRegisterVersion() {
		return cashRegisterVersion;
	}

	public void setCashRegisterVersion(String cashRegisterVersion) {
		this.cashRegisterVersion = cashRegisterVersion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

}
