package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResCropFarmingFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_crop_farming_file")

public class TbResCropFarmingFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer cropFarmingId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResCropFarmingFile() {
	}

	/** full constructor */
	public TbResCropFarmingFile(Integer cropFarmingId, Integer fileId, Integer delFlag) {
		this.cropFarmingId = cropFarmingId;
		this.fileId = fileId;
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

	@Column(name = "crop_farming_id", nullable = false)

	public Integer getCropFarmingId() {
		return this.cropFarmingId;
	}

	public void setCropFarmingId(Integer cropFarmingId) {
		this.cropFarmingId = cropFarmingId;
	}

	@Column(name = "file_id", nullable = false)

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}