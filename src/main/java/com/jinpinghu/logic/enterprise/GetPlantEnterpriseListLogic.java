package com.jinpinghu.logic.enterprise;

import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetPlantEnterpriseListParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class GetPlantEnterpriseListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetPlantEnterpriseListParam myParam = (GetPlantEnterpriseListParam)logicParam;
		String name=myParam.getName();
		String time = myParam.getTime();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbToolOrderDao toolOrderDao = new TbToolOrderDao(em);
		TbPlantProtectionOrderDao plantProtectionOrderDao = new TbPlantProtectionOrderDao(em);
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		Integer count = enterpriseDao.findPlantByAllCount(name, time);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = enterpriseDao.findPlantByAll(name,time,nowPage,pageCount);
		if(list!=null)
		for(Map<String, Object> map:list) {
			Integer enterpriseId = (Integer) map.get("id");
			Object[] completeCount = toolOrderDao.getCompleteCount(enterpriseId);
			if(completeCount!=null) {
				map.put("toolCount", completeCount[0]);
				map.put("toolSum", completeCount[2]);
				map.put("toolSumMoney", completeCount[3]);
			}
			Object[] plantCompleteCount = plantProtectionOrderDao.getCompleteCount(enterpriseId);
			if(plantCompleteCount!=null) {
				map.put("plantOrderCount", plantCompleteCount[0]);
				map.put("plantServiceCount", plantCompleteCount[1]);
				map.put("plantSum", plantCompleteCount[2]);
				map.put("plantArea", plantCompleteCount[3]);
			}
		}

//		Object workSum = enterpriseDao.getSumPlantScope(name,dscd);
//		res.add("area", workSum == null ? "" : workSum);
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}
}
