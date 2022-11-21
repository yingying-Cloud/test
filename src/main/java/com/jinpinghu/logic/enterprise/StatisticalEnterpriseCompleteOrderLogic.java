package com.jinpinghu.logic.enterprise;

import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.StatisticalEnterpriseCompleteOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class StatisticalEnterpriseCompleteOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		StatisticalEnterpriseCompleteOrderParam myParam = (StatisticalEnterpriseCompleteOrderParam)logicParam;
		String name=myParam.getName();
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String dscd = myParam.getDscd();
		String startTime = StringUtils.isEmpty(myParam.getStartTime()) ? DateTimeUtil.firstDayOfMonth(DateTimeUtil.formatSelf(new Date(), "yyyy-MM")) : myParam.getStartTime();
		String endTime = StringUtils.isEmpty(myParam.getEndTime()) ? DateTimeUtil.lastDayOfMonth(DateTimeUtil.formatSelf(new Date(), "yyyy-MM")) : myParam.getEndTime();
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		JSONObject jo = new JSONObject();
		Object[] completeCount = toolOrderDao.statisticalAllCompleteOrder(name,dscd,enterpriseId,startTime,endTime);
		if(completeCount!=null) {
			jo.put("count", completeCount[0]);
			jo.put("sum", completeCount[2]);
			jo.put("sumMoney", completeCount[3]);
			jo.put("buyNum", completeCount[4]);
			jo.put("sellNum", completeCount[5]);
			jo.put("recoveryNum", completeCount[6]);
			jo.put("peopleCount", completeCount[7]);
			jo.put("toolCount", completeCount[8]);
		}
		
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
