package com.jinpinghu.common.tools.loan;

class ApiRequestHead implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2657390316167184699L;
	private String TRAN_DATE;
	private String CONSUMER_SEQ_NO;
	private String CONSUMER_ID;
	private String SERVICE_CODE;
	private String SERVICE_SCENE;
	private String TRAN_TIMESTAMP;
	private String CHANNEL;
	private String PLATNUM;
	
	public ApiRequestHead() {}
	
	public ApiRequestHead(String tRAN_DATE, String cONSUMER_SEQ_NO, String cONSUMER_ID, String sERVICE_CODE,
			String sERVICE_SCENE, String tRAN_TIMESTAMP, String cHANNEL, String pLATNUM) {
		super();
		TRAN_DATE = tRAN_DATE;
		CONSUMER_SEQ_NO = cONSUMER_SEQ_NO;
		CONSUMER_ID = cONSUMER_ID;
		SERVICE_CODE = sERVICE_CODE;
		SERVICE_SCENE = sERVICE_SCENE;
		TRAN_TIMESTAMP = tRAN_TIMESTAMP;
		CHANNEL = cHANNEL;
		PLATNUM = pLATNUM;
	}
	public String getTRAN_DATE() {
		return TRAN_DATE;
	}
	public void setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}
	public String getCONSUMER_SEQ_NO() {
		return CONSUMER_SEQ_NO;
	}
	public void setCONSUMER_SEQ_NO(String cONSUMER_SEQ_NO) {
		CONSUMER_SEQ_NO = cONSUMER_SEQ_NO;
	}
	public String getCONSUMER_ID() {
		return CONSUMER_ID;
	}
	public void setCONSUMER_ID(String cONSUMER_ID) {
		CONSUMER_ID = cONSUMER_ID;
	}
	public String getSERVICE_CODE() {
		return SERVICE_CODE;
	}
	public void setSERVICE_CODE(String sERVICE_CODE) {
		SERVICE_CODE = sERVICE_CODE;
	}
	public String getSERVICE_SCENE() {
		return SERVICE_SCENE;
	}
	public void setSERVICE_SCENE(String sERVICE_SCENE) {
		SERVICE_SCENE = sERVICE_SCENE;
	}
	public String getTRAN_TIMESTAMP() {
		return TRAN_TIMESTAMP;
	}
	public void setTRAN_TIMESTAMP(String tRAN_TIMESTAMP) {
		TRAN_TIMESTAMP = tRAN_TIMESTAMP;
	}
	public String getCHANNEL() {
		return CHANNEL;
	}
	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	public String getPLATNUM() {
		return PLATNUM;
	}
	public void setPLATNUM(String pLATNUM) {
		PLATNUM = pLATNUM;
	}
	
}
