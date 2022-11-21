package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPostReply entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_post_reply")

public class TbPostReply extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer postId;
	private Integer userTabId;
	private String content;
	private Date inputTime;
	private Integer status;
	private Integer delFlag;
	private String star;
	private String toolName;

	// Constructors

	/** default constructor */
	public TbPostReply() {
	}

	/** minimal constructor */
	public TbPostReply(Integer postId, Integer userTabId, String content, Date inputTime, Integer status) {
		this.postId = postId;
		this.userTabId = userTabId;
		this.content = content;
		this.inputTime = inputTime;
		this.status = status;
	}

	/** full constructor */
	public TbPostReply(Integer postId, Integer userTabId, String content, Date inputTime, Integer status,
			Integer delFlag) {
		this.postId = postId;
		this.userTabId = userTabId;
		this.content = content;
		this.inputTime = inputTime;
		this.status = status;
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

	@Column(name = "post_id", nullable = false)

	public Integer getPostId() {
		return this.postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	@Column(name = "user_tab_id", nullable = false)

	public Integer getUserTabId() {
		return this.userTabId;
	}

	public void setUserTabId(Integer userTabId) {
		this.userTabId = userTabId;
	}

	@Column(name = "content", nullable = false)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "status", nullable = false)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "star")
	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}
	@Column(name = "tool_name")
	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

}