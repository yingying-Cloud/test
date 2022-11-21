package com.jinpinghu.logic.home;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbEnterpriseUserinfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.GetUserTypeCountParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetUserTypeCountLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetUserTypeCountParam myParam = (GetUserTypeCountParam)logicParam;
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		TbEnterpriseUserinfoDao enterpriseUserinfoDao = new TbEnterpriseUserinfoDao(em);
		List<Object[]> list = enterpriseUserinfoDao.getEnterpriseUserinfoTypeCount(enterpriseId);
		JSONArray ja = new JSONArray();
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("count", o[0]);
				jo.put("type",o[1]);
				ja.add(jo);
			}
		}
		res.add("result", ja).add("status", 1).add("msg", "操作成功");
		return true;
	}

}
