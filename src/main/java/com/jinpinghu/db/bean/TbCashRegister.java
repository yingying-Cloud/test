package com.jinpinghu.db.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cash_register")
public class TbCashRegister extends BaseZEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cashRegisterId;
	private String baiduAifaceSn;
	private Integer machineVersion;
	private Date inputTime;
	private Date updateTime;
	private Integer delFlag;
	
	
	public TbCashRegister(String cashRegisterId, String baiduAifaceSn,Date inputTime,Date updateTime,Integer delFlag) {
		super();
		this.cashRegisterId = cashRegisterId;
		this.baiduAifaceSn = baiduAifaceSn;
		this.delFlag = delFlag;
		this.updateTime = updateTime;
		this.inputTime = inputTime;
	}
	
	
	public TbCashRegister() {
		super();
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "cash_register_id")
	public String getCashRegisterId() {
		return cashRegisterId;
	}
	public void setCashRegisterId(String cashRegisterId) {
		this.cashRegisterId = cashRegisterId;
	}
	@Column(name = "baidu_aiface_sn")
	public String getBaiduAifaceSn() {
		return baiduAifaceSn;
	}
	public void setBaiduAifaceSn(String baiduAifaceSn) {
		this.baiduAifaceSn = baiduAifaceSn;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@Column(name = "del_flag")
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "machine_version")
	public Integer getMachineVersion() {
		return machineVersion;
	}


	public void setMachineVersion(Integer machineVersion) {
		this.machineVersion = machineVersion;
	}

}
