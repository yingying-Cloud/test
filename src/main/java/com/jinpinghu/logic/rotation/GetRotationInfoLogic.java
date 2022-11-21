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

public class GetRotationInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetRotationParam myParam = (GetRotationParam)logicParam;
		String rotationId = myParam.getCode();
		
		TbRotationTimeDao rotationTimeDao = new TbRotationTimeDao(em);
		TbRotationAdviceDao tbRotationAdviceDao = new TbRotationAdviceDao(em);
		TbRotationEnterpriseDao tbRotationEnterpriseDao = new TbRotationEnterpriseDao(em);
		TbRotationToolDao tbRotationToolDao = new TbRotationToolDao(em);
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(!StringUtils.isNullOrEmpty(rotationId)) {
			TbRotationTime tbRotationTime = rotationTimeDao.findById(Integer.valueOf(rotationId));
			List<Map<String, Object>> enterprise = tbRotationEnterpriseDao.findByAll(Integer.valueOf(rotationId), null);
			List<Map<String, Object>> advice = tbRotationAdviceDao.findByAll(Integer.valueOf(rotationId), null);
			List<Map<String, Object>> tool = tbRotationToolDao.findByAll(Integer.valueOf(rotationId), null);
			map.put("advice", advice);
			map.put("tool", tool);
			map.put("enterprise", enterprise);
			map.put("rotationTime", tbRotationTime.getRotationTime());
		}else {
			map = rotationTimeDao.findNew();
			if(map!=null) {
				List<Map<String, Object>> enterprise = tbRotationEnterpriseDao.findByAll(Integer.valueOf(map.get("rotationId").toString()), null);
				List<Map<String, Object>> advice = tbRotationAdviceDao.findByAll(Integer.valueOf(map.get("rotationId").toString()), null);
				List<Map<String, Object>> tool = tbRotationToolDao.findByAll(Integer.valueOf(map.get("rotationId").toString()), null);
				map.put("advice", advice);
				map.put("tool", tool);
				map.put("enterprise", enterprise);
				map.put("rotationTime", map.get("rotationTime").toString());
			}
		}
		res.add("result", map);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
