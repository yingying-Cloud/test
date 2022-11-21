package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbBrand entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_brand")

public class TbBrand extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String productName;
	private Integer enterpriseId;
	private String model;
	private String registeredTrademark;
	private String authorizationCategory;
	private Date inputTime;
	private Integer status;
	private Integer delFlag;
	private Integer type;
	private String price;
	private String unit;
	private  String spec;
	private  String describe;
//	private Integer upStatus;
	// Constructors

	/** default constructor */
	public TbBrand() {
	}

	/** minimal constructor */
	public TbBrand( String productName, Date inputTime, Integer delFlag) {
		this.productName = productName;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbBrand(String productName, String model, String registeredTrademark,
			String authorizationCategory, Date inputTime, Integer delFlag) {
		this.productName = productName;
		this.model = model;
		this.registeredTrademark = registeredTrademark;
		this.authorizationCategory = authorizationCategory;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
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

	@Column(name = "product_name", nullable = false)

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "enterprise_id")
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "model")

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "registered_trademark")

	public String getRegisteredTrademark() {
		return this.registeredTrademark;
	}

	public void setRegisteredTrademark(String registeredTrademark) {
		this.registeredTrademark = registeredTrademark;
	}

	@Column(name = "authorization_category")

	public String getAuthorizationCategory() {
		return this.authorizationCategory;
	}

	public void setAuthorizationCategory(String authorizationCategory) {
		this.authorizationCategory = authorizationCategory;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "describe_")
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Column(name = "spec")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name = "price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
//	@Column(name = "up_status")
//	public Integer getUpStatus() {
//		return upStatus;
//	}
//
//	public void setUpStatus(Integer upStatus) {
//		this.upStatus = upStatus;
//	}
}