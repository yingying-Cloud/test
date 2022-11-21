package com.jinpinghu.common.tools.loan;



public class ApiRequest implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5254563235100420815L;
	private ApiRequestHead head;
	private ApiRequestBody body;
	
	public ApiRequest() {}
	
	public ApiRequest(ApiRequestHead head, ApiRequestBody body) {
		super();
		this.head = head;
		this.body = body;
	}
	public ApiRequestHead getHead() {
		return head;
	}
	public void setHead(ApiRequestHead head) {
		this.head = head;
	}
	public ApiRequestBody getBody() {
		return body;
	}
	public void setBody(ApiRequestBody body) {
		this.body = body;
	}
	public long getSerialversionuid() {
		return serialVersionUID;
	}
	
}