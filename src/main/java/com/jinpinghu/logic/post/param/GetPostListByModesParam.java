package com.jinpinghu.logic.post.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetPostListByModesParam  extends BaseZLogicParam {

		public GetPostListByModesParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}

		private String title;
		private String startTime;
		private String endTime;
		private String types;
		private String modes;
		private String status;
		private String keywords;
		private String sort;
		private String enterpriseId;
		private String expertId;
		private String isStar;
		private Integer pageCount;
		private Integer nowPage;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getTypes() {
			return types;
		}

		public void setTypes(String types) {
			this.types = types;
		}

		public String getModes() {
			return modes;
		}

		public void setModes(String modes) {
			this.modes = modes;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public Integer getPageCount() {
			return pageCount;
		}

		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}

		public Integer getNowPage() {
			return nowPage;
		}

		public void setNowPage(Integer nowPage) {
			this.nowPage = nowPage;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public String getEnterpriseId() {
			return enterpriseId;
		}

		public void setEnterpriseId(String enterpriseId) {
			this.enterpriseId = enterpriseId;
		}

		public String getExpertId() {
			return expertId;
		}

		public void setExpertId(String expertId) {
			this.expertId = expertId;
		}

		public String getIsStar() {
			return isStar;
		}

		public void setIsStar(String isStar) {
			this.isStar = isStar;
		}

	}
