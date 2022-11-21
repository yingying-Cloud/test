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
@Table(name = "tb_agricultural_advice")
@SuppressWarnings("serial")
public class TbAgriculturalAdvice extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private String title;
	private Date inputTime;
	private String author;
	private String adviceType;
	private String content;
	// Constructors

	/** default constructor */
	public TbAgriculturalAdvice() {
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
	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	@Column(name = "advice_type")
	public String getAdviceType() {
		return adviceType;
	}

	public void setAdviceType(String adviceType) {
		this.adviceType = adviceType;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}



}