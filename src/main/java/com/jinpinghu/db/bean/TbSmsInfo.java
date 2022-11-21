package com.jinpinghu.db.bean;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbSmsInfo entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_sms_info")
public class TbSmsInfo extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private Integer id;
	private String phones;
	private String template;
	private String content;
	private Date time;

	// Constructors

	/** default constructor */
	public TbSmsInfo() {
	}

	/** full constructor */
	public TbSmsInfo(String phones,String template, String content, Date time) {
		this.phones = phones;
		this.template = template;
		this.content = content;
		this.time = time;
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

	@Column(name = "phones", nullable = false, length = 65535)
	public String getPhones() {
		return this.phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	@Column(name = "template", nullable = false)
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "time", nullable = false, length = 19)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}