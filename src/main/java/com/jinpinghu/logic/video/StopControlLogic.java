package com.jinpinghu.logic.video;

import java.net.URLEncoder;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.HttpRequestUtil;
import com.jinpinghu.common.tools.YSUtil;
import com.jinpinghu.db.bean.TbVideo;
import com.jinpinghu.db.dao.TbVideoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.video.param.StopControlParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class StopControlLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StopControlParam myParam = (StopControlParam)logicParam;
		Integer videoId = StringUtils.isEmpty(myParam.getVideoId()) ? null : Integer.valueOf(myParam.getVideoId());
		String direction = myParam.getDirection();
		
		TbVideoDao videoDao = new TbVideoDao(em);
		TbVideo video = videoDao.findById(videoId);
		
		if(video == null) {
			res.add("status", -1).add("msg", "该摄像头不存在！");
			return false;
		}
		
		String deviceSerial = StringUtils.trimToEmpty(video.getDeviceSerial());
		String channelNo = StringUtils.trimToEmpty(video.getChannelNo());
		
		String accessToken = YSUtil.getAccessToken();
		if(StringUtils.isEmpty(accessToken)) {
			accessToken = YSUtil.initAccessToken();
		}
		String param = "accessToken="+URLEncoder.encode(accessToken, "utf-8");
		param += "&deviceSerial="+URLEncoder.encode(deviceSerial, "utf-8");
		param += "&channelNo="+URLEncoder.encode(channelNo, "utf-8");
		if(!StringUtils.isEmpty(direction))
			param += "&direction="+URLEncoder.encode(direction, "utf-8");
		
		String returnStr = HttpRequestUtil.sendPost(YSUtil.getStopcontrolApiurl(), param, "utf-8");
		JSONObject returnJo = JSONObject.fromObject(returnStr);
		if(!"200".equals(returnJo.getString("code"))) {
			if("10002".equals(returnJo.getString("code"))) {
				accessToken = YSUtil.initAccessToken();
				param = "accessToken="+URLEncoder.encode(accessToken, "utf-8");
				param += "&deviceSerial="+URLEncoder.encode(deviceSerial, "utf-8");
				param += "&channelNo="+URLEncoder.encode(channelNo, "utf-8");
				if(!StringUtils.isEmpty(direction))
					param += "&direction="+URLEncoder.encode(direction, "utf-8");
				
				HttpRequestUtil.sendPost(YSUtil.getStopcontrolApiurl(), param, "utf-8");
			}else {
				res.add("status", -1).add("msg", "摄像头控制失败。失败信息："+returnJo.getString("msg"));
				return false;	
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StopControlParam myParam = (StopControlParam)logicParam;
		String videoId = myParam.getVideoId();
		String direction = myParam.getDirection();
		
		if(StringUtils.isEmpty(videoId)) {
			res.add("status", -1).add("msg", "必填参数不能为空");
			return false;
		}
		
		if(!StringUtils.isEmpty(direction)) {
			try {
				Integer.valueOf(direction);
			}catch(NumberFormatException ex) {
				res.add("status",-1).add("msg", "参数格式有误，请检查！");
				return false;
			}
		}
		return true;
	}

}
