package com.jinpinghu.db.bean;

import java.util.Date;

public class TbVideoImg extends BaseZEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer videoId;
	private String fileUrl;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbVideoImg() {
	}

	/** full constructor */
	public TbVideoImg(Integer videoId, String fileUrl,Date inputTime, Integer delFlag) {
		this.videoId = videoId;
		this.fileUrl = fileUrl;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

}