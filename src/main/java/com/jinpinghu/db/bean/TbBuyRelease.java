package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the tb_buy_release database table.
 * 
 */
@Entity
@Table(name="tb_buy_release")
public class TbBuyRelease extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer id;
	private String contactAddress;
	private String contactPerson;
	private String contactPhone;
	private Integer delFlag;
	private Date endTime;
	private Integer enterpriseId;
	private Integer num;
	private BigDecimal price;
	private String productName;
	private Date startTime;
	private Integer type;
	private String dscd;
	private Integer status;
	private String specification;

	public TbBuyRelease() {
	}
	
	

	public TbBuyRelease(Integer id,String contactAddress, String contactPerson, String contactPhone, Date endTime,
			Integer enterpriseId, Integer num, BigDecimal price, String productName, Date startTime, 
			Integer type, String dscd,String specification) {
		super();
		this.id = id;
		this.contactAddress = contactAddress;
		this.contactPerson = contactPerson;
		this.contactPhone = contactPhone;
		this.endTime = endTime;
		this.enterpriseId = enterpriseId;
		this.num = num;
		this.price = price;
		this.productName = productName;
		this.startTime = startTime;
		this.type = type;
		this.dscd = dscd;
		this.specification = specification;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Column(name="product_name")
	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name="start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}



	public String getDscd() {
		return dscd;
	}



	public void setDscd(String dscd) {
		this.dscd = dscd;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getSpecification() {
		return specification;
	}



	public void setSpecification(String specification) {
		this.specification = specification;
	}


}