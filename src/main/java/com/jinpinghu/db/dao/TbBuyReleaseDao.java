package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbBuyRelease;

public class TbBuyReleaseDao extends BaseZDao {

	public TbBuyReleaseDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbBuyRelease findById(Integer id) {
		try {
			String queryString = "from TbBuyRelease where id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbBuyRelease> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> listBuyRelease(Integer enterpriseId,String productName,Integer type,String dscd,Integer status,
			Integer nowPage,Integer pageCount){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tbr.id,tbr.product_name productName,DATE_FORMAT(tbr.start_time,'%Y-%m-%d') startTime, ");
		queryString.append(" DATE_FORMAT(tbr.end_time,'%Y-%m-%d') endTime,tt.name type,tbr.num,tbr.price,tbr.status, ");
		queryString.append(" ifnull(tbr.contact_person,'') contactPerson,tbr.dscd dscd,ta.name dsnm,ifnull(tbr.specification,'') ");
		queryString.append(" specification FROM tb_buy_release tbr ");
		queryString.append(" left join tb_type tt on tt.id = tbr.type left join tb_area ta on tbr.dscd = ta.id where tbr.del_flag = 0 ");
		if (!StringUtils.isEmpty(dscd)) {
			queryString.append(" and tbr.dscd like :dscd ");
		}
		if (enterpriseId != null) {
			queryString.append(" and tbr.enterprise_id = :enterpriseId ");
		}
		if (!StringUtils.isEmpty(productName)) {
			queryString.append(" and tbr.product_name like :productName ");
		}
		if (type != null) {
			queryString.append(" and tbr.type = :type ");
		}
		if (status != null) {
			queryString.append(" and tbr.status = :status ");
		}
		queryString.append(" group by tbr.id order by tbr.input_time desc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if (enterpriseId != null) {
			query.setParameter("enterpriseId",enterpriseId);
		}
		if (!StringUtils.isEmpty(productName)) {
			query.setParameter("productName","%"+productName+"%");
		}
		if (type != null) {
			query.setParameter("type",type);
		}
		if (status != null) {
			query.setParameter("status",status);
		}
		if (nowPage != null && pageCount != null) {
			query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Integer listBuyReleaseCount(Integer enterpriseId,String productName,Integer type,String dscd,Integer status){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT count(distinct tbr.id) FROM tb_buy_release tbr ");
		queryString.append(" left join tb_type tt on tt.id = tbr.type where tbr.del_flag = 0 ");
		if (!StringUtils.isEmpty(dscd)) {
			queryString.append(" and tbr.dscd like :dscd ");
		}
		if (enterpriseId != null) {
			queryString.append(" and tbr.enterprise_id = :enterpriseId ");
		}
		if (!StringUtils.isEmpty(productName)) {
			queryString.append(" and tbr.product_name like :productName ");
		}
		if (type != null) {
			queryString.append(" and tbr.type = :type ");
		}
		if (status != null) {
			queryString.append(" and tbr.status = :status ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if (enterpriseId != null) {
			query.setParameter("enterpriseId",enterpriseId);
		}
		if (!StringUtils.isEmpty(productName)) {
			query.setParameter("productName","%"+productName+"%");
		}
		if (type != null) {
			query.setParameter("type",type);
		}
		if (status != null) {
			query.setParameter("status",status);
		}
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
	}
	
	public Map<String, Object> detailBuyRelease(Integer id){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tbr.id,tbr.product_name productName,tbr.num,tbr.price,tt.name type,tbr.type typeId, ");
		queryString.append(" date_format(tbr.start_time,'%Y-%m-%d') startTime,date_format(tbr.end_time,'%Y-%m-%d') endTime, ");
		queryString.append(" ifnull(tbr.contact_phone,'') contactPhone,ifnull(tbr.contact_person,'') contactPerson, ");
		queryString.append(" ifnull(tbr.contact_address,'') contactAddress,tbr.dscd dscd,ta.name dsnm,tbr.enterprise_id enterpriseId, ");
		queryString.append(" te.name enterpriseName,date_format(tbr.input_time,'%Y-%m-%d') inputTime,ifnull(tbr.specification,'') specification ");
		queryString.append(" FROM tb_buy_release tbr left join tb_type tt on tt.id = tbr.type left join tb_enterprise te on te.id = tbr.enterprise_id ");
		queryString.append(" left join tb_area ta on ta.id = tbr.dscd where tbr.id = :id ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("id", id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
