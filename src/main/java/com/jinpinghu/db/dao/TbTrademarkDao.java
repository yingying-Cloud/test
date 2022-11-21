package com.jinpinghu.db.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.bean.TbTrademark;
import com.mysql.cj.util.StringUtils;

public class TbTrademarkDao extends BaseZDao{

	public TbTrademarkDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbTrademark findById(Integer id) {
		try {
			String queryString = "from TbTrademark where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbTrademark> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Object[] findInfoById(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" id, ");
			sb.append(" brand_Name, ");
			sb.append(" address, ");
			sb.append("trademark_name, ");
			sb.append("product_certification, ");
			sb.append("source, ");
			sb.append("date_format(input_time,'%Y-%m-%d'), ");
			sb.append(" contract_number, ");
			sb.append(" x, ");
			sb.append(" y ");
			sb.append(" from tb_trademark tt  ");
			sb.append(" where tt.del_flag=0 ");
			sb.append(" and tt.id = :id ");
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
	
	public List<Object[]> findByName(String name,Integer nowPage,Integer pageCount) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" id, ");
			sb.append(" brand_Name, ");
			sb.append(" address, ");
			sb.append("trademark_name, ");
			sb.append("product_certification, ");
			sb.append("source, ");
			sb.append("date_format(input_time,'%Y-%m-%d'), ");
			sb.append(" contract_number, ");
			sb.append(" x, ");
			sb.append(" y ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_trademark_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.trademark_id = tt.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) fileUrl ");
			sb.append(" from tb_trademark tt  ");
			sb.append(" where tt.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tt.brand_name like :name ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%");
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

	public Integer findByNameCount(String name) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(id) ");
			sb.append(" from tb_trademark tt  ");
			sb.append(" where tt.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tt.brand_name like :name ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%");
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
	
	public List<Object[]> findByBrand(String name,String productName,Integer nowPage,Integer pageCount,Integer brandId,Integer trademarkId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("tt.id, ");
			sb.append("tt.brand_Name, ");
			sb.append("tt.address, ");
			sb.append("tt.trademark_name, ");
			sb.append("tt.product_certification, ");
			sb.append("tt.source, ");
			sb.append("date_format(tt.input_time,'%Y-%m-%d'), ");
			sb.append("tt.contract_number, ");
			sb.append("tt.x, ");
			sb.append("tt.y, ");
			sb.append("trtb.area, ");
			sb.append("trtb.yield, ");
			sb.append("tb.product_name ");
			sb.append(" ,( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_trademark_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.trademark_id = tt.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ) fileUrl ");
			sb.append("from tb_res_trademark_brand trtb left join tb_trademark tt on tt.id=trtb.trademark_id  ");
			sb.append("left join tb_brand tb on tb.id=trtb.brand_id  ");
			sb.append("where tt.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tt.brand_name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(productName)) {
				sb.append(" and tb.product_name like :productName ");
			}
			if(brandId!=null) {
				sb.append(" and trtb.brand_id=:brandId ");
			}
			if(trademarkId!=null) {
				sb.append(" and trtb.trademark_id=:trademarkId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(productName)) {
				query.setParameter("productName","%"+productName+"%");
			}
			if(brandId!=null) {
				query.setParameter("brandId",brandId);
			}
			if(trademarkId!=null) {
				query.setParameter("trademarkId",trademarkId);
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

	public Integer findByBrandCount(String name,String productName,Integer brandId,Integer trademarkId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(trtb.id) ");
			sb.append("from tb_res_trademark_brand trtb left join tb_trademark tt on tt.id=trtb.trademark_id  ");
			sb.append("left join tb_brand tb on tb.id=trtb.brand_id  ");
			sb.append("where tt.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and tt.brand_name like :name ");
			}
			if(!StringUtils.isNullOrEmpty(productName)) {
				sb.append(" and tb.product_name like :productName ");
			}
			if(brandId!=null) {
				sb.append(" and trtb.brand_id=:brandId ");
			}
			if(trademarkId!=null) {
				sb.append(" and trtb.trademark_id=:trademarkId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(productName)) {
				query.setParameter("productName","%"+productName+"%");
			}
			if(brandId!=null) {
				query.setParameter("brandId",brandId);
			}
			if(trademarkId!=null) {
				query.setParameter("trademarkId",trademarkId);
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
