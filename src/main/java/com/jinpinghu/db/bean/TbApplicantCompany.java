package com.jinpinghu.db.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_applicant_company")
public class TbApplicantCompany extends BaseZEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer enterpriseId;
	private String code;
	private String name;
	private String profile;
	private String address;
	private String zipCode;
	private String legalPerson;
	private String legalContact;
	private String legalMobile;
	private String agent;
	private String agentContact;
	private String agentMobile;
	private String fax;
	private String email;
	private String registeredTrademark;
	private Date trademarkServiceStartTime;
	private Date trademarkServiceEndTime;
	private Double x;
	private Double y;
	
	private String type;
	private String productionSituation;
	private String productQualityAttestation;
	private String machiningInfo;
	private String productSales;
	private String packageSourceManager;
	private String otherNt;
	private Integer status;
	private String uniformSocialCreditCode;
	private Date operationPeriodStartTime;
	private Date operationPeriodEndTime;
	private String spybName;
	private String spybProduct;
	private Date spybStartTime;
	private Date spybEndTime;
	private Date inputTime;
	private Integer delFlag;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "enterprise_id")
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "zip_code")
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Column(name = "legal_person")
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	@Column(name = "legal_contact")
	public String getLegalContact() {
		return legalContact;
	}
	public void setLegalContact(String legalContact) {
		this.legalContact = legalContact;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	@Column(name = "agent_contact")
	public String getAgentContact() {
		return agentContact;
	}
	public void setAgentContact(String agentContact) {
		this.agentContact = agentContact;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "registered_trademark")
	public String getRegisteredTrademark() {
		return registeredTrademark;
	}
	public void setRegisteredTrademark(String registeredTrademark) {
		this.registeredTrademark = registeredTrademark;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "production_situation")
	public String getProductionSituation() {
		return productionSituation;
	}
	public void setProductionSituation(String productionSituation) {
		this.productionSituation = productionSituation;
	}
	@Column(name = "product_quality_attestation")
	public String getProductQualityAttestation() {
		return productQualityAttestation;
	}
	public void setProductQualityAttestation(String productQualityAttestation) {
		this.productQualityAttestation = productQualityAttestation;
	}
	@Column(name = "machining_info")
	public String getMachiningInfo() {
		return machiningInfo;
	}
	public void setMachiningInfo(String machiningInfo) {
		this.machiningInfo = machiningInfo;
	}
	@Column(name = "product_sales")
	public String getProductSales() {
		return productSales;
	}
	public void setProductSales(String productSales) {
		this.productSales = productSales;
	}
	@Column(name = "package_source_manager")
	public String getPackageSourceManager() {
		return packageSourceManager;
	}
	public void setPackageSourceManager(String packageSourceManager) {
		this.packageSourceManager = packageSourceManager;
	}
	@Column(name = "other_nt")
	public String getOtherNt() {
		return otherNt;
	}
	public void setOtherNt(String otherNt) {
		this.otherNt = otherNt;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "del_flag")
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Column(name = "trademark_service_start_time")
	public Date getTrademarkServiceStartTime() {
		return trademarkServiceStartTime;
	}
	public void setTrademarkServiceStartTime(Date trademarkServiceStartTime) {
		this.trademarkServiceStartTime = trademarkServiceStartTime;
	}
	@Column(name = "trademark_service_end_time")
	public Date getTrademarkServiceEndTime() {
		return trademarkServiceEndTime;
	}
	public void setTrademarkServiceEndTime(Date trademarkServiceEndTime) {
		this.trademarkServiceEndTime = trademarkServiceEndTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	@Column(name = "uniform_social_credit_code")
	public String getUniformSocialCreditCode() {
		return uniformSocialCreditCode;
	}
	public void setUniformSocialCreditCode(String uniformSocialCreditCode) {
		this.uniformSocialCreditCode = uniformSocialCreditCode;
	}
	@Column(name = "operation_period_start_time")
	public Date getOperationPeriodStartTime() {
		return operationPeriodStartTime;
	}
	public void setOperationPeriodStartTime(Date operationPeriodStartTime) {
		this.operationPeriodStartTime = operationPeriodStartTime;
	}
	@Column(name = "operation_period_end_time")
	public Date getOperationPeriodEndTime() {
		return operationPeriodEndTime;
	}
	public void setOperationPeriodEndTime(Date operationPeriodEndTime) {
		this.operationPeriodEndTime = operationPeriodEndTime;
	}
	@Column(name = "spyb_name")
	public String getSpybName() {
		return spybName;
	}
	public void setSpybName(String spybName) {
		this.spybName = spybName;
	}
	@Column(name = "spyb_product")
	public String getSpybProduct() {
		return spybProduct;
	}
	public void setSpybProduct(String spybProduct) {
		this.spybProduct = spybProduct;
	}
	@Column(name = "spyb_start_time")
	public Date getSpybStartTime() {
		return spybStartTime;
	}
	public void setSpybStartTime(Date spybStartTime) {
		this.spybStartTime = spybStartTime;
	}
	@Column(name = "spyb_end_time")
	public Date getSpybEndTime() {
		return spybEndTime;
	}
	public void setSpybEndTime(Date spybEndTime) {
		this.spybEndTime = spybEndTime;
	}
	@Column(name = "legal_mobile")
	public String getLegalMobile() {
		return legalMobile;
	}
	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}
	@Column(name = "agent_mobile")
	public String getAgentMobile() {
		return agentMobile;
	}
	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}
	
	


}
