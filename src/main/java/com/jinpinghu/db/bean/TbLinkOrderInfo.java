package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbEnterpriseId entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_link_order_info")

public class TbLinkOrderInfo extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String zoneName;
	private String name;
	private String creditCode;
	private String legalPerson;
	private String legalPersonIdcard;
	private String linkPeople;
	private String linkMobile;
	private String address;
	private Date inputTime;
	private String area;
	private Integer delFlag;
	private Integer type;
	private String nation;
	private String country;
	private String sex;
	private String idcardPic;
	private String lastPic;
	private Date updateTime;
	private String isValidation;
	private Integer isSync;
	private String syncNumber;
	private String isOther;

	// Constructors

	/** default constructor */
	public TbLinkOrderInfo() {
		setIsSync(0);
	}

	/** minimal constructor */
	public TbLinkOrderInfo(Integer id, String name, Date inputTime, Integer delFlag) {
		this.id = id;
		this.name = name;
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

	@Column(name = "enterprise_id",nullable = false)

	public Integer getEnterpriseId() { return enterpriseId; }

	public void setEnterpriseId(Integer enterpriseId) { this.enterpriseId = enterpriseId; }

	@Column(name="zone_name",nullable = false)

	public String getZoneName() { return zoneName; }

	public void setZoneName(String zoneName) { this.zoneName = zoneName; }

	@Column(name = "name", nullable = false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	@Column(name = "credit_code")
	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	@Column(name = "legal_person")
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	@Column(name = "legal_person_idcard")
	public String getLegalPersonIdcard() {
		return legalPersonIdcard;
	}

	public void setLegalPersonIdcard(String legalPersonIdcard) {
		this.legalPersonIdcard = legalPersonIdcard;
	}
	@Column(name = "link_people")
	public String getLinkPeople() {
		return linkPeople;
	}

	public void setLinkPeople(String linkPeople) {
		this.linkPeople = linkPeople;
	}
	@Column(name = "link_mobile")
	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "idcard_pic")
	public String getIdcardPic() {
		return idcardPic;
	}

	public void setIdcardPic(String idcardPic) {
		this.idcardPic = idcardPic;
	}

	@Column(name = "last_pic")
	public String getLastPic() {
		return lastPic;
	}

	public void setLastPic(String lastPic) {
		this.lastPic = lastPic;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "is_validation")
	public String getIsValidation() {
		return isValidation;
	}

	public void setIsValidation(String isValidation) {
		this.isValidation = isValidation;
	}
	@Column(name = "is_sync")
	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}
	@Column(name = "sync_number")
	public String getSyncNumber() {
		return syncNumber;
	}

	public void setSyncNumber(String syncNumber) {
		this.syncNumber = syncNumber;
	}
	@Column(name = "is_other")
	public String getIsOther() {
		return isOther;
	}

	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}
	
	
}