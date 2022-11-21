package com.jinpinghu.common.tools.loan;

import java.io.Serializable;

public class LoanApplyResponse extends ApiResponseBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4468030362393630442L;
	
	private ApiResponseHead head;
	private LoanApplyResponseBody body;
	
	public LoanApplyResponse() {}
	public ApiResponseHead getHead() {
		return head;
	}
	public void setHead(ApiResponseHead head) {
		this.head = head;
	}
	public LoanApplyResponseBody getBody() {
		return body;
	}
	public void setBody(LoanApplyResponseBody body) {
		this.body = body;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
