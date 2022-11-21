package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_plant_protection_server_time")
public class TbPlantProtectionServerTime  extends BaseZEntity  implements java.io.Serializable {
	private Integer id;
	private Integer protectionId;
	private Date serverTime;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "protection_id")
	public Integer getProtectionId() {
		return protectionId;
	}

	public void setProtectionId(Integer protectionId) {
		this.protectionId = protectionId;
	}
	@Column(name = "server_time")
	public Date getServerTime() {
		return serverTime;
	}

	public void setServerTime(Date serverTime) {
		this.serverTime = serverTime;
	}
	
}
