package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbFile entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_file")

public class TbFile extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private String fileName;
	private String fileSize;
	private Integer fileType;
	private String fileUrl;

	// Constructors

	/** default constructor */
	public TbFile() {
	}

	/** minimal constructor */
	public TbFile(Integer fileType) {
		this.fileType = fileType;
	}

	/** full constructor */
	public TbFile(String fileName, String fileSize, Integer fileType, String fileUrl) {
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
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

	@Column(name = "file_name")

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "file_size")

	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "file_type", nullable = false)

	public Integer getFileType() {
		return this.fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	@Column(name = "file_url")

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}