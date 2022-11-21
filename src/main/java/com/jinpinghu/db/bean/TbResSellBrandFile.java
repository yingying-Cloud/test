package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResCropFarmingFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_sell_brand_file")

public class TbResSellBrandFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer sellBrandId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResSellBrandFile() {
	}

	/** full constructor */
	public TbResSellBrandFile(Integer sellBrandId, Integer fileId, Integer delFlag) {
		this.sellBrandId = sellBrandId;
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

	@Column(name = "sell_brand_id", nullable = false)

	public Integer getSellBrandId() {
		return this.sellBrandId;
	}

	public void setSellBrandId(Integer sellBrandId) {
		this.sellBrandId = sellBrandId;
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

}