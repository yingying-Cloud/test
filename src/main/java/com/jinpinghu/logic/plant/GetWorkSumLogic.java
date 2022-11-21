package com.jinpinghu.logic.plant;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetWorkSumParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetWorkSumLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetWorkSumParam myParam = (GetWorkSumParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		
		TbWorkDao workDao = new TbWorkDao(em);
		
		Map<String, Object> resultMap = workDao.getSum(enterpriseId);
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultMap == null ? new HashMap<String, Object>() : resultMap);
		return true;
	}

}
