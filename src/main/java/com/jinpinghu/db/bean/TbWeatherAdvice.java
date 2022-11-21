package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_weather_advice")
public class TbWeatherAdvice  extends BaseZEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String cropFarmingAdvice;
	private Date inputTime;
	private String area;
	private Integer delFlag;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "del_flag")
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	
	@Column(name = "area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "crop_farming_advice")
	public String getCropFarmingAdvice() {
		return cropFarmingAdvice;
	}

	public void setCropFarmingAdvice(String cropFarmingAdvice) {
		this.cropFarmingAdvice = cropFarmingAdvice;
	}
	@Column(name = "input_time")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

}