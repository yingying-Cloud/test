package com.jinpinghu.logic.post.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetOldReplyPostParam extends BaseZLogicParam{

	public GetOldReplyPostParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	private String sortPost;
	private String sortReply;
	private Integer nowPage;
	private Integer pageCount;
	public Integer getNowPage() {
		return nowPage;
	}
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getSortPost() {
		return sortPost;
	}
	public void setSortPost(String sortPost) {
		this.sortPost = sortPost;
	}
	public String getSortReply() {
		return sortReply;
	}
	public void setSortReply(String sortReply) {
		this.sortReply = sortReply;
	}

	
}
