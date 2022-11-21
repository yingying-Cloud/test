package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPlantProtection entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_plant_service")

public class TbPlantService extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String name;
	private String orderDescribe;
	private String content;
	private String price;
	private Integer delFlag;
	private Date startTime;
	private Date endTime;
	private String serverType;
	private String status;
	// Constructors

	/** default constructor */
	public TbPlantService() {
	}

	/** minimal constructor */
	public TbPlantService(Integer enterpriseId, String name, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.name = name;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbPlantService(Integer enterpriseId, String name, String orderDescribe, String content, String price,
			Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.name = name;
		this.orderDescribe = orderDescribe;
		this.content = content;
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

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "name", nullable = false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "order_describe")

	public String getOrderDescribe() {
		return this.orderDescribe;
	}

	public void setOrderDescribe(String orderDescribe) {
		this.orderDescribe = orderDescribe;
	}

	@Column(name = "content")

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "price", precision = 10, scale = 0)

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
	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name = "server_type")

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}