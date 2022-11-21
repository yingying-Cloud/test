package com.jinpinghu.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_res_plant_protection_file")
public class TbResPlantProtectionFile extends BaseZEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5921413513216186324L;
	
	private Integer id;
	private Integer plantProtectionId;
	private Integer fileId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "plant_protection_id")
	public Integer getPlantProtectionId() {
		return plantProtectionId;
	}
	public void setPlantProtectionId(Integer plantProtectionId) {
		this.plantProtectionId = plantProtectionId;
	}
	@Column(name = "file_id")
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
	

}
