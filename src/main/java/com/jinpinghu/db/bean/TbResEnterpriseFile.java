package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResCropFarmingFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_enterprise_file")

public class TbResEnterpriseFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private Integer fileId;
	private Integer type;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResEnterpriseFile() {
	}

	/** full constructor */
	public TbResEnterpriseFile(Integer enterpriseId, Integer fileId, Integer delFlag) {
		this.enterpriseId = enterpriseId;
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

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
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