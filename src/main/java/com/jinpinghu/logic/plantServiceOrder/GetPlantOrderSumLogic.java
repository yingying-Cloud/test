package com.jinpinghu.logic.plantServiceOrder;

import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.dao.TbPlantServiceOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantServiceOrder.param.GetPlantOrderSumParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantOrderSumLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetPlantOrderSumParam myParam = (GetPlantOrderSumParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		TbPlantServiceOrderDao tbBrandOrderDao = new TbPlantServiceOrderDao(em);
		Map<String, Object> list = tbBrandOrderDao.getOrderCountSum(enterpriseId,startTime,endTime);
		res.add("status", 1).add("msg", "操作成功").add("result", list);
		return true;
	}


}
