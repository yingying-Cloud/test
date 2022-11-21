package com.jinpinghu.db.bean;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TbResToolFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_tool_catalog_file")

public class TbResToolCatalogFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer toolCatalogId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResToolCatalogFile() {
	}

	/** full constructor */
	public TbResToolCatalogFile(Integer toolCatalogId, Integer fileId, Integer delFlag) {
		this.toolCatalogId = toolCatalogId;
		this.fileId = fileId;
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

	@Column(name = "tool_catalog_id", nullable = false)

	public Integer getToolCatalogId() {
		return this.toolCatalogId;
	}

	public void setToolCatalogId(Integer toolId) {
		this.toolCatalogId = toolId;
	}

	@Column(name = "file_id", nullable = false)

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}