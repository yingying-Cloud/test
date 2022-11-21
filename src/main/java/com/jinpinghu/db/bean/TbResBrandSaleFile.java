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
@Table(name = "tb_res_brand_sale_file")

public class TbResBrandSaleFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer brandSaleId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResBrandSaleFile() {
	}

	/** full constructor */
	public TbResBrandSaleFile(Integer brandSaleId, Integer fileId, Integer delFlag) {
		this.brandSaleId = brandSaleId;
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
	@Column(name = "brand_sale_id")
	public Integer getBrandSaleId() {
		return brandSaleId;
	}

	public void setBrandSaleId(Integer brandSaleId) {
		this.brandSaleId = brandSaleId;
	}

}