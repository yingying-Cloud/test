package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResToolOrder entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_shopping_car")

public class TbShoppingCar extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private Integer status;
	private Integer toolId;
	private String num;
	private String price;
	private Integer isPay;
	private Integer delFlag;
	private Date inputTime;
	private String originalPrice;
	private String uniformPrice;

	// Constructors

	/** default constructor */
	public TbShoppingCar() {
	}

	/** minimal constructor */
	public TbShoppingCar(Integer enterpriseId, Integer toolId, String num, String price, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.toolId = toolId;
		this.num = num;
		this.price = price;
		this.delFlag = delFlag;
	}


	public TbShoppingCar(Integer enterpriseId, Integer status, Integer toolId, String num, String price,
			Integer isPay, Integer delFlag, Date inputTime, String originalPrice, String uniformPrice) {
		this.enterpriseId = enterpriseId;
		this.status = status;
		this.toolId = toolId;
		this.num = num;
		this.price = price;
		this.isPay = isPay;
		this.delFlag = delFlag;
		this.inputTime = inputTime;
		this.originalPrice = originalPrice;
		this.uniformPrice = uniformPrice;
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

	@Column(name = "enterprise_id")

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "tool_id", nullable = false)

	public Integer getToolId() {
		return this.toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
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

	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "original_price")
	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	@Column(name = "uniform_price")
	public String getUniformPrice() {
		return uniformPrice;
	}

	public void setUniformPrice(String uniformPrice) {
		this.uniformPrice = uniformPrice;
	}

}