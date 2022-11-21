package com.jinpinghu.db.bean;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TbResToolFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_tool_zero_file")

public class TbResToolZeroFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer toolZeroId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResToolZeroFile() {
	}

	/** full constructor */
	public TbResToolZeroFile(Integer toolId, Integer fileId, Integer delFlag) {
		this.toolZeroId = toolId;
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

	@Column(name = "tool_zero_id", nullable = false)

	public Integer getToolZeroId() {
		return this.toolZeroId;
	}

	public void setToolZeroId(Integer toolZeroId) {
		this.toolZeroId = toolZeroId;
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