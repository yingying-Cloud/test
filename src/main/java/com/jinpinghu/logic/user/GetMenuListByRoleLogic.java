package com.jinpinghu.logic.user;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.bean.TbToolRecovery;
import com.jinpinghu.db.bean.TbZone;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbRoleDao;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.db.dao.TbToolRecoveryDao;
import com.jinpinghu.db.dao.TbZoneDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.GetMenuListByRoleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMenuListByRoleLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		GetMenuListByRoleParam myParam = (GetMenuListByRoleParam)logicParam;
		Integer roleId = myParam.getRoleId();
		Integer parentId = StringUtils.isEmpty(myParam.getParentId()) ? null : Integer.valueOf(myParam.getParentId());
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbZoneDao zoneDao = new TbZoneDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser.getId());
		
		List<TbZone> zones = zoneDao.findByEnterpriseId(enterprise == null ? null : enterprise.getId());
		
		TbRoleDao tbRoleDao = new TbRoleDao(em);
		List<Map<String, Object>> resultList = tbRoleDao.getMenuListByRole(roleId,parentId);
		if(resultList==null) {
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> map = resultList.get(i);
			if(zones == null && (Integer)map.get("menuId") == 47) {
				resultList.remove(i);
				continue;
			}
			if((Integer)map.get("menuId") == 1 || (Integer)map.get("menuId") == 2 || (Integer)map.get("menuId") == 3 || (Integer)map.get("menuId") == 4 || parentId != null) {
				List<Map<String, Object>> childrenList = tbRoleDao.getMenuListByRole(roleId, (Integer)map.get("menuId"));
				map.put("children", childrenList);
			}
		}
		
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMenuListByRoleParam myParam = (GetMenuListByRoleParam)logicParam;
		Integer roleId = myParam.getRoleId();
		if(roleId==null) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空");
			return false;
		}
		return true;
	}
	
}
