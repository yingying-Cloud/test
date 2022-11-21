package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.bean.TbResUserExpert;
import com.jinpinghu.db.bean.TbUser;

public class TbResUserExpertDao extends BaseZDao {

	public TbResUserExpertDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	
	public TbResUserExpert checkIsExist(Integer userTabId,Integer expertId) {
		try {
			String queryString = "from TbResUserExpert where delFlag =0  "
					+ "  and userTabId = :userTabId and expertId = :expertId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userTabId", userTabId);
			query.setParameter("expertId", expertId);
			List<TbResUserExpert> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbExpert findByUserTabId(Integer userTabId) {
		try {
			String queryString = " from TbExpert te  where te.delFlag =0  "
					+ " and te.id in (select expertId from TbResUserExpert where  userTabId = :userTabId ) ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("userTabId", userTabId);
			List<TbExpert> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TbUser getUserByExpertId(Integer expertId) {
		try {
			String queryString =  "SELECT tu.* "
					+ "FROM Tb_User tu LEFT JOIN tb_res_user_expert rue ON tu.id=rue.user_tab_id "
					+ "WHERE tu.del_flag=0 AND rue.del_flag=0 "
					+ " AND expert_Id = :expertId ";
			Query query = getEntityManager().createNativeQuery(queryString,TbUser.class);
			query.setParameter("expertId", expertId);
			List<TbUser> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public int delByExpertId(Integer expertId) {
		try {
			String queryString = " UPDATE tb_res_user_expert SET del_flag=1 WHERE expert_Id=:expertId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("expertId", expertId);
			int result =query.executeUpdate();
			
			return result;
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
}
