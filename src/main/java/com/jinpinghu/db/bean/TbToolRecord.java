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
@Table(name = "tb_tool_record")
@SuppressWarnings("serial")
public class TbToolRecord extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String supplierName;
	private String useName;
	private Date useTime;
	private Integer toolId;
	private Integer recordType;
	private String allNumber;
	private String number;
	private Date inputTime;
	private Integer delFlag;
	private String outName;
	private String outMobile;
	private Integer isSync ;
	private String price;
	private Integer fromEnterpriseId;
	private Integer outEnterpriseId;
	private String isOther;

	// Constructors

	/** default constructor */
	public TbToolRecord() {
		setIsSync(0);
	}

	/** minimal constructor */
	public TbToolRecord(Integer enterpriseId, Integer toolId, Integer recordType, String allNumber, String number,
			Date inputTime, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.toolId = toolId;
		this.recordType = recordType;
		this.allNumber = allNumber;
		this.number = number;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbToolRecord(Integer enterpriseId, Integer toolEnterpriseId, Integer toolId, Integer recordType,
			String allNumber, String number, Date inputTime,String useName,Date useTime,Integer delFlag,String supplierName) {
		this.enterpriseId = enterpriseId;
		this.toolId = toolId;
		this.recordType = recordType;
		this.allNumber = allNumber;
		this.number = number;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
		this.useName = useName;
		this.useTime = useTime;
		this.supplierName = supplierName;
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


	@Column(name = "tool_id", nullable = false)

	public Integer getToolId() {
		return this.toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}


	@Column(name = "record_type", nullable = false)

	public Integer getRecordType() {
		return this.recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
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

	@Column(name = "use_name")
	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	@Column(name = "use_time")
	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	@Column(name = "supplier_name")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Column(name = "out_name")
	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}
	@Column(name = "out_mobile")
	public String getOutMobile() {
		return outMobile;
	}

	public void setOutMobile(String outMobile) {
		this.outMobile = outMobile;
	}

	@Column(name = "is_sync")
	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}
	@Column(name = "price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	@Column(name = "from_enterprise_id")
	public Integer getFromEnterpriseId() {
		return fromEnterpriseId;
	}

	public void setFromEnterpriseId(Integer fromEnterpriseId) {
		this.fromEnterpriseId = fromEnterpriseId;
	}
	@Column(name = "out_enterprise_id")
	public Integer getOutEnterpriseId() {
		return outEnterpriseId;
	}

	public void setOutEnterpriseId(Integer outEnterpriseId) {
		this.outEnterpriseId = outEnterpriseId;
	}
	@Column(name = "is_other")
	public String getIsOther() {
		return isOther;
	}

	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}



}