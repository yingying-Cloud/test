package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_plant_warehouse")
@SuppressWarnings("serial")
public class TbPlantWarehouse extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer brandId;
	private Integer workId;
	private Integer enterpriseId;
	private Integer persionId;
	private String persionName;
	private Integer recordType;
	private String batchNum;
	private String outDirection;
	private String logisticsInfo;
	private String linkPeople;
	private String linkMobile;
	private String number;
	private String oddNumber;
	private String wrapper;
	private Date inputTime;
	private Integer delFlag;
	private String price;

	// Constructors

	/** default constructor */
	public TbPlantWarehouse() {
	}

	/** minimal constructor */
	public TbPlantWarehouse(Integer brandId, Integer recordType, String number,
			Date inputTime, Integer delFlag) {
		this.setBrandId(brandId);
		this.recordType = recordType;
		this.number = number;
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

	@Column(name = "work_id", nullable = false)
	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	@Column(name = "persion_id")
	public Integer getPersionId() {
		return persionId;
	}

	public void setPersionId(Integer persionId) {
		this.persionId = persionId;
	}

	@Column(name = "persion_name")
	public String getPersionName() {
		return persionName;
	}

	public void setPersionName(String persionName) {
		this.persionName = persionName;
	}

	@Column(name = "record_type", nullable = false)

	public Integer getRecordType() {
		return this.recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	@Column(name = "batch_num")

	public String getBatchNum() {
		return this.batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	@Column(name = "out_direction")

	public String getOutDirection() {
		return this.outDirection;
	}

	public void setOutDirection(String outDirection) {
		this.outDirection = outDirection;
	}

	@Column(name = "logistics_info")

	public String getLogisticsInfo() {
		return this.logisticsInfo;
	}

	public void setLogisticsInfo(String logisticsInfo) {
		this.logisticsInfo = logisticsInfo;
	}

	@Column(name = "link_people")

	public String getLinkPeople() {
		return this.linkPeople;
	}

	public void setLinkPeople(String linkPeople) {
		this.linkPeople = linkPeople;
	}

	@Column(name = "link_mobile")

	public String getLinkMobile() {
		return this.linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	@Column(name = "number", nullable = false)

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
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
	@Column(name = "odd_number")
	public String getOddNumber() {
		return oddNumber;
	}

	public void setOddNumber(String oddNumber) {
		this.oddNumber = oddNumber;
	}
	@Column(name = "wrapper")
	public String getWrapper() {
		return wrapper;
	}

	public void setWrapper(String wrapper) {
		this.wrapper = wrapper;
	}
	@Column(name = "enterprise_id")
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	@Column(name = "brand_id", nullable = false)
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	@Column(name = "price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


}
