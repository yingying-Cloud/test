package com.jinpinghu.logic.offlineStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.collections.map.HashedMap;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbRole;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;
import com.jinpinghu.db.dao.TbResUserRoleDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbTypeDao;
import com.jinpinghu.db.dao.TbUserDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.offlineStore.param.ListGoodsGroupByIdTypeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ListGoodsGroupByIdTypeLogic extends BaseZLogic {

	public ListGoodsGroupByIdTypeLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ListGoodsGroupByIdTypeParam myParam = (ListGoodsGroupByIdTypeParam)logicParam;
		String id = myParam.getId();
		String goodsType = myParam.getGoodsType();
		String userId = myParam.getUserId();
		
		TbUserDao userDao = new TbUserDao(em);
		
		loginUser = userDao.checkLogin2(userId);
		
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(loginUser == null ? null : loginUser.getId());
		
		TbTypeDao typeDao = new TbTypeDao(em);
		TbToolDao toolDao = new TbToolDao(em);
		
		List<Map<String, Object>> list = typeDao.getListByTypeId(id, goodsType);
		Integer uniformPriceCount = toolDao.getUniformPriceCount(enterprise == null ? null : enterprise.getId());
		if (uniformPriceCount > 0) {
			Map<String, Object> uniformPriceMap = new HashedMap();
			uniformPriceMap.put("id", 23);
			uniformPriceMap.put("groupName", "零差价");
			list.add(uniformPriceMap);
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", list == null ? new ArrayList<>(0) : list);
		return true;
	}
	@Override
	protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

}
