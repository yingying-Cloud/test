package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResToolOrder entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_brand_order_car")

public class TbResBrandOrderCar extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer brandCarId;
	private Integer brandOrderId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResBrandOrderCar() {
	}

	/** minimal constructor */
	public TbResBrandOrderCar(Integer brandCarId, Integer brandOrderId,Integer delFlag) {
		this.brandCarId = brandCarId;
		this.brandOrderId=brandOrderId;
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

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "brand_car_id")

	public Integer getBrandCarId() {
		return brandCarId;
	}

	public void setBrandCarId(Integer brandCarId) {
		this.brandCarId = brandCarId;
	}
	@Column(name = "brand_order_id")

	public Integer getBrandOrderId() {
		return brandOrderId;
	}

	public void setBrandOrderId(Integer brandOrderId) {
		this.brandOrderId = brandOrderId;
	}
}