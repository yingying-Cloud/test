package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResToolFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_tool_recovery_file")

public class TbResToolRecoveryFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer toolRecoveryId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResToolRecoveryFile() {
	}

	/** full constructor */
	public TbResToolRecoveryFile(Integer toolRecoveryId, Integer fileId, Integer delFlag) {
		this.toolRecoveryId = toolRecoveryId;
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
	@Column(name = "tool_recovery_id")
	public Integer getToolRecoveryId() {
		return toolRecoveryId;
	}

	public void setToolRecoveryId(Integer toolRecoveryId) {
		this.toolRecoveryId = toolRecoveryId;
	}

}