package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbResCropFarmingTool entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_res_crop_farming_tool")

public class TbResCropFarmingTool extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer workId;
	private Integer enterpriseId;
	private String addPeople;
	private Integer toolId;
	private String toolName;
	private String specification;
	private String unit;
	private String num;
	private Date inputTime;
	private Integer delFlag;
	// Constructors

	/** default constructor */
	public TbResCropFarmingTool() {
	}

	/** minimal constructor */
	public TbResCropFarmingTool(Integer workId, Integer enterpriseId, String addPeople, String toolName,
			Date inputTime, Integer delFlag) {
		this.workId = workId;
		this.enterpriseId = enterpriseId;
		this.addPeople = addPeople;
		this.toolName = toolName;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbResCropFarmingTool(Integer workId, Integer enterpriseId, String addPeople, Integer toolId, String toolName,
			String specification, String unit, String num, Date inputTime, Integer delFlag) {
		this.workId = workId;
		this.enterpriseId = enterpriseId;
		this.addPeople = addPeople;
		this.toolId = toolId;
		this.toolName = toolName;
		this.specification = specification;
		this.unit = unit;
		this.num = num;
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
		return this.workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
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

	@Column(name = "tool_id")

	public Integer getToolId() {
		return this.toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	@Column(name = "tool_name", nullable = false)

	public String getToolName() {
		return this.toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	@Column(name = "specification")

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	@Column(name = "unit")

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "num")

	public String getNum() {
		return this.num;
	}

	public void setNum(String num) {
		this.num = num;
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

}