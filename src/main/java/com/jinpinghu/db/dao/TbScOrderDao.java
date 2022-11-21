package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbScOrder;

public class TbScOrderDao extends BaseZDao {

	public TbScOrderDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbScOrder findById(Integer id) {
		try {
			String queryString = "from TbScOrder where id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbScOrder> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> listScOrder(String orderNumber,String productName,String startTime,String endTime,
			Integer supplyEnterpriseId,Integer buyEnterpriseId,String releaseType,Integer nowPage,Integer pageCount,String status){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tso.id id,date_format(tso.input_time,'%Y-%m-%d') inputTime, ");
		queryString.append(" date_format(tso.start_time,'%Y-%m-%d') startTime,tso.order_number orderNumber, ");
		queryString.append(" date_format(tso.end_time,'%Y-%m-%d') endTime,tso.product_name productName, ");
		queryString.append(" IFNULL(tso.buy_contact_person,'') contactPerson,tso.price price,tso.num num,tso.status ");
		queryString.append(" ,ifnull(tso.specification,'') specification,tso.is_evaluate isEvaluate,if(tso.release_type is null or tso.release_type = '',1,tso.release_type) releaseType  ");
		queryString.append(" FROM tb_sc_order tso where tso.del_flag = 0 ");
		if (!StringUtils.isEmpty(orderNumber)) {
			queryString.append(" and tso.order_number like :orderNumber ");
		}
		if (!StringUtils.isEmpty(productName)) {
			queryString.append(" and tso.product_name like :productName ");
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(tso.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(tso.input_time,'%Y-%m-%d') <= :endTime ");
		}
		if (supplyEnterpriseId != null) {
			queryString.append(" and supply_enterprise_id = :supplyEnterpriseId ");
		}
		if (buyEnterpriseId != null) {
			queryString.append(" and buy_enterprise_id = :buyEnterpriseId ");
		}
		if (!StringUtils.isEmpty(releaseType)) {
			queryString.append(" and release_type = :releaseType ");
		}
		if (!StringUtils.isEmpty(status)) {
			queryString.append(" and status = :status ");
		}
		queryString.append(" order by tso.input_time desc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (!StringUtils.isEmpty(orderNumber)) {
			query.setParameter("orderNumber", "%"+orderNumber+"%");
		}
		if (!StringUtils.isEmpty(productName)) {
			query.setParameter("productName", "%"+productName+"%");
		}
		if (!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if (supplyEnterpriseId != null) {
			query.setParameter("supplyEnterpriseId", supplyEnterpriseId);
		}
		if (buyEnterpriseId != null) {
			query.setParameter("buyEnterpriseId", buyEnterpriseId);
		}
		if (!StringUtils.isEmpty(releaseType)) {
			query.setParameter("releaseType", releaseType);
		}
		if (!StringUtils.isEmpty(status)) {
			query.setParameter("status", status);
		}
		if (nowPage != null && pageCount != null) {
			query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
		
	}
	
	public Integer listScOrderCount(String orderNumber,String productName,String startTime,String endTime,
			Integer supplyEnterpriseId,Integer buyEnterpriseId,String releaseType,String status){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT count(distinct tso.id) FROM tb_sc_order tso where tso.del_flag = 0 ");
		if (!StringUtils.isEmpty(orderNumber)) {
			queryString.append(" and tso.order_number like :orderNumber ");
		}
		if (!StringUtils.isEmpty(productName)) {
			queryString.append(" and tso.product_name like :productName ");
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(tso.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(tso.input_time,'%Y-%m-%d') <= :endTime ");
		}
		if (supplyEnterpriseId != null) {
			queryString.append(" and supply_enterprise_id = :supplyEnterpriseId ");
		}
		if (buyEnterpriseId != null) {
			queryString.append(" and buy_enterprise_id = :buyEnterpriseId ");
		}
		if (!StringUtils.isEmpty(releaseType)) {
			queryString.append(" and release_type = :releaseType ");
		}
		if (!StringUtils.isEmpty(status)) {
			queryString.append(" and status = :status ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (!StringUtils.isEmpty(orderNumber)) {
			query.setParameter("orderNumber", "%"+orderNumber+"%");
		}
		if (!StringUtils.isEmpty(productName)) {
			query.setParameter("productName", "%"+productName+"%");
		}
		if (!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if (supplyEnterpriseId != null) {
			query.setParameter("supplyEnterpriseId", supplyEnterpriseId);
		}
		if (buyEnterpriseId != null) {
			query.setParameter("buyEnterpriseId", buyEnterpriseId);
		}
		if (!StringUtils.isEmpty(releaseType)) {
			query.setParameter("releaseType", releaseType);
		}
		if (!StringUtils.isEmpty(status)) {
			query.setParameter("status", status);
		}
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
		
	}
	
	public Map<String, Object> detailScOrder(Integer id){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tso.id id,date_format(tso.input_time,'%Y-%m-%d') inputTime, ");
		queryString.append(" date_format(tso.start_time,'%Y-%m-%d') startTime,tso.order_number orderNumber, ");
		queryString.append(" date_format(tso.end_time,'%Y-%m-%d') endTime,tso.product_name productName, ");
		queryString.append(" IFNULL(tso.buy_contact_person,'') contactPerson,tso.price price,tso.num num, ");
		queryString.append(" IFNULL(tso.buy_contact_phone,'') contactPhone,IFNULL(tso.remark,'') remark,");
		queryString.append(" IFNULL(tso.buy_contact_address,'') contactAddress,tso.status, ");
		queryString.append(" supplyEnterprise.name supplyEnterpriseName,buyEnterprise.name buyEnterpriseName, ");
		queryString.append(" ifnull(tso.supply_contact_person,'') supplyContactPerson,ifnull(tso.supply_contact_address,'') supplyContactAddress, ");
		queryString.append(" ifnull(tso.supply_contact_phone,'') supplyContactPhone,ifnull(tso.specification,'') specification	 ");
		queryString.append(" FROM tb_sc_order tso ");
		queryString.append(" left join tb_enterprise supplyEnterprise on tso.supply_enterprise_id = supplyEnterprise.id ");
		queryString.append(" left join tb_enterprise buyEnterprise on tso.buy_enterprise_id = buyEnterprise.id ");
		queryString.append(" where tso.del_flag = 0 and tso.id = :id ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("id", id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Map<String, Object> statisticScOrderCount(Integer enterpriseId){
		StringBuilder queryString = new StringBuilder();
		queryString.append("select ( SELECT count(*) supplyEnterpriseCount ");
		queryString.append(" FROM tb_sc_order tso where tso.del_flag = 0 ");
		if (enterpriseId != null) {
			queryString.append(" and supply_enterprise_id = :enterpriseId ");
		}
		queryString.append(" )supplyEnterpriseCount ");
		
		queryString.append(", ( SELECT count(*) buyEnterpriseCount  ");
		queryString.append(" FROM tb_sc_order tso where tso.del_flag = 0 ");
		if (enterpriseId != null) {
			queryString.append(" and buy_enterprise_id = :enterpriseId ");
		}
		queryString.append(" )buyEnterpriseCount ");
		
		queryString.append(", ( SELECT count(*) count ");
		queryString.append(" FROM tb_sc_order tso where tso.del_flag = 0 ");
		queryString.append(" )count ");
		
		
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (enterpriseId != null) {
			query.setParameter("enterpriseId", enterpriseId);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.get(0);
		
	}
	
	public List<Map<String, Object>> groupByStatus(Integer supplyEnterpriseId,Integer buyEnterpriseId){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tso.status,count(distinct tso.id) count,round(sum(tso.price*tso.num),2) sumPrice FROM tb_sc_order tso where tso.del_flag = 0 ");
		if (supplyEnterpriseId != null) {
			queryString.append(" and supply_enterprise_id = :supplyEnterpriseId ");
		}
		if (buyEnterpriseId != null) {
			queryString.append(" and buy_enterprise_id = :buyEnterpriseId ");
		}
		queryString.append(" order by tso.status asc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (supplyEnterpriseId != null) {
			query.setParameter("supplyEnterpriseId", supplyEnterpriseId);
		}
		if (buyEnterpriseId != null) {
			query.setParameter("buyEnterpriseId", buyEnterpriseId);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
		
	}
	
	
}
