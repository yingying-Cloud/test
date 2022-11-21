package com.jinpinghu.db.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jinpinghu.db.bean.BaseZEntity;

/**
 * TbResInspectionCompleteFile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_res_inspection_complete_file", catalog = "jinpinghu")

public class TbResInspectionCompleteFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer inspectionCompleteId;
	private Integer fileId;

	// Constructors

	/** default constructor */
	public TbResInspectionCompleteFile() {
	}

	/** full constructor */
	public TbResInspectionCompleteFile(Integer inspectionCompleteId, Integer fileId) {
		this.inspectionCompleteId = inspectionCompleteId;
		this.fileId = fileId;
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

	@Column(name = "inspection_complete_id", nullable = false)

	public Integer getInspectionCompleteId() {
		return this.inspectionCompleteId;
	}

	public void setInspectionCompleteId(Integer inspectionCompleteId) {
		this.inspectionCompleteId = inspectionCompleteId;
	}

	@Column(name = "file_id", nullable = false)

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}