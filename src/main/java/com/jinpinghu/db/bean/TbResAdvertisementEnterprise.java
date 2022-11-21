package com.jinpinghu.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tb_res_advertisement_enterprise")
public class TbResAdvertisementEnterprise  extends BaseZEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer advertisementId;
	private Integer enterpriseId;

	public TbResAdvertisementEnterprise() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="advertisement_id")
	public Integer getAdvertisementId() {
		return advertisementId;
	}


	public void setAdvertisementId(Integer advertisementId) {
		this.advertisementId = advertisementId;
	}

	@Column(name="enterprise_id")
	public Integer getEnterpriseId() {
		return enterpriseId;
	}


	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}