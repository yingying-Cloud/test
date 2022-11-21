package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbAdvertisement;

public class TbAdvertisementDao extends BaseZDao {

	public TbAdvertisementDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbAdvertisement findById(Integer id){
		try {
			TbAdvertisement instance = getEntityManager().find(TbAdvertisement.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}	
	

	public List<Map<String, Object>> getAdvertisementList(String title,String startTime,String endTime,String type,String visible ,
			String status,Integer nowPage,Integer pageCount){
		try {
			String queryString = " SELECT id," + 
					" title," + 
					" type," + 
					" date_format(start_time,'%Y-%m-%d') startTime," + 
					" date_format(end_time,'%Y-%m-%d') endTime, " + 
					" visible" + 
					" from tb_advertisement tac where del_flag=0 ";
			if(!StringUtils.isEmpty(title)) {
				queryString += " and tac.title like :title ";
			}
			if(!StringUtils.isEmpty(startTime)) {
				queryString += " and date_format(tac.start_Time,'%Y-%m-%d') >= :startTime ";
			}
			if(!StringUtils.isEmpty(endTime)) {
				queryString += " and date_format(tac.start_Time,'%Y-%m-%d') <= :endTime ";
			}
			if(!StringUtils.isEmpty(type)) {
				queryString += " and tac.type = :type ";
			}
			if(!StringUtils.isEmpty(visible)) {
				queryString += " and tac.visible = :visible ";
			}
			if(!StringUtils.isEmpty(status)) {
				if(status.equals("0")) {
					queryString += " and tac.start_Time > now() ";
				}else if(status.equals("1")) {
					queryString += " and tac.start_time <= now() ";
					queryString += " and tac.end_time >= now() ";
				}else if(status.equals("2")) {
					queryString += " and tac.end_time < now() ";
				}
			}
			queryString += " order by start_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(title)) {
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtils.isEmpty(visible)) {
				query.setParameter("visible", visible);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
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
	public Integer getAdvertisementListCount(String title,String startTime,String endTime,String type,String visible ,
			String status){
		try {
			String queryString = " SELECT count(id) " + 
					"from tb_advertisement tac where del_flag=0 ";
			if(!StringUtils.isEmpty(title)) {
				queryString += " and tac.title like :title ";
			}
			if(!StringUtils.isEmpty(startTime)) {
				queryString += " and date_format(tac.start_Time,'%Y-%m-%d') >= :startTime ";
			}
			if(!StringUtils.isEmpty(endTime)) {
				queryString += " and date_format(tac.start_Time,'%Y-%m-%d') <= :endTime ";
			}
			if(!StringUtils.isEmpty(type)) {
				queryString += " and tac.type = :type ";
			}
			if(!StringUtils.isEmpty(visible)) {
				queryString += " and tac.visible = :visible ";
			}
			if(!StringUtils.isEmpty(status)) {
				if(status.equals("0")) {
					queryString += " and tac.start_Time > now() ";
				}else if(status.equals("1")) {
					queryString += " and tac.start_time <= now() ";
					queryString += " and tac.end_time >= now() ";
				}else if(status.equals("2")) {
					queryString += " and tac.end_time < now() ";
				}
			}
			queryString += " order by start_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(title)) {
				query.setParameter("title", "%"+title+"%");
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtils.isEmpty(visible)) {
				query.setParameter("visible", visible);
			}
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
	public Map<String, Object> getShowAdvertisement(Integer enterpriseId){
		try {
			String queryString = " SELECT id," + 
					" title," + 
					" type," + 
					" start_time startTime," + 
					" end_time endTime, " + 
					" visible" + 
					" from tb_advertisement tac where del_flag=0  and visible=1 "
					+ "and id in(select advertisement_id from tb_res_advertisement_enterprise where enterprise_id=:enterpriseId ) ";
					queryString += " and tac.start_time <= now() ";
					queryString += " and tac.end_time >= now() ";
			queryString += " order by start_time desc limit 1 ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("enterpriseId", enterpriseId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
}
