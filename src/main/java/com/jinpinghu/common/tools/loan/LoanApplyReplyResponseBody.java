package com.jinpinghu.common.tools.loan;

import java.io.Serializable;

public class LoanApplyReplyResponseBody extends ApiResponseBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8946873434854290777L;
	
	private String TLR_NAME;
	private String REMARK;
	private String TLR_TEL;
	private String LOAN_AMOUNT;
	private String LOAN_TIME;
	private String CUSTOMER_NAME;
	private String RESULT;
	private String ID_NUMBER;
	private String ID_TYPE;
	
	
	public LoanApplyReplyResponseBody() {
		super();
	}


	public LoanApplyReplyResponseBody(String tLR_NAME, String rEMARK, String tLR_TEL, String lOAN_AMOUNT,
			String lOAN_TIME, String cUSTOMER_NAME, String rESULT, String iD_NUMBER, String iD_TYPE) {
		super();
		TLR_NAME = tLR_NAME;
		REMARK = rEMARK;
		TLR_TEL = tLR_TEL;
		LOAN_AMOUNT = lOAN_AMOUNT;
		LOAN_TIME = lOAN_TIME;
		CUSTOMER_NAME = cUSTOMER_NAME;
		RESULT = rESULT;
		ID_NUMBER = iD_NUMBER;
		ID_TYPE = iD_TYPE;
	}


	public String getTLR_NAME() {
		return TLR_NAME;
	}


	public void setTLR_NAME(String tLR_NAME) {
		TLR_NAME = tLR_NAME;
	}


	public String getREMARK() {
		return REMARK;
	}


	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}


	public String getTLR_TEL() {
		return TLR_TEL;
	}


	public void setTLR_TEL(String tLR_TEL) {
		TLR_TEL = tLR_TEL;
	}


	public String getLOAN_AMOUNT() {
		return LOAN_AMOUNT;
	}


	public void setLOAN_AMOUNT(String lOAN_AMOUNT) {
		LOAN_AMOUNT = lOAN_AMOUNT;
	}


	public String getLOAN_TIME() {
		return LOAN_TIME;
	}


	public void setLOAN_TIME(String lOAN_TIME) {
		LOAN_TIME = lOAN_TIME;
	}


	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}


	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}


	public String getRESULT() {
		return RESULT;
	}


	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}


	public String getID_NUMBER() {
		return ID_NUMBER;
	}


	public void setID_NUMBER(String iD_NUMBER) {
		ID_NUMBER = iD_NUMBER;
	}


	public String getID_TYPE() {
		return ID_TYPE;
	}


	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
