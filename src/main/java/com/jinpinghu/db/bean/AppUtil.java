package com.jinpinghu.db.bean;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Util entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "app_util")
public class AppUtil extends BaseZEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5610613179084730029L;
	// Fields

	private Integer id;
	private String key;
	private String value;
	private String note;
	private String url;
	private String size;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public AppUtil() {
	}

	/** minimal constructor */
	public AppUtil(String key, String value, String url, String size,
			Integer delFlag) {
		this.key = key;
		this.value = value;
		this.url = url;
		this.size = size;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public AppUtil(String key, String value, String note, String url, String size,
			Integer delFlag) {
		this.key = key;
		this.value = value;
		this.note = note;
		this.url = url;
		this.size = size;
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

	@Column(name = "`key`", nullable = false)
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "`value`", nullable = false)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "note", length = 1000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "url", nullable = false, length = 1000)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "size", nullable = false)
	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Column(name = "delFlag", nullable = false)
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}