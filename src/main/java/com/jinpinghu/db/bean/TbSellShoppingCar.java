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
@Table(name = "tb_sell_shopping_car")

public class TbSellShoppingCar extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer status;
	private Integer brandId;
	private String num;
	private String price;
	private Integer isPay;
	private Integer delFlag;
	private Integer enterpriseId;
	private String brandPrice;
	private Integer sellId;
	// Constructors

	/** default constructor */
	public TbSellShoppingCar() {
	}

	/** minimal constructor */
	public TbSellShoppingCar(Integer brandId, String num, String price, Integer delFlag) {
		this.brandId = brandId;
		this.num = num;
		this.price = price;
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

	@Column(name = "num", nullable = false)

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Column(name = "price", nullable = false)

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "status" )
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "is_pay")
	public Integer getIsPay() {
		return isPay;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	@Column(name = "brand_id")
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	@Column(name = "enterprise_id")
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	@Column(name = "brand_price")
	public String getBrandPrice() {
		return brandPrice;
	}

	public void setBrandPrice(String brandPrice) {
		this.brandPrice = brandPrice;
	}
	@Column(name = "sell_id")
	public Integer getSellId() {
		return sellId;
	}

	public void setSellId(Integer sellId) {
		this.sellId = sellId;
	}
}