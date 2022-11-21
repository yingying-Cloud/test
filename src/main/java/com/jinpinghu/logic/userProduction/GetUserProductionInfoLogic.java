package com.jinpinghu.logic.userProduction;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterpriseUserProductionInfo;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.userProduction.param.DelUserProductionParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONObject;

public class GetUserProductionInfoLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelUserProductionParam myParam = (DelUserProductionParam)logicParam;
		Integer id = myParam.getId();
		TbEnterpriseUserProductionInfoDao userProductionInfoDao = new TbEnterpriseUserProductionInfoDao(em);
		TbEnterpriseUserProductionInfo userProductionInfo = userProductionInfoDao.getById(id);
		JSONObject jo =new JSONObject();
		if(userProductionInfo!=null) {
			jo.put("userIdCard", userProductionInfo.getUserIdCard());
			jo.put("userName", userProductionInfo.getUserName());
			jo.put("id", userProductionInfo.getId());
			jo.put("type",  userProductionInfo.getType());
		}
		res.add("status", 1).add("msg","操作成功").add("result", jo);
		return true;
	}

}
