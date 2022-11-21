package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbRotationTool entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_rotation_tool")

public class TbRotationTool extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer toolZeroId;
	private Integer rotationId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbRotationTool() {
	}

	/** full constructor */
	public TbRotationTool(Integer toolZeroId, Integer rotationId, Integer delFlag) {
		this.toolZeroId = toolZeroId;
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

	@Column(name = "tool_zero_id")

	public Integer getToolZeroId() {
		return this.toolZeroId;
	}

	public void setToolZeroId(Integer toolZeroId) {
		this.toolZeroId = toolZeroId;
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