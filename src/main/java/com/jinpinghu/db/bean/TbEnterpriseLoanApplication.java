package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_enterprise_loan_application database table.
 * 
 */
@Entity
@Table(name="tb_enterprise_loan_application")
@NamedQuery(name="TbEnterpriseLoanApplication.findAll", query="SELECT t FROM TbEnterpriseLoanApplication t")
public class TbEnterpriseLoanApplication extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String applyCode;
	private String bankBorrow;
	private String businessIncome;
	private String currentAssets;
	private String customerName;
	private Integer delFlag;
	private Date endTime;
	private Integer enterpriseId;
	private String financialStatement;
	private String fixedAssets;
	private String guarantyStyle;
	private String guarantyUnit;
	private String handleBank;
	private String idNumber;
	private String idType;
	private Date inputTime;
	private String loanAmount;
	private String loanTime;
	private String ownershipInterest;
	private String receivables;
	private String remark;
	private Date startTime;
	private Integer status;
	private String tlrName;
	private String tlrTel;
	private String totalAssets;
	private String totalIndebtedness;
	private String totalProfit;
	private String useInstruction;

	public TbEnterpriseLoanApplication() {
	}
	
	


	public TbEnterpriseLoanApplication(String applyCode, String bankBorrow, String businessIncome, String currentAssets,
			String customerName, Integer delFlag, Date endTime, Integer enterpriseId, String financialStatement,
			String fixedAssets, String guarantyStyle, String guarantyUnit, String handleBank, String idNumber,
			String idType, Date inputTime, String loanAmount, String loanTime, String ownershipInterest,
			String receivables, String remark, Date startTime, Integer status, String tlrName, String tlrTel,
			String totalAssets, String totalIndebtedness, String totalProfit, String useInstruction) {
		super();
		this.applyCode = applyCode;
		this.bankBorrow = bankBorrow;
		this.businessIncome = businessIncome;
		this.currentAssets = currentAssets;
		this.customerName = customerName;
		this.delFlag = delFlag;
		this.endTime = endTime;
		this.enterpriseId = enterpriseId;
		this.financialStatement = financialStatement;
		this.fixedAssets = fixedAssets;
		this.guarantyStyle = guarantyStyle;
		this.guarantyUnit = guarantyUnit;
		this.handleBank = handleBank;
		this.idNumber = idNumber;
		this.idType = idType;
		this.inputTime = inputTime;
		this.loanAmount = loanAmount;
		this.loanTime = loanTime;
		this.ownershipInterest = ownershipInterest;
		this.receivables = receivables;
		this.remark = remark;
		this.startTime = startTime;
		this.status = status;
		this.tlrName = tlrName;
		this.tlrTel = tlrTel;
		this.totalAssets = totalAssets;
		this.totalIndebtedness = totalIndebtedness;
		this.totalProfit = totalProfit;
		this.useInstruction = useInstruction;
	}




	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="apply_code")
	public String getApplyCode() {
		return this.applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}


	@Column(name="bank_borrow")
	public String getBankBorrow() {
		return this.bankBorrow;
	}

	public void setBankBorrow(String bankBorrow) {
		this.bankBorrow = bankBorrow;
	}


	@Column(name="business_income")
	public String getBusinessIncome() {
		return this.businessIncome;
	}

	public void setBusinessIncome(String businessIncome) {
		this.businessIncome = businessIncome;
	}


	@Column(name="current_assets")
	public String getCurrentAssets() {
		return this.currentAssets;
	}

	public void setCurrentAssets(String currentAssets) {
		this.currentAssets = currentAssets;
	}


	@Column(name="customer_name")
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	@Column(name="del_flag")
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	@Column(name="enterprise_id")
	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}


	@Column(name="financial_statement")
	public String getFinancialStatement() {
		return this.financialStatement;
	}

	public void setFinancialStatement(String financialStatement) {
		this.financialStatement = financialStatement;
	}


	@Column(name="fixed_assets")
	public String getFixedAssets() {
		return this.fixedAssets;
	}

	public void setFixedAssets(String fixedAssets) {
		this.fixedAssets = fixedAssets;
	}


	@Column(name="guaranty_style")
	public String getGuarantyStyle() {
		return this.guarantyStyle;
	}

	public void setGuarantyStyle(String guarantyStyle) {
		this.guarantyStyle = guarantyStyle;
	}


	@Column(name="guaranty_unit")
	public String getGuarantyUnit() {
		return this.guarantyUnit;
	}

	public void setGuarantyUnit(String guarantyUnit) {
		this.guarantyUnit = guarantyUnit;
	}


	@Column(name="handle_bank")
	public String getHandleBank() {
		return this.handleBank;
	}

	public void setHandleBank(String handleBank) {
		this.handleBank = handleBank;
	}


	@Column(name="id_number")
	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}


	@Column(name="id_type")
	public String getIdType() {
		return this.idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="input_time")
	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}


	@Column(name="loan_amount")
	public String getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}


	@Column(name="loan_time")
	public String getLoanTime() {
		return this.loanTime;
	}

	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}


	@Column(name="ownership_interest")
	public String getOwnershipInterest() {
		return this.ownershipInterest;
	}

	public void setOwnershipInterest(String ownershipInterest) {
		this.ownershipInterest = ownershipInterest;
	}


	public String getReceivables() {
		return this.receivables;
	}

	public void setReceivables(String receivables) {
		this.receivables = receivables;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	@Column(name="tlr_name")
	public String getTlrName() {
		return this.tlrName;
	}

	public void setTlrName(String tlrName) {
		this.tlrName = tlrName;
	}


	@Column(name="tlr_tel")
	public String getTlrTel() {
		return this.tlrTel;
	}

	public void setTlrTel(String tlrTel) {
		this.tlrTel = tlrTel;
	}


	@Column(name="total_assets")
	public String getTotalAssets() {
		return this.totalAssets;
	}

	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}


	@Column(name="total_indebtedness")
	public String getTotalIndebtedness() {
		return this.totalIndebtedness;
	}

	public void setTotalIndebtedness(String totalIndebtedness) {
		this.totalIndebtedness = totalIndebtedness;
	}


	@Column(name="total_profit")
	public String getTotalProfit() {
		return this.totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}


	@Column(name="use_instruction")
	public String getUseInstruction() {
		return this.useInstruction;
	}

	public void setUseInstruction(String useInstruction) {
		this.useInstruction = useInstruction;
	}

}