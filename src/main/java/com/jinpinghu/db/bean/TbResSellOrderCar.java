package com.jinpinghu.db.bean;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TbResToolOrder entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_sell_order_car")

public class TbResSellOrderCar extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer sellCarId;
	private Integer sellOrderId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResSellOrderCar() {
	}

	/** minimal constructor */
	public TbResSellOrderCar(Integer sellCarId, Integer sellOrderId, Integer delFlag) {
		this.sellCarId = sellCarId;
		this.sellOrderId=sellOrderId;
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

	@Column(name = "sell_car_id")

	public Integer getSellCarId() {
		return sellCarId;
	}

	public void setSellCarId(Integer brandCarId) {
		this.sellCarId = brandCarId;
	}
	@Column(name = "sell_order_id")

	public Integer getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(Integer brandOrderId) {
		this.sellOrderId = brandOrderId;
	}
}