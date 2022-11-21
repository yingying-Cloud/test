package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_res_expert_file database table.
 * 
 */
@Entity
@Table(name="tb_res_expert_file")
public class TbResExpertFile extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer expertId;
	private Integer fileId;
	private Integer type;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResExpertFile() {
	}

	/** full constructor */
	public TbResExpertFile(Integer enterpriseId, Integer fileId, Integer delFlag) {
		this.expertId = enterpriseId;
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

	@Column(name = "expert_id", nullable = false)

	public Integer getExpertId() {
		return this.expertId;
	}

	public void setExpertId(Integer enterpriseId) {
		this.expertId = enterpriseId;
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
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}