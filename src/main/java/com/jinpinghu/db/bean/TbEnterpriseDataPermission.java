package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResUserEnterprise entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_enterprise_data_permission")

public class TbEnterpriseDataPermission extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userTabId;
	private Integer enterpriseId;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbEnterpriseDataPermission() {
	}

	/** full constructor */
	public TbEnterpriseDataPermission(Integer userTabId, Integer enterpriseId, Integer delFlag) {
		this.userTabId = userTabId;
		this.enterpriseId = enterpriseId;
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

	@Column(name = "user_tab_id", nullable = false)

	public Integer getUserTabId() {
		return this.userTabId;
	}

	public void setUserTabId(Integer userTabId) {
		this.userTabId = userTabId;
	}

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "input_time", nullable = false)
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

}