package com.jinpinghu.common.tools.loan;


public class LoanApplyRequestBody extends ApiRequestBody implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4329227538826734115L;
	private String PTNAME;
	private String PTNUM;
	private String CUSTOMER_NAME;
	private String ID_TYPE;
	private String ID_NUMBER;
	private String LOAN_AMOUNT;
	private String LOAN_TIME;
	private String START_TIME;
	private String END_TIME;
	private String LOAN_USE;
	private String GUARANTY_STYLE;
	private String GUARANTY_UNIT;
	private String HANDLE_BANK;
	private String FINANCIAL_STATEMENT;
	private String CURRENT_ASSETS;
	private String RECEIVABLES;
	private String FIXED_ASSETS;
	private String TOTAL_ASSETS;
	private String BANK_BORROW;
	private String TOTAL_INDEBTEDNESS;
	private String OWNERSHIP_INTEREST;
	private String BUSINESS_INCOME;
	private String TOTAL_PROFIT;
	private String EXT1;
	private String EXT2;
	private String EXT3;
	
	public LoanApplyRequestBody() {}
	
	public LoanApplyRequestBody(String pTNAME, String pTNUM, String cUSTOMER_NAME, String iD_TYPE, String iD_NUMBER,
			String lOAN_AMOUNT, String lOAN_TIME, String sTART_TIME, String eND_TIME, String lOAN_USE,
			String gUARANTY_STYLE, String gUARANTY_UNIT, String hANDLE_BANK, String fINANCIAL_STATEMENT,
			String cURRENT_ASSETS, String rECEIVABLES, String fIXED_ASSETS, String tOTAL_ASSETS, String bANK_BORROW,
			String tOTAL_INDEBTEDNESS, String oWNERSHIP_INTEREST, String bUSINESS_INCOME, String tOTAL_PROFIT,
			String eXT1, String eXT2, String eXT3) {
		super();
		PTNAME = pTNAME;
		PTNUM = pTNUM;
		CUSTOMER_NAME = cUSTOMER_NAME;
		ID_TYPE = iD_TYPE;
		ID_NUMBER = iD_NUMBER;
		LOAN_AMOUNT = lOAN_AMOUNT;
		LOAN_TIME = lOAN_TIME;
		START_TIME = sTART_TIME;
		END_TIME = eND_TIME;
		LOAN_USE = lOAN_USE;
		GUARANTY_STYLE = gUARANTY_STYLE;
		GUARANTY_UNIT = gUARANTY_UNIT;
		HANDLE_BANK = hANDLE_BANK;
		FINANCIAL_STATEMENT = fINANCIAL_STATEMENT;
		CURRENT_ASSETS = cURRENT_ASSETS;
		RECEIVABLES = rECEIVABLES;
		FIXED_ASSETS = fIXED_ASSETS;
		TOTAL_ASSETS = tOTAL_ASSETS;
		BANK_BORROW = bANK_BORROW;
		TOTAL_INDEBTEDNESS = tOTAL_INDEBTEDNESS;
		OWNERSHIP_INTEREST = oWNERSHIP_INTEREST;
		BUSINESS_INCOME = bUSINESS_INCOME;
		TOTAL_PROFIT = tOTAL_PROFIT;
		EXT1 = eXT1;
		EXT2 = eXT2;
		EXT3 = eXT3;
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
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
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
	public String getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public String getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getLOAN_USE() {
		return LOAN_USE;
	}
	public void setLOAN_USE(String lOAN_USE) {
		LOAN_USE = lOAN_USE;
	}
	public String getGUARANTY_STYLE() {
		return GUARANTY_STYLE;
	}
	public void setGUARANTY_STYLE(String gUARANTY_STYLE) {
		GUARANTY_STYLE = gUARANTY_STYLE;
	}
	public String getGUARANTY_UNIT() {
		return GUARANTY_UNIT;
	}
	public void setGUARANTY_UNIT(String gUARANTY_UNIT) {
		GUARANTY_UNIT = gUARANTY_UNIT;
	}
	public String getHANDLE_BANK() {
		return HANDLE_BANK;
	}
	public void setHANDLE_BANK(String hANDLE_BANK) {
		HANDLE_BANK = hANDLE_BANK;
	}
	public String getFINANCIAL_STATEMENT() {
		return FINANCIAL_STATEMENT;
	}
	public void setFINANCIAL_STATEMENT(String fINANCIAL_STATEMENT) {
		FINANCIAL_STATEMENT = fINANCIAL_STATEMENT;
	}
	public String getCURRENT_ASSETS() {
		return CURRENT_ASSETS;
	}
	public void setCURRENT_ASSETS(String cURRENT_ASSETS) {
		CURRENT_ASSETS = cURRENT_ASSETS;
	}
	public String getRECEIVABLES() {
		return RECEIVABLES;
	}
	public void setRECEIVABLES(String rECEIVABLES) {
		RECEIVABLES = rECEIVABLES;
	}
	public String getFIXED_ASSETS() {
		return FIXED_ASSETS;
	}
	public void setFIXED_ASSETS(String fIXED_ASSETS) {
		FIXED_ASSETS = fIXED_ASSETS;
	}
	public String getTOTAL_ASSETS() {
		return TOTAL_ASSETS;
	}
	public void setTOTAL_ASSETS(String tOTAL_ASSETS) {
		TOTAL_ASSETS = tOTAL_ASSETS;
	}
	public String getBANK_BORROW() {
		return BANK_BORROW;
	}
	public void setBANK_BORROW(String bANK_BORROW) {
		BANK_BORROW = bANK_BORROW;
	}
	public String getTOTAL_INDEBTEDNESS() {
		return TOTAL_INDEBTEDNESS;
	}
	public void setTOTAL_INDEBTEDNESS(String tOTAL_INDEBTEDNESS) {
		TOTAL_INDEBTEDNESS = tOTAL_INDEBTEDNESS;
	}
	public String getOWNERSHIP_INTEREST() {
		return OWNERSHIP_INTEREST;
	}
	public void setOWNERSHIP_INTEREST(String oWNERSHIP_INTEREST) {
		OWNERSHIP_INTEREST = oWNERSHIP_INTEREST;
	}
	public String getBUSINESS_INCOME() {
		return BUSINESS_INCOME;
	}
	public void setBUSINESS_INCOME(String bUSINESS_INCOME) {
		BUSINESS_INCOME = bUSINESS_INCOME;
	}
	public String getTOTAL_PROFIT() {
		return TOTAL_PROFIT;
	}
	public void setTOTAL_PROFIT(String tOTAL_PROFIT) {
		TOTAL_PROFIT = tOTAL_PROFIT;
	}
	public String getEXT1() {
		return EXT1;
	}
	public void setEXT1(String eXT1) {
		EXT1 = eXT1;
	}
	public String getEXT2() {
		return EXT2;
	}
	public void setEXT2(String eXT2) {
		EXT2 = eXT2;
	}
	public String getEXT3() {
		return EXT3;
	}
	public void setEXT3(String eXT3) {
		EXT3 = eXT3;
	}
	public long getSerialversionuid() {
		return serialVersionUID;
	}
}
