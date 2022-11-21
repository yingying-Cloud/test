package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbArableLand;
import com.mysql.cj.util.StringUtils;

public class TbArableLandDao extends BaseZDao {

	public TbArableLandDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public TbArableLand findById(String id) {
		try {
			String queryString = "from TbArableLand where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbArableLand> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> findMapById(String id) {
		try {
			String queryString = "SELECT tal.id," + 
					" village_group villageGroup," + 
					" user_name userName," + 
					" id_card idCard," + 
					" area," + 
					" rice_area riceArea," + 
					" wheat_area wheatArea," + 
					" vegetables_area vegetablesArea," + 
					" fruits_area fruitsArea," + 
					" other_area otherArea," + 
					" remark," + 
					" town,ta.name dsnm" + 
					" FROM `tb_arable_land` tal  left join tb_area ta on ta.id=tal.town where tal.id=:id or id_card in ( select legal_person_idcard from tb_link_order_info where id=:id	 ) ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
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
	
	public Map<String, Object> findMapInfoById(String id) {
		try {
			String queryString = "SELECT tal.id," + 
					" village_group villageGroup," + 
					" user_name userName," + 
					" id_card idCard," + 
					" area," + 
					" rice_area riceArea," + 
					" wheat_area wheatArea," + 
					" vegetables_area vegetablesArea," + 
					" fruits_area fruitsArea," + 
					" other_area otherArea," + 
					" remark," + 
					" town,ta.name dsnm" + 
					" FROM `tb_arable_land` tal  left join tb_area ta on ta.id=tal.town where tal.id=:id  ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
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
	
	
	public List<Map<String, Object>> getListMap(String idCard,String userName,String dscd,Integer nowPage,Integer pageCount) {
		try {
			String queryString = "SELECT tal.id," + 
					" village_group villageGroup," + 
					" user_name userName," + 
					" id_card idCard," + 
					" area," + 
					" rice_area riceArea," + 
					" wheat_area wheatArea," + 
					" vegetables_area vegetablesArea," + 
					" fruits_area fruitsArea," + 
					" other_area otherArea," + 
					" remark," + 
					" town,ta.name dsnm " + 
					" FROM `tb_arable_land` tal left join tb_area ta on ta.id=tal.town where del_flag=0 ";
			if(!StringUtils.isNullOrEmpty(idCard)) {
				queryString += " and id_card = :idCard ";
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				queryString += " and user_name like :userName ";
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				queryString += " and town = :dscd ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(idCard)) {
				query.setParameter("idCard", idCard);
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				query.setParameter("userName", "%"+userName+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
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
	public Integer getListMapCount(String idCard,String userName,String dscd) {
		try {
			String queryString = "SELECT count(id) " + 
					" FROM `tb_arable_land` where del_flag=0 ";
			if(!StringUtils.isNullOrEmpty(idCard)) {
				queryString += " and id_card = :idCard ";
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				queryString += " and user_name like :userName ";
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				queryString += " and town = :dscd ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isNullOrEmpty(idCard)) {
				query.setParameter("idCard", idCard);
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				query.setParameter("userName", "%"+userName+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
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
	
}
