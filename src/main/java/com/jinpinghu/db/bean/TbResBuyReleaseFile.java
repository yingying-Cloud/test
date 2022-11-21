package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_res_buy_release_file database table.
 * 
 */
@Entity
@Table(name="tb_res_buy_release_file")
public class TbResBuyReleaseFile extends BaseZEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer buyReleaseId;
	private Integer fileId;

	public TbResBuyReleaseFile() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="buy_release_id")
	public Integer getBuyReleaseId() {
		return this.buyReleaseId;
	}

	public void setBuyReleaseId(Integer buyReleaseId) {
		this.buyReleaseId = buyReleaseId;
	}


	@Column(name="file_id")
	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}