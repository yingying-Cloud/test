package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_res_user_expert database table.
 * 
 */
@Entity
@Table(name="tb_res_user_expert")
public class TbResUserExpert extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int delFlag;
	private int expertId;
	private int userTabId;

	public TbResUserExpert() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="del_flag")
	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name="expert_id")
	public int getExpertId() {
		return expertId;
	}

	public void setExpertId(int expertId) {
		this.expertId = expertId;
	}
	@Column(name="user_tab_id")
	public int getUserTabId() {
		return userTabId;
	}

	public void setUserTabId(int userTabId) {
		this.userTabId = userTabId;
	}

}