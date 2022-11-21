package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_res_plant_protection_mechine")
public class TbResPlantProtectionMechine extends BaseZEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9183348668139792469L;
	private Integer id;
	private Integer plantProtectionId;
	private Integer mechineId;
	
	
	public TbResPlantProtectionMechine() {
	}
	
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
	@Column(name = "mechine_id")
	public Integer getMechineId() {
		return mechineId;
	}
	public void setMechineId(Integer mechineId) {
		this.mechineId = mechineId;
	}

}
