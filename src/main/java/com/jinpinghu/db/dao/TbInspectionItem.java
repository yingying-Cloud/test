package com.jinpinghu.db.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jinpinghu.db.bean.BaseZEntity;

/**
 * TbInspectionItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_inspection_item", catalog = "jinpinghu")

public class TbInspectionItem extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer inspectionType;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbInspectionItem() {
	}

	/** full constructor */
	public TbInspectionItem(String name, Integer inspectionType, Integer delFlag) {
		this.name = name;
		this.inspectionType = inspectionType;
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

	@Column(name = "name", length = 500)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "inspection_type")

	public Integer getInspectionType() {
		return this.inspectionType;
	}

	public void setInspectionType(Integer inspectionType) {
		this.inspectionType = inspectionType;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}