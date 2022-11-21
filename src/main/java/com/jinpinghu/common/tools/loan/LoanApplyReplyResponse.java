package com.jinpinghu.common.tools.loan;

import java.io.Serializable;

public class LoanApplyReplyResponse extends ApiResponseBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8946873434854290777L;
	
	private ApiResponseHead head;
	private LoanApplyReplyResponseBody body;
	
	public LoanApplyReplyResponse() {}
	public ApiResponseHead getHead() {
		return head;
	}
	public void setHead(ApiResponseHead head) {
		this.head = head;
	}
	public LoanApplyReplyResponseBody getBody() {
		return body;
	}
	public void setBody(LoanApplyReplyResponseBody body) {
		this.body = body;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
