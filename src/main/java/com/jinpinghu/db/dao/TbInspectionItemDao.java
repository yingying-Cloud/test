package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;

public class TbInspectionItemDao extends BaseZDao{

	public TbInspectionItemDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public Integer getItemListCount(String name, Integer type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(0) "
					+ "from tb_inspection_item "
					+ "where del_flag = 0 ");
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" and name LIKE :name ");
			}
			if(type != null){
				sb.append(" and inspection_type = :type ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(type != null){
				query.setParameter("type", type);
			}
			List<Object> list = query.getResultList();
			if(list != null && list.size() > 0){
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String, Object>> getItemList(String name, Integer type, Integer nowPage, Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ii.id, ii.name name,inspection_type inspectionType,it.name typeName  "
					+ "from tb_inspection_item ii left join tb_inspection_type it on ii.inspection_type = it.id "
					+ "where ii.del_flag = 0 ");

			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" and ii.name LIKE :name ");
			}
			if(type != null){
				sb.append(" and inspection_type = :type ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
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
	
	public Map<String, Object> getItemInfo(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id, name,inspection_type inspectionType  from tb_inspection_item where id = :id ");

			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id", id);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbInspectionItem getItemById(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from TbInspectionItem where id = :id ");
			Query query = getEntityManager().createQuery(sb.toString());
			query.setParameter("id", id);
			List<TbInspectionItem> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void delItem(List<String> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update tb_inspection_item set del_flag = 1 where id in (:ids) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("ids", ids);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
