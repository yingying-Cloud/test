package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResWorkCheckFile;

public class TbResWorkCheckFileDao extends BaseZDao{

	public TbResWorkCheckFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbResWorkCheckFile findById(Integer id) {
		try {
			String queryString = "from TbResWorkCheckFile where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbResWorkCheckFile> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void DelResByCheckId(Integer id) {
		try {
			String queryString = "UPDATE tb_res_work_check_file SET del_flag=1 WHERE work_check_id = :id ";
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
					 + "cf.id,"
					 + "file_name,"
					 + "file_size,"
					 + "file_type,"
					 + "file_url "
					+ "FROM "
					+ " tb_res_work_check_file  cf "
					+ "	LEFT JOIN "
					+ "	tb_file tf "
					+ "	ON cf.file_id=tf.id "
					+ "where work_check_id = :id and cf.del_flag = 0";
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
