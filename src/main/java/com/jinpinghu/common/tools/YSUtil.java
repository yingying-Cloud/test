package com.jinpinghu.common.tools;

import net.sf.json.JSONObject;

/**
 * 萤石开放平台
 *
 */
public class YSUtil {

	private static String accessToken = "";
	private static long setDataTime = 0;
	private static final String appKey = "a2e906d4f41e4b12b00683311c155148";
	private static final String secret = "35342bd6bb27f2c6169002452c903872";
	/** 获取accessToken **/
	private static final String getAccessToken_apiurl = "https://open.ys7.com/api/lapp/token/get";
	/** 设备抓拍图片 **/
	private static final String capture_apiurl = "https://open.ys7.com/api/lapp/device/capture";
	/** 开始云台控制 **/
	private static final String startControl_apiurl = "https://open.ys7.com/api/lapp/device/ptz/start";
	/** 停止云台控制 **/
	private static final String stopControl_apiurl = "https://open.ys7.com/api/lapp/device/ptz/stop";
	private static final long TIMEOUTTIME = 1000 * 60 * 60;

	public static String getAccessToken() {
		if(setDataTime+TIMEOUTTIME>=System.currentTimeMillis()){
			return accessToken;
		}
		return null;
	}

	public static void setAccessToken(String accessToken) {
		YSUtil.accessToken = accessToken;
		setDataTime = System.currentTimeMillis();
	}
	
	public static String initAccessToken() {
		String param = "appKey="+appKey+"&appSecret="+secret;
		String returnStr = HttpRequestUtil.sendPost(YSUtil.getAccessToken_apiurl, param, "utf-8");
		JSONObject returnJo = JSONObject.fromObject(returnStr);
		if(!"200".equals(returnJo.getString("code"))) {
			return "";
		}else {
			JSONObject data = returnJo.getJSONObject("data");
			YSUtil.setAccessToken(data.getString("accessToken"));
			return data.getString("accessToken");
		}
	}

	public static String getCaptureApiurl() {
		return capture_apiurl;
	}

	public static String getStartcontrolApiurl() {
		return startControl_apiurl;
	}

	public static String getStopcontrolApiurl() {
		return stopControl_apiurl;
	}
}
