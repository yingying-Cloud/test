package com.jinpinghu.logic.brandOrder;

import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.dao.TbBrandOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.GetBrandOrderSumParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetBrandOrderSumLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetBrandOrderSumParam myParam = (GetBrandOrderSumParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		TbBrandOrderDao tbBrandOrderDao = new TbBrandOrderDao(em);
		Map<String, Object> list = tbBrandOrderDao.getOrderCountSum(enterpriseId,startTime,endTime);
		res.add("status", 1).add("msg", "操作成功").add("result", list);
		return true;
	}


}
