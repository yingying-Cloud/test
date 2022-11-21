package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbToolZero entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_tool_zero")

public class TbToolZero extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer toolCatalogId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbToolZero() {
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

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "tool_catalog_id" )
	public Integer getToolCatalogId() {
		return toolCatalogId;
	}


	public void setToolCatalogId(Integer toolCatalogId) {
		this.toolCatalogId = toolCatalogId;
	}

}