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
@Table(name = "tb_applicant_company_credentials")
public class TbApplicantCompanyCredentials extends BaseZEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer applicantCompanyId;
	private String licenseName;
	private String licensingOrganization;
	private String licensingTime;
	private String licenseNo;
	private Integer delFlag;
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
	@Column(name = "del_flag")
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "license_name")
	public String getLicenseName() {
		return licenseName;
	}
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	@Column(name = "licensing_organization")
	public String getLicensingOrganization() {
		return licensingOrganization;
	}
	public void setLicensingOrganization(String licensingOrganization) {
		this.licensingOrganization = licensingOrganization;
	}
	@Column(name = "licensing_time")
	public String getLicensingTime() {
		return licensingTime;
	}
	public void setLicensingTime(String licensingTime) {
		this.licensingTime = licensingTime;
	}
	@Column(name = "license_no")
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	

}
