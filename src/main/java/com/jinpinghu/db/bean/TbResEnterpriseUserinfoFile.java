package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResEnterpriseUserinfoFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_enterprise_userinfo_file")

public class TbResEnterpriseUserinfoFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseUserinfoId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResEnterpriseUserinfoFile() {
	}

	/** full constructor */
	public TbResEnterpriseUserinfoFile(Integer enterpriseUserinfoId, Integer fileId, Integer delFlag) {
		this.enterpriseUserinfoId = enterpriseUserinfoId;
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

	@Column(name = "enterprise_userinfo_id", nullable = false)

	public Integer getEnterpriseUserinfoId() {
		return this.enterpriseUserinfoId;
	}

	public void setEnterpriseUserinfoId(Integer enterpriseUserinfoId) {
		this.enterpriseUserinfoId = enterpriseUserinfoId;
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