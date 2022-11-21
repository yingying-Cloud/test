package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbType;

public class TbTypeDao extends BaseZDao{

	public TbTypeDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public TbType findById(Integer id) {
		try {
			String queryString = "from TbType where 0 = 0 ";
			if(id!=null){
				queryString+="AND id = :id ";
			}
			Query query = getEntityManager().createQuery(queryString);
			if(id!=null){
				query.setParameter("id",id);
			}
			List<TbType> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbType> getListByType(Integer type) {
		try {
			String queryString = "from TbType where 0 = 0 ";
			if(type!=null){
				queryString+="AND type = :type ";
			}
			Query query = getEntityManager().createQuery(queryString);
			if(type!=null){
				query.setParameter("type",type);
			}
			List<TbType> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getListCountByType(Integer type) {
		try {
			String queryString =
					 " SELECT COUNT(1) "
					+ "FROM tb_type "
					+ "WHERE 0 = 0 ";
			if(type!=null){
				queryString+="AND type = :type ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(type!=null){
				query.setParameter("type",type);
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				String count = list.get(0).toString();
				return Integer.valueOf(count);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getListByTypeId(String id,String goodsType) {
		try {
			String queryString = "select id,name groupName from tb_type where 0 = 0 ";
			if(!StringUtils.isEmpty(goodsType)){
				queryString+="AND type = :goodsType ";
			}
			if(!StringUtils.isEmpty(id)){
				queryString+="AND id = :id ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(goodsType)){
				query.setParameter("goodsType",goodsType);
			}
			if(!StringUtils.isEmpty(id)){
				query.setParameter("id",id);
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
	
	public List<Map<String, Object>> getExpertTypeList(Integer type) {
		try {
			String queryString = "select tt.id,name,cost from tb_type tt left join tb_cost tc on tc.type=tt.id where 0 = 0 ";
			if(type!=null){
				queryString+="AND tt.type = :type ";
			}
			queryString += " order by tt.id asc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(type!=null){
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
