package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbToolOrder entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_tool_order")

public class TbToolOrder extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer toolEnterpriseId;
	private Integer plantEnterpriseId;
	private String addPeople;
	private String orderNumber;
	private String price;
	private Integer status;
	private String cancelInfo;
	private String rejectedInfo;
	private Date inputTime;
	private Date timeAudit;
	private Date timePay;
	private Date timeSend;
	private Date timeComplete;
	private Date timeCancel;
	private Date timeRejected;
	private Integer delFlag;
	private Integer check;
	private String checkMoney;
	private Integer type;
	private Integer isSync ;
	private String pic;
	private String isEvaluate;
	private String isOther;
	// Constructors

	/** default constructor */
	public TbToolOrder() {
		setIsSync(0);
	}

	/** minimal constructor */
	public TbToolOrder(Integer toolEnterpriseId, Integer plantEnterpriseId, String orderNumber, Integer status,
			Date inputTime, Integer delFlag) {
		this.toolEnterpriseId = toolEnterpriseId;
		this.plantEnterpriseId = plantEnterpriseId;
		this.orderNumber = orderNumber;
		this.status = status;
		this.inputTime = inputTime;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbToolOrder(Integer toolEnterpriseId, Integer plantEnterpriseId, String addPeople, String orderNumber,
			String price, Integer status, String cancelInfo, String rejectedInfo, Date inputTime,
			Date timeAudit, Date timePay, Date timeSend, Date timeComplete, Date timeCancle,
			Date timeRejected, Integer delFlag) {
		this.toolEnterpriseId = toolEnterpriseId;
		this.plantEnterpriseId = plantEnterpriseId;
		this.addPeople = addPeople;
		this.orderNumber = orderNumber;
		this.price = price;
		this.status = status;
		this.setCancelInfo(cancelInfo);
		this.rejectedInfo = rejectedInfo;
		this.inputTime = inputTime;
		this.timeAudit = timeAudit;
		this.timePay = timePay;
		this.timeSend = timeSend;
		this.timeComplete = timeComplete;
		this.setTimeCancel(timeCancle);
		this.timeRejected = timeRejected;
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

	@Column(name = "tool_enterprise_id", nullable = false)

	public Integer getToolEnterpriseId() {
		return this.toolEnterpriseId;
	}

	public void setToolEnterpriseId(Integer toolEnterpriseId) {
		this.toolEnterpriseId = toolEnterpriseId;
	}

	@Column(name = "plant_enterprise_id", nullable = false)

	public Integer getPlantEnterpriseId() {
		return this.plantEnterpriseId;
	}

	public void setPlantEnterpriseId(Integer plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}

	@Column(name = "add_people")

	public String getAddPeople() {
		return this.addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}

	@Column(name = "order_number", nullable = false)

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Column(name = "price")

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(name = "status", nullable = false)

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	@Column(name = "rejected_info")

	public String getRejectedInfo() {
		return this.rejectedInfo;
	}

	public void setRejectedInfo(String rejectedInfo) {
		this.rejectedInfo = rejectedInfo;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "time_audit", length = 19)

	public Date getTimeAudit() {
		return this.timeAudit;
	}

	public void setTimeAudit(Date timeAudit) {
		this.timeAudit = timeAudit;
	}

	@Column(name = "time_pay", length = 19)

	public Date getTimePay() {
		return this.timePay;
	}

	public void setTimePay(Date timePay) {
		this.timePay = timePay;
	}

	@Column(name = "time_complete", length = 19)

	public Date getTimeComplete() {
		return this.timeComplete;
	}

	public void setTimeComplete(Date timeComplete) {
		this.timeComplete = timeComplete;
	}

	@Column(name = "time_rejected", length = 19)

	public Date getTimeRejected() {
		return this.timeRejected;
	}

	public void setTimeRejected(Date timeRejected) {
		this.timeRejected = timeRejected;
	}

	@Column(name = "del_flag", nullable = false)

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "time_send")
	public Date getTimeSend() {
		return timeSend;
	}

	public void setTimeSend(Date timeSend) {
		this.timeSend = timeSend;
	}
	@Column(name = "cancel_info")
	public String getCancelInfo() {
		return cancelInfo;
	}

	public void setCancelInfo(String cancelInfo) {
		this.cancelInfo = cancelInfo;
	}
	@Column(name = "time_cancel")
	public Date getTimeCancel() {
		return timeCancel;
	}

	public void setTimeCancel(Date timeCancel) {
		this.timeCancel = timeCancel;
	}
	@Column(name = "check_")
	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}
	@Column(name = "check_money")
	public String getCheckMoney() {
		return checkMoney;
	}

	public void setCheckMoney(String checkMoney) {
		this.checkMoney = checkMoney;
	}
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "is_sync")
	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	@Column(name = "is_evaluate")
	public String getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(String isEvaluate) {
		this.isEvaluate = isEvaluate;
	}
	@Column(name = "is_other")
	public String getIsOther() {
		return isOther;
	}

	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}

}