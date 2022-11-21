package com.jinpinghu.db.bean;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TbResToolOrder entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_brand_car_num")

public class TbResBrandCarNum extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer brandCarId;
	private Integer enterpriseId;
	private String num;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResBrandCarNum() {
	}

	/** minimal constructor */
	public TbResBrandCarNum(Integer brandCarId, String num, Integer delFlag) {
		this.brandCarId = brandCarId;
		this.num=num;
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
	@Column(name = "num")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	@Column(name = "enterprise_id")
//	public Integer getTrademarkId() {
//		return trademarkId;
//	}
//
//	public void setTrademarkId(Integer trademarkId) {
//		this.trademarkId = trademarkId;
//	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
}