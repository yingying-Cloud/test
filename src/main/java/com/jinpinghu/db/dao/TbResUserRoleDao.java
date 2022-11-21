package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResUserRole;
import com.jinpinghu.db.bean.TbRole;

public class TbResUserRoleDao extends BaseZDao{

	public TbResUserRoleDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public TbRole findByUserTabId(Integer userTabId) {
		try {
			String queryString = " SELECT tr.* FROM `tb_res_user_role` trur left join tb_role tr on trur.role_id = tr.id ";
			queryString       += " where trur.del_flag = 0 and tr.del_flag = 0 and trur.user_tab_id = :userTabId ";
			Query query = getEntityManager().createNativeQuery(queryString,TbRole.class);
			query.setParameter("userTabId", userTabId);
			List<TbRole> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}

	public TbResUserRole checkIsExist(Integer userTabId,Integer roleId) {
		try {
			String queryString = "from TbResUserRole where delFlag =0  "
					+ "  and userTabId = :userTabId and roleId = :roleId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userTabId", userTabId);
			query.setParameter("roleId", roleId);
			List<TbResUserRole> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public Object findRoleIdByUserId(String userId) {
		try {
			String queryString = 
					"  SELECT "
					+ " role_id "
					+ "FROM "
					+ " tb_res_user_role trur "
					+ " LEFT JOIN"
					+ " tb_user tu "
					+ "	ON trur.user_tab_id = tu.id "
					+ "WHERE "
					+ " trur.del_flag = 0 "
					+ "	AND tu.del_flag = 0 "
					+ " AND tu.user_id = :userId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("userId", userId);
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}

	public Integer delResUserRoleId(Integer userTabId){
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" delete from tb_res_user_role WHERE user_tab_id=:userTabId ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("userTabId",userTabId);
			Integer res = query.executeUpdate();
			return res;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
