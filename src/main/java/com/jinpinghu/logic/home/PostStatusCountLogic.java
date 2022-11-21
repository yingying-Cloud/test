package com.jinpinghu.logic.home;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.OrderStatusCountParam;
import com.jinpinghu.logic.home.param.PostOrderStatusCountParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PostStatusCountLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		PostOrderStatusCountParam myParam=(PostOrderStatusCountParam)logicParam;
		Integer enterpriseId=StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer expertId=StringUtils.isEmpty(myParam.getExpertId())?null:Integer.valueOf(myParam.getExpertId());
		TbPostDao orderDao = new TbPostDao(em);
		JSONArray ja = new JSONArray();
		List<Object[]> list = orderDao.getStatusCount(enterpriseId,expertId);
		if(list!=null) {
			for(Object[] o:list) {
				JSONObject jo = new JSONObject();
				jo.put("count", o[0]);
				jo.put("status", o[1]);
				ja.add(jo);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("result", ja);
		return true;
	}

}
