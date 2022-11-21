package com.jinpinghu.db.bean;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TbResToolFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_loan_application_file")

public class TbResLoanApplicationFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer loanApplicationId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResLoanApplicationFile() {
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

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "loan_application_id", nullable = false)

	public Integer getLoanApplicationId() {
		return loanApplicationId;
	}

	public void setLoanApplicationId(Integer loanApplicationId) {
		this.loanApplicationId = loanApplicationId;
	}
}