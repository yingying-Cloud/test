package com.jinpinghu.logic.role;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.ConvertUtils;

import com.jinpinghu.db.dao.TbRoleDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.role.param.GetRoleLIstParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetRoleLIstLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetRoleLIstParam myParam = (GetRoleLIstParam)logicParam;
		String[] id = StringUtils.isNullOrEmpty(myParam.getId())? null : myParam.getId().split(",");
		String roleName = myParam.getRoleName();
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount()) ? null : Integer.valueOf(myParam.getPageCount());
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage()) ? null : Integer.valueOf(myParam.getNowPage());
		
		TbRoleDao tbRoleDao = new TbRoleDao(em);
		List<Map<String, Object>> resultList = null;
		List<Integer> idList = null;
		if(id != null){
			Integer[] ids = (Integer[])ConvertUtils.convert(id,Integer.class);
			idList = Arrays.asList(ids);
		}
		Integer count = tbRoleDao.getRoleLIstCountById(idList,roleName);
		resultList = tbRoleDao.getRoleListById(idList, roleName, nowPage, pageCount);
		
		
		
		res.add("count", count);
		res.add("result", resultList);
		res.add("msg", "成功！");
		res.add("status", 1);
		return true;
	}

}
