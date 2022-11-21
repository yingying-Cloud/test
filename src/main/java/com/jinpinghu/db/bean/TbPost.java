package com.jinpinghu.db.bean;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TbPost entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "tb_post")

public class TbPost extends BaseZEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer enterpriseId;
	private Integer userTabId;
	private String addPeople;
	private String title;
	private String content;
	private Date inputTime;
	private Integer type;
	private Integer mode;
	private Integer top;
	private Integer importantLevel;
	private Date changeTime;
	private Date lastReplyTime;
	private Integer lastReplyId;
	private Integer replyCount;
	private Integer status;
	private Integer delFlag;
	private Integer expertId;
	private String workSn;
	private String isStar;
	private String level;

	// Constructors

	/** default constructor */
	public TbPost() {
	}

	/** minimal constructor */
	public TbPost(Integer enterpriseId, String title, String content, Date inputTime, Integer type, Integer mode,
			Integer top, Integer importantLevel, Date changeTime, Integer replyCount, Integer status,
			Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.title = title;
		this.content = content;
		this.inputTime = inputTime;
		this.type = type;
		this.mode = mode;
		this.top = top;
		this.importantLevel = importantLevel;
		this.changeTime = changeTime;
		this.replyCount = replyCount;
		this.status = status;
		this.delFlag = delFlag;
	}

	/** full constructor */
	public TbPost(Integer enterpriseId, String addPeople, String title, String content, Date inputTime,
			Integer type, Integer mode, Integer top, Integer importantLevel, Date changeTime,Integer userTabId,
			Date lastReplyTime, Integer lastReplyId, Integer replyCount, Integer status, Integer delFlag) {
		this.enterpriseId = enterpriseId;
		this.addPeople = addPeople;
		this.title = title;
		this.content = content;
		this.inputTime = inputTime;
		this.type = type;
		this.mode = mode;
		this.top = top;
		this.importantLevel = importantLevel;
		this.changeTime = changeTime;
		this.lastReplyTime = lastReplyTime;
		this.lastReplyId = lastReplyId;
		this.replyCount = replyCount;
		this.status = status;
		this.delFlag = delFlag;
		this.userTabId = userTabId;
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

	@Column(name = "add_people")

	public String getAddPeople() {
		return this.addPeople;
	}

	public void setAddPeople(String addPeople) {
		this.addPeople = addPeople;
	}

	@Column(name = "title", nullable = false)

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false, length = 65535)

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "input_time", nullable = false, length = 19)

	public Date getInputTime() {
		return this.inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	@Column(name = "type", nullable = false)

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "mode", nullable = false)

	public Integer getMode() {
		return this.mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	@Column(name = "top", nullable = false)

	public Integer getTop() {
		return this.top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	@Column(name = "important_level", nullable = false)

	public Integer getImportantLevel() {
		return this.importantLevel;
	}

	public void setImportantLevel(Integer importantLevel) {
		this.importantLevel = importantLevel;
	}

	@Column(name = "change_time", nullable = false, length = 19)

	public Date getChangeTime() {
		return this.changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	@Column(name = "last_reply_time", length = 19)

	public Date getLastReplyTime() {
		return this.lastReplyTime;
	}

	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	@Column(name = "last_reply_id")

	public Integer getLastReplyId() {
		return this.lastReplyId;
	}

	public void setLastReplyId(Integer lastReplyId) {
		this.lastReplyId = lastReplyId;
	}

	@Column(name = "reply_count", nullable = false)

	public Integer getReplyCount() {
		return this.replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
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

	@Column(name = "user_tab_id")
	public Integer getUserTabId() {
		return userTabId;
	}

	public void setUserTabId(Integer userTabId) {
		this.userTabId = userTabId;
	}
	@Column(name = "expert_id")
	public Integer getExpertId() {
		return expertId;
	}

	public void setExpertId(Integer expertId) {
		this.expertId = expertId;
	}
	@Column(name = "work_sn")
	public String getWorkSn() {
		return workSn;
	}

	public void setWorkSn(String workSn) {
		this.workSn = workSn;
	}
	@Column(name = "is_star")
	public String getIsStar() {
		return isStar;
	}

	public void setIsStar(String isStar) {
		this.isStar = isStar;
	}
	@Column(name = "level")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}