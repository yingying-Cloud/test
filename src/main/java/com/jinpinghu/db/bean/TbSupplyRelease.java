package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the tb_suply_release database table.
 * 
 */
@Entity
@Table(name="tb_supply_release")
public class TbSupplyRelease extends BaseZEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer brandId;
	private String contactAddress;
	private String contactPerson;
	private String contactPhone;
	private Integer delFlag;
	private Integer enterpriseId;
	private Integer num;
	private Integer stock;
	private BigDecimal price;
	private String specification;
	private Integer type;
	private Integer workId;
	private Integer status;
	private String dscd;
	private String estimateOutput;

	public TbSupplyRelease() {
	}

	public TbSupplyRelease(Integer id,Integer brandId, String contactAddress, String contactPerson, String contactPhone,
			Integer enterpriseId, Integer num, BigDecimal price, String specification,
			Integer type, Integer workId,String dscd,String estimateOutput) {
		super();
		this.id = id;
		this.brandId = brandId;
		this.contactAddress = contactAddress;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
		this.enterpriseId = enterpriseId;
		this.num = num;
		this.price = price;
		this.specification = specification;
		this.type = type;
		this.workId = workId;
		this.dscd = dscd;
		this.estimateOutput = estimateOutput;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="brand_id")
	public Integer getBrandId() {
		return this.brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}


	@Column(name="contact_address")
	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}


	@Column(name="contact_person")
	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


	@Column(name="contact_phone")
	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	@Column(name="del_flag")
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	@Column(name="enterprise_id")
	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}


	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	@Column(name="work_id")
	public Integer getWorkId() {
		return this.workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDscd() {
		return dscd;
	}

	public void setDscd(String dscd) {
		this.dscd = dscd;
	}

	@Column(name = "estimate_output")
	public String getEstimateOutput() {
		return estimateOutput;
	}

	public void setEstimateOutput(String estimateOutput) {
		this.estimateOutput = estimateOutput;
	}

}