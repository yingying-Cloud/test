package com.jinpinghu.db.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_applicant_company_check_record")
public class TbApplicantCompanyCheckRecord extends BaseZEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer applicantCompanyId;
	private Integer status;
	private Date inputTime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "applicant_company_id")
	public Integer getApplicantCompanyId() {
		return applicantCompanyId;
	}
	public void setApplicantCompanyId(Integer applicantCompanyId) {
		this.applicantCompanyId = applicantCompanyId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
