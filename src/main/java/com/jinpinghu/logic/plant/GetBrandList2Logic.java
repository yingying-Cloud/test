package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetBrandList2Param;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetBrandList2Logic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetBrandList2Param myParam = (GetBrandList2Param)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId()) ? null : Integer.valueOf(myParam.getEnterpriseId());
		String brandIds = myParam.getToolIds();
		
		List<Integer> brandIdList = null;
		if(!StringUtils.isEmpty(brandIds)) {
			String[] brandIdArray = brandIds.split(",");
			brandIdList = new ArrayList<Integer>();
			for(int i=0;i<brandIdArray.length;i++)
				brandIdList.add(Integer.valueOf(brandIdArray[i]));
		}
		
		TbBrandDao brandDao = new TbBrandDao(em);
		
		List<Map<String, Object>> list = brandDao.findNotAddBrandList(enterpriseId, brandIdList);
		
		res.add("status", 1).add("msg", "操作成功").add("result", list == null ? new ArrayList<>() : list);
		return true;
	}

}
