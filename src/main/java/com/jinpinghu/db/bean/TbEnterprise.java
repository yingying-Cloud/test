package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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
@Table(name = "tb_enterprise")

public class TbEnterprise extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String enterpriseCreditCode;
	private String enterpriseLegalPerson;
	private String enterpriseLegalPersonIdcard;
	private String enterpriseLinkPeople;
	private String enterpriseLinkMobile;
	private String enterpriseAddress;
	private String plantScope;
	private String x;
	private String y;
	private Integer enterpriseType;
	private Date inputTime;
	private Integer status;
	private Integer delFlag;
	private String baseAddress;
	private String plantName;
	private String dscd;
	private String registeredFunds;
	private String changes;
	private String enterpriseNature;
	private String deviceSn;
	private Integer listOrder;
	private String type;
	private Integer state;
	private String syncNumber;
	private Integer isSync;
	private String industrial;
	private Double area;
	private Double confirmArea;
	private Double inflowArea;
	private Double outflowArea;
	private String village;
	private BigDecimal nmLimitAmount;
	private BigDecimal nyLimitAmount;
	private String businessScope;
	private String permitForoperationNum;
	private String operationMode;
	
	// Constructors


	/** default constructor */
	public TbEnterprise() {
		setIsSync(0);
	}

	/** minimal constructor */
	public TbEnterprise(Integer id, String name, Date inputTime,Integer status, Integer delFlag) {
		this.id = id;
		this.name = name;
		this.inputTime = inputTime;
		this.status = status;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbEnterprise(Integer id, String name, String enterpriseCreditCode, String enterpriseLegalPerson,
			String enterpriseLegalPersonIdcard, String enterpriseLinkPeople, String enterpriseLinkMobile,
			String enterpriseAddress,Integer enterpriseType, Date inputTime,Integer status, Integer delFlag,String plantScope,String x,String y) {
		this.id = id;
		this.name = name;
		this.enterpriseCreditCode = enterpriseCreditCode;
		this.enterpriseLegalPerson = enterpriseLegalPerson;
		this.enterpriseLegalPersonIdcard = enterpriseLegalPersonIdcard;
		this.enterpriseLinkPeople = enterpriseLinkPeople;
		this.enterpriseLinkMobile = enterpriseLinkMobile;
		this.enterpriseAddress = enterpriseAddress;
		this.enterpriseType = enterpriseType;
		this.inputTime = inputTime;
		this.status = status;
		this.delFlag = delFlag;
		this.setPlantScope(plantScope);
		this.setX(x);
		this.setY(y);
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "enterprise_credit_code")

	public String getEnterpriseCreditCode() {
		return this.enterpriseCreditCode;
	}

	public void setEnterpriseCreditCode(String enterpriseCreditCode) {
		this.enterpriseCreditCode = enterpriseCreditCode;
	}

	@Column(name = "enterprise_legal_person", length = 50)

	public String getEnterpriseLegalPerson() {
		return this.enterpriseLegalPerson;
	}

	public void setEnterpriseLegalPerson(String enterpriseLegalPerson) {
		this.enterpriseLegalPerson = enterpriseLegalPerson;
	}

	@Column(name = "enterprise_legal_person_idcard", length = 24)

	public String getEnterpriseLegalPersonIdcard() {
		return this.enterpriseLegalPersonIdcard;
	}

	public void setEnterpriseLegalPersonIdcard(String enterpriseLegalPersonIdcard) {
		this.enterpriseLegalPersonIdcard = enterpriseLegalPersonIdcard;
	}

	@Column(name = "enterprise_link_people", length = 50)

	public String getEnterpriseLinkPeople() {
		return this.enterpriseLinkPeople;
	}

	public void setEnterpriseLinkPeople(String enterpriseLinkPeople) {
		this.enterpriseLinkPeople = enterpriseLinkPeople;
	}

	@Column(name = "enterprise_link_mobile", length = 20)

	public String getEnterpriseLinkMobile() {
		return this.enterpriseLinkMobile;
	}

	public void setEnterpriseLinkMobile(String enterpriseLinkMobile) {
		this.enterpriseLinkMobile = enterpriseLinkMobile;
	}

	@Column(name = "enterprise_address")

	public String getEnterpriseAddress() {
		return this.enterpriseAddress;
	}

	public void setEnterpriseAddress(String enterpriseAddress) {
		this.enterpriseAddress = enterpriseAddress;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	
	@Column(name = "status", nullable = false)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	@Column(name = "enterprise_type")
	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	@Column(name = "plant_scope")
	public String getPlantScope() {
		return plantScope;
	}

	public void setPlantScope(String plantScope) {
		this.plantScope = plantScope;
	}

	@Column(name = "x")
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	@Column(name = "y")
	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	@Column(name = "base_address")
	public String getBaseAddress() {
		return baseAddress;
	}

	public void setBaseAddress(String baseAddress) {
		this.baseAddress = baseAddress;
	}
	@Column(name = "plant_name")
	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	@Column(name = "dscd")
	public String getDscd() {
		return dscd;
	}

	public void setDscd(String dscd) {
		this.dscd = dscd;
	}
	@Column(name = "registered_funds")
	public String getRegisteredFunds() {
		return registeredFunds;
	}

	public void setRegisteredFunds(String registeredFunds) {
		this.registeredFunds = registeredFunds;
	}
	@Column(name = "changes")
	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}
	@Column(name = "enterprise_nature")
	public String getEnterpriseNature() {
		return enterpriseNature;
	}

	public void setEnterpriseNature(String enterpriseNature) {
		this.enterpriseNature = enterpriseNature;
	}
	@Column(name = "device_sn")
	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	@Column(name = "list_order")
	public Integer getListOrder() {
		return listOrder;
	}

	public void setListOrder(Integer listOrder) {
		this.listOrder = listOrder;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	@Column(name = "sync_number")
	public String getSyncNumber() {
		return syncNumber;
	}

	public void setSyncNumber(String syncNumber) {
		this.syncNumber = syncNumber;
	}
	@Column(name = "is_sync")
	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}
	@Column(name = "industrial")
	public String getIndustrial() {
		return industrial;
	}

	public void setIndustrial(String industrial) {
		this.industrial = industrial;
	}
	@Column(name = "outflow_area")
	public Double getOutflowArea() {
		return outflowArea;
	}

	public void setOutflowArea(Double outflowArea) {
		this.outflowArea = outflowArea;
	}
	@Column(name = "confirm_area")
	public Double getConfirmArea() {
		return confirmArea;
	}

	public void setConfirmArea(Double confirmArea) {
		this.confirmArea = confirmArea;
	}
	@Column(name = "area")
	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}
	@Column(name = "inflow_area")
	public Double getInflowArea() {
		return inflowArea;
	}

	public void setInflowArea(Double inflowArea) {
		this.inflowArea = inflowArea;
	}
	@Column(name = "village")
	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}
	@Column(name = "nm_limit_amount")
	public BigDecimal getNmLimitAmount() {
		return nmLimitAmount;
	}

	public void setNmLimitAmount(BigDecimal nmLimitAmount) {
		this.nmLimitAmount = nmLimitAmount;
	}
	@Column(name = "ny_limit_amount")
	public BigDecimal getNyLimitAmount() {
		return nyLimitAmount;
	}

	public void setNyLimitAmount(BigDecimal nyLimitAmount) {
		this.nyLimitAmount = nyLimitAmount;
	}

	@Column(name = "business_scope")
	
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Column(name = "permit_foroperation_num")

	public String getPermitForoperationNum() {
		return permitForoperationNum;
	}

	public void setPermitForoperationNum(String permitForoperationNum) {
		this.permitForoperationNum = permitForoperationNum;
	}

	@Column(name = "operation_mode")

	public String getOperationMode() {
		return operationMode;
	}

	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}

}