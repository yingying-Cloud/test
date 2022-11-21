package com.jinpinghu.db.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jinpinghu.db.bean.BaseZEntity;

/**
 * TbInspection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_inspection", catalog = "jinpinghu")

public class TbInspection extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userId;
	private Integer enterpriseId;
	private Integer status;
	private String remark;
	private Integer inspectionItemId;
	private Integer inspectionType;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbInspection() {
	}

	/** full constructor */
	public TbInspection(String userId, Integer enterpriseId, Integer status, String remark, Integer inspectionItemId,
			Integer inspectionType, Date inputTime, Integer delFlag) {
		this.userId = userId;
		this.enterpriseId = enterpriseId;
		this.status = status;
		this.remark = remark;
		this.inspectionItemId = inspectionItemId;
		this.inspectionType = inspectionType;
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

	@Column(name = "user_id")

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "enterprise_id")

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "status")

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "remark", length = 1000)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "inspection_item_id")

	public Integer getInspectionItemId() {
		return this.inspectionItemId;
	}

	public void setInspectionItemId(Integer inspectionItemId) {
		this.inspectionItemId = inspectionItemId;
	}

	@Column(name = "inspection_type")

	public Integer getInspectionType() {
		return this.inspectionType;
	}

	public void setInspectionType(Integer inspectionType) {
		this.inspectionType = inspectionType;
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