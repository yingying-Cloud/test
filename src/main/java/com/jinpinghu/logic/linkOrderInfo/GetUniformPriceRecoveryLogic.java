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

public class GetUniformPriceRecoveryLogic extends BaseZLogic {

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
			Object[] recoveryData = enterpriseUserProductionInfoDao.getRecoveryData(userEnterpriseIdList);
			//购买的零差价农资（包、袋）
			BigDecimal bagUniformPriceNum = BigDecimal.ZERO;
			//购买的零差价农资（瓶）
			BigDecimal bottleUniformPriceNum = BigDecimal.ZERO;
			//回收的零差价农资（包、袋）
			BigDecimal bagUniformPriceRecoveryNum = BigDecimal.ZERO;
			//回收的零差价农资（瓶）
			BigDecimal bottleUniformPriceRecoveryNum = BigDecimal.ZERO;
			//回收的非零差价农资（包、袋）
			BigDecimal bagRecoveryPrice = BigDecimal.ZERO;
			//回收的非零差价农资（瓶）
			BigDecimal bottleRecoveryPrice = BigDecimal.ZERO;
			//回收的农膜
			BigDecimal nmRecoveryPrice = BigDecimal.ZERO;
			//非零差价农药限制
			BigDecimal notUniformPriceTrashLimit = BigDecimal.ZERO;
			//回收农膜限制
			BigDecimal recoveryNmLimit = BigDecimal.ZERO;
			if (recoveryData != null) {
				try {recoveryNmLimit = new BigDecimal(recoveryData[0].toString());} catch (Exception e) {}
				try {notUniformPriceTrashLimit = new BigDecimal(recoveryData[1].toString());} catch (Exception e) {}
				String uniformPriceNum = recoveryData[2].toString();
				String uniformPriceRecoveryNum = recoveryData[3].toString();
				String normalRecoveryPrice = recoveryData[4].toString();
				String[] uniformPriceNumArray = uniformPriceNum.split(",");
				String[] uniformPriceRecoveryNumArray = uniformPriceRecoveryNum.split(",");
				String[] normalRecoveryPriceArray = normalRecoveryPrice.split(",");
				
				try {bottleUniformPriceNum = new BigDecimal(uniformPriceNumArray[0].toString());} catch (Exception e) {}
				try {bagUniformPriceNum = new BigDecimal(uniformPriceNumArray[1].toString());} catch (Exception e) {}
				try {bottleUniformPriceRecoveryNum = new BigDecimal(uniformPriceRecoveryNumArray[0].toString());} catch (Exception e) {}
				try {bagUniformPriceRecoveryNum = new BigDecimal(uniformPriceRecoveryNumArray[1].toString());} catch (Exception e) {}
				try {bottleRecoveryPrice = new BigDecimal(normalRecoveryPriceArray[0].toString());} catch (Exception e) {}
				try {bagRecoveryPrice = new BigDecimal(normalRecoveryPriceArray[1].toString());} catch (Exception e) {}
				try {nmRecoveryPrice = new BigDecimal(normalRecoveryPriceArray[2].toString());} catch (Exception e) {}
				
			}
			resultMap.put("recoveryNmLimit", recoveryNmLimit);
			resultMap.put("notUniformPriceTrashLimit", notUniformPriceTrashLimit);
			resultMap.put("bottleUniformPriceNum", bottleUniformPriceNum);
			resultMap.put("bagUniformPriceNum", bagUniformPriceNum);
			resultMap.put("bagUniformPriceRecoveryNum", bagUniformPriceRecoveryNum);
			resultMap.put("bottleUniformPriceRecoveryNum", bottleUniformPriceRecoveryNum);
			resultMap.put("bagRecoveryPrice", bagRecoveryPrice);
			resultMap.put("bottleRecoveryPrice", bottleRecoveryPrice);
			resultMap.put("nmRecoveryPrice", nmRecoveryPrice);
			resultMap.put("enterpriseName", recoveryData[5] == null ? "" : recoveryData[5].toString());
			resultMap.put("town", recoveryData[6] == null ? "" : recoveryData[6].toString());
			resultMap.put("area", recoveryData[7] == null ? "" : recoveryData[7]);
			resultMap.put("village", recoveryData[8] == null ? "" : recoveryData[8].toString());
		}
		
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap);
		return true;
	}

}
