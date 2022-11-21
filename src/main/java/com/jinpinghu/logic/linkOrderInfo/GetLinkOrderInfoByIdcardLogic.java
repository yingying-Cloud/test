package com.jinpinghu.logic.linkOrderInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoByIdcardParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLinkOrderInfoByIdcardLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetLinkOrderInfoByIdcardParam myParam = (GetLinkOrderInfoByIdcardParam)logicParam;
		String idcard = myParam.getIdcard();
		
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		List<TbLinkOrderInfo> linkOrderInfoList = linkOrderInfoDao.findByIdcard2(idcard);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>(linkOrderInfoList.size());
		Map<String, Object> resultMap;
		for (TbLinkOrderInfo linkOrderInfo : linkOrderInfoList) {
			resultMap = new HashMap<String, Object>();
			resultMap.put("name", linkOrderInfo.getName() == null ? "" : linkOrderInfo.getName());
			resultMap.put("idcard", linkOrderInfo.getLegalPersonIdcard() == null ? "" : linkOrderInfo.getLegalPersonIdcard());
			resultMap.put("pic", linkOrderInfo.getLastPic() == null ? "" : linkOrderInfo.getLastPic());
			resultMap.put("country", linkOrderInfo.getCountry() == null ? "" : linkOrderInfo.getCountry());
			resultMap.put("nation", linkOrderInfo.getNation() == null ? "" : linkOrderInfo.getNation());
			resultMap.put("address", linkOrderInfo.getAddress() == null ? "" : linkOrderInfo.getAddress());
			resultMap.put("sex", linkOrderInfo.getSex() == null ? "" : linkOrderInfo.getSex());
			resultMap.put("mobile", linkOrderInfo.getLinkMobile() == null ? "" : linkOrderInfo.getLinkMobile());
			resultList.add(resultMap);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}

}
