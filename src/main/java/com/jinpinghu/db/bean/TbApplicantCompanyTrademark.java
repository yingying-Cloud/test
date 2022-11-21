package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_applicant_company_trademark database table.
 * 
 */
@Entity
@Table(name="tb_applicant_company_trademark")
public class TbApplicantCompanyTrademark extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer applicantCompanyId;
	private Integer delFlag;
	private Date endTime;
	private String name;
	private Date startTime;
	private String type;

	public TbApplicantCompanyTrademark() {
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


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}