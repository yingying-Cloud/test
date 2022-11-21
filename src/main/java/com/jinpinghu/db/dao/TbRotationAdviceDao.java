package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbRotationAdviceDao extends BaseZDao{

	public TbRotationAdviceDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public List<Map<String, Object>> findByAll(Integer rotationId,Integer enterpriseId) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,title,content,date_format(advice_time,'%Y-%m-%d') adviceTime  ");
		sb.append(" from tb_rotation_advice	 ttt  where del_flag=0 ");
		if(rotationId!=null) {
			sb.append(" and rotation_id=:rotationId  ");
		}
		sb.append(" order by advice_time desc,id desc   ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(rotationId!=null) {
			query.setParameter("rotationId", rotationId);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List <Map<String,Object>> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list;
		}
		return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Map<String, Object> findById(Integer id) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,title,content,date_format(advice_time,'%Y-%m-%d') adviceTime  ");
		sb.append(" from tb_rotation_advice	 ttt  where del_flag=0 ");
		if(id!=null) {
			sb.append(" and id=:id  ");
		}
		sb.append(" order by advice_time desc,id desc   ");
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(id!=null) {
			query.setParameter("id", id);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List <Map<String,Object>> list = query.getResultList();
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void delByRotationId(Integer rotationId) {
		try {
			String queryString = 
					" DELETE FROM  tb_rotation_advice  "
					+ "where rotation_id = :rotationId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("rotationId",rotationId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
