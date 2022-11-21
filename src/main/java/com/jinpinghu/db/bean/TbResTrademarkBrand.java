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
@Table(name = "tb_res_trademark_brand")

public class TbResTrademarkBrand extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer trademarkId;
	private Integer brandId;
	private Integer delFlag;
	private String area;
	private String yield;

	// Constructors

	/** default constructor */
	public TbResTrademarkBrand() {
	}

	/** full constructor */
	public TbResTrademarkBrand(Integer trademarkId, Integer brandId, Integer delFlag) {
		this.trademarkId = trademarkId;
		this.brandId = brandId;
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
	@Column(name = "brand_id")
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	@Column(name = "area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "yield")
	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

}