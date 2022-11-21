package com.jinpinghu.logic.linkOrderInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.AliyunAPIUtil;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoByPicParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLinkOrderInfoByPicLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetLinkOrderInfoByPicParam myParam = (GetLinkOrderInfoByPicParam)logicParam;
		String pic = myParam.getPic();
		
		if (StringUtils.isEmpty(pic)) {
			res.add("status", -1).add("msg", "人脸匹配失败");
			return false;
		}
		
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		
		List<TbLinkOrderInfo> linkOrderInfos = linkOrderInfoDao.findAll();
		
		if (linkOrderInfos == null) {
			res.add("status", -1).add("msg", "人脸匹配失败");
			return false;
		}
		
		TbLinkOrderInfo matchLinkOrderInfo = null;
		BigDecimal matchScore = BigDecimal.ZERO;
		for (int i = 0; i < linkOrderInfos.size(); i++) {
			TbLinkOrderInfo linkOrderInfo = linkOrderInfos.get(i);
			if(StringUtils.isEmpty(linkOrderInfo.getLastPic()))
				continue;
			BigDecimal tempMatchScore = AliyunAPIUtil.aiFaceMatch(pic, linkOrderInfo.getLastPic());
			if(tempMatchScore.compareTo(matchScore) == 1) {
				matchScore = tempMatchScore;
				matchLinkOrderInfo = linkOrderInfo;
			}
		}
		
		if(matchScore.compareTo(new BigDecimal("70")) == -1) {
			res.add("status", -1).add("msg", "人脸匹配失败,匹配度为:"+matchScore);
			return false;
		}
		
		matchLinkOrderInfo.setLastPic(pic);
		matchLinkOrderInfo.setUpdateTime(new Date());
		linkOrderInfoDao.update(matchLinkOrderInfo);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", matchLinkOrderInfo.getName() == null ? "" : matchLinkOrderInfo.getName());
		resultMap.put("idcard", matchLinkOrderInfo.getLegalPersonIdcard() == null ? "" : matchLinkOrderInfo.getLegalPersonIdcard());
		resultMap.put("sex", matchLinkOrderInfo.getSex() == null ? "" : matchLinkOrderInfo.getSex());
		resultMap.put("nation", matchLinkOrderInfo.getNation() == null ? "" : matchLinkOrderInfo.getNation());
		resultMap.put("country", matchLinkOrderInfo.getCountry() == null ? "" : matchLinkOrderInfo.getCountry());
		resultMap.put("address", matchLinkOrderInfo.getAddress() == null ? "" : matchLinkOrderInfo.getAddress());
		resultMap.put("pic", matchLinkOrderInfo.getLastPic() == null ? "" : matchLinkOrderInfo.getLastPic());
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}

}
