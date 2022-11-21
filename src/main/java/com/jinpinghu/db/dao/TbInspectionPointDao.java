package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;

public class TbInspectionPointDao extends BaseZDao{

	public TbInspectionPointDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public Integer getPointListCount(String name, String year) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(0) "
					+ "from tb_inspection_point tip LEFT JOIN tb_enterprise te ON te.id = tip.enterprise_id "
					+ "where tip.del_flag = 0 ");
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" and te.name LIKE :name ");
			}
			if(year != null){
				sb.append(" and year = :year ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(year != null){
				query.setParameter("year", year);
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String, Object>> getPointList(String name, String year, Integer nowPage, Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT tip.id id, te.NAME name, round(tip.point,0) point, inspection_id inspectionId,"
					+ "		date_format(ti.input_time,'%Y-%m-%d %T') inputTime, tii.`name` itemName, tit.`name` levelName "
					+ "FROM	tb_inspection_point tip"
					+ "		LEFT JOIN tb_inspection ti ON tip.inspection_id = ti.id"
					+ "		LEFT JOIN tb_enterprise te ON te.id = tip.enterprise_id"
					+ "		LEFT JOIN tb_inspection_type tit ON ti.inspection_type = tit.id"
					+ "		LEFT JOIN tb_inspection_item tii ON tii.id = ti.inspection_item_id "
					+ "WHERE tip.del_flag = 0 ");

			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" and te.name LIKE :name ");
			}
			if(year != null){
				sb.append(" and year = :year ");
			}
			sb.append(" ORDER BY ti.input_time DESC ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(year != null){
				query.setParameter("year", year);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
