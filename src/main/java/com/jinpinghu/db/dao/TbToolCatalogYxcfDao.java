package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbToolCatalogYxcf;

public class TbToolCatalogYxcfDao  extends BaseZDao {

	public TbToolCatalogYxcfDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public void DelYxcfByToolId(Integer id) {
		try {
			String queryString = "delete from  tb_tool_catalog_yxcf WHERE tool_catalog_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbToolCatalogYxcf> findByToolCatalogId(Integer toolCatalogId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbToolCatalogYxcf where toolCatalogId =:toolCatalogId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolCatalogId", toolCatalogId);
			List<TbToolCatalogYxcf> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> findMapByToolCatalogId(Integer toolCatalogId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select id,effective_ingredients_name effectiveIngredientsName,effective_ingredients_value effectiveIngredientsValue,unit from tb_tool_catalog_yxcf where tool_catalog_id =:toolCatalogId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("toolCatalogId", toolCatalogId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public String findStringByToolCatalogId(Integer toolCatalogId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select ifnull(group_concat(concat(effective_ingredients_name,'(',effective_ingredients_value,unit,')')),'') from tb_tool_catalog_yxcf where tool_catalog_id =:toolCatalogId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("toolCatalogId", toolCatalogId);
			List <String> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
}
