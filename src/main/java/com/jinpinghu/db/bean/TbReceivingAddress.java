package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbReceivingAddress entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_receiving_address")

public class TbReceivingAddress extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userId;
	private String linkPeople;
	private String linkMobile;
	private String province;
	private String city;
	private String county;
	private String address;
	private Date inputTime;
	private Integer listIndex;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbReceivingAddress() {
	}

	/** minimal constructor */
	public TbReceivingAddress(String userId, String linkPeople, String linkMobile, Date inputTime,
			Integer delFlag) {
		this.userId = userId;
		this.linkPeople = linkPeople;
		this.linkMobile = linkMobile;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbReceivingAddress(String userId, String linkPeople, String linkMobile, String province, String city,
			String county, String address, Date inputTime,Integer listIndex, Integer delFlag) {
		this.userId = userId;
		this.linkPeople = linkPeople;
		this.linkMobile = linkMobile;
		this.province = province;
		this.city = city;
		this.county = county;
		this.address = address;
		this.inputTime = inputTime;
		this.listIndex = listIndex;
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

	@Column(name = "user_id", nullable = false)

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "link_people", nullable = false)

	public String getLinkPeople() {
		return this.linkPeople;
	}

	public void setLinkPeople(String linkPeople) {
		this.linkPeople = linkPeople;
	}

	@Column(name = "link_mobile", nullable = false, length = 11)

	public String getLinkMobile() {
		return this.linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}
	@Column(name = "list_index", length = 2)

	public Integer getListIndex() {
		return listIndex;
	}

	public void setListIndex(Integer listIndex) {
		this.listIndex = listIndex;
	}


	@Column(name = "province")

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city")

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "county")

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	@Column(name = "address")

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}