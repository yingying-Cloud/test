package com.jinpinghu.logic.sellBrand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellBrand.param.GetBrandList2Param;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetBrandList2Logic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetBrandList2Param myParam = (GetBrandList2Param)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		TbSellBrandDao brandDao = new TbSellBrandDao(em);
		Integer count = brandDao.findNotAddSellBrandListCount(enterpriseId);
		List<Map<String, Object>> list = brandDao.findNotAddSellBrandList(enterpriseId);
		res.add("status", 1).add("msg", "操作成功").add("result", list == null ? new ArrayList<>() : list).add("allCounts", count);
		return true;
	}

}
