package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbAgriculturalGreenhouses entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_agricultural_classroom")
@SuppressWarnings("serial")
public class TbAgriculturalClassroom extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private String title;
	private Date inputTime;
	// Constructors

	/** default constructor */
	public TbAgriculturalClassroom() {
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

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



}