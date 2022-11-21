package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_plant_protection_order_completion database table.
 * 
 */
@Entity
@Table(name="tb_plant_protection_order_completion")
public class TbPlantProtectionOrderCompletion extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String area;
	private Date completeTime;
	private String content;
	private Integer delFlag;
	private Integer plantProtectionOrderId;
	private String price;

	public TbPlantProtectionOrderCompletion() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="complete_time")
	public Date getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}


	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	@Column(name="del_flag")
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	@Column(name="plant_protection_order_id")
	public Integer getPlantProtectionOrderId() {
		return this.plantProtectionOrderId;
	}

	public void setPlantProtectionOrderId(Integer plantProtectionOrderId) {
		this.plantProtectionOrderId = plantProtectionOrderId;
	}


	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}