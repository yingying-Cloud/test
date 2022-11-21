package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResEnterpriseUserinfoFile;

public class TbResEnterpriseUserinfoFileDao extends BaseZDao{

	public TbResEnterpriseUserinfoFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbResEnterpriseUserinfoFile findById(Integer id) {
		try {
			String queryString = "from TbResEnterpriseUserinfoFile where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbResEnterpriseUserinfoFile> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void DelResByEnterpriseUserinfoId(Integer id) {
		try {
			String queryString = "UPDATE Tb_res_Enterprise_Userinfo_file SET del_flag=1 WHERE enterprise_userinfo_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> findFileById(Integer id) {
		try {
			String queryString = 
					 " SELECT "
					 + "uf.id,"
					 + "file_name,"
					 + "file_size,"
					 + "file_type,"
					 + "file_url "
					+ "FROM "
					+ " Tb_res_Enterprise_Userinfo_file  uf "
					+ "	LEFT JOIN "
					+ "	tb_file tf "
					+ "	ON uf.file_id=tf.id "
					+ "where enterprise_userinfo_id = :id and uf.del_flag = 0";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
