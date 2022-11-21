package com.jinpinghu.logic.tool;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.GetWorkToolSumListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWorkToolSumListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetWorkToolSumListParam myParam = (GetWorkToolSumListParam)logicParam;
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		TbToolDao tbToolDao = new TbToolDao(em);
		List<Map<String, Object>> list = tbToolDao.getTbResCropFarmingToolStatictis(enterpriseId);
		res.add("status", 1).add("msg", "操作成功").add("result", list);
		return true;
	}


}
