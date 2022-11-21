package com.jinpinghu.logic.toolCatalog.param;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateToolCatalogParam extends BaseZLogicParam{

	public AddOrUpdateToolCatalogParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}
	private String id;
	private String enterpriseId;
	private String enterpriseType;
	private String name;
	private String model;
	private String specification;
	private String unit;
	private String type;
	private String price;
	private String file;
	private String number;
	private String describe;
	private String supplierName;
	private String productionUnits;
	private String registrationCertificateNumber;
	private String explicitIngredients;
	private String effectiveIngredients;
	private String implementationStandards;
	private String dosageForms;
	private String productAttributes;
	private String toxicity;
	private String qualityGrade;
	private String uniformPrice;
	private String code;
	private String wholesalePrice;
	private String remark;
	private String hfzc;
	private String approvalEndDate;
	private String approvalNo;
	private String approveNo;
	private String certificateNo;
	private String checkHealthNo;
	private String healthNo;
	private String limitDate;
	private String produced;
	private String productionNo;
	private String n;
	private String p;
	private String k;
	private String qt;
	private String qrCode;
	private String NPK;
	private String NP;
	private String NK;
	private String PK;
	private String zjzl;
	private String yxcfUnit;
	private String yxcfJa;
	private String nysx;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getEnterpriseType() {
		return enterpriseType;
	}
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProductionUnits() {
		return productionUnits;
	}
	public void setProductionUnits(String productionUnits) {
		this.productionUnits = productionUnits;
	}
	public String getRegistrationCertificateNumber() {
		return registrationCertificateNumber;
	}
	public void setRegistrationCertificateNumber(String registrationCertificateNumber) {
		this.registrationCertificateNumber = registrationCertificateNumber;
	}
	public String getExplicitIngredients() {
		return explicitIngredients;
	}
	public void setExplicitIngredients(String explicitIngredients) {
		this.explicitIngredients = explicitIngredients;
	}
	public String getEffectiveIngredients() {
		return effectiveIngredients;
	}
	public void setEffectiveIngredients(String effectiveIngredients) {
		this.effectiveIngredients = effectiveIngredients;
	}
	public String getImplementationStandards() {
		return implementationStandards;
	}
	public void setImplementationStandards(String implementationStandards) {
		this.implementationStandards = implementationStandards;
	}
	public String getDosageForms() {
		return dosageForms;
	}
	public void setDosageForms(String dosageForms) {
		this.dosageForms = dosageForms;
	}
	public String getProductAttributes() {
		return productAttributes;
	}
	public void setProductAttributes(String productAttributes) {
		this.productAttributes = productAttributes;
	}
	public String getToxicity() {
		return toxicity;
	}
	public void setToxicity(String toxicity) {
		this.toxicity = toxicity;
	}
	public String getQualityGrade() {
		return qualityGrade;
	}
	public void setQualityGrade(String qualityGrade) {
		this.qualityGrade = qualityGrade;
	}
	public String getUniformPrice() {
		return uniformPrice;
	}
	public void setUniformPrice(String uniformPrice) {
		this.uniformPrice = uniformPrice;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWholesalePrice() {
		return wholesalePrice;
	}
	public void setWholesalePrice(String wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHfzc() {
		return hfzc;
	}
	public void setHfzc(String hfzc) {
		this.hfzc = hfzc;
	}
	public String getApprovalEndDate() {
		return approvalEndDate;
	}
	public void setApprovalEndDate(String approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	public String getApproveNo() {
		return approveNo;
	}
	public void setApproveNo(String approveNo) {
		this.approveNo = approveNo;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getCheckHealthNo() {
		return checkHealthNo;
	}
	public void setCheckHealthNo(String checkHealthNo) {
		this.checkHealthNo = checkHealthNo;
	}
	public String getHealthNo() {
		return healthNo;
	}
	public void setHealthNo(String healthNo) {
		this.healthNo = healthNo;
	}
	public String getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}
	public String getProduced() {
		return produced;
	}
	public void setProduced(String produced) {
		this.produced = produced;
	}
	public String getProductionNo() {
		return productionNo;
	}
	public void setProductionNo(String productionNo) {
		this.productionNo = productionNo;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getQt() {
		return qt;
	}
	public void setQt(String qt) {
		this.qt = qt;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getNPK() {
		return NPK;
	}
	public void setNPK(String nPK) {
		NPK = nPK;
	}
	public String getNP() {
		return NP;
	}
	public void setNP(String nP) {
		NP = nP;
	}
	public String getNK() {
		return NK;
	}
	public void setNK(String nK) {
		NK = nK;
	}
	public String getPK() {
		return PK;
	}
	public void setPK(String pK) {
		PK = pK;
	}
	public String getYxcfJa() {
		return yxcfJa;
	}
	public void setYxcfJa(String yxcfJa) {
		this.yxcfJa = yxcfJa;
	}
	public String getYxcfUnit() {
		return yxcfUnit;
	}
	public void setYxcfUnit(String yxcfUnit) {
		this.yxcfUnit = yxcfUnit;
	}
	public String getZjzl() {
		return zjzl;
	}
	public void setZjzl(String zjzl) {
		this.zjzl = zjzl;
	}
	public String getNysx() {
		return nysx;
	}
	public void setNysx(String nysx) {
		this.nysx = nysx;
	}
	
}
