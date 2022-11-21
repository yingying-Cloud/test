package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResCropFarmingWaterFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_crop_farming_water_file")

public class TbResCropFarmingWaterFile extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer resCropFarmingWaterId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResCropFarmingWaterFile() {
	}

	/** full constructor */
	public TbResCropFarmingWaterFile(Integer resCropFarmingWaterId, Integer fileId,
			Integer delFlag) {
		this.resCropFarmingWaterId = resCropFarmingWaterId;
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

	@Column(name = "res_crop_farming_water_id", nullable = false)

	public Integer getResCropFarmingWaterId() {
		return this.resCropFarmingWaterId;
	}

	public void setResCropFarmingWaterId(Integer resCropFarmingWaterId) {
		this.resCropFarmingWaterId = resCropFarmingWaterId;
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