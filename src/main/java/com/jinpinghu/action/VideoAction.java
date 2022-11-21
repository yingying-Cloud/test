package com.jinpinghu.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import com.jinpinghu.logic.video.GetVideoImgHistoryLogic;
import com.jinpinghu.logic.video.GetVideoListLogic;
import com.jinpinghu.logic.video.StartControlLogic;
import com.jinpinghu.logic.video.StopControlLogic;
import com.jinpinghu.logic.video.SyncVideoImgLogic;
import com.jinpinghu.logic.video.param.GetVideoImgHistoryParam;
import com.jinpinghu.logic.video.param.GetVideoListParam;
import com.jinpinghu.logic.video.param.StartControlParam;
import com.jinpinghu.logic.video.param.StopControlParam;
import com.jinpinghu.logic.video.param.SyncVideoImgParam;


@Path("video")
@Produces("application/json;charset=UTF-8")
public class VideoAction extends BaseZAction{
	
	/**
	 *获取视频列表
	 */
	@POST
	@Path("getVideoList.do")
	public String getVideoList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetVideoListParam myParam = new GetVideoListParam(userId, apiKey, request);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNowPage(StringUtils.isEmpty(nowPage)?null:Integer.valueOf(nowPage));
		myParam.setPageCount(StringUtils.isEmpty(pageCount)?null:Integer.valueOf(pageCount));
		return new GetVideoListLogic().process(myParam);
	}
	
	/**
	 *获取视频图片列表
	 */
	@POST
	@Path("getVideoImgList.do")
	public String getVideoImgList(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("videoId") String videoId,
			@FormParam("videoName") String videoName,
			@FormParam("startTime") String startTime,
			@FormParam("endTime") String endTime,
			@FormParam("enterpriseId") String enterpriseId,
			@FormParam("nowPage") String nowPage,
			@FormParam("pageCount") String pageCount,
			@Context HttpServletRequest request) {
		GetVideoImgHistoryParam myParam = new GetVideoImgHistoryParam(userId, apiKey, request);
		myParam.setVideoId(videoId);
		myParam.setVideoName(videoName);
		myParam.setStartTime(startTime);
		myParam.setEndTime(endTime);
		myParam.setEnterpriseId(enterpriseId);
		myParam.setNowPage(nowPage);
		myParam.setPageCount(pageCount);
		return new GetVideoImgHistoryLogic().process(myParam);
	}
	
	/**
	 *	同步设备抓拍图片
	 */
	@POST
	@Path("syncVideoImg.do")
	public String syncVideoImg(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("videoId") String videoId,
			@Context HttpServletRequest request) {
		SyncVideoImgParam myParam = new SyncVideoImgParam(userId, apiKey, request);
		myParam.setVideoId(videoId);
		return new SyncVideoImgLogic().process(myParam);
	}
	
	/**
	 *	开启云台控制
	 */
	@POST
	@Path("startControl.do")
	public String startControl(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("videoId") String videoId,
			@FormParam("direction") String direction,
			@FormParam("speed") String speed,
			@Context HttpServletRequest request) {
		StartControlParam myParam = new StartControlParam(userId, apiKey, request);
		myParam.setVideoId(videoId);
		myParam.setDirection(direction);
		myParam.setSpeed(speed);
		return new StartControlLogic().process(myParam);
	}
	
	/**
	 *	结束云台控制
	 */
	@POST
	@Path("stopControl.do")
	public String stopControl(
			@FormParam("userId") String userId,
			@FormParam("apiKey") String apiKey,
			@FormParam("videoId") String videoId,
			@FormParam("direction") String direction,
			@Context HttpServletRequest request) {
		StopControlParam myParam = new StopControlParam(userId, apiKey, request);
		myParam.setVideoId(videoId);
		myParam.setDirection(direction);
		return new StopControlLogic().process(myParam);
	}
	

}
