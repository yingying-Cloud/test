package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbLogisticsDao extends BaseZDao {

	public TbLogisticsDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public Map<String, Object> findMapById(Integer id) {
		try {
			String queryString = "SELECT tal.id," + 
					" name,content,mobile,address,link_people linkPeople,type  " + 
					" FROM `tb_logistics` tal where tal.id=:id  ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> getListMap(Integer nowPage,Integer pageCount) {
		try {
			String queryString = "SELECT tal.id," + 
					" name,content,mobile,address,"
					+" ( SELECT file_url "
					+" FROM tb_file f INNER JOIN tb_res_logistics_file rfg "
					+" ON f.id = rfg.file_id  "
					+" WHERE rfg.logistics_id = tal.id "
					+" AND f.file_type = 1  LIMIT 1 ) fileUrl,link_people linkPeople,type "
					+" FROM `tb_logistics` tal where 1=1 ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer getListMapCount() {
		try {
			String queryString = "SELECT count(*) " + 
					" FROM `tb_logistics` tal where 1=1 ";
			Query query = getEntityManager().createNativeQuery(queryString);
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
