package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbToolOrderBook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_tool_order_book")

public class TbToolOrderBook extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer toolEnterpriseId;
	private String plantEnterpriseId;
	private String orderNumber;
	private String price;
	private Integer status;
	private String contactName;
	private String contactMobile;
	private String contactAddress;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbToolOrderBook() {
	}

	/** minimal constructor */
	public TbToolOrderBook(Integer toolEnterpriseId, String orderNumber, Integer status, Date inputTime,
			Integer delFlag) {
		this.toolEnterpriseId = toolEnterpriseId;
		this.orderNumber = orderNumber;
		this.status = status;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbToolOrderBook(Integer toolEnterpriseId, String plantEnterpriseId, String orderNumber, String price,
			Integer status, String contactName, String contactMobile, String contactAddress, Date inputTime,
			Integer delFlag) {
		this.toolEnterpriseId = toolEnterpriseId;
		this.plantEnterpriseId = plantEnterpriseId;
		this.orderNumber = orderNumber;
		this.price = price;
		this.status = status;
		this.contactName = contactName;
		this.contactMobile = contactMobile;
		this.contactAddress = contactAddress;
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

	@Column(name = "tool_enterprise_id", nullable = false)

	public Integer getToolEnterpriseId() {
		return this.toolEnterpriseId;
	}

	public void setToolEnterpriseId(Integer toolEnterpriseId) {
		this.toolEnterpriseId = toolEnterpriseId;
	}

	@Column(name = "plant_enterprise_id")

	public String getPlantEnterpriseId() {
		return this.plantEnterpriseId;
	}

	public void setPlantEnterpriseId(String plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}

	@Column(name = "order_number", nullable = false)

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "price")

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "status", nullable = false)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "contact_name")

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "contact_mobile")

	public String getContactMobile() {
		return this.contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	@Column(name = "contact_address")

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
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

}