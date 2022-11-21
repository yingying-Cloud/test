package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_res_plant_protection_order_completion_file database table.
 * 
 */
@Entity
@Table(name="tb_res_plant_protection_order_completion_file")
public class TbResPlantProtectionOrderCompletionFile extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer delFlag;
	private Integer fileId;
	private Integer plantProtectionOrderCompletionId;

	public TbResPlantProtectionOrderCompletionFile() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="del_flag")
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	@Column(name="file_id")
	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}


	@Column(name="plant_protection_order_completion_id")
	public Integer getPlantProtectionOrderCompletionId() {
		return this.plantProtectionOrderCompletionId;
	}

	public void setPlantProtectionOrderCompletionId(Integer plantProtectionOrderCompletionId) {
		this.plantProtectionOrderCompletionId = plantProtectionOrderCompletionId;
	}

}