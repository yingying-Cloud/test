package com.jinpinghu.logic.arableLand;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbArableLandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.arableLand.param.AddOrUpdateArableLandParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetArableLandInfoByIdLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrUpdateArableLandParam myParam = (AddOrUpdateArableLandParam)logicParam;
		String id = myParam.getId();
		TbArableLandDao tbArableaLandDao = new TbArableLandDao(em);
		
		Map<String, Object> result = tbArableaLandDao.findMapInfoById(id);
		
		
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
