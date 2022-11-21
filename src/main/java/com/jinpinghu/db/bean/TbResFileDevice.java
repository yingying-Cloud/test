package com.jinpinghu.db.bean;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResFileBase entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_res_file_device")
@SuppressWarnings("serial")
public class TbResFileDevice extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fileId;
	private Integer deviceId;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResFileDevice() {
	}

	/** full constructor */
	public TbResFileDevice(Integer fileId, Integer deviceId, Integer delFlag) {
		this.fileId = fileId;
		this.deviceId = deviceId;
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

	@Column(name = "device_id", nullable = false)

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}