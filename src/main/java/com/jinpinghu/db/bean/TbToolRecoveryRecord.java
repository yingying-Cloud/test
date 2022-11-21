package com.jinpinghu.db.bean;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbToolRecord entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tb_tool_recovery_record")
@SuppressWarnings("serial")
public class TbToolRecoveryRecord extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private Integer linkOrderInfoId;
	private String useName;
	private String useMobile;
	private Integer toolRecoveryId;
	private String allNumber;
	private String number;
	private Date inputTime;
	private Integer delFlag;
	private String operator;
	private String totalPrice;
	private Integer toolId;
	private Integer isSync;
	private String recordNumber;
	// Constructors

	/** default constructor */
	public TbToolRecoveryRecord() {
		setIsSync(0);
	}

	/** minimal constructor */
	public TbToolRecoveryRecord(Integer enterpriseId, Integer toolRecoveryId, String allNumber, String number,
			Date inputTime, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.toolRecoveryId = toolRecoveryId;
		this.allNumber = allNumber;
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

	@Column(name = "enterprise_id", nullable = false)

	public Integer getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}



	@Column(name = "all_number")
	public String getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(String allNumber) {
		this.allNumber = allNumber;
	}

	@Column(name = "number", nullable = false)

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


	@Column(name = "input_time")

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

	@Column(name = "use_name")
	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	
	@Column(name = "tool_recovery_id")
	public Integer getToolRecoveryId() {
		return toolRecoveryId;
	}

	public void setToolRecoveryId(Integer toolRecoveryId) {
		this.toolRecoveryId = toolRecoveryId;
	}
	@Column(name = "operator")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name = "use_mobile")
	public String getUseMobile() {
		return useMobile;
	}

	public void setUseMobile(String useMobile) {
		this.useMobile = useMobile;
	}

	@Column(name = "link_order_info_id")
	public Integer getLinkOrderInfoId() {
		return linkOrderInfoId;
	}

	public void setLinkOrderInfoId(Integer linkOrderInfoId) {
		this.linkOrderInfoId = linkOrderInfoId;
	}
	@Column(name = "total_price")
	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Column(name = "tool_id")
	public Integer getToolId() {
		return toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	@Column(name = "is_sync")
	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}
	@Column(name = "record_number")
	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

}