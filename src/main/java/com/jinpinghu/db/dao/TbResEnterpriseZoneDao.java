package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.aliyuncs.ecs.transform.v20140526.RebootInstanceResponseUnmarshaller;
import com.jinpinghu.db.bean.TbResEnterpriseZone;

public class TbResEnterpriseZoneDao extends BaseZDao {

	public TbResEnterpriseZoneDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Map<String, Object>> getEnterpriseZoneList(Integer enterpriseId,String type,String name ,Integer nowPage,Integer pageCount){
		try {
			String queryString = " SELECT tz.id,tz.code,tz.name,ifnull(tz.describe_,'') 'describe',ifnull(tz.area,'') area ";
			queryString += " FROM tb_zone tz where tz.del_flag = 0 ";
			if("2".equals(type)) {
				queryString += " and tz.id not in (select zone_id from tb_res_enterprise_zone trez where trez.enterprise_id = :enterpriseId and trez.del_flag = 0) ";
			}else if("1".equals(type)) {
				queryString += " and tz.id in (select zone_id from tb_res_enterprise_zone trez where trez.enterprise_id = :enterpriseId and trez.del_flag = 0) ";
			}
			if(!StringUtils.isEmpty(name))
				queryString += " and tz.name like :name ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if("2".equals(type)) {
				query.setParameter("enterpriseId", enterpriseId);
			}else if("1".equals(type)) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if(nowPage != null && pageCount != null)
				query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Integer getEnterpriseZoneListCount(Integer enterpriseId,String type,String name){
		try {
			String queryString = " SELECT count(distinct tz.id) ";
			queryString += " FROM tb_zone tz where tz.del_flag = 0 ";
			if("2".equals(type)) {
				queryString += " and tz.id not in (select zone_id from tb_res_enterprise_zone trez where trez.enterprise_id = :enterpriseId and trez.del_flag = 0) ";
			}else if("1".equals(type)) {
				queryString += " and tz.id in (select zone_id from tb_res_enterprise_zone trez where trez.enterprise_id = :enterpriseId and trez.del_flag = 0) ";
			}
			if(!StringUtils.isEmpty(name))
				queryString += " and tz.name like :name ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if("2".equals(type)) {
				query.setParameter("enterpriseId", enterpriseId);
			}else if("1".equals(type)) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public TbResEnterpriseZone findByZoneIdEnterpriseId(Integer enterpriseId,Integer zoneId){
		try {
			String queryString = " from TbResEnterpriseZone where delFlag = 0 and enterpriseId = :enterpriseId and zoneId in (:zoneId) ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("enterpriseId", enterpriseId);
			query.setParameter("zoneId", zoneId);
			List<TbResEnterpriseZone> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<TbResEnterpriseZone> findByZoneIdEnterpriseId(Integer enterpriseId,List<Integer> zoneIdList){
		try {
			String queryString = " from TbResEnterpriseZone where delFlag = 0 and enterpriseId = :enterpriseId and zoneId in (:zoneIdList) ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("enterpriseId", enterpriseId);
			query.setParameter("zoneIdList", zoneIdList);
			List<TbResEnterpriseZone> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}


}
