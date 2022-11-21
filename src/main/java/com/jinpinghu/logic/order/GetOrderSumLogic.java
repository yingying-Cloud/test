package com.jinpinghu.logic.order;

import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.GetOrderSumParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetOrderSumLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetOrderSumParam myParam = (GetOrderSumParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		TbToolOrderDao tbBrandOrderDao = new TbToolOrderDao(em);
		Map<String, Object> list = tbBrandOrderDao.getOrderCountSum(enterpriseId,startTime,endTime);
		res.add("status", 1).add("msg", "操作成功").add("result", list);
		return true;
	}


}
