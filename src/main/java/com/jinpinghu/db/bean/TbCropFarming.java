package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbCropFarming entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_crop_farming")

public class TbCropFarming extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer workId;
	private Integer enterpriseId;
	private String describe;
	private String addPeople;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbCropFarming() {
	}

	/** minimal constructor */
	public TbCropFarming(Integer workId, Integer enterpriseId, Date inputTime, Integer delFlag) {
		this.workId = workId;
		this.enterpriseId = enterpriseId;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbCropFarming(Integer workId, Integer enterpriseId, String describe, String addPeople, Date inputTime,
			Integer delFlag) {
		this.workId = workId;
		this.enterpriseId = enterpriseId;
		this.describe = describe;
		this.addPeople = addPeople;
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

	@Column(name = "work_id", nullable = false)

	public Integer getWorkId() {
		return this.workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "describe_")

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "add_people")

	public String getAddPeople() {
		return this.addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
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