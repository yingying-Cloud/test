package com.jinpinghu.common.tools.loan;

import java.io.Serializable;

public class ApiResponseHead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -232681545410248849L;
	private String RSPMSG;
	private String TRAN_TIMESTAMP;
	private String SERVICE_SCENE;
	private String TRAN_DATE;
	private String CHANNEL;
	private String CONSUMER_ID;
	private String PLATNUM;
	private String SERVICE_CODE;
	private String CONSUMER_SEQ_NO;
	private String RSPCODE;
	private String _rd;
	private String _rm;
	public String get_rd() {
		return _rd;
	}
	public void set_rd(String _rd) {
		this._rd = _rd;
	}
	public String get_rm() {
		return _rm;
	}
	public void set_rm(String _rm) {
		this._rm = _rm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public ApiResponseHead() {
	}
	public ApiResponseHead(String rSPMSG, String tRAN_TIMESTAMP, String sERVICE_SCENE, String tRAN_DATE, String cHANNEL,
			String cONSUMER_ID, String pLATNUM, String sERVICE_CODE, String cONSUMER_SEQ_NO, String rSPCODE) {
		super();
		RSPMSG = rSPMSG;
		TRAN_TIMESTAMP = tRAN_TIMESTAMP;
		SERVICE_SCENE = sERVICE_SCENE;
		TRAN_DATE = tRAN_DATE;
		CHANNEL = cHANNEL;
		CONSUMER_ID = cONSUMER_ID;
		PLATNUM = pLATNUM;
		SERVICE_CODE = sERVICE_CODE;
		CONSUMER_SEQ_NO = cONSUMER_SEQ_NO;
		RSPCODE = rSPCODE;
	}
	public String getRSPMSG() {
		return RSPMSG;
	}
	public void setRSPMSG(String rSPMSG) {
		RSPMSG = rSPMSG;
	}
	public String getTRAN_TIMESTAMP() {
		return TRAN_TIMESTAMP;
	}
	public void setTRAN_TIMESTAMP(String tRAN_TIMESTAMP) {
		TRAN_TIMESTAMP = tRAN_TIMESTAMP;
	}
	public String getSERVICE_SCENE() {
		return SERVICE_SCENE;
	}
	public void setSERVICE_SCENE(String sERVICE_SCENE) {
		SERVICE_SCENE = sERVICE_SCENE;
	}
	public String getTRAN_DATE() {
		return TRAN_DATE;
	}
	public void setTRAN_DATE(String tRAN_DATE) {
		TRAN_DATE = tRAN_DATE;
	}
	public String getCHANNEL() {
		return CHANNEL;
	}
	public void setCHANNEL(String cHANNEL) {
		CHANNEL = cHANNEL;
	}
	public String getCONSUMER_ID() {
		return CONSUMER_ID;
	}
	public void setCONSUMER_ID(String cONSUMER_ID) {
		CONSUMER_ID = cONSUMER_ID;
	}
	public String getPLATNUM() {
		return PLATNUM;
	}
	public void setPLATNUM(String pLATNUM) {
		PLATNUM = pLATNUM;
	}
	public String getSERVICE_CODE() {
		return SERVICE_CODE;
	}
	public void setSERVICE_CODE(String sERVICE_CODE) {
		SERVICE_CODE = sERVICE_CODE;
	}
	public String getCONSUMER_SEQ_NO() {
		return CONSUMER_SEQ_NO;
	}
	public void setCONSUMER_SEQ_NO(String cONSUMER_SEQ_NO) {
		CONSUMER_SEQ_NO = cONSUMER_SEQ_NO;
	}
	public String getRSPCODE() {
		return RSPCODE;
	}
	public void setRSPCODE(String rSPCODE) {
		RSPCODE = rSPCODE;
	}
	
	
}
