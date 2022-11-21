package com.jinpinghu.db.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResFilePlantWarehouse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_res_file_plant_warehouse")
@SuppressWarnings("serial")
public class TbResFilePlantWarehouse extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fileId;
	private Integer plantWarehouseId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResFilePlantWarehouse() {
	}

	/** full constructor */
	public TbResFilePlantWarehouse(Integer fileId, Integer plantWarehouseId, Integer delFlag) {
		this.fileId = fileId;
		this.plantWarehouseId = plantWarehouseId;
		this.delFlag = delFlag;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "file_id", nullable = false)

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "plant_warehouse_id", nullable = false)

	public Integer getPlantWarehouseId() {
		return this.plantWarehouseId;
	}

	public void setPlantWarehouseId(Integer plantWarehouseId) {
		this.plantWarehouseId = plantWarehouseId;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}