package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbTool entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_tool_recovery")

public class TbToolRecovery extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String name;
	private String model;
	private String specification;
	private String unit;
	private String number;
	private String describe;
	private Integer delFlag;
	private Integer type;
	private Date inputTime;
	private String price;

	// Constructors

	/** default constructor */
	public TbToolRecovery() {
	}

	/** minimal constructor */
	public TbToolRecovery(Integer enterpriseId, String name, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.name = name;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbToolRecovery(Integer enterpriseId, String name, String model,  String specification, String unit,
			String number, String describe, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.name = name;
		this.model = model;
		this.specification = specification;
		this.unit = unit;
		this.number = number;
		this.describe = describe;
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

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "name", nullable = false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "model")

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "specification")

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "unit")

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "number")

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "describe_")

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}