package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPlantRecord entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_plant_record")

public class TbPlantRecord extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private Integer toolId;
	private Integer toolEnterpriseId;
	private Integer recordType;
	private Integer allNumber;
	private Integer number;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbPlantRecord() {
	}

	/** full constructor */
	public TbPlantRecord(Integer id, Integer enterpriseId, Integer toolId, Integer toolEnterpriseId,
			Integer recordType, Integer allNumber, Integer number, Date inputTime, Integer delFlag) {
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.toolId = toolId;
		this.toolEnterpriseId = toolEnterpriseId;
		this.recordType = recordType;
		this.allNumber = allNumber;
		this.number = number;
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

	@Column(name = "enterprise_id")

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "tool_id")

	public Integer getToolId() {
		return this.toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	@Column(name = "tool_enterprise_id")

	public Integer getToolEnterpriseId() {
		return this.toolEnterpriseId;
	}

	public void setToolEnterpriseId(Integer toolEnterpriseId) {
		this.toolEnterpriseId = toolEnterpriseId;
	}

	@Column(name = "record_type")

	public Integer getRecordType() {
		return this.recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	@Column(name = "all_number")

	public Integer getAllNumber() {
		return this.allNumber;
	}

	public void setAllNumber(Integer allNumber) {
		this.allNumber = allNumber;
	}

	@Column(name = "number")

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "input_time", length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}