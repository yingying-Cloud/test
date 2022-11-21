package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_res_supply_release_file database table.
 * 
 */
@Entity
@Table(name="tb_res_supply_release_file")
public class TbResSupplyReleaseFile extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer fileId;
	private Integer supplyReleaseId;

	public TbResSupplyReleaseFile() {
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


	@Column(name="supply_release_id")
	public Integer getSupplyReleaseId() {
		return this.supplyReleaseId;
	}

	public void setSupplyReleaseId(Integer supplyReleaseId) {
		this.supplyReleaseId = supplyReleaseId;
	}

}