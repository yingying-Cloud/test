package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.mysql.cj.util.StringUtils;

public class TbRoleDao extends BaseZDao {

	public TbRoleDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMenuListByRole(Integer roleId,Integer parentId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT m.id menuId,m.menu_name menuName,m.menu_url menuUrl,m.index_ menuIndex ");
			sb.append("FROM tb_res_menu_role rmr INNER JOIN tb_menu m ON rmr.menu_id = m.id ");
			sb.append("WHERE rmr.del_flag = 0 AND m.del_flag = 0 ");
			if(parentId != null)
				sb.append(" and m.parent_id = :parentId ");
			else
				sb.append(" and m.parent_id is null ");
			sb.append("AND rmr.role_id = :roleId ");
			sb.append("order BY m.index_ DESC ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(parentId != null)
				query.setParameter("parentId", parentId);
			query.setParameter("roleId", roleId);
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
	
	public List<Map<String, Object>> getRoleListById(List<Integer> id, String roleName, Integer nowPage, Integer pageCount){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT id Id, role_name roleName, date_format(input_time,'%Y-%m-%d %T') inputTime ");
			sb.append("FROM tb_role tr ");
			sb.append("WHERE tr.del_flag = 0 ");
			if(id != null)
				sb.append(" and tr.id in :id ");
			if(!StringUtils.isNullOrEmpty(roleName))
				sb.append(" and tr.role_name LIKE :roleName ");
			sb.append("order BY tr.id ASC ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(id != null)
				query.setParameter("id", id);
			if(!StringUtils.isNullOrEmpty(roleName))
				query.setParameter("roleName", "%"+roleName+"%");
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
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
	
	public Integer getRoleLIstCountById(List<Integer> id, String roleName){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(id) ");
			sb.append("FROM tb_role tr ");
			sb.append("WHERE tr.del_flag = 0 ");
			if(id != null)
				sb.append(" and tr.id in :id ");
			if(!StringUtils.isNullOrEmpty(roleName))
				sb.append(" and tr.role_name LIKE :roleName ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(id != null)
				query.setParameter("id", id);
			if(!StringUtils.isNullOrEmpty(roleName))
				query.setParameter("roleName", "%"+roleName+"%");
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
