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
@Table(name = "tb_res_tool_recovery_record_file")
@SuppressWarnings("serial")
public class TbResToolRecoveryRecordFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fileId;
	private Integer toolRecoveryRecordId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResToolRecoveryRecordFile() {
	}

	/** full constructor */
	public TbResToolRecoveryRecordFile(Integer fileId, Integer toolRecoveryRecordId, Integer delFlag) {
		this.fileId = fileId;
		this.setToolRecoveryRecordId(toolRecoveryRecordId);
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

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "tool_recovery_record_id")
	public Integer getToolRecoveryRecordId() {
		return toolRecoveryRecordId;
	}

	public void setToolRecoveryRecordId(Integer toolRecoveryRecordId) {
		this.toolRecoveryRecordId = toolRecoveryRecordId;
	}

}