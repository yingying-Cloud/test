package com.jinpinghu.common.tools;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDataPermissionDao;
import com.jinpinghu.db.dao.TbResUserEnterpriseDao;

public class EnterpriseDataPermissionUtil {

	public static List<Integer> getPermissionEnterpriseIdList(Integer userTabId,EntityManager em){
		TbEnterpriseDataPermissionDao enterpriseDataPermissionDao = new TbEnterpriseDataPermissionDao(em);
		List<Integer> list = enterpriseDataPermissionDao.getPermissionEnterpriseId(userTabId);
		return list;
	}
	
	public static Integer getLoginEnterpriseId(Integer userTabId,EntityManager em){
		TbResUserEnterpriseDao resUserEnterpriseDao = new TbResUserEnterpriseDao(em);
		TbEnterprise enterprise = resUserEnterpriseDao.findByUserTabId(userTabId);
		if (enterprise != null) {
			return enterprise.getId();
		}
		return null;
	}
}
