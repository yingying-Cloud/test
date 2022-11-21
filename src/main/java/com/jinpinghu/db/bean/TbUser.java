package com.jinpinghu.db.bean;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbUser entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_user")

public class TbUser extends BaseZEntity  implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;
	private String apiKey;
	private String mobile;
	private String password;
	private Date regTime;
	private Date lastTime;
	private String name;
	private String expertIdcard;
	private String expertField;
	private String wxId;
	private String headPic;
	private String cashRegisterId;
	private String cashRegisterVersion;
	private Integer delFlag;
	private String integral;
	private String dscd;
	private String username2;

	// Constructors

	/** default constructor */
	public TbUser() {
	}

	/** minimal constructor */
	public TbUser(Integer id, String userId, String apiKey, String mobile, String password, Date regTime) {
		this.id = id;
		this.userId = userId;
		this.apiKey = apiKey;
		this.mobile = mobile;
		this.password = password;
		this.regTime = regTime;
	}

	/** full constructor */
	public TbUser(Integer id, String userId, String apiKey, String mobile, String password, Date regTime,
			Date lastTime, String name, String expertIdcard, String expertField,Integer delFlag) {
		this.id = id;
		this.userId = userId;
		this.apiKey = apiKey;
		this.mobile = mobile;
		this.password = password;
		this.regTime = regTime;
		this.lastTime = lastTime;
		this.name = name;
		this.expertIdcard = expertIdcard;
		this.expertField = expertField;
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

	@Column(name = "user_id", nullable = false)

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "api_key", nullable = false)

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Column(name = "mobile", nullable = false, length = 20)

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "password", nullable = false, length = 50)

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "reg_time", nullable = false, length = 19)

	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	@Column(name = "last_time", length = 19)

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Column(name = "name", length = 50)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "expert_idcard", length = 24)

	public String getExpertIdcard() {
		return this.expertIdcard;
	}

	public void setExpertIdcard(String expertIdcard) {
		this.expertIdcard = expertIdcard;
	}

	@Column(name = "expert_field")

	public String getExpertField() {
		return this.expertField;
	}

	public void setExpertField(String expertField) {
		this.expertField = expertField;
	}

	@Column(name = "del_flag")

	public Integer getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "head_pic")
	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	@Column(name = "wx_id")
	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	
	@Column(name = "cash_register_id")
	public String getCashRegisterId() {
		return cashRegisterId;
	}

	public void setCashRegisterId(String cashRegisterId) {
		this.cashRegisterId = cashRegisterId;
	}

	@Column(name = "cash_register_version")
	public String getCashRegisterVersion() {
		return cashRegisterVersion;
	}

	public void setCashRegisterVersion(String cashRegisterVersion) {
		this.cashRegisterVersion = cashRegisterVersion;
	}
	@Column(name = "integral")
	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getDscd() {
		return dscd;
	}

	public void setDscd(String dscd) {
		this.dscd = dscd;
	}
	
	@Column(name = "username2")
	public String getUsername2() {
		return username2;
	}

	public void setUsername2(String username2) {
		this.username2 = username2;
	}
}