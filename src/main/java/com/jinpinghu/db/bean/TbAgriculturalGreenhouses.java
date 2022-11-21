package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbAgriculturalGreenhouses entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_agricultural_greenhouses")
@SuppressWarnings("serial")
public class TbAgriculturalGreenhouses extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String greenhousesName;
	private Date inputTime;
	private String area;
	private String mu;
	private Integer delFlag;
	private Integer listOrder;
	private Integer classify;
	// Constructors

	/** default constructor */
	public TbAgriculturalGreenhouses() {
	}

	/** minimal constructor */
	public TbAgriculturalGreenhouses(Integer id, Integer enterpriseId, String greenhousesName, Date inputTime,
			Integer delFlag) {
		this.id = id;
		this.setEnterpriseId(enterpriseId);
		this.greenhousesName = greenhousesName;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbAgriculturalGreenhouses(Integer id, Integer enterpriseId, String greenhousesName, Date inputTime, String area,
			String mu, Integer delFlag) {
		this.id = id;
		this.setEnterpriseId(enterpriseId);
		this.greenhousesName = greenhousesName;
		this.inputTime = inputTime;
		this.area = area;
		this.mu = mu;
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

	@Column(name = "greenhouses_name", nullable = false)

	public String getGreenhousesName() {
		return this.greenhousesName;
	}

	public void setGreenhousesName(String greenhousesName) {
		this.greenhousesName = greenhousesName;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "area")

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "mu")
	public String getMu() {
		return mu;
	}

	public void setMu(String mu) {
		this.mu = mu;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "list_order")
	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}
	@Column(name = "classify")
	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}
	@Column(name = "enterprise_id")

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}