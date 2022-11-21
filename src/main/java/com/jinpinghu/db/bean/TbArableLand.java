package com.jinpinghu.db.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tb_arable_land")
public class TbArableLand extends BaseZEntity implements java.io.Serializable {
	private String id;
	private String villageGroup;
	private String userName;
	private String idCard;
	private String area;
	private String riceArea;
	private String wheatArea;
	private String vegetablesArea;
	private String fruitsArea;
	private String otherArea;
	private String remark;
	private String town;
	private Integer delFlag;

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "village_group")
	public String getVillageGroup() {
		return villageGroup;
	}
	public void setVillageGroup(String villageGroup) {
		this.villageGroup = villageGroup;
	}
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "id_card")
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	@Column(name = "area")
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "rice_area")
	public String getRiceArea() {
		return riceArea;
	}
	public void setRiceArea(String riceArea) {
		this.riceArea = riceArea;
	}
	@Column(name = "wheat_area")
	public String getWheatArea() {
		return wheatArea;
	}
	public void setWheatArea(String wheatArea) {
		this.wheatArea = wheatArea;
	}
	@Column(name = "vegetables_area")
	public String getVegetablesArea() {
		return vegetablesArea;
	}
	public void setVegetablesArea(String vegetablesArea) {
		this.vegetablesArea = vegetablesArea;
	}
	@Column(name = "fruits_area")
	public String getFruitsArea() {
		return fruitsArea;
	}
	public void setFruitsArea(String fruitsArea) {
		this.fruitsArea = fruitsArea;
	}
	@Column(name = "other_area")
	public String getOtherArea() {
		return otherArea;
	}
	public void setOtherArea(String otherArea) {
		this.otherArea = otherArea;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "town")
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	@Column(name = "del_flag")
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
