package com.jinpinghu.logic.rotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbRotationTime;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbRotationAdviceDao;
import com.jinpinghu.db.dao.TbRotationEnterpriseDao;
import com.jinpinghu.db.dao.TbRotationTimeDao;
import com.jinpinghu.db.dao.TbRotationToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.rotation.param.GetRotationParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetRotationAdviceInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetRotationParam myParam = (GetRotationParam)logicParam;
		Integer id = myParam.getId();
		
		TbRotationTimeDao rotationTimeDao = new TbRotationTimeDao(em);
		TbRotationAdviceDao tbRotationAdviceDao = new TbRotationAdviceDao(em);
		TbRotationEnterpriseDao tbRotationEnterpriseDao = new TbRotationEnterpriseDao(em);
		TbRotationToolDao tbRotationToolDao = new TbRotationToolDao(em);
		
		Map<String, Object> advice = tbRotationAdviceDao.findById(id);
		res.add("result", advice);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
