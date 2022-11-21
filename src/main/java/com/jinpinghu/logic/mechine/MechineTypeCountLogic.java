package com.jinpinghu.logic.mechine;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbMechineDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.mechine.param.MechineTypeCountParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MechineTypeCountLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		MechineTypeCountParam myParam=(MechineTypeCountParam)logicParam;
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		TbMechineDao orderDao = new TbMechineDao(em);
		JSONArray ja = new JSONArray();
		List<Object[]> list = orderDao.getMechineTypeCount(enterpriseId);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("count", o[0]);
				jo.put("type", o[1]);
//				jo.put("typeName", o[2]);
				ja.add(jo);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("result", ja);
		return true;
	}

}
