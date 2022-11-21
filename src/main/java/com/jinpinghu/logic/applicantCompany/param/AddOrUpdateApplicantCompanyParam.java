package com.jinpinghu.logic.applicantCompany.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateApplicantCompanyParam extends BaseZLogicParam{

	public AddOrUpdateApplicantCompanyParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	private Integer id;
	private Integer enterpriseId;
	private String name;
	private String address;
	private String zipCode;
	private String legalPerson;
	private String legalContact;
	private String agent;
	private String agentContact;
	private String fax;
	private String email;
	private String registeredTrademark;
	private String trademarkServiceStartTime;
	private String trademarkServiceEndTime;
	private String type;
	private String productionSituation;
	private String productQualityAttestation;
	private String machiningInfo;
	private String productSales;
	private String packageSourceManager;
	private String otherNt;
	private String applicantCompanyProducts;
	private String applicantCompanyCredentials;
	private Double x;
	private Double y;
	private String uniformSocialCreditCode;
	private String operationPeriodStartTime;
	private String operationPeriodEndTime;
	private String spybName;
	private String spybProduct;
	private String spybStartTime;
	private String spybEndTime;
	private String profile;
	private String trademarks;
	private String files;
	private String agentMobile;
	private String legalMobile;
	public String getAgentMobile() {
		return agentMobile;
	}
	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}
	public String getLegalMobile() {
		return legalMobile;
	}
	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}
	public String getTrademarks() {
		return trademarks;
	}
	public void setTrademarks(String trademarks) {
		this.trademarks = trademarks;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getUniformSocialCreditCode() {
		return uniformSocialCreditCode;
	}
	public void setUniformSocialCreditCode(String uniformSocialCreditCode) {
		this.uniformSocialCreditCode = uniformSocialCreditCode;
	}
	public String getOperationPeriodStartTime() {
		return operationPeriodStartTime;
	}
	public void setOperationPeriodStartTime(String operationPeriodStartTime) {
		this.operationPeriodStartTime = operationPeriodStartTime;
	}
	public String getOperationPeriodEndTime() {
		return operationPeriodEndTime;
	}
	public void setOperationPeriodEndTime(String operationPeriodEndTime) {
		this.operationPeriodEndTime = operationPeriodEndTime;
	}
	public String getSpybName() {
		return spybName;
	}
	public void setSpybName(String spybName) {
		this.spybName = spybName;
	}
	public String getSpybProduct() {
		return spybProduct;
	}
	public void setSpybProduct(String spybProduct) {
		this.spybProduct = spybProduct;
	}
	public String getSpybStartTime() {
		return spybStartTime;
	}
	public void setSpybStartTime(String spybStartTime) {
		this.spybStartTime = spybStartTime;
	}
	public String getSpybEndTime() {
		return spybEndTime;
	}
	public void setSpybEndTime(String spybEndTime) {
		this.spybEndTime = spybEndTime;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
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
	public String getProductionSituation() {
		return productionSituation;
	}
	public void setProductionSituation(String productionSituation) {
		this.productionSituation = productionSituation;
	}
	public String getProductQualityAttestation() {
		return productQualityAttestation;
	}
	public void setProductQualityAttestation(String productQualityAttestation) {
		this.productQualityAttestation = productQualityAttestation;
	}
	public String getMachiningInfo() {
		return machiningInfo;
	}
	public void setMachiningInfo(String machiningInfo) {
		this.machiningInfo = machiningInfo;
	}
	public String getProductSales() {
		return productSales;
	}
	public void setProductSales(String productSales) {
		this.productSales = productSales;
	}
	public String getPackageSourceManager() {
		return packageSourceManager;
	}
	public void setPackageSourceManager(String packageSourceManager) {
		this.packageSourceManager = packageSourceManager;
	}
	public String getOtherNt() {
		return otherNt;
	}
	public void setOtherNt(String otherNt) {
		this.otherNt = otherNt;
	}
	public String getApplicantCompanyProducts() {
		return applicantCompanyProducts;
	}
	public void setApplicantCompanyProducts(String applicantCompanyProducts) {
		this.applicantCompanyProducts = applicantCompanyProducts;
	}
	public String getApplicantCompanyCredentials() {
		return applicantCompanyCredentials;
	}
	public void setApplicantCompanyCredentials(String applicantCompanyCredentials) {
		this.applicantCompanyCredentials = applicantCompanyCredentials;
	}
	public String getTrademarkServiceStartTime() {
		return trademarkServiceStartTime;
	}
	public void setTrademarkServiceStartTime(String trademarkServiceStartTime) {
		this.trademarkServiceStartTime = trademarkServiceStartTime;
	}
	public String getTrademarkServiceEndTime() {
		return trademarkServiceEndTime;
	}
	public void setTrademarkServiceEndTime(String trademarkServiceEndTime) {
		this.trademarkServiceEndTime = trademarkServiceEndTime;
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
	
	

}
