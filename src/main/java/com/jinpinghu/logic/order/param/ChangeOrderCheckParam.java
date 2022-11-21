package com.jinpinghu.logic.order.param;

		import javax.servlet.http.HttpServletRequest;

		import com.jinpinghu.logic.BaseZLogicParam;

public class ChangeOrderCheckParam extends BaseZLogicParam{

	public ChangeOrderCheckParam(String _userId, String _apiKey,
								 HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public String getCheckJa() {
		return checkJa;
	}
	public void setCheckJa(String checkJa) {
		this.checkJa = checkJa;
	}
	private String checkJa;
}
