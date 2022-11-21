package com.jinpinghu.db.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResFileGreenhouses entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_res_file_greenhouses")
@SuppressWarnings("serial")
public class TbResFileGreenhouses extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fileId;
	private Integer greenhousesId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResFileGreenhouses() {
	}

	/** full constructor */
	public TbResFileGreenhouses(Integer fileId, Integer greenhousesId, Integer delFlag) {
		this.fileId = fileId;
		this.greenhousesId = greenhousesId;
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

	

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "greenhouses_id", nullable = false)

	public Integer getGreenhousesId() {
		return greenhousesId;
	}

	public void setGreenhousesId(Integer greenhousesId) {
		this.greenhousesId = greenhousesId;
	}

}