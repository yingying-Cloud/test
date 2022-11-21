package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbSellBrandRecord;
import com.mysql.cj.util.StringUtils;

import javax.persistence.Query;

public class TbSellBrandRecordDao extends BaseZDao {

	public TbSellBrandRecordDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbSellBrandRecord findById(Integer id) {
		String queryString = " from TbSellBrandRecord where delFlag = 0 and id = :id ";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("id", id);
		List<TbSellBrandRecord> list = query.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public Object[] findInfoById(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id, ");
			sb.append(" te.name tnm, ");
			sb.append(" sellBrand.product_name, ");
			sb.append(" tr.record_type, ");
			sb.append("tr.all_Number, ");
			sb.append("tr.number, ");
			sb.append("tr.use_name, ");
			sb.append("date_format(tr.use_time,'%Y-%m-%d %H-%i-%s'),sellBrand.unit,sellBrand.type ");
			sb.append(" ,tr.out_name,tr.out_mobile ");
			sb.append(" from tb_sell_brand_record tr left join tb_sell_brand sellBrand on sellBrand.id=tr.sell_brand_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" where tr.del_flag=0 ");
			sb.append(" and tr.id = :id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id",id );
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getSellBrandRecordList(Integer enterpriseId ,Integer sellBrandId,String name,Integer nowPage,
			Integer pageCount,Integer recordType,String startTime,String endTime,String type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tr.id, ");
			sb.append(" te.name tnm, ");
			sb.append(" sellBrand.product_name, ");
			sb.append(" tr.record_type, ");
			sb.append(" tr.all_Number, ");
			sb.append(" tr.number ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_sell_brand_record_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.sell_brand_record_id = sellBrand.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) goods_pic ");
			
			sb.append(" ,tr.use_name, ");
			sb.append(" date_format(tr.use_time,'%Y-%m-%d %H:%i:%s'),sellBrand.unit,sellBrand.type ");
			sb.append(" ,tr.out_name,tr.out_mobile  ");
			sb.append(" from tb_sell_brand_record tr left join tb_sell_brand sellBrand on sellBrand.id=tr.sell_brand_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" where tr.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and sellBrand.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if(recordType!=null) {
				sb.append(" and tr.record_type = :recordType ");
			}
			if(sellBrandId!=null) {
				sb.append(" and tr.sell_brand_id = :sellBrandId ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and sellBrand.product_name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tr.use_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tr.use_time) <= :endTime ");
			}
			sb.append(" order by tr.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(sellBrandId!=null) {
				query.setParameter("sellBrandId",sellBrandId );
			}
			if(recordType!=null) {
				query.setParameter("recordType",recordType );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer getSellBrandRecordListCount(Integer enterpriseId ,Integer sellBrandId,String name,Integer recordType,
			String startTime,String endTime,String type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(tr.id) ");
			sb.append(" from tb_sell_brand_record tr left join tb_sell_brand sellBrand on sellBrand.id=tr.sell_brand_id LEFT JOIN tb_enterprise te on te.id=tr.enterprise_id  ");
			sb.append(" where tr.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and sellBrand.type = :type ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tr.enterprise_id = :enterpriseId ");
			}
			if(sellBrandId!=null) {
				sb.append(" and tr.sell_brand_id = :sellBrandId ");
			}
			if(recordType!=null) {
				sb.append(" and tr.record_type = :recordType ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and sellBrand.product_name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tr.use_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tr.use_time) <= :endTime ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(sellBrandId!=null) {
				query.setParameter("sellBrandId",sellBrandId );
			}
			if(recordType!=null) {
				query.setParameter("recordType",recordType );
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",DateTimeUtil.formatTime(startTime ));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",DateTimeUtil.formatTime(endTime ));
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
