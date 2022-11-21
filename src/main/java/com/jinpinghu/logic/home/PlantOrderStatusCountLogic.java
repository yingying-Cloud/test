package com.jinpinghu.logic.home;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.OrderStatusCountParam;
import com.jinpinghu.logic.home.param.PlantOrderStatusCountParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PlantOrderStatusCountLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		PlantOrderStatusCountParam myParam=(PlantOrderStatusCountParam)logicParam;
		Integer plantEnterpriseId=StringUtils.isEmpty(myParam.getPlantEnterpriseId())?null:Integer.valueOf(myParam.getPlantEnterpriseId());
		Integer plantProtectionEnterpriseId=StringUtils.isEmpty(myParam.getPlantProtectionenterpriseId())?null:Integer.valueOf(myParam.getPlantProtectionenterpriseId());
		TbPlantProtectionOrderDao orderDao = new TbPlantProtectionOrderDao(em);
		JSONArray ja = new JSONArray();
		List<Object[]> list = orderDao.getStatusCount(plantEnterpriseId,plantProtectionEnterpriseId);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("count", o[0]);
				jo.put("status", o[1]);
				jo.put("sumPrice", o[2]);
				jo.put("sumArea", o[3]);
				ja.add(jo);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("result", ja);
		return true;
	}

}
