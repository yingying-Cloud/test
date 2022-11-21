package com.jinpinghu.logic.enterprise;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseZeroInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEnterpriseZeroInfoLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseZeroInfoParam myParam = (GetEnterpriseZeroInfoParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		TbEnterpriseZeroDao enterpriseZeroDao = new TbEnterpriseZeroDao(em);
		TbEnterpriseZero zero = enterpriseZeroDao.findByEnterpriseId(enterpriseId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("flag", zero == null ? 0 : zero.getFlag());
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", map);
		return true;
	}

}
