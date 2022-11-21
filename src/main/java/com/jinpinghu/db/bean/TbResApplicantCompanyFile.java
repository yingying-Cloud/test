package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_res_applicant_company_file database table.
 * 
 */
@Entity
@Table(name="tb_res_applicant_company_file")
public class TbResApplicantCompanyFile extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer applicantCompanyId;
	private Integer delFlag;
	private Integer fileId;
	private Integer type;

	public TbResApplicantCompanyFile() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="applicant_company_id")
	public Integer getApplicantCompanyId() {
		return this.applicantCompanyId;
	}

	public void setApplicantCompanyId(Integer applicantCompanyId) {
		this.applicantCompanyId = applicantCompanyId;
	}


	@Column(name="del_flag")
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	@Column(name="file_id")
	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}


	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}