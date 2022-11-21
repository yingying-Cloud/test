package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_expert database table.
 * 
 */
@Entity
@Table(name="tb_expert")
public class TbExpert extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String address;
	private Integer delFlag;
	private String goodsField;
	private String idcard;
	private Date inputTime;
	private String status;
	private String synopsis;
	private String cost;
	private String type;
	private String adnm;
	private String productTeam;

	public TbExpert() {
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "del_flag")
	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "goods_field")
	public String getGoodsField() {
		return goodsField;
	}

	public void setGoodsField(String goodsField) {
		this.goodsField = goodsField;
	}
	@Column(name = "idcard")
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "synopsis")
	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	@Column(name = "cost")
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "adnm")
	public String getAdnm() {
		return adnm;
	}

	public void setAdnm(String adnm) {
		this.adnm = adnm;
	}
	@Column(name = "product_team")
	public String getProductTeam() {
		return productTeam;
	}

	public void setProductTeam(String productTeam) {
		this.productTeam = productTeam;
	}



}