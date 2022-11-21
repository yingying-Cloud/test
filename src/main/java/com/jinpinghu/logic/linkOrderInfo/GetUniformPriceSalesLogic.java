package com.jinpinghu.logic.linkOrderInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.linkOrderInfo.param.GetUniformPriceDataParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetUniformPriceSalesLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetUniformPriceDataParam myParam = (GetUniformPriceDataParam)logicParam;
		Integer id = myParam.getId();
		String idcard = myParam.getIdcard();
		
		TbLinkOrderInfoDao linkOrderInfoDao = new TbLinkOrderInfoDao(em);
		TbEnterpriseUserProductionInfoDao enterpriseUserProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		List<Integer> userEnterpriseIdList = null;
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(!StringUtil.isNullOrEmpty(idcard)){//如果idcard不为空，直接查找
			userEnterpriseIdList = enterpriseUserProductionInfoDao.getEnterpriseIdByIdcard(idcard);
		}else{ //否则，先通过id查找出 linkOrderInfo 再返回idcard
			TbLinkOrderInfo linkOrderInfo = linkOrderInfoDao.findById(id);
			if (linkOrderInfo != null) {
				userEnterpriseIdList = enterpriseUserProductionInfoDao.getEnterpriseIdByIdcard(linkOrderInfo.getLegalPersonIdcard());
			}
		}
		if (!userEnterpriseIdList.isEmpty()) {
			Object[] uniforPriceData = enterpriseUserProductionInfoDao.getUniformPriceData(userEnterpriseIdList);
				if (uniforPriceData != null) {
					BigDecimal limitPrice = BigDecimal.ZERO;
					BigDecimal buyPrice = BigDecimal.ZERO;
					try {limitPrice = new BigDecimal(uniforPriceData[0].toString());} catch (Exception e) {}
					try {buyPrice = new BigDecimal(uniforPriceData[1].toString());} catch (Exception e) {}
					resultMap.put("limitPrice", limitPrice);
					resultMap.put("buyPrice", buyPrice);
					resultMap.put("enterpriseName", uniforPriceData[2] == null ? "" : uniforPriceData[2].toString());
					resultMap.put("town", uniforPriceData[3] == null ? "" : uniforPriceData[3].toString());
					resultMap.put("area", uniforPriceData[4] == null ? "" : uniforPriceData[4]);
					resultMap.put("village", uniforPriceData[5] == null ? "" : uniforPriceData[5].toString());
				}
			}
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}

}
