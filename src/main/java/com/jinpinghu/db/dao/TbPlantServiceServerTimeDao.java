package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbPlantServiceServerTime;

public class TbPlantServiceServerTimeDao extends BaseZDao {

	public TbPlantServiceServerTimeDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbPlantServiceServerTime findById(Integer id) {
		try {
			String queryString = " from TbPlantServiceServerTime where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbPlantServiceServerTime> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findPlantServiceServerTimeList(Integer serviceId,Integer nowPage,Integer pageCount) {
		try {
			String queryString = " SELECT id,date_format(server_time,'%Y-%m-%d') time,service_id serviceId ";
			
			queryString += " FROM tb_plant_service_server_time  ";
			queryString += " where 1=1 ";
			if(serviceId!=null)
				queryString += " and service_id = :serviceId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(serviceId!=null)
				query.setParameter("serviceId", serviceId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if(pageCount != null && nowPage != null) 
				query.setMaxResults(pageCount).setFirstResult((nowPage -1)*pageCount);
			List<Map<String, Object>> list = query.getResultList();
			return list;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public Integer findPlantServiceServerTimeListCount(Integer serviceId) {
		try {
			String queryString = " SELECT count(*) ";
			queryString += " FROM tb_plant_service_server_time  ";
			queryString += " where 1=1 ";
			if(serviceId!=null)
				queryString += " and te.service_id = :serviceId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(serviceId!=null)
				query.setParameter("serviceId", serviceId);
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public void DelServerTimeByPlantServiceId(Integer id) {
		try {
			String queryString = "delete from  tb_plant_service_server_time WHERE service_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
