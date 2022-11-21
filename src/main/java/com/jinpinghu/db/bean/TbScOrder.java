package com.jinpinghu.db.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the tb_sc_order database table.
 * 
 */
@Entity
@Table(name="tb_sc_order")
public class TbScOrder extends BaseZEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer buyEnterpriseId;
	private Integer buyReleaseId;
	private String buyContactAddress;
	private String buyContactPerson;
	private String buyContactPhone;
	private String supplyContactAddress;
	private String supplyContactPerson;
	private String supplyContactPhone;
	private Integer delFlag;
	private Date endTime;
	private Integer num;
	private String orderNumber;
	private BigDecimal price;
	private String remark;
	private Date startTime;
	private Integer supplyEnterpriseId;
	private Integer supplyReleaseId;
	private Integer workId;
	private String productName;
	private String releaseType;
	private Integer status;
	private String isEvaluate;
	private String specification;

	public TbScOrder() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="buy_enterprise_id")
	public Integer getBuyEnterpriseId() {
		return this.buyEnterpriseId;
	}

	public void setBuyEnterpriseId(Integer buyEnterpriseId) {
		this.buyEnterpriseId = buyEnterpriseId;
	}


	@Column(name="buy_release_id")
	public Integer getBuyReleaseId() {
		return this.buyReleaseId;
	}

	public void setBuyReleaseId(Integer buyReleaseId) {
		this.buyReleaseId = buyReleaseId;
	}


	@Column(name = "buy_contact_address")
	public String getBuyContactAddress() {
		return buyContactAddress;
	}


	public void setBuyContactAddress(String buyContactAddress) {
		this.buyContactAddress = buyContactAddress;
	}

	@Column(name = "buy_contact_person")
	public String getBuyContactPerson() {
		return buyContactPerson;
	}


	public void setBuyContactPerson(String buyContactPerson) {
		this.buyContactPerson = buyContactPerson;
	}

	@Column(name = "buy_contact_phone")
	public String getBuyContactPhone() {
		return buyContactPhone;
	}


	public void setBuyContactPhone(String buyContactPhone) {
		this.buyContactPhone = buyContactPhone;
	}

	@Column(name = "supply_contact_address")
	public String getSupplyContactAddress() {
		return supplyContactAddress;
	}


	public void setSupplyContactAddress(String supplyContactAddress) {
		this.supplyContactAddress = supplyContactAddress;
	}

	@Column(name = "supply_contact_person")
	public String getSupplyContactPerson() {
		return supplyContactPerson;
	}


	public void setSupplyContactPerson(String supplyContactPerson) {
		this.supplyContactPerson = supplyContactPerson;
	}

	@Column(name = "supply_contact_phone")
	public String getSupplyContactPhone() {
		return supplyContactPhone;
	}


	public void setSupplyContactPhone(String supplyContactPhone) {
		this.supplyContactPhone = supplyContactPhone;
	}



	@Column(name="del_flag")
	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name="end_time")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	@Column(name="order_number")
	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	@Column(name="supply_enterprise_id")
	public Integer getSupplyEnterpriseId() {
		return this.supplyEnterpriseId;
	}

	public void setSupplyEnterpriseId(Integer supplyEnterpriseId) {
		this.supplyEnterpriseId = supplyEnterpriseId;
	}


	@Column(name="supply_release_id")
	public Integer getSupplyReleaseId() {
		return this.supplyReleaseId;
	}

	public void setSupplyReleaseId(Integer supplyReleaseId) {
		this.supplyReleaseId = supplyReleaseId;
	}


	@Column(name="work_id")
	public Integer getWorkId() {
		return this.workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}


	@Column(name = "product_name")
	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	@Column(name = "release_type")
	public String getReleaseType() {
		return releaseType;
	}


	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "is_evaluate")
	public String getIsEvaluate() {
		return isEvaluate;
	}


	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}


	public String getSpecification() {
		return specification;
	}


	public void setSpecification(String specification) {
		this.specification = specification;
	}

}