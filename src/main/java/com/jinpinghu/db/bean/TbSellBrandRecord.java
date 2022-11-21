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
@Table(name = "tb_sell_brand_record")
@SuppressWarnings("serial")
public class TbSellBrandRecord extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private String useName;
	private Date useTime;
	private Integer sellBrandId;
	private Integer recordType;
	private String allNumber;
	private String number;
	private Date inputTime;
	private Integer delFlag;
	private String outName;
	private String outMobile;

	// Constructors

	/** default constructor */
	public TbSellBrandRecord() {
	}

	/** minimal constructor */
	public TbSellBrandRecord(Integer enterpriseId, Integer sellBrandId, Integer recordType, String allNumber, String number,
			Date inputTime, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.sellBrandId = sellBrandId;
		this.recordType = recordType;
		this.allNumber = allNumber;
		this.number = number;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbSellBrandRecord(Integer enterpriseId, Integer toolEnterpriseId, Integer sellBrandId, Integer recordType,
			String allNumber, String number, Date inputTime,String useName,Date useTime,Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.sellBrandId = sellBrandId;
		this.recordType = recordType;
		this.allNumber = allNumber;
		this.number = number;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
		this.useName = useName;
		this.useTime = useTime;
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


	@Column(name = "sell_brand_id", nullable = false)

	public Integer getSellBrandId() {
		return this.sellBrandId;
	}

	public void setSellBrandId(Integer sellBrandId) {
		this.sellBrandId = sellBrandId;
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


}