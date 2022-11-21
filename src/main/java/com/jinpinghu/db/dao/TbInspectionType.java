package com.jinpinghu.db.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.Table;

import com.jinpinghu.db.bean.BaseZEntity;

/**
 * TbInspectionType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_inspection_type", catalog = "jinpinghu")

public class TbInspectionType extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private BigDecimal point;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbInspectionType() {
	}

	/** full constructor */
	public TbInspectionType(String name, BigDecimal point, Integer delFlag) {
		this.name = name;
		this.point = point;
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

	@Column(name = "name")

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "point", precision = 18, scale = 0)

	public BigDecimal getPoint() {
		return this.point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}