package com.jinpinghu.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_res_enterprise_linkorderinfo")
public class TbResEnterpriseLinkorderinfo extends BaseZEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer enterpriseId;
	private Integer linkOrderInfoId;

	public TbResEnterpriseLinkorderinfo() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "enterprise_id")
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "link_order_info_id")
	public Integer getLinkOrderInfoId() {
		return linkOrderInfoId;
	}

	public void setLinkOrderInfoId(Integer linkOrderInfoId) {
		this.linkOrderInfoId = linkOrderInfoId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
