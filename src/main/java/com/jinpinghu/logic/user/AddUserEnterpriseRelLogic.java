package com.jinpinghu.logic.user;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterpriseDataPermission;
import com.jinpinghu.db.dao.TbEnterpriseDataPermissionDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.AddUserEnterpriseRelParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddUserEnterpriseRelLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddUserEnterpriseRelParam myParam = (AddUserEnterpriseRelParam)logicParam;
		String enterpriseId = myParam.getEnterpriseId();
		Integer userTabId = myParam.getUserTabId();
		TbEnterpriseDataPermissionDao tbResUserEnterpriseRelDao = new TbEnterpriseDataPermissionDao(em);
		
		List<String> list = Arrays.asList(enterpriseId.split(","));
		if(list!=null) {
			for(String id:list) {
				TbEnterpriseDataPermission checkIsExist = tbResUserEnterpriseRelDao.checkIsExist(userTabId, Integer.valueOf(id));
				if(checkIsExist!=null) {
					tbResUserEnterpriseRelDao.delete(checkIsExist);
				}else {
					TbEnterpriseDataPermission tbResUserEnterpriseRel = new TbEnterpriseDataPermission();
					tbResUserEnterpriseRel.setDelFlag(0);
					tbResUserEnterpriseRel.setEnterpriseId( Integer.valueOf(id));
					tbResUserEnterpriseRel.setUserTabId(userTabId);
					tbResUserEnterpriseRel.setInputTime(new Date());
					tbResUserEnterpriseRelDao.save(tbResUserEnterpriseRel);
				}
			}
		}
		
		
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
