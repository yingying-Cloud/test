package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_tool")

public class TbResTool extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer toolId;
	private Integer plantToolId;
	private Integer plantEnterpriseId;
	private Integer delFlag;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "tool_id", nullable = false)

	public Integer getToolId() {
		return this.toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "plant_tool_id" )
	public Integer getPlantToolId() {
		return plantToolId;
	}

	public void setPlantToolId(Integer plantToolId) {
		this.plantToolId = plantToolId;
	}
	@Column(name = "plant_enterprise_id" )
	public Integer getPlantEnterpriseId() {
		return plantEnterpriseId;
	}

	public void setPlantEnterpriseId(Integer plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}
}
