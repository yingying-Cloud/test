package com.jinpinghu.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_res_agricultural_classroom_file")
public class TbResAgriculturalClassroomFile extends BaseZEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer agriculturalClassroomId;
	private Integer fileId;

	public TbResAgriculturalClassroomFile() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="file_id")
	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	@Column(name="agricultural_classroom_id")


	public Integer getAgriculturalClassroomId() {
		return agriculturalClassroomId;
	}


	public void setAgriculturalClassroomId(Integer agriculturalClassroomId) {
		this.agriculturalClassroomId = agriculturalClassroomId;
	}

}