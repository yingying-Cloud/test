package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TbResUserWorkTime entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_user_work_time")

public class TbResUserWorkTime extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userTabId;
	private Date inputTime;
	private Double workTime;
	private Integer enterpriseId;
	private Integer workId;
	
	// Constructors

	/** default constructor */
	public TbResUserWorkTime() {
	}

	/** minimal constructor */
	public TbResUserWorkTime(Integer userTabId, Date inputTime) {
		this.userTabId = userTabId;
		this.inputTime = inputTime;
	}

	/** full constructor */
	public TbResUserWorkTime(Integer userTabId, Date inputTime, Double workTime) {
		this.userTabId = userTabId;
		this.inputTime = inputTime;
		this.workTime = workTime;
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

	@Column(name = "user_tab_id")

	public Integer getUserTabId() {
		return this.userTabId;
	}

	public void setUserTabId(Integer userTabId) {
		this.userTabId = userTabId;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "input_time", nullable = false, length = 10)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "work_time", precision = 10, scale = 2)

	public Double getWorkTime() {
		return this.workTime;
	}

	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
	@Column(name = "work_id" )
	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	@Column(name = "enterprise_id", nullable = false)
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}