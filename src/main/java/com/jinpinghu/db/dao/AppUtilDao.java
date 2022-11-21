package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.AppUtil;

@SuppressWarnings("unchecked")
public class AppUtilDao extends BaseZDao{
	
	public AppUtilDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	
	public AppUtil findById(Integer Id){
		try {
			AppUtil instance = getEntityManager().find(AppUtil.class, Id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}	
	
	public AppUtil findByKey(String keyName){
		try {
			final String queryString = "from AppUtil where key like '"+keyName+"' and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			List <AppUtil> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public AppUtil findByValue(String valueName){
		try {
			final String queryString = "from AppUtil where value like '"+valueName+"' and delFlag = 0 and key <> 'current_version' ";
			Query query = getEntityManager().createQuery(queryString);
			List <AppUtil> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	/**
	 * 获取全部
	 * @return
	 */
	public List<AppUtil> findAll(){
		try {
			final String queryString = "from AppUtil where delFlag = 0";
			Query query = getEntityManager().createQuery(queryString);
			List <AppUtil> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> getMapList(){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select id, `key` ,`value`, note, url "
					+ " from app_util  "
					+ "     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}