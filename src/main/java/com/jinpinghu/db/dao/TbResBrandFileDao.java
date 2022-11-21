package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TbResBrandFileDao extends BaseZDao {

	public TbResBrandFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public void DelResByBrandId(Integer id) {
		try {
			String queryString = "UPDATE tb_res_brand_file bf Right JOIN tb_brand tb ON tb.id=bf.brand_id SET bf.del_flag=1, tb.del_flag=1 WHERE tb.id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void DelFileByBrandId(Integer id) {
		try {
			String queryString = "UPDATE tb_res_brand_file bf SET bf.del_flag=1 WHERE brand_id = :id ";
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
					 + "bf.id,"
					 + "file_name,"
					 + "file_size,"
					 + "file_type,"
					 + "file_url "
					+ "FROM "
					+ " tb_res_brand_file  bf "
					+ "	LEFT JOIN "
					+ "	tb_file tf "
					+ "	ON bf.file_id=tf.id "
					+ "where brand_id = :id and bf.del_flag = 0 ";
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
	
	public void delBrandById(Integer id) {
		try {
			String queryString = 
					 " UPDATE "
					+ "	tb_brand tb LEFT JOIN tb_res_brand_file bf ON tb.id=bf.brand_id "
					+ "SET tb.del_flag=1 AND bf.del_flag=1 "
					+ "WHERE tb.id = :id";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
