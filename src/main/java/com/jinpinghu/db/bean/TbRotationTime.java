package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbRotationEnterprise entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_rotation_time")

public class TbRotationTime extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String rotationTime;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbRotationTime() {
	}

	/** full constructor */
	public TbRotationTime(String rotationTime, Date inputTime, Integer delFlag) {
		this.rotationTime=rotationTime;
		this.inputTime=inputTime;
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

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "rotation_time")
	public String getRotationTime() {
		return rotationTime;
	}

	public void setRotationTime(String rotationTime) {
		this.rotationTime = rotationTime;
	}

}