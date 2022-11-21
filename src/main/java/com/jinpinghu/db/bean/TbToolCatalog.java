package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbTool entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_tool_catalog")

public class TbToolCatalog extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private String supplierName;
	private String name;
	private String model;
	private String specification;
	private String unit;
	private String price;
	private String number;
	private String describe;
	private Integer delFlag;
	private Integer type;
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
	private Integer status;
	private String wholesalePrice;
	private String userId;
	private String remark;
	private Date inputTime;
	private String hfzc;
	private Date approvalEndDate;
	private String approvalNo;
	private String approveNo;
	private String certificateNo;
	private String checkHealthNo;
	private String healthNo;
	private Date limitDate;
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
	private String isOther;
	//nysx字段
	private String nysx;
	// Constructors

	/** default constructor */
	public TbToolCatalog() {
	}

	/** minimal constructor */
	public TbToolCatalog(String name, String price, Integer delFlag) {
		this.name = name;
		this.price = price;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbToolCatalog(String name, String model,  String specification, String unit,
			String price, String number, String describe, Integer delFlag,String supplierName) {
		this.name = name;
		this.model = model;
		this.specification = specification;
		this.unit = unit;
		this.price = price;
		this.number = number;
		this.describe = describe;
		this.delFlag = delFlag;
		this.supplierName = supplierName;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "model")

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "specification")

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "unit")

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "price", nullable = false)

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "number")

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "describe_")

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "supplier_name")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "production_units")
	public String getProductionUnits() {
		return productionUnits;
	}

	public void setProductionUnits(String productionUnits) {
		this.productionUnits = productionUnits;
	}
	@Column(name = "registration_certificate_number")
	public String getRegistrationCertificateNumber() {
		return registrationCertificateNumber;
	}

	public void setRegistrationCertificateNumber(String registrationCertificateNumber) {
		this.registrationCertificateNumber = registrationCertificateNumber;
	}
	@Column(name = "explicit_ingredients")
	public String getExplicitIngredients() {
		return explicitIngredients;
	}

	public void setExplicitIngredients(String explicitIngredients) {
		this.explicitIngredients = explicitIngredients;
	}
	@Column(name = "effective_ingredients")
	public String getEffectiveIngredients() {
		return effectiveIngredients;
	}

	public void setEffectiveIngredients(String effectiveIngredients) {
		this.effectiveIngredients = effectiveIngredients;
	}
	@Column(name = "implementation_standards")
	public String getImplementationStandards() {
		return implementationStandards;
	}

	public void setImplementationStandards(String implementationStandards) {
		this.implementationStandards = implementationStandards;
	}
	@Column(name = "dosage_forms")
	public String getDosageForms() {
		return dosageForms;
	}

	public void setDosageForms(String dosageForms) {
		this.dosageForms = dosageForms;
	}
	@Column(name = "product_attributes")
	public String getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(String productAttributes) {
		this.productAttributes = productAttributes;
	}
	@Column(name = "toxicity")
	public String getToxicity() {
		return toxicity;
	}

	public void setToxicity(String toxicity) {
		this.toxicity = toxicity;
	}
	@Column(name = "quality_grade")
	public String getQualityGrade() {
		return qualityGrade;
	}

	public void setQualityGrade(String qualityGrade) {
		this.qualityGrade = qualityGrade;
	}
	@Column(name = "uniform_price")
	public String getUniformPrice() {
		return uniformPrice;
	}

	public void setUniformPrice(String uniformPrice) {
		this.uniformPrice = uniformPrice;
	}
	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "wholesale_price")
	public String getWholesalePrice() {
		return wholesalePrice;
	}

	public void setWholesalePrice(String wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "hfzc")
	public String getHfzc() {
		return hfzc;
	}

	public void setHfzc(String hfzc) {
		this.hfzc = hfzc;
	}
	@Column(name = "approval_end_date")
	public Date getApprovalEndDate() {
		return approvalEndDate;
	}

	public void setApprovalEndDate(Date approvalEndDate) {
		this.approvalEndDate = approvalEndDate;
	}
	@Column(name = "approval_no")
	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	@Column(name = "approve_no")
	public String getApproveNo() {
		return approveNo;
	}

	public void setApproveNo(String approveNo) {
		this.approveNo = approveNo;
	}
	@Column(name = "certificate_no")
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	@Column(name = "check_health_no")
	public String getCheckHealthNo() {
		return checkHealthNo;
	}

	public void setCheckHealthNo(String checkHealthNo) {
		this.checkHealthNo = checkHealthNo;
	}
	@Column(name = "health_no")
	public String getHealthNo() {
		return healthNo;
	}

	public void setHealthNo(String healthNo) {
		this.healthNo = healthNo;
	}
	@Column(name = "limit_date")
	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}
	@Column(name = "produced")
	public String getProduced() {
		return produced;
	}

	public void setProduced(String produced) {
		this.produced = produced;
	}
	@Column(name = "production_no")
	public String getProductionNo() {
		return productionNo;
	}

	public void setProductionNo(String productionNo) {
		this.productionNo = productionNo;
	}
	@Column(name = "n")
	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}
	@Column(name = "k")
	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}
	@Column(name = "qt")
	public String getQt() {
		return qt;
	}

	public void setQt(String qt) {
		this.qt = qt;
	}
	@Column(name = "p")
	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}
	@Column(name = "qr_code")
	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	@Column(name = "NPK")
	public String getNPK() {
		return NPK;
	}

	public void setNPK(String nPK) {
		NPK = nPK;
	}
	@Column(name = "NP")
	public String getNP() {
		return NP;
	}

	public void setNP(String nP) {
		NP = nP;
	}
	@Column(name = "NK")
	public String getNK() {
		return NK;
	}

	public void setNK(String nK) {
		NK = nK;
	}
	@Column(name = "PK")
	public String getPK() {
		return PK;
	}

	public void setPK(String pK) {
		PK = pK;
	}
	@Column(name = "zjzl")
	public String getZjzl() {
		return zjzl;
	}

	public void setZjzl(String zjzl) {
		this.zjzl = zjzl;
	}
	@Column(name = "yxcf_unit")
	public String getYxcfUnit() {
		return yxcfUnit;
	}

	public void setYxcfUnit(String yxcfUnit) {
		this.yxcfUnit = yxcfUnit;
	}
	@Column(name = "is_other")
	public String getIsOther() {
		return isOther;
	}

	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}

	@Column(name = "nysx")
	public String getNysx() {
		return nysx;
	}

	public void setNysx(String nysx) {
		this.nysx = nysx;
	}

}