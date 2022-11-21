package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbRotationAdvice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_rotation_advice")

public class TbRotationAdvice extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	private Date adviceTime;
	private Integer rotationId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbRotationAdvice() {
	}

	/** full constructor */
	public TbRotationAdvice(String title, String content, Date adviceTime, Integer rotationId,
			Integer delFlag) {
		this.title = title;
		this.content = content;
		this.adviceTime = adviceTime;
		this.rotationId = rotationId;
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

	@Column(name = "title")

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 65535)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "advice_time", length = 19)

	public Date getAdviceTime() {
		return this.adviceTime;
	}

	public void setAdviceTime(Date adviceTime) {
		this.adviceTime = adviceTime;
	}

	@Column(name = "rotation_id", length = 19)

	public Integer getRotationId() {
		return this.rotationId;
	}

	public void setRotationId(Integer rotationId) {
		this.rotationId = rotationId;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}