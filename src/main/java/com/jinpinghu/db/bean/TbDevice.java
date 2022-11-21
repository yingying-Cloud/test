package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbDecive entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_device")

public class TbDevice extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String deviceSn;
	private String deviceName;
	private String describe;
	private Integer type;
	private Date inputTime;
	private Integer delFlag;
	
	private Date installTime;
	private String installAddress;
	private String factory;
	private String equipmentType;
//	private String sensorNo;
	private String openInstruction;
	private String closeInstruction;
	private String searchInstruction;
	private Integer classify;

	// Constructors

	/** default constructor */
	public TbDevice() {
	}

	/** minimal constructor */
	public TbDevice(Integer enterpriseId, String deviceName, Integer type, Date inputTime, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.deviceName = deviceName;
		this.type = type;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbDevice(Integer enterpriseId, String deviceSn, String deviceName, String describe, Integer type, Date inputTime,
			Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.deviceSn = deviceSn;
		this.deviceName = deviceName;
		this.describe = describe;
		this.type = type;
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

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "device_sn")
	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	@Column(name = "device_name", nullable = false)

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name = "describe_")

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "type")

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
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
	@Column(name = "install_time")
	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}
	@Column(name = "install_address")
	public String getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}
	@Column(name = "factory")
	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}
	@Column(name = "equipment_type")
	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
//	@Column(name = "sensor_no")
//	public String getSensorNo() {
//		return sensorNo;
//	}
//
//	public void setSensorNo(String sensorNo) {
//		this.sensorNo = sensorNo;
//	}
	@Column(name = "openInstruction")
	public String getOpenInstruction() {
		return openInstruction;
	}

	public void setOpenInstruction(String openInstruction) {
		this.openInstruction = openInstruction;
	}@Column(name = "closeInstruction")

	public String getCloseInstruction() {
		return closeInstruction;
	}

	public void setCloseInstruction(String closeInstruction) {
		this.closeInstruction = closeInstruction;
	}
	@Column(name = "searchInstruction")
	public String getSearchInstruction() {
		return searchInstruction;
	}

	public void setSearchInstruction(String searchInstruction) {
		this.searchInstruction = searchInstruction;
	}
	@Column(name = "classify")
	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}
}