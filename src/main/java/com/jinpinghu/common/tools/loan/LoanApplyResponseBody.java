package com.jinpinghu.common.tools.loan;

import java.io.Serializable;

public class LoanApplyResponseBody extends ApiResponseBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4468030362393630442L;
	
	private String APPLY_CODE;
	
	public LoanApplyResponseBody() {}

	public LoanApplyResponseBody(String APPLY_CODE) {
		super();
		this.setAPPLY_CODE(APPLY_CODE);
	}



	public String getAPPLY_CODE() {
		return APPLY_CODE;
	}



	public void setAPPLY_CODE(String aPPLY_CODE) {
		APPLY_CODE = aPPLY_CODE;
	}


}
