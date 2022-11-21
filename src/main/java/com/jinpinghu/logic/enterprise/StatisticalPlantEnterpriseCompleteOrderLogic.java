package com.jinpinghu.logic.enterprise;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.StatisticalEnterpriseCompleteOrderParam;
import com.jinpinghu.logic.enterprise.param.StatisticalPlantEnterpriseCompleteOrderParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class StatisticalPlantEnterpriseCompleteOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		StatisticalPlantEnterpriseCompleteOrderParam myParam = (StatisticalPlantEnterpriseCompleteOrderParam)logicParam;
		String name=myParam.getName();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String dscd = myParam.getDscd();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		
		TbPlantProtectionOrderDao plantProtectionOrderDao = new TbPlantProtectionOrderDao(em);
		JSONObject jo = new JSONObject();
		Object[] plantCompleteCount = plantProtectionOrderDao.statisticalAllCompleteOrder(name,dscd,enterpriseId,startTime,endTime);
		if(plantCompleteCount!=null) {
			jo.put("plantOrderCount", plantCompleteCount[0]);
			jo.put("plantServiceCount", plantCompleteCount[1]);
			jo.put("plantSum", plantCompleteCount[2]);
			jo.put("plantArea", plantCompleteCount[3]);
		}
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", jo);
		return true;
	}
}
