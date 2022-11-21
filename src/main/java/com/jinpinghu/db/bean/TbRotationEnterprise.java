package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbRotationEnterprise entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_rotation_enterprise")

public class TbRotationEnterprise extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer enterpriseId;
	private Integer rotationId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbRotationEnterprise() {
	}

	/** full constructor */
	public TbRotationEnterprise(Integer enterpriseId, Integer rotationId, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.rotationId = rotationId;
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

	@Column(name = "rotation_id", length = 19)

	public Integer getRotationId() {
		return this.rotationId;
	}

	public void setRotationId(Integer rotationId) {
		this.rotationId = rotationId;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}