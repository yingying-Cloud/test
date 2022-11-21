package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbAgriculturalGreenhouses entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_pests")
@SuppressWarnings("serial")
public class TbPests extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private String title;
	private String features;
	private String happen;
	private String agriculturalControl;
	private String greenControl;
	private String organicControl;
	private String allControl;
	private String greenControlMedicine;
	private String organicControlMedicine;
	private String allControlMedicine;

	
	// Constructors

	/** default constructor */
	public TbPests() {
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

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "features")
	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}
	@Column(name = "happen")
	public String getHappen() {
		return happen;
	}

	public void setHappen(String happen) {
		this.happen = happen;
	}
	@Column(name = "agricultural_control")
	public String getAgriculturalControl() {
		return agriculturalControl;
	}

	public void setAgriculturalControl(String agriculturalControl) {
		this.agriculturalControl = agriculturalControl;
	}
	@Column(name = "green_control")
	public String getGreenControl() {
		return greenControl;
	}

	public void setGreenControl(String greenControl) {
		this.greenControl = greenControl;
	}
	@Column(name = "organic_control")
	public String getOrganicControl() {
		return organicControl;
	}

	public void setOrganicControl(String organicControl) {
		this.organicControl = organicControl;
	}
	@Column(name = "all_control")
	public String getAllControl() {
		return allControl;
	}

	public void setAllControl(String allControl) {
		this.allControl = allControl;
	}
	@Column(name = "green_control_medicine")
	public String getGreenControlMedicine() {
		return greenControlMedicine;
	}

	public void setGreenControlMedicine(String greenControlMedicine) {
		this.greenControlMedicine = greenControlMedicine;
	}
	@Column(name = "organic_control_medicine")
	public String getOrganicControlMedicine() {
		return organicControlMedicine;
	}

	public void setOrganicControlMedicine(String organicControlMedicine) {
		this.organicControlMedicine = organicControlMedicine;
	}
	@Column(name = "all_control_medicine")
	public String getAllControlMedicine() {
		return allControlMedicine;
	}

	public void setAllControlMedicine(String allControlMedicine) {
		this.allControlMedicine = allControlMedicine;
	}



}