package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbDecive entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_video")

public class TbVideo extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String videoAddress;
	private String videoName;
	private String describe;
	private Date inputTime;
	private Integer delFlag;
	private String deviceSerial;
	private String channelNo;

	// Constructors

	/** default constructor */
	public TbVideo() {
	}

	/** minimal constructor */
	public TbVideo(Integer enterpriseId, String videoName, Date inputTime, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.videoName = videoName;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbVideo(Integer enterpriseId, String videoAddress, String videoName, String describe, Date inputTime,
			Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.videoAddress = videoAddress;
		this.videoName = videoName;
		this.describe = describe;
		this.inputTime = inputTime;
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

	@Column(name = "enterprise_id", nullable = false)
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "describe_")

	public String getDescribe() {
		return this.describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}


	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "video_address")
	public String getVideoAddress() {
		return videoAddress;
	}

	public void setVideoAddress(String videoAddress) {
		this.videoAddress = videoAddress;
	}
	@Column(name = "video_name")
	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	@Column(name = "device_serial")
	public String getDeviceSerial() {
		return deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}
	@Column(name = "channel_no")
	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

}