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
 * TbInspectionPoint entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_inspection_point", catalog = "jinpinghu")

public class TbInspectionPoint extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer year;
	private Integer enterpriseId;
	private BigDecimal point;
	private Integer inspectionId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbInspectionPoint() {
	}

	/** minimal constructor */
	public TbInspectionPoint(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	/** full constructor */
	public TbInspectionPoint(Integer year, Integer enterpriseId, BigDecimal point, Integer delFlag, Integer inspectionId) {
		this.year = year;
		this.enterpriseId = enterpriseId;
		this.point = point;
		this.delFlag = delFlag;
		this.inspectionId = inspectionId;
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
	
	@Column(name = "inspection_id")
	
	public Integer getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(Integer inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	@Column(name = "year")

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "point", precision = 18)

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