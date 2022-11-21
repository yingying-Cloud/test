package com.jinpinghu.common.tools.loan;


public class LoanApplyReplyRequestBody extends ApiRequestBody implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7246962434338663904L;
	private String PTNAME;
	private String PTNUM;
	private String ID_TYPE;
	private String ID_NUMBER;
	private String APPLY_CODE;
	
	public LoanApplyReplyRequestBody() {}
	
	
	public LoanApplyReplyRequestBody(String pTNAME, String pTNUM, String iD_TYPE, String iD_NUMBER, String aPPLY_CODE) {
		super();
		PTNAME = pTNAME;
		PTNUM = pTNUM;
		ID_TYPE = iD_TYPE;
		ID_NUMBER = iD_NUMBER;
		APPLY_CODE = aPPLY_CODE;
	}
	public String getPTNAME() {
		return PTNAME;
	}
	public void setPTNAME(String pTNAME) {
		PTNAME = pTNAME;
	}
	public String getPTNUM() {
		return PTNUM;
	}
	public void setPTNUM(String pTNUM) {
		PTNUM = pTNUM;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}
	public String getID_NUMBER() {
		return ID_NUMBER;
	}
	public void setID_NUMBER(String iD_NUMBER) {
		ID_NUMBER = iD_NUMBER;
	}
	public String getAPPLY_CODE() {
		return APPLY_CODE;
	}
	public void setAPPLY_CODE(String aPPLY_CODE) {
		APPLY_CODE = aPPLY_CODE;
	}
	
}
