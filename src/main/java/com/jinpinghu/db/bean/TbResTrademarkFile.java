package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResToolFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_trademark_file")

public class TbResTrademarkFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer trademarkId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResTrademarkFile() {
	}

	/** full constructor */
	public TbResTrademarkFile(Integer trademarkId, Integer fileId, Integer delFlag) {
		this.trademarkId = trademarkId;
		this.fileId = fileId;
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


	@Column(name = "file_id")

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "trademark_id")
	public Integer getTrademarkId() {
		return trademarkId;
	}

	public void setTrademarkId(Integer trademarkId) {
		this.trademarkId = trademarkId;
	}

}