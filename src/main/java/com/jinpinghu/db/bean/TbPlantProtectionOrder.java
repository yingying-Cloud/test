package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPlantProtectionOrder entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_plant_protection_order")

public class TbPlantProtectionOrder extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer plantProtectionEnterpriseId;
	private Integer plantEnterpriseId;
	private Integer plantProtectionId;
	private String addPeople;
	private String serviceName;
	private String orderNumber;
	private String price;
	private String area;
	private Integer status;
	private String cancleInfo;
	private Date serviceStartTime;
	private Date serviceEndTime;
	private Date inputTime;
	private Date timeReceive;
	private Date timeConfirm;
	private Date timeCancle;
	private Integer delFlag;
	private String address;
	private String contact;
	private String phone;
	private String isEvaluate;
	// Constructors

	/** default constructor */
	public TbPlantProtectionOrder() {
	}

	/** minimal constructor */
	public TbPlantProtectionOrder(Integer plantProtectionEnterpriseId, Integer plantEnterpriseId,
			Integer plantProtectionId, String orderNumber, String price, Integer status, Date inputTime,
			Integer delFlag) {
		this.plantProtectionEnterpriseId = plantProtectionEnterpriseId;
		this.plantEnterpriseId = plantEnterpriseId;
		this.plantProtectionId = plantProtectionId;
		this.orderNumber = orderNumber;
		this.price = price;
		this.status = status;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbPlantProtectionOrder(Integer plantProtectionEnterpriseId, Integer plantEnterpriseId,String serviceName,
			Integer plantProtectionId, String addPeople, String orderNumber, String price, String area, Integer status,
			String cancleInfo, Date serviceStartTime, Date inputTime, Date timeReceive,Date timeConfirm,Date timeCancle,
			Integer delFlag,Date serviceEndTime) {
		this.plantProtectionEnterpriseId = plantProtectionEnterpriseId;
		this.plantEnterpriseId = plantEnterpriseId;
		this.plantProtectionId = plantProtectionId;
		this.addPeople = addPeople;
		this.orderNumber = orderNumber;
		this.price = price;
		this.area = area;
		this.status = status;
		this.cancleInfo = cancleInfo;
		this.serviceStartTime = serviceStartTime;
		this.inputTime = inputTime;
		this.timeReceive = timeReceive;
		this.timeConfirm = timeConfirm;
		this.timeCancle = timeCancle;
		this.delFlag = delFlag;
		this.serviceName = serviceName;
		this.serviceEndTime = serviceEndTime;
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

	@Column(name = "plant_protection_enterprise_id", nullable = false)

	public Integer getPlantProtectionEnterpriseId() {
		return this.plantProtectionEnterpriseId;
	}

	public void setPlantProtectionEnterpriseId(Integer plantProtectionEnterpriseId) {
		this.plantProtectionEnterpriseId = plantProtectionEnterpriseId;
	}

	@Column(name = "plant_enterprise_id", nullable = false)

	public Integer getPlantEnterpriseId() {
		return this.plantEnterpriseId;
	}

	public void setPlantEnterpriseId(Integer plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}

	@Column(name = "plant_protection_id", nullable = false)

	public Integer getPlantProtectionId() {
		return this.plantProtectionId;
	}

	public void setPlantProtectionId(Integer plantProtectionId) {
		this.plantProtectionId = plantProtectionId;
	}

	@Column(name = "add_people")

	public String getAddPeople() {
		return this.addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}

	@Column(name = "order_number", nullable = false)

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "price", nullable = false)

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "area")

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "status", nullable = false)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "cancle_info")

	public String getCancleInfo() {
		return this.cancleInfo;
	}

	public void setCancleInfo(String cancleInfo) {
		this.cancleInfo = cancleInfo;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "time_confirm", length = 19)

	public Date getTimeConfirm() {
		return this.timeConfirm;
	}

	public void setTimeConfirm(Date timeConfirm) {
		this.timeConfirm = timeConfirm;
	}

	@Column(name = "time_cancle", length = 19)

	public Date getTimeCancle() {
		return this.timeCancle;
	}

	public void setTimeCancle(Date timeCancle) {
		this.timeCancle = timeCancle;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "service_name")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Column(name = "time_receive", length = 19)
	public Date getTimeReceive() {
		return timeReceive;
	}

	public void setTimeReceive(Date timeReceive) {
		this.timeReceive = timeReceive;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "contact")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "is_evaluate")
	public String getIsEvaluate() {
		return isEvaluate;
	}


	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	@Column(name = "service_start_time")
	public Date getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(Date serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	@Column(name = "service_end_time")
	public Date getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(Date serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}


}