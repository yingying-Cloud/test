package com.jinpinghu.logic.rotation;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbUser;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbRotationAdviceDao;
import com.jinpinghu.db.dao.TbRotationEnterpriseDao;
import com.jinpinghu.db.dao.TbRotationTimeDao;
import com.jinpinghu.db.dao.TbRotationToolDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.rotation.param.GetRotationParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetRotationLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetRotationParam myParam = (GetRotationParam)logicParam;
		String code = myParam.getCode();
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbUserDao tbUserDao = new TbUserDao(em);
		TbUser tbUser = tbUserDao.checkLogin2(code);
		TbEnterprise tbEnterprise = resUserEnterpriseDao.findByUserTabId(tbUser.getId());
		Integer enterpriseId=  tbEnterprise==null?null:tbEnterprise.getId();
		
		
		TbRotationTimeDao rotationTimeDao = new TbRotationTimeDao(em);
		TbRotationAdviceDao tbRotationAdviceDao = new TbRotationAdviceDao(em);
		TbRotationEnterpriseDao tbRotationEnterpriseDao = new TbRotationEnterpriseDao(em);
		TbRotationToolDao tbRotationToolDao = new TbRotationToolDao(em);
		
		Map<String, Object> map = rotationTimeDao.findNew();
		if(map!=null) {
			List<Map<String, Object>> enterprise = tbRotationEnterpriseDao.findByAll(Integer.valueOf(map.get("rotationId").toString()), enterpriseId);
			if(enterprise==null) {
				res.add("status", 1);
				res.add("msg", "无数据");
			}
			map.put("enterpriseName", tbEnterprise==null?"":tbEnterprise.getName());
			List<Map<String, Object>> advice = tbRotationAdviceDao.findByAll(Integer.valueOf(map.get("rotationId").toString()), enterpriseId);
			List<Map<String, Object>> tool = tbRotationToolDao.findByAll(Integer.valueOf(map.get("rotationId").toString()), enterpriseId);
			map.put("advice", advice);
			map.put("tool", tool);
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
