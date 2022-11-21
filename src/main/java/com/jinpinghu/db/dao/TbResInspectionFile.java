package com.jinpinghu.db.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jinpinghu.db.bean.BaseZEntity;

/**
 * TbResInspectionFile entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_res_inspection_file", catalog = "jinpinghu")

public class TbResInspectionFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer inspectionId;
	private Integer fileId;

	// Constructors

	/** default constructor */
	public TbResInspectionFile() {
	}

	/** full constructor */
	public TbResInspectionFile(Integer inspectionId, Integer fileId) {
		this.inspectionId = inspectionId;
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

	@Column(name = "inspection_id", nullable = false)

	public Integer getInspectionId() {
		return this.inspectionId;
	}

	public void setInspectionId(Integer inspectionId) {
		this.inspectionId = inspectionId;
	}

	@Column(name = "file_id", nullable = false)

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}