package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;

public class TbInspectionTypeDao extends BaseZDao{

	public TbInspectionTypeDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public TbInspectionType findById(Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select t from TbInspectionType as t , TbInspectionItem as i  where t.id = i.inspectionType and i.id = :id   ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("id", id);
			List<TbInspectionType> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getTypeListCount(String name, Integer point) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(0) "
					+ "from tb_inspection_type "
					+ "where del_flag = 0 ");
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" and name LIKE :name ");
			}
			if(point != null){
				sb.append(" and point = :point ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(point != null){
				query.setParameter("point", point);
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
	
	
	public List<Map<String, Object>> getTypeList(String name, Integer point, Integer nowPage, Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id, name, point  from tb_inspection_type where del_flag = 0 ");

			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" and name LIKE :name ");
			}
			if(point != null){
				sb.append(" and point = :point ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(point != null){
				query.setParameter("point", point);
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
	
	public Map<String, Object> getTypeInfo(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id, name,point  from tb_inspection_type where id = :id ");

			
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
	
	public TbInspectionType getTypeById(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from TbInspectionType where id = :id ");
			Query query = getEntityManager().createQuery(sb.toString());
			query.setParameter("id", id);
			List<TbInspectionType> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void delType(List<String> ids) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update tb_inspection_type set del_flag = 1 where id in (:ids) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("ids", ids);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
