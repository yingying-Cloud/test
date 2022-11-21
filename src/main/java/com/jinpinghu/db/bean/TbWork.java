package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbWork entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_work")

public class TbWork  extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String addPeople;
	private String workName;
	private String workSn;
	private String landBlockSn;
	private String area;
	private String crop;
	private Date startTime;
	private Date endTime;
	private Date recoveryTime;
	private String expectedProduction;
	private Date inputTime;
	private Integer delFlag;
	private String purchaseSource;
	private String purchasePeople;
	private Date purchaseTime;
	private Integer greenhousesId;

	// Constructors

	/** default constructor */
	public TbWork() {
	}

	/** minimal constructor */
	public TbWork(Integer enterpriseId, String addPeople, String workName, String workSn, Date inputTime,
			Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.addPeople = addPeople;
		this.workName = workName;
		this.workSn = workSn;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbWork(Integer enterpriseId, String addPeople, String workName, String workSn, String landBlockSn,
			String area, String crop, Date startTime, Date endTime, Date recoveryTime,
			String expectedProduction, Date inputTime, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.addPeople = addPeople;
		this.workName = workName;
		this.workSn = workSn;
		this.landBlockSn = landBlockSn;
		this.area = area;
		this.crop = crop;
		this.startTime = startTime;
		this.endTime = endTime;
		this.recoveryTime = recoveryTime;
		this.expectedProduction = expectedProduction;
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

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	@Column(name = "add_people", nullable = false)

	public String getAddPeople() {
		return this.addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}

	@Column(name = "work_name", nullable = false)

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	@Column(name = "work_sn", nullable = false)

	public String getWorkSn() {
		return this.workSn;
	}

	public void setWorkSn(String workSn) {
		this.workSn = workSn;
	}

	@Column(name = "land_block_sn")

	public String getLandBlockSn() {
		return this.landBlockSn;
	}

	public void setLandBlockSn(String landBlockSn) {
		this.landBlockSn = landBlockSn;
	}

	@Column(name = "area")

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Column(name = "crop")

	public String getCrop() {
		return this.crop;
	}

	public void setCrop(String crop) {
		this.crop = crop;
	}

	@Column(name = "start_time", length = 19)

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 19)

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "recovery_time", length = 19)

	public Date getRecoveryTime() {
		return this.recoveryTime;
	}

	public void setRecoveryTime(Date recoveryTime) {
		this.recoveryTime = recoveryTime;
	}

	@Column(name = "expected_production")

	public String getExpectedProduction() {
		return this.expectedProduction;
	}

	public void setExpectedProduction(String expectedProduction) {
		this.expectedProduction = expectedProduction;
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
	@Column(name = "purchase_source")
	public String getPurchaseSource() {
		return purchaseSource;
	}

	public void setPurchaseSource(String purchaseSource) {
		this.purchaseSource = purchaseSource;
	}
	@Column(name = "purchase_people")
	public String getPurchasePeople() {
		return purchasePeople;
	}

	public void setPurchasePeople(String purchasePeople) {
		this.purchasePeople = purchasePeople;
	}
	@Column(name = "purchase_time")
	public Date getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	@Column(name = "greenhouses_id")
	public Integer getGreenhousesId() {
		return greenhousesId;
	}

	public void setGreenhousesId(Integer greenhousesId) {
		this.greenhousesId = greenhousesId;
	}

}