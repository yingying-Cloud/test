package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_plant_service_order_completion database table.
 * 
 */
@Entity
@Table(name="tb_plant_service_order_completion")
public class TbPlantServiceOrderCompletion extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String area;
	private Date completeTime;
	private String content;
	private Integer delFlag;
	private Integer plantServiceOrderId;
	private String price;

	public TbPlantServiceOrderCompletion() {
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


	@Column(name="plant_service_order_id")
	public Integer getPlantServiceOrderId() {
		return this.plantServiceOrderId;
	}

	public void setPlantServiceOrderId(Integer plantServiceOrderId) {
		this.plantServiceOrderId = plantServiceOrderId;
	}


	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}