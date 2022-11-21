package com.jinpinghu.logic.linkOrderInfo;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;
import com.jinpinghu.common.tools.BaiduAIUtil;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoByPicParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLinkOrderInfoByPicLogic3 extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetLinkOrderInfoByPicParam myParam = (GetLinkOrderInfoByPicParam)logicParam;
		String pic = myParam.getPic();
		
		if (StringUtils.isEmpty(pic)) {
			res.add("status", -1).add("msg", "人脸匹配失败");
			return false;
		}
		
		HashMap<String, String> options = new HashMap<String, String>();
	    options.put("max_face_num", "3");
	    options.put("match_threshold", "70");
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    options.put("max_user_num", "1");
	    
	    // 人脸搜索
	    AipFace client = BaiduAIUtil.initClient();
	    JSONObject returnJson = client.search(pic, "URL", "jinpinghu", options);
	    if (returnJson.get("result").equals(null)) {
	    	res.add("status", -1).add("msg", "人脸匹配失败");
			return false;
		}
	    
	    JSONObject result = returnJson.getJSONObject("result");
		JSONArray userList = result.getJSONArray("user_list");
		JSONObject userInfo = userList.getJSONObject(0);
		String idcard = userInfo.getString("user_id");
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbLinkOrderInfo matchLinkOrderInfo = linkOrderInfoDao.findByIdcard(idcard);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", matchLinkOrderInfo.getName() == null ? "" : matchLinkOrderInfo.getName());
		resultMap.put("idcard", matchLinkOrderInfo.getLegalPersonIdcard() == null ? "" : matchLinkOrderInfo.getLegalPersonIdcard());
		resultMap.put("sex", matchLinkOrderInfo.getSex() == null ? "" : matchLinkOrderInfo.getSex());
		resultMap.put("nation", matchLinkOrderInfo.getNation() == null ? "" : matchLinkOrderInfo.getNation());
		resultMap.put("country", matchLinkOrderInfo.getCountry() == null ? "" : matchLinkOrderInfo.getCountry());
		resultMap.put("address", matchLinkOrderInfo.getAddress() == null ? "" : matchLinkOrderInfo.getAddress());
		resultMap.put("pic", matchLinkOrderInfo.getLastPic() == null ? "" : matchLinkOrderInfo.getLastPic());
		resultMap.put("mobile", matchLinkOrderInfo.getLinkMobile() == null ? "" : matchLinkOrderInfo.getLinkMobile());
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}

}
