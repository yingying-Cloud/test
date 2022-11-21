package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResPostFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_evaluate_file")

public class TbResEvaluateFile extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer evaluateId;
	private Integer fileId;

	// Constructors

	/** default constructor */
	public TbResEvaluateFile() {
	}

	/** full constructor */
	public TbResEvaluateFile(Integer evaluateId, Integer fileId) {
		this.setEvaluateId(evaluateId);
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

	@Column(name = "file_id", nullable = false)

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
	@Column(name = "evaluate_id", nullable = false)

	public Integer getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Integer evaluateId) {
		this.evaluateId = evaluateId;
	}

}