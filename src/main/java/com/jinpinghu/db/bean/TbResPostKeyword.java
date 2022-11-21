package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResPostKeyword entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_post_keyword")

public class TbResPostKeyword extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer postId;
	private Integer keywordId;

	// Constructors

	/** default constructor */
	public TbResPostKeyword() {
	}

	/** full constructor */
	public TbResPostKeyword(Integer postId, Integer keywordId) {
		this.postId = postId;
		this.keywordId = keywordId;
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

	@Column(name = "keyword_id", nullable = false)

	public Integer getKeywordId() {
		return this.keywordId;
	}

	public void setKeywordId(Integer keywordId) {
		this.keywordId = keywordId;
	}

}