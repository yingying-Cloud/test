package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbRotationEnterpriseDao extends BaseZDao{

	public TbRotationEnterpriseDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public List<Map<String, Object>> findByAll(Integer rotationId,Integer enterpriseId) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ttt.id rotationEnterpriseId,rotation_id rotationId,te.name enterpriseName,ttt.enterprise_id enterpriseId  ");
		sb.append(" from tb_rotation_enterprise  ttt inner join tb_enterprise te on te.id=ttt.enterprise_id where ttt.del_flag=0 ");
		if(rotationId!=null) {
			sb.append(" and ttt.rotation_id=:rotationId  ");
		}
		if(enterpriseId!=null) {
			sb.append(" and ttt.enterprise_id=:enterpriseId ");
		}
		Query query = getEntityManager().createNativeQuery(sb.toString());
		if(rotationId!=null) {
			query.setParameter("rotationId", rotationId);
		}
		if(enterpriseId!=null) {
			query.setParameter("enterpriseId", enterpriseId);
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
	public void delByRotationId(Integer rotationId) {
		try {
			String queryString = 
					" DELETE FROM  tb_rotation_enterprise  "
					+ "where rotation_id = :rotationId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("rotationId",rotationId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
