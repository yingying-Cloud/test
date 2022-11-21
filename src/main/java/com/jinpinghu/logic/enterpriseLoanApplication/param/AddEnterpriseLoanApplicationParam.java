package com.jinpinghu.logic.enterpriseLoanApplication.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class AddEnterpriseLoanApplicationParam extends BaseZLogicParam{
	public AddEnterpriseLoanApplicationParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO �Զ����ɵĹ��캯�����
	}
	
	
	public AddEnterpriseLoanApplicationParam(String _userId, String _apiKey, HttpServletRequest request, String id,
			String enterpriseId, String loanAmount, String startTime, String endTime, String useInstruction,
			String file, String bankBorrow, String businessIncome, String currentAssets, String customerName,
			String financialStatement, String fixedAssets, String guarantyStyle, String guarantyUnit, String handleBank,
			String idNumber, String idType, String loanTime, String ownershipInterest, String receivables,
			String totalAssets, String totalIndebtedness, String totalProfit) {
		super(_userId, _apiKey, request);
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.loanAmount = loanAmount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.useInstruction = useInstruction;
		this.file = file;
		this.bankBorrow = bankBorrow;
		this.businessIncome = businessIncome;
		this.currentAssets = currentAssets;
		this.customerName = customerName;
		this.financialStatement = financialStatement;
		this.fixedAssets = fixedAssets;
		this.guarantyStyle = guarantyStyle;
		this.guarantyUnit = guarantyUnit;
		this.handleBank = handleBank;
		this.idNumber = idNumber;
		this.idType = idType;
		this.loanTime = loanTime;
		this.ownershipInterest = ownershipInterest;
		this.receivables = receivables;
		this.totalAssets = totalAssets;
		this.totalIndebtedness = totalIndebtedness;
		this.totalProfit = totalProfit;
	}


	private String id;
	private String enterpriseId;
	private String loanAmount;
	private String startTime;
	private String endTime;
	private String useInstruction;
	private String file;
	private String bankBorrow;
	private String businessIncome;
	private String currentAssets;
	private String customerName;
	private String financialStatement;
	private String fixedAssets;
	private String guarantyStyle;
	private String guarantyUnit;
	private String handleBank;
	private String idNumber;
	private String idType;
	private String loanTime;
	private String ownershipInterest;
	private String receivables;
	private String totalAssets;
	private String totalIndebtedness;
	private String totalProfit;

	public String getBankBorrow() {
		return bankBorrow;
	}

	public void setBankBorrow(String bankBorrow) {
		this.bankBorrow = bankBorrow;
	}

	public String getBusinessIncome() {
		return businessIncome;
	}

	public void setBusinessIncome(String businessIncome) {
		this.businessIncome = businessIncome;
	}

	public String getCurrentAssets() {
		return currentAssets;
	}

	public void setCurrentAssets(String currentAssets) {
		this.currentAssets = currentAssets;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFinancialStatement() {
		return financialStatement;
	}

	public void setFinancialStatement(String financialStatement) {
		this.financialStatement = financialStatement;
	}

	public String getFixedAssets() {
		return fixedAssets;
	}

	public void setFixedAssets(String fixedAssets) {
		this.fixedAssets = fixedAssets;
	}

	public String getGuarantyStyle() {
		return guarantyStyle;
	}

	public void setGuarantyStyle(String guarantyStyle) {
		this.guarantyStyle = guarantyStyle;
	}

	public String getGuarantyUnit() {
		return guarantyUnit;
	}

	public void setGuarantyUnit(String guarantyUnit) {
		this.guarantyUnit = guarantyUnit;
	}

	public String getHandleBank() {
		return handleBank;
	}

	public void setHandleBank(String handleBank) {
		this.handleBank = handleBank;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}

	public String getOwnershipInterest() {
		return ownershipInterest;
	}

	public void setOwnershipInterest(String ownershipInterest) {
		this.ownershipInterest = ownershipInterest;
	}

	public String getReceivables() {
		return receivables;
	}

	public void setReceivables(String receivables) {
		this.receivables = receivables;
	}

	public String getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}

	public String getTotalIndebtedness() {
		return totalIndebtedness;
	}

	public void setTotalIndebtedness(String totalIndebtedness) {
		this.totalIndebtedness = totalIndebtedness;
	}

	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUseInstruction() {
		return useInstruction;
	}

	public void setUseInstruction(String useInstruction) {
		this.useInstruction = useInstruction;
	}


	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
