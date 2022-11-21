package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbResUserEnterprise;

public class TbResUserEnterpriseDao extends BaseZDao{

	public TbResUserEnterpriseDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public TbEnterprise findByUserTabId(Integer userTabId) {
		try {
			String queryString = " SELECT te.* FROM `tb_res_user_enterprise` rue left join tb_enterprise te on rue.enterprise_id = te.id ";
			queryString       += " where rue.del_flag = 0 and te.del_flag = 0 and rue.user_tab_id = :userTabId ";
			Query query = getEntityManager().createNativeQuery(queryString,TbEnterprise.class);
			query.setParameter("userTabId", userTabId);
			List<TbEnterprise> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
	
	@SuppressWarnings("unchecked")
	public TbResUserEnterprise checkIsExist(Integer userTabId,Integer enterpriseId) {
		try {
			String queryString = "from TbResUserEnterprise where delFlag =0  "
					+ "  and userTabId = :userTabId and enterpriseId = :enterpriseId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userTabId", userTabId);
			query.setParameter("enterpriseId", enterpriseId);
			List<TbResUserEnterprise> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public int delByEnterpriseId(Integer enterpriseId) {
		try {
			String queryString = " UPDATE tb_res_user_enterprise SET del_flag=1 WHERE enterprise_id=:enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("enterpriseId", enterpriseId);
			int result =query.executeUpdate();

			return result;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}

	public Integer delResUserEnterpriseId(Integer userTabId){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" UPDATE tb_res_user_enterprise set del_flag=1 WHERE user_tab_id=:userTabId ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("userTabId",userTabId);
			Integer res = query.executeUpdate();
			return res;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
