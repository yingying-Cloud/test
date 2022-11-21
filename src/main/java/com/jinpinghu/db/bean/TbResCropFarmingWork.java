package com.jinpinghu.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_res_crop_farming_work")
public class TbResCropFarmingWork extends BaseZEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer cropFarmingId;
	private Integer workId;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "crop_farming_id")
	public Integer getCropFarmingId() {
		return cropFarmingId;
	}
	public void setCropFarmingId(Integer cropFarmingId) {
		this.cropFarmingId = cropFarmingId;
	}
	@Column(name = "work_id")
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
