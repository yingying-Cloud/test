package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbService entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_service")

public class TbService extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String address;
	private String x;
	private String y;
	private Integer type;
	private String linkPeople;
	private String linkMobile;
	private String delFlag;

	// Constructors

	/** default constructor */
	public TbService() {
	}

	/** minimal constructor */
	public TbService(String name, Integer type, String delFlag) {
		this.name = name;
		this.type = type;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbService(String name, String address, String x, String y, Integer type, String linkPeople,
			String linkMobile, String delFlag) {
		this.name = name;
		this.address = address;
		this.x = x;
		this.y = y;
		this.type = type;
		this.linkPeople = linkPeople;
		this.linkMobile = linkMobile;
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

	@Column(name = "name", nullable = false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address")

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "x")

	public String getX() {
		return this.x;
	}

	public void setX(String x) {
		this.x = x;
	}

	@Column(name = "y")

	public String getY() {
		return this.y;
	}

	public void setY(String y) {
		this.y = y;
	}

	@Column(name = "type", nullable = false)

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "link_people")

	public String getLinkPeople() {
		return this.linkPeople;
	}

	public void setLinkPeople(String linkPeople) {
		this.linkPeople = linkPeople;
	}

	@Column(name = "link_mobile")

	public String getLinkMobile() {
		return this.linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	@Column(name = "del_flag", nullable = false)

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}