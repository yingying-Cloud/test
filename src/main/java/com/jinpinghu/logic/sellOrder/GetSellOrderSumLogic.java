package com.jinpinghu.logic.sellOrder;

import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.dao.TbSellOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellOrder.param.GetSellOrderSumParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetSellOrderSumLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetSellOrderSumParam myParam = (GetSellOrderSumParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		TbSellOrderDao tbSellOrderDao = new TbSellOrderDao(em);
		Map<String, Object> list = tbSellOrderDao.getOrderCountSum(enterpriseId,startTime,endTime);
		res.add("status", 1).add("msg", "操作成功").add("result", list);
		return true;
	}


}
