package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbShoppingCarBook entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_shopping_car_book")

public class TbShoppingCarBook extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer orderBookId;
	private Integer toolId;
	private String num;
	private Integer status;
	private String price;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbShoppingCarBook() {
	}

	/** minimal constructor */
	public TbShoppingCarBook(Integer toolId, String num, String price) {
		this.toolId = toolId;
		this.num = num;
		this.price = price;
	}

	/** full constructor */
	public TbShoppingCarBook(Integer orderBookId, Integer toolId, String num, Integer status, String price, Date inputTime, Integer delFlag) {
		this.orderBookId = orderBookId;
		this.toolId = toolId;
		this.num = num;
		this.status = status;
		this.price = price;
		this.inputTime = inputTime;
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

	@Column(name = "order_book_id")

	public Integer getOrderBookId() {
		return this.orderBookId;
	}

	public void setOrderBookId(Integer orderBookId) {
		this.orderBookId = orderBookId;
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

	@Column(name = "status")

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "price", nullable = false)

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "input_time", length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}