package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.jinpinghu.logic.post.AddTbPostLogic;
import com.jinpinghu.logic.post.DelTbPostLogic;
import com.jinpinghu.logic.post.DelTbPostReplyLogic;
import com.jinpinghu.logic.post.GetMyPostListLogic;
import com.jinpinghu.logic.post.GetOldReplyPostLogic;
import com.jinpinghu.logic.post.GetPostListByModesLogic;
import com.jinpinghu.logic.post.GetTbPostDetailLogic;
import com.jinpinghu.logic.post.GetTbPostReplyListLogic;
import com.jinpinghu.logic.post.SaveOrUpdateTbPostReplyLogic;
import com.jinpinghu.logic.post.UpdatePostStatusLogic;
import com.jinpinghu.logic.post.UpdateReplyStatusLogic;
import com.jinpinghu.logic.post.UpdateTbPostLogic;
import com.jinpinghu.logic.post.UpdateTbPostReplyStarLogic;
import com.jinpinghu.logic.post.param.AddTbPostParam;
import com.jinpinghu.logic.post.param.DelTbPostParam;
import com.jinpinghu.logic.post.param.DelTbPostReplyParam;
import com.jinpinghu.logic.post.param.GetMyPostListParam;
import com.jinpinghu.logic.post.param.GetOldReplyPostParam;
import com.jinpinghu.logic.post.param.GetPostListByModesParam;
import com.jinpinghu.logic.post.param.GetTbPostDetailParam;
import com.jinpinghu.logic.post.param.GetTbPostReplyListParam;
import com.jinpinghu.logic.post.param.SaveOrUpdateTbPostReplyParam;
import com.jinpinghu.logic.post.param.UpdatePostStatusParam;
import com.jinpinghu.logic.post.param.UpdateReplyStatusParam;
import com.jinpinghu.logic.post.param.UpdateTbPostParam;

@Path("post")
@Produces("application/json;charset=UTF-8")
public class PostAction extends BaseZAction {

	/**
	 * 获取我的帖子列表
	 */
	@POST
	@Path("getMyPostList.do")
	public String getMyPostList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("title") String title,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("types") String types,
			@FormParam("modes") String modes,
			@FormParam("status") String status,
			@FormParam("keywords") String keywords,
			@FormParam("sort") String sort,
			@FormParam("pageCount") Integer pageCount,
			@FormParam("nowPage") Integer nowPage,
			@Context HttpServletRequest request) {
		GetMyPostListParam myParam = new GetMyPostListParam(userId, apiKey, request);
		myParam.setTitle(title);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setTypes(types);
		myParam.setSort(sort);
		myParam.setModes(modes);
		myParam.setStatus(status);
		myParam.setKeywords(keywords);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		return new GetMyPostListLogic().process(myParam);
	}
	/**
	 * 获取自己的私人/公开帖子other==1，或者别人的公开帖子other==2,或者自己+别人的帖子other==3
	 */
	@POST
	@Path("getPostListByOther.do")
	public String GetPostListByModes(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("title") String title,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("types") String types,
			@FormParam("modes") String modes,
			@FormParam("status") String status,
			@FormParam("keywords") String keywords,
			@FormParam("sort") String sort,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("expertId") String expertId,
			@FormParam("isStar") String isStar,
			@FormParam("pageCount") Integer pageCount,
			@FormParam("nowPage") Integer nowPage,
			@Context HttpServletRequest request) {
		GetPostListByModesParam myParam = new GetPostListByModesParam(userId, apiKey, request);
		myParam.setTitle(title);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setTypes(types);
		myParam.setSort(sort);
		myParam.setModes(modes);
		myParam.setStatus(status);
		myParam.setKeywords(keywords);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setExpertId(expertId);
		myParam.setIsStar(isStar);
		return new GetPostListByModesLogic().process(myParam);
	}
	
	/**
	 * 获取帖子详情
	 * del_flag必须为0（未被删除）
	 * 帖子正文可能有文件（图片/音频/视频）
	 */
	@POST
	@Path("getTbPostDetail.do")
	public String getTbPostDetail(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") Integer id,
			@Context HttpServletRequest request) {
		GetTbPostDetailParam myParam = new GetTbPostDetailParam(userId, apiKey, request);
		myParam.setId(id);
		return new GetTbPostDetailLogic().process(myParam);
	}
	/**
	 * 获取帖子回复
	 * 回复可能有文件（图片/音频/视频）
	 * 按照时间排序，最新的在最下面
	 * （暂时不需要分页,暂时）
	 */
	@POST
	@Path("getTbPostReplyList.do")
	public String GetTbPostReplyList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("postId") Integer postId,
			@FormParam("sort") String sort,
			@Context HttpServletRequest request) {
		GetTbPostReplyListParam myParam = new GetTbPostReplyListParam(userId, apiKey, request);
		myParam.setPostId(postId);
		myParam.setSort(sort);
		return new GetTbPostReplyListLogic().process(myParam);
	}
	
	
	/**
	 * 提问帖子
	 * 文件字段保存顺序为：1·插入到tb_file表 2·保存关联表tb_res_post_file
	 * 文件可能有多个 （图片/音频/视频）注意类型区分保存入数据库
	 * 关键字保存顺序为：1·先保存帖子tb_post 2·保存关联表tb_res_post_keywork
	 * 关键字可能有多个
	 * 关键字由前端传递id给你
	 */
	@POST
	@Path("addTbPost.do")
	public String AddTbPost(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("title") String title,
			@FormParam("content") String content,
			@FormParam("type") Integer type,
			@FormParam("mode") Integer mode,
			@FormParam("top") Integer top,
			@FormParam("importantLevel") Integer importantLevel,
			@FormParam("file") String file,
			@FormParam("keyword") String keyword,
			@FormParam("expertId") String expertId,
			@FormParam("workSn") String workSn,
			@FormParam("isStar") String isStar,
			@FormParam("level") String level,
			@Context HttpServletRequest request) {
		AddTbPostParam myParam = new AddTbPostParam(userId, apiKey, request);
		myParam.setTitle(title);
		myParam.setContent(content);
		myParam.setType(type);
		myParam.setMode(mode);
		myParam.setTop(top);
		myParam.setImportantLevel(importantLevel);
		myParam.setFile(file);
		myParam.setKeyword(keyword);
		myParam.setExpertId(expertId);
		myParam.setWorkSn(workSn);
		myParam.setIsStar(isStar);
		myParam.setLevel(level);
		return new AddTbPostLogic().process(myParam);
	}
	
	/**
	 * 回复帖子
	 * 被回复的帖子del_flag必须为0（未被删除）
	 * 回复帖子必须有正文
	 * 回复可能有文件（图片/音频/视频）
	 */
	@POST
	@Path("saveOrUpdateTbPostReply.do")
	public String SaveOrUpdateTbPostReply(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("postId")  Integer postId,
			@FormParam("content")  String content,
			@FormParam("id")  String id,
			@FormParam("file")  String file,
			@FormParam("toolName")  String toolName,
			@Context HttpServletRequest request) {
		SaveOrUpdateTbPostReplyParam myParam = new SaveOrUpdateTbPostReplyParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setPostId(postId);
		myParam.setContent(content);
		myParam.setFile(file);
		myParam.setToolName(toolName);
		return new SaveOrUpdateTbPostReplyLogic().process(myParam);
	}
	/**
	 * 删除帖子
	 * 可以多选删除
	 * 删除为del_flag置为1
	 */
	@POST
	@Path("delTbPost.do")
	public String DelTbPost(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id") String id,
			@Context HttpServletRequest request) {
		DelTbPostParam myParam = new DelTbPostParam(userId, apiKey, request);
		myParam.setId(id);
		return new DelTbPostLogic().process(myParam);
	}
	/**
	 * 编辑帖子主题内容图片
	 */
	@POST
	@Path("updateTbPost.do")
	public String UpdateTbPost(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("content") String content,
			@FormParam("title") String title,
			@FormParam("id") Integer id,
			@FormParam("file") String file,
			@FormParam("mode")String mode,
			@Context HttpServletRequest request) {
		UpdateTbPostParam myParam = new UpdateTbPostParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setTitle(title);
		myParam.setContent(content);
		myParam.setFile(file);
		myParam.setMode(mode);
		return new UpdateTbPostLogic().process(myParam);
	}
	/**
	 * 获取回复的帖子列表和回复内容
	 */
	@POST
	@Path("getOldReplyPost.do")
	public String GetOldReplyPost(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("sortPost") String sortPost,
			@FormParam("sortReply") String sortReply,
			@FormParam("nowPage") Integer nowPage,
			@FormParam("pageCount") Integer pageCount,
			@Context HttpServletRequest request) {
		GetOldReplyPostParam myParam = new GetOldReplyPostParam(userId, apiKey, request);
		myParam.setPageCount(pageCount);
		myParam.setNowPage(nowPage);
		myParam.setSortPost(sortPost);
		myParam.setSortReply(sortReply);
		return new GetOldReplyPostLogic().process(myParam);
	}
	@POST
	@Path("delTbPostReply.do")
	public String DelTbPostReply(
		@FormParam("userId") String userId,
		@FormParam("apiKey") String apiKey,
		@FormParam("id") String id,
		@FormParam("postId")String postId,
		@Context HttpServletRequest request) {
		DelTbPostReplyParam myParam = new DelTbPostReplyParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setPostId(postId);
		return new DelTbPostReplyLogic().process(myParam);
	}
	
	@POST
	@Path("updatePostStatus.do")
	public String updatePostStatus(
		@FormParam("userId") String userId,
		@FormParam("apiKey") String apiKey,
		@FormParam("id") String id,
		@FormParam("status")String status,
		@Context HttpServletRequest request) {
		UpdatePostStatusParam myParam = new UpdatePostStatusParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new UpdatePostStatusLogic().process(myParam);
	}
	
	@POST
	@Path("updateReplyStatus.do")
	public String updateReplyStatus(
		@FormParam("userId") String userId,
		@FormParam("apiKey") String apiKey,
		@FormParam("id") String id,
		@FormParam("status")String status,
		@Context HttpServletRequest request) {
		UpdateReplyStatusParam myParam = new UpdateReplyStatusParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStatus(status);
		return new UpdateReplyStatusLogic().process(myParam);
	}
	/*
	 * 评价星级
	 */
	@POST
	@Path("updateTbPostReplyStar.do")
	public String updateTbPostReplyStar(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("id")  String id,
			@FormParam("star")  String star,
			@Context HttpServletRequest request) {
		SaveOrUpdateTbPostReplyParam myParam = new SaveOrUpdateTbPostReplyParam(userId, apiKey, request);
		myParam.setId(id);
		myParam.setStar(star);
		return new UpdateTbPostReplyStarLogic().process(myParam);
	}
	
}
