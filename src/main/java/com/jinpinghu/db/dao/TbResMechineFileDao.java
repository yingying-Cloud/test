package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TbResMechineFileDao extends BaseZDao{

	public TbResMechineFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public void DelResByMechineId(Integer id) {
		try {
			String queryString =
					 " UPDATE tb_res_mechine_file mf Right JOIN tb_mechine tm ON tm.id=mf.mechine_id "
					+ "SET mf.del_flag=1, tm.del_flag=1 "
					+ "WHERE tm.id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void DelFileByMechineId(Integer id) {
		try {
			String queryString = "UPDATE tb_res_mechine_file mf SET mf.del_flag=1 WHERE mechine_id = :id ";
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
					 + "mf.id,"
					 + "file_name,"
					 + "file_size,"
					 + "file_type,"
					 + "file_url "
					+ "FROM "
					+ " tb_res_mechine_file  mf "
					+ "	LEFT JOIN "
					+ "	tb_file tf "
					+ "	ON mf.file_id=tf.id "
					+ "WHERE mechine_id = :id and mf.del_flag = 0 "
					+ "ORDER BY mf.id DESC";
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
