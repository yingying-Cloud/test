package com.jinpinghu.logic.linkOrderInfo;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.db.dao.TbResEnterpriseLinkorderinfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.linkOrderInfo.param.GetLinkOrderInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetLinkOrderInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetLinkOrderInfoParam myParam = (GetLinkOrderInfoParam)logicParam;
		Integer id = myParam.getId();
		
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbResEnterpriseLinkorderinfoDao tbResEnterpriseLinkorderinfoDao = new TbResEnterpriseLinkorderinfoDao(em);
		TbLinkOrderInfo linkOrderInfo = linkOrderInfoDao.findById(id);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (linkOrderInfo != null) {
			resultMap.put("name", linkOrderInfo.getName() == null ? "" : linkOrderInfo.getName());
			resultMap.put("idcard", linkOrderInfo.getLegalPersonIdcard() == null ? "" : linkOrderInfo.getLegalPersonIdcard());
			resultMap.put("pic", linkOrderInfo.getLastPic() == null ? "" : linkOrderInfo.getLastPic());
			resultMap.put("country", linkOrderInfo.getCountry() == null ? "" : linkOrderInfo.getCountry());
			resultMap.put("nation", linkOrderInfo.getNation() == null ? "" : linkOrderInfo.getNation());
			resultMap.put("address", linkOrderInfo.getAddress() == null ? "" : linkOrderInfo.getAddress());
			resultMap.put("sex", linkOrderInfo.getSex() == null ? "" : linkOrderInfo.getSex());
			resultMap.put("mobile", linkOrderInfo.getLinkMobile() == null ? "" : linkOrderInfo.getLinkMobile());
			resultMap.put("inputTime", linkOrderInfo.getInputTime() == null ? "" : DateTimeUtil.formatTime2(linkOrderInfo.getInputTime()));
			
			
			resultMap.put("ownEnterpriseId", linkOrderInfo.getEnterpriseId() == null ? "" : linkOrderInfo.getEnterpriseId());
			resultMap.put("ownEnterpriseName", "");
			resultMap.put("ownEnterpriseBaseAddress","");
			if (linkOrderInfo.getEnterpriseId() != null) {
				TbEnterprise ownEnterprise = enterpriseDao.findById(linkOrderInfo.getEnterpriseId());
				resultMap.put("ownEnterpriseName", ownEnterprise == null ? "" : ownEnterprise.getName());
				resultMap.put("ownEnterpriseBaseAddress", ownEnterprise == null ? "" : ownEnterprise.getBaseAddress());
			}
			
			Map<String, Object> enterpriseName = tbResEnterpriseLinkorderinfoDao.findMapByEnterpriseIdAndLinkOrderInfoId(linkOrderInfo.getId());
			if(enterpriseName!=null) {
				resultMap.putAll(enterpriseName);
			}
			
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}
	
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		return true;
	}

}
