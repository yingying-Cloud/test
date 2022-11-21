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
@Table(name = "tb_trademark")

public class TbTrademark extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String brandName;
	private String address;
	private String trademarkName;
	private String productCertification;
	private String source;
	private Date inputTime;
	private String contractNumber;
	private String x;
	private String y;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbTrademark() {
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

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "brand_name")
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "trademark_name")
	public String getTrademarkName() {
		return trademarkName;
	}

	public void setTrademarkName(String trademarkName) {
		this.trademarkName = trademarkName;
	}
	@Column(name = "product_certification")
	public String getProductCertification() {
		return productCertification;
	}

	public void setProductCertification(String productCertification) {
		this.productCertification = productCertification;
	}
	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "contract_number")
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	@Column(name = "x")
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}
	@Column(name = "y")
	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

}