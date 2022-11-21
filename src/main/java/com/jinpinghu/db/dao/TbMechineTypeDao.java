package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbMechineTypeDao extends BaseZDao{

	public TbMechineTypeDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Map<String, Object>> getListByParentId(Integer parentId) {
		try {
			String queryString = "select id,name from tb_mechine_type where 0 = 0 ";
			if(parentId!=null){
				queryString+="AND parent_id = :parentId ";
			}else {
				queryString+="AND (parent_id is null or parent_id = '') ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(parentId!=null){
				query.setParameter("parentId",parentId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
