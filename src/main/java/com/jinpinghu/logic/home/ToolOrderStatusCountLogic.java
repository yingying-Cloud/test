package com.jinpinghu.logic.home;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.OrderStatusCountParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ToolOrderStatusCountLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		OrderStatusCountParam myParam=(OrderStatusCountParam)logicParam;
		Integer plantEnterpriseId=StringUtils.isEmpty(myParam.getPlantEnterpriseId())?null:Integer.valueOf(myParam.getPlantEnterpriseId());
		Integer toolEnterpriseId=StringUtils.isEmpty(myParam.getToolEnterpriseId())?null:Integer.valueOf(myParam.getToolEnterpriseId());
		TbToolOrderDao orderDao = new TbToolOrderDao(em);
		JSONArray ja = new JSONArray();
		List<Object[]> list = orderDao.getStatusCount(toolEnterpriseId,plantEnterpriseId);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("count", o[0]);
				jo.put("status", o[1]);
				jo.put("sum", o[2]);
				ja.add(jo);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("result", ja);
		return true;
	}

}
