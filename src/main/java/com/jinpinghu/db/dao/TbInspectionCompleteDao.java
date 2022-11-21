package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;

public class TbInspectionCompleteDao extends BaseZDao{

	public TbInspectionCompleteDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, Object>> getCompleteList(Integer inspectionId, Integer type, Integer nowPage, Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select id, type, inspection_id inspectionId, `status`, remark, date_format(input_time, '%Y-%m-%d %H:%i') inputTime  "
					+ "from tb_inspection_complete "
					+ "where inspection_id = :inspectionId ");
			if(type != null){
				sb.append(" and type = :type");
			}
			sb.append(" order by inputTime desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("inspectionId", inspectionId);
			if(type != null){
				query.setParameter("type", type);
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
	
	
	public Integer getCompleteListCount(Integer inspectionId, Integer type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select count(0) "
					+ "from tb_inspection_complete "
					+ "where inspection_id = :inspectionId ");

			if(type != null){
				sb.append(" and type = :type");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("inspectionId", inspectionId);
			if(type != null){
				query.setParameter("type", type);
			}
			query.setParameter("inspectionId", inspectionId);
		
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0 && list.get(0) != null) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
