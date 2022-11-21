package com.jinpinghu.common.tools;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

public class BaiduAIUtil {
	
	private static final String APP_ID = "17504987";
	private static final String API_KEY = "F73ITqT0YZgjrkDAwj2pmkyX";
	private static final String SECRET_KEY = "UUXSXQt9RLxjzPDzI271FUn2O8A1s701";
    private static AipFace client;
    private static long setDataTime = 0;
	private static final long TIMEOUTTIME = 1000 * 60 * 60 * 24;
	
	private static AipFace getClient() {
		if(setDataTime+TIMEOUTTIME>=System.currentTimeMillis()){
			return client;
		}
		return null;
	}
	
	private static void setClient(AipFace client) {
		BaiduAIUtil.client = client;
		setDataTime = System.currentTimeMillis();
	}
	
	public static AipFace initClient() {
		AipFace client = BaiduAIUtil.getClient();
		if(client != null) {
			return client;
		}else{
			client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
			BaiduAIUtil.setClient(client);
			return client;
		}
	}
	
	public static void main(String[] args) throws JSONException {
		
		client = BaiduAIUtil.initClient();
		HashMap<String, String> options = new HashMap<String, String>();
	    options.put("start", "0");
	    options.put("length", "50");
	    
	    
	    // 组列表查询
	    JSONObject res = client.getGroupList(options);
	    System.out.println(res.toString(2));
	}
	
	

}
