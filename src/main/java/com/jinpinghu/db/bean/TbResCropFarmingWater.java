package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResCropFarmingWater entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_crop_farming_water")

public class TbResCropFarmingWater extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private Integer workId;
	private String addPeople;
	private String traffic;
	private Date startIrrigationTime;
	private Date endIrrigationTime;
	private String waterAmount;
	private String describe_;
	private Date inputTime;
	private Integer delFlag;

	// Constructors

	/** default constructor */
	public TbResCropFarmingWater() {
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


	@Column(name = "traffic", nullable = false)

	public String getTraffic() {
		return this.traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	@Column(name = "start_irrigation_time", nullable = false, length = 19)

	public Date getStartIrrigationTime() {
		return this.startIrrigationTime;
	}

	public void setStartIrrigationTime(Date startIrrigationTime) {
		this.startIrrigationTime = startIrrigationTime;
	}

	@Column(name = "end_irrigation_time", nullable = false, length = 19)

	public Date getEndIrrigationTime() {
		return this.endIrrigationTime;
	}

	public void setEndIrrigationTime(Date endIrrigationTime) {
		this.endIrrigationTime = endIrrigationTime;
	}

	@Column(name = "water_amount", nullable = false)

	public String getWaterAmount() {
		return this.waterAmount;
	}

	public void setWaterAmount(String waterAmount) {
		this.waterAmount = waterAmount;
	}


	@Column(name = "describe_")

	public String getDescribe() {
		return this.describe_;
	}

	public void setDescribe(String describe_) {
		this.describe_ = describe_;
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
	@Column(name = "enterprise_id")
	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	@Column(name = "work_id")
	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	@Column(name = "add_people")
	public String getAddPeople() {
		return addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}

}