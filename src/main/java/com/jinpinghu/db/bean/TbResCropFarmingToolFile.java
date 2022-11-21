package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResCropFarmingToolFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_crop_farming_tool_file")

public class TbResCropFarmingToolFile extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer cropFarmingToolId;
	private Integer fileId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResCropFarmingToolFile() {
	}

	/** full constructor */
	public TbResCropFarmingToolFile(Integer cropFarmingToolId, Integer fileId, Integer delFlag) {
		this.cropFarmingToolId = cropFarmingToolId;
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

	@Column(name = "crop_farming_tool_id", nullable = false)

	public Integer getCropFarmingToolId() {
		return this.cropFarmingToolId;
	}

	public void setCropFarmingToolId(Integer cropFarmingToolId) {
		this.cropFarmingToolId = cropFarmingToolId;
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