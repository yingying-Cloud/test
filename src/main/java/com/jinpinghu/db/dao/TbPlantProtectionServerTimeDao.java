package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbPlantProtectionServerTime;

public class TbPlantProtectionServerTimeDao extends BaseZDao {

	public TbPlantProtectionServerTimeDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbPlantProtectionServerTime findById(Integer id) {
		try {
			String queryString = " from TbPlantProtectionServerTime where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbPlantProtectionServerTime> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findPlantProtectionServerTimeList(Integer protectionId,Integer nowPage,Integer pageCount) {
		try {
			String queryString = " SELECT id,date_format(server_time,'%Y-%m-%d') time,protection_id protectionId ";
			
			queryString += " FROM tb_plant_protection_server_time  ";
			queryString += " where 1=1 ";
			if(protectionId!=null)
				queryString += " and protection_id = :protectionId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(protectionId!=null)
				query.setParameter("protectionId", protectionId);
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
	public Integer findPlantProtectionServerTimeListCount(Integer protectionId) {
		try {
			String queryString = " SELECT count(*) ";
			queryString += " FROM tb_plant_protection_server_time  ";
			queryString += " where 1=1 ";
			if(protectionId!=null)
				queryString += " and te.protection_id = :protectionId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(protectionId!=null)
				query.setParameter("protectionId", protectionId);
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	public void DelServerTimeByPlantProtectionId(Integer id) {
		try {
			String queryString = "delete from  tb_plant_protection_server_time WHERE protection_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
