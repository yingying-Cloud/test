package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbCart entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_cart")

public class TbCart extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userid;
	private Integer toolId;
	private String cartNum;
	private Integer status;
	private Date updateTime;
	private Date inputTime;

	// Constructors

	/** default constructor */
	public TbCart() {
	}

	/** minimal constructor */
	public TbCart(Date updateTime, Date inputTime) {
		this.updateTime = updateTime;
		this.inputTime = inputTime;
	}

	/** full constructor */
	public TbCart(String userid, Integer toolId, String cartNum, Integer status, Date updateTime,
			Date inputTime) {
		this.userid = userid;
		this.toolId = toolId;
		this.cartNum = cartNum;
		this.status = status;
		this.updateTime = updateTime;
		this.inputTime = inputTime;
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

	@Column(name = "userid")

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "tool_id")

	public Integer getToolId() {
		return this.toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	@Column(name = "cart_num", length = 50)

	public String getCartNum() {
		return this.cartNum;
	}

	public void setCartNum(String cartNum) {
		this.cartNum = cartNum;
	}

	@Column(name = "status")

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "update_time", nullable = false, length = 19)

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

}