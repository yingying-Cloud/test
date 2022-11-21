package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.aliyuncs.utils.StringUtils;

public class TbTopFileDao extends BaseZDao{

	public TbTopFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public List<Map<String, Object>> getListByType(String type) {
		try {
			String queryString = "select id,name,type,url from tb_top_file where 0 = 0 ";
			if(!StringUtils.isEmpty(type)){
				queryString+="AND type = :type ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(type)){
				query.setParameter("type",type);
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
