package com.jinpinghu.db.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResFileToolRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_res_tool_record_file")
@SuppressWarnings("serial")
public class TbResToolRecordFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fileId;
	private Integer toolRecordId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResToolRecordFile() {
	}

	/** full constructor */
	public TbResToolRecordFile(Integer fileId, Integer toolRecordId, Integer delFlag) {
		this.fileId = fileId;
		this.toolRecordId = toolRecordId;
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

	@Column(name = "file_id", nullable = false)

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "tool_record_id", nullable = false)

	public Integer getToolRecordId() {
		return this.toolRecordId;
	}

	public void setToolRecordId(Integer toolRecordId) {
		this.toolRecordId = toolRecordId;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}