package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.bean.TbService;



public class TbServiceDao extends BaseZDao{

	public TbServiceDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	
	public TbService findId(Integer id) {
		try {
			TbService instance = getEntityManager().find(TbService.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String,Object>> findServiceListByType(Integer type) {
		try {
			String queryString =
					   "SELECT "
					 + " id, "
					 + " name, "
					 + " address, "
					 + " x, "
					 + " y, "
					 + " link_people linkPeople, " 
					 + " link_mobile linkMobile, "
					 + " type "
					 + "FROM tb_service "
					 + "WHERE del_flag = 0 ";
			if(type!=null)
				queryString+="AND type = :type";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(type!=null)
				query.setParameter("type", type);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Integer findServiceListCountByType(Integer type) {
		try {
			String queryString =
					   "SELECT "
					 + " COUNT(1) "
					 + "FROM tb_service "
					 + "WHERE del_flag = 0 ";
			if(type!=null)
				queryString+="AND type = :type";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(type!=null)
				query.setParameter("type", type);
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				String count=list.get(0).toString();
				return Integer.valueOf(count);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Map<String,Object> findById(Integer id) {
		try {
			String queryString = 
					   "SELECT "
					 + " name, "
					 + " address, "
					 + " x, "
					 + " y, "
					 + " link_people linkPeople, "
					 + " link_mobile linkMobile, "
					 + " type "
					 + "FROM tb_service "
					 + "WHERE del_flag = 0 AND id= :id";
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
	
	public List<Map<String,Object>> getFileListByServiceId(Integer serviceId) {
		try {
			String queryString =
					   "SELECT "
					 + " file_name fileName, "
					 + " file_type fileType,"
					 + " file_url fileUrl "
					 + "FROM tb_res_service_file trsf INNER JOIN "
					 + " tb_file tf ON trsf.file_id=tf.id "
					 + "WHERE trsf.service_id = :serviceId ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("serviceId", serviceId);
			
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
}
