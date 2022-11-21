package com.jinpinghu.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;
import com.jinpinghu.common.tools.BaiduAIUtil;

public class Test3 {
	static List<byte[]> array = new ArrayList<>();
	public static void main(String[] args) throws JSONException {
		// TODO 自动生成的方法存根
		HashMap<String, String> options = new HashMap<String, String>();
	    options.put("max_face_num", "3");
	    options.put("match_threshold", "70");
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    options.put("max_user_num", "1");
	    
	    // 人脸搜索
	    AipFace client = BaiduAIUtil.initClient();
	    JSONObject returnJson = client.search("http://shabang.oss-cn-beijing.aliyuncs.com/cf180ba2-e124-4515-bee2-2eaf61461748.png", "URL", "jinpinghu", options);
	    System.out.println(returnJson.toString());
	    if (returnJson.get("result").equals(null)) {
	    	System.out.println(returnJson.toString());
		}else {
			JSONObject result = returnJson.getJSONObject("result");
			JSONArray userList = result.getJSONArray("user_list");
			JSONObject userInfo = userList.getJSONObject(0);
			String user_id = userInfo.getString("user_id");
			System.out.println(user_id);
		}
	    
	    
	
	}
}
