package com.jinpinghu.common.tools;

public class WeChatUtil {
	private static String msg = "";
	private static long setDataTime = 0;
	private static final long TIMEOUTTIME = 1000 * 60 * 10;

	public static String getMsg() {
		if(setDataTime+TIMEOUTTIME>=System.currentTimeMillis()){
			return msg;
		}
		return null;
	}

	public static void setMsg(String msg) {
		WeChatUtil.msg = msg;
		setDataTime = System.currentTimeMillis();
	}

}
