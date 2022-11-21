package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbRotationToolDao extends BaseZDao {

	public TbRotationToolDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public List<Map<String, Object>> findByAll(Integer rotationId,Integer enterpriseId) {
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" select  ");
		sb.append(" tool.id, ");
		sb.append(" tool.name, ");
		sb.append(" tool.model, ");
		sb.append(" tool.specification, ");
		sb.append(" tool.unit,");
		sb.append(" tool.price, ");
		sb.append(" tool.number, ");
		sb.append(" tool.describe_ 'describe', ");
		sb.append(" tool.type ");
		sb.append(" ,( SELECT file_url ");
		sb.append(" FROM tb_file f INNER JOIN tb_res_tool_catalog_file rfg ");
		sb.append(" ON f.id = rfg.file_id  ");
		sb.append(" WHERE rfg.tool_catalog_id = tool.id ");
		sb.append(" AND f.file_type = 1  LIMIT 1 ) goodsPic,tool.production_units productionUnits,tool.uniform_price uniformPrice ");
		sb.append(" from tb_rotation_tool ttt inner join tb_tool_catalog tool on tool.id=ttt.tool_zero_id where ttt.del_flag=0 ");
		if(rotationId!=null) {
			sb.append(" and ttt.rotation_id=:rotationId  ");
		}
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
	public void delByRotationId(Integer rotationId) {
		try {
			String queryString = 
					" DELETE FROM  tb_rotation_tool  "
					+ "where rotation_id = :rotationId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("rotationId",rotationId);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
