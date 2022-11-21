package com.jinpinghu.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbSupplyRelease;

public class TbSupplyReleaseDao extends BaseZDao {

	public TbSupplyReleaseDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbSupplyRelease findById(Integer id) {
		try {
			String queryString = "from TbSupplyRelease where id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbSupplyRelease> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> listSupplyRelease(Integer enterpriseId,String productName,Integer type,Integer status,String dscd,Integer nowPage,Integer pageCount){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tsr.id,tb.product_name productName,ifnull(sum(tso.num),0) sellNum,ifnull(tw.expected_production,'') expectedProduction,  ");
		queryString.append(" ifnull(tsr.specification,'') specification,tsr.price,tsr.num num,tsr.num-ifnull(sum(tso.num),0) stock,tt.name type, ");
		queryString.append(" tsr.dscd,ta.name dsnm,tsr.status,ifnull(tsr.estimate_output,'') estimateOutput FROM tb_supply_release tsr  ");
		queryString.append(" left join tb_work tw on tw.id = tsr.work_id left join tb_brand tb on tsr.brand_id = tb.id left join tb_type tt on tt.id = tsr.type ");
		queryString.append(" left join tb_sc_order tso on tso.supply_release_id = tsr.id left join tb_area ta on ta.id = tsr.dscd where tsr.del_flag = 0 ");
		if (!StringUtils.isEmpty(dscd)) {
			queryString.append(" and tsr.dscd like :dscd ");
		}
		if (enterpriseId != null) {
			queryString.append(" and tsr.enterprise_id = :enterpriseId ");
		}
		if (!StringUtils.isEmpty(productName)) {
			queryString.append(" and tb.product_name like :productName ");
		}
		if (type != null) {
			queryString.append(" and tsr.type = :type ");
		}
		if (status != null) {
			queryString.append(" and tsr.status = :status ");
		}
		queryString.append(" group by tsr.id order by tsr.input_time desc ");
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
	
	public Integer listSupplyReleaseCount(Integer enterpriseId,String productName,Integer type,Integer status,String dscd){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT count(distinct tsr.id) FROM tb_supply_release tsr ");
		queryString.append(" left join tb_brand tb on tsr.brand_id = tb.id ");
		queryString.append(" where tsr.del_flag = 0 ");
		if (!StringUtils.isEmpty(dscd)) {
			queryString.append(" and tsr.dscd like :dscd ");
		}
		if (enterpriseId != null) {
			queryString.append(" and tsr.enterprise_id = :enterpriseId ");
		}
		if (!StringUtils.isEmpty(productName)) {
			queryString.append(" and tb.product_name like :productName ");
		}
		if (type != null) {
			queryString.append(" and tsr.type = :type ");
		}
		if (status != null) {
			queryString.append(" and tsr.status = :status ");
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
	
	public Map<String, Object> detailSupplyRelease(Integer id){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tsr.id,tsr.brand_id brandId,tb.product_name productName,tsr.work_id workId, ");
		queryString.append(" ifnull(tw.expected_production,'') expectedProduction,ifnull(tsr.specification,'') specification, ");
		queryString.append(" tsr.num,tsr.price,tt.name type,ifnull(tsr.contact_person,'') contactPerson,tw.work_sn workSn, ");
		queryString.append(" ifnull(tsr.contact_phone,'') contactPhone,ifnull(tsr.contact_address,'') contactAddress,tsr.type typeId, ");
		queryString.append(" tsr.dscd,ta.name dsnm,ifnull(tsr.estimate_output,'') estimateOutput,tsr.enterprise_id enterpriseId,te.name enterpriseName ");
		queryString.append(" FROM tb_supply_release tsr left join tb_brand tb on tsr.brand_id = tb.id ");
		queryString.append(" left join tb_work tw on tw.id = tsr.work_id left join tb_type tt on tt.id = tsr.type ");
		queryString.append(" left join tb_area ta on ta.id = tsr.dscd left join tb_enterprise te on te.id = tsr.enterprise_id ");
		queryString.append(" where tsr.del_flag = 0 and tsr.id = :id ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("id", id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> listUnionSupplyAndBuy(String dscd,String type,String name,String startPrice,String endPrice,
			String releaseType,Integer nowPage,Integer pageCount,Integer loginEnterpriseId,String orderby,String sortDirection){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select * from ( ");
		queryString.append(" SELECT tsr.id,tb.product_name productName,te.name enterpriseName,tt.name type,tsr.price price, ");
		queryString.append(" count(distinct tso.id) orderCount,tf.file_url fileUrl,DATE_FORMAT(tsr.input_time,'%Y-%m-%d') inputTime, ");
		queryString.append(" tred.distance,'1' releaseType,tsr.dscd,ta.name dsnm,tsr.type typeId,ifnull(tsr.specification,'') specification  ");
		queryString.append(" FROM tb_supply_release tsr left join tb_enterprise te on te.id = tsr.enterprise_id ");
		queryString.append(" left join tb_type tt on tsr.type = tt.id left join tb_brand tb on tsr.brand_id = tb.id ");
		queryString.append(" left join tb_res_supply_release_file trsrf on trsrf.supply_release_id = tsr.id ");
		queryString.append(" left join tb_file tf on tf.id = trsrf.file_id left join tb_sc_order tso on tso.supply_release_id = tsr.id ");
		queryString.append(" left join tb_area ta on ta.id = tsr.dscd left join tb_res_enterprise_distance tred  ");
		queryString.append(" on tred.primary_enterprise_id = :loginEnterpriseId and tred.compare_enterprise_id = tsr.enterprise_id ");
		queryString.append(" where tsr.del_flag = 0 and tsr.status = 1 group by tsr.id ");
		queryString.append(" union all ");
		queryString.append(" SELECT tbr.id,tbr.product_name productName,te.name enterpriseName,tt.name type,tbr.price price, ");
		queryString.append(" count(distinct tso.id) orderCount,tf.file_url fileUrl,DATE_FORMAT(tbr.input_time,'%Y-%m-%d') inputTime, ");
		queryString.append(" tred.distance,'2' releaseType,tbr.dscd,ta.name dsnm,tbr.type typeId,ifnull(tbr.specification,'') specification ");
		queryString.append(" FROM tb_buy_release tbr left join tb_enterprise te on te.id = tbr.enterprise_id  ");
		queryString.append(" left join tb_type tt on tbr.type = tt.id left join tb_res_buy_release_file trbrf on trbrf.buy_release_id = tbr.id ");
		queryString.append(" left join tb_file tf on tf.id = trbrf.file_id left join tb_sc_order tso on tso.buy_release_id = tbr.id ");
		queryString.append(" left join tb_area ta on ta.id = tbr.dscd left join tb_res_enterprise_distance tred  ");
		queryString.append(" on tred.primary_enterprise_id = :loginEnterpriseId and tred.compare_enterprise_id = tbr.enterprise_id ");
		queryString.append(" where tbr.del_flag = 0 and tbr.status = 1 group by tbr.id ");
		queryString.append(" ) data where 1=1 ");
		if (!StringUtils.isEmpty(dscd)) {
			queryString.append(" and data.dscd like :dscd ");
		}
		if (!StringUtils.isEmpty(type)) {
			queryString.append(" and data.typeId = :type ");
		}
		if (!StringUtils.isEmpty(name)) {
			queryString.append(" and (data.productName like :name or data.enterpriseName like :name) ");
		}
		if (!StringUtils.isEmpty(startPrice)) {
			queryString.append(" and data.price+0.0 >= :startPrice ");
		}
		if (!StringUtils.isEmpty(endPrice)) {
			queryString.append(" and data.price+0.0 <= :endPrice ");
		}
		if (!StringUtils.isEmpty(releaseType)) {
			queryString.append(" and data.releaseType = :releaseType ");
		}
		if(StringUtils.isEmpty(orderby)) {
			queryString.append(" order by convert(productName using gbk) asc ");
		}else if(!StringUtils.isEmpty(orderby)){
			if(orderby.equals("1")) {
				queryString.append(" order by convert(productName using gbk) ASC ");
			}else if(orderby.equals("4")) {
				queryString.append(" order by if(orderCount is null ,1,0),orderCount ");
			}else if(orderby.equals("5")) {
				queryString.append(" order by inputTime ");
			}else if(orderby.equals("6")) {
				queryString.append(" order by if(price is null,1,0),price+0.0 ");
			}
			List<String> pcSort = new ArrayList<String>(3) {{add("4");add("5");add("6");}};
			if (pcSort.contains(orderby)) {
				if (StringUtils.isEmpty(sortDirection)) {
					queryString.append(" desc ");
				}else if ("1".equals(sortDirection)) {
					queryString.append(" asc ");
				}else if ("2".equals(sortDirection)) {
					queryString.append(" desc ");
				}
			}
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("loginEnterpriseId", loginEnterpriseId);
		if (!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if (!StringUtils.isEmpty(type)) {
			query.setParameter("type", type);
		}
		if (!StringUtils.isEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if (!StringUtils.isEmpty(startPrice)) {
			query.setParameter("startPrice", startPrice);
		}
		if (!StringUtils.isEmpty(endPrice)) {
			query.setParameter("endPrice", endPrice);
		}
		if (!StringUtils.isEmpty(releaseType)) {
			query.setParameter("releaseType", releaseType);
		}
		if (nowPage != null && pageCount != null) {
			query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	} 
	
	public Integer listUnionSupplyAndBuyCount(String dscd,String type,String name,String startPrice,String endPrice,
			String releaseType,Integer loginEnterpriseId){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select count(*) from ( ");
		queryString.append(" SELECT tsr.id,tb.product_name productName,te.name enterpriseName,tt.name type,tsr.price price, ");
		queryString.append(" count(distinct tso.id) orderCount,tf.file_url fileUrl,DATE_FORMAT(tsr.input_time,'%Y-%m-%d') inputTime, ");
		queryString.append(" tred.distance,'1' releaseType,tsr.dscd,ta.name dsnm,tsr.type typeId,ifnull(tsr.specification,'') specification  ");
		queryString.append(" FROM tb_supply_release tsr left join tb_enterprise te on te.id = tsr.enterprise_id ");
		queryString.append(" left join tb_type tt on tsr.type = tt.id left join tb_brand tb on tsr.brand_id = tb.id ");
		queryString.append(" left join tb_res_supply_release_file trsrf on trsrf.supply_release_id = tsr.id ");
		queryString.append(" left join tb_file tf on tf.id = trsrf.file_id left join tb_sc_order tso on tso.supply_release_id = tsr.id ");
		queryString.append(" left join tb_area ta on ta.id = tsr.dscd left join tb_res_enterprise_distance tred  ");
		queryString.append(" on tred.primary_enterprise_id = :loginEnterpriseId and tred.compare_enterprise_id = tsr.enterprise_id ");
		queryString.append(" where tsr.del_flag = 0 and tsr.status = 1 group by tsr.id ");
		queryString.append(" union all ");
		queryString.append(" SELECT tbr.id,tbr.product_name productName,te.name enterpriseName,tt.name type,tbr.price price, ");
		queryString.append(" count(distinct tso.id) orderCount,tf.file_url fileUrl,DATE_FORMAT(tbr.input_time,'%Y-%m-%d') inputTime, ");
		queryString.append(" tred.distance,'2' releaseType,tbr.dscd,ta.name dsnm,tbr.type typeId,ifnull(tbr.specification,'') specification ");
		queryString.append(" FROM tb_buy_release tbr left join tb_enterprise te on te.id = tbr.enterprise_id  ");
		queryString.append(" left join tb_type tt on tbr.type = tt.id left join tb_res_buy_release_file trbrf on trbrf.buy_release_id = tbr.id ");
		queryString.append(" left join tb_file tf on tf.id = trbrf.file_id left join tb_sc_order tso on tso.buy_release_id = tbr.id ");
		queryString.append(" left join tb_area ta on ta.id = tbr.dscd left join tb_res_enterprise_distance tred  ");
		queryString.append(" on tred.primary_enterprise_id = :loginEnterpriseId and tred.compare_enterprise_id = tbr.enterprise_id ");
		queryString.append(" where tbr.del_flag = 0 and tbr.status = 1 group by tbr.id ");
		queryString.append(" ) data where 1=1 ");
		if (!StringUtils.isEmpty(dscd)) {
			queryString.append(" and data.dscd like :dscd ");
		}
		if (!StringUtils.isEmpty(type)) {
			queryString.append(" and data.typeId = :type ");
		}
		if (!StringUtils.isEmpty(name)) {
			queryString.append(" and (data.productName like :name or data.enterpriseName like :name) ");
		}
		if (!StringUtils.isEmpty(startPrice)) {
			queryString.append(" and data.price+0.0 >= :startPrice ");
		}
		if (!StringUtils.isEmpty(endPrice)) {
			queryString.append(" and data.price+0.0 <= :endPrice ");
		}
		if (!StringUtils.isEmpty(releaseType)) {
			queryString.append(" and data.releaseType = :releaseType ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("loginEnterpriseId", loginEnterpriseId);
		if (!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if (!StringUtils.isEmpty(type)) {
			query.setParameter("type", type);
		}
		if (!StringUtils.isEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if (!StringUtils.isEmpty(startPrice)) {
			query.setParameter("startPrice", startPrice);
		}
		if (!StringUtils.isEmpty(endPrice)) {
			query.setParameter("endPrice", endPrice);
		}
		if (!StringUtils.isEmpty(releaseType)) {
			query.setParameter("releaseType", releaseType);
		}
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
	} 

}
