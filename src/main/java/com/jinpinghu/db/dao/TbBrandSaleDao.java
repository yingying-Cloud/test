package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbBrandSale;
import com.mysql.cj.util.StringUtils;

public class TbBrandSaleDao extends BaseZDao{

	public TbBrandSaleDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	

	public TbBrandSale findById(Integer id) {
		try {
			String queryString = "from TbBrandSale where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbBrandSale> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Map<String,Object> findMapById(Integer id) {
		try {
			String queryString = "select tbs.id,te.name enterpriseName,tl.product_name productName,tbs.price,tbs.num,date_format(tbs.input_time,'%Y-%m-%d') inputTime ,tl.id brandId,tbs.status,tbs.describe_ 'describe',tbs.up_status upStatus  "
					+ " from tb_brand_sale tbs "
					+ " left join tb_enterprise te on te.id=tbs.enterprise_id "
					+ " left join tb_brand tl on tl.id= tbs.brand_id"
					+ " where tbs.id = :id and tbs.del_Flag = 0 ";
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
	
	public List<Map<String,Object>> findList(Integer enterpriseId,Integer nowPage,Integer pageCount,Integer status,String name) {
		try {
			String queryString = "select tbs.id,te.name enterpriseName,tl.product_name productName,tbs.price,tbs.num,date_format(tbs.input_time,'%Y-%m-%d') inputTime  "
					+ " ,ifnull(( SELECT file_url "
					+ " FROM tb_file f INNER JOIN tb_res_brand_file rfg "
					+ " ON f.id = rfg.file_id  "
					+ " WHERE rfg.brand_id = tl.id "
					+ " AND f.file_type = 1  LIMIT 1 ),'') as fileUrl,tbs.brand_id brandId,tbs.describe_  'describe',tbs.status,tbs.up_status upStatus  "
					+ " from tb_brand_sale tbs "
					+ " left join tb_enterprise te on te.id=tbs.enterprise_id "
					+ " left join tb_brand tl on tl.id= tbs.brand_id"
					+ " where tbs.del_Flag = 0  ";
			if(enterpriseId!=null) {
				queryString+= " and tbs.enterprise_id=:enterpriseId";
			}
			if(status!=null) {
				queryString+= " and tbs.status=:status";
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				queryString += " and tl.product_name like :name";
			}
			queryString+= " order by tbs.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(status!=null) {
				query.setParameter("status",status);
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name","%"+name+"%");
			}
			if(pageCount != null && nowPage != null) {
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
	public Integer findListCount(Integer enterpriseId,Integer status,String name) {
		try {
			String queryString = "select count(*)  "
					+ "  from tb_brand_sale tbs  "
					+ "  left join tb_enterprise te on te.id=tbs.enterprise_id  "
					+ "  left join tb_brand tl on tl.id= tbs.brand_id "
					+ "  where tbs.del_Flag = 0  ";
			if(enterpriseId!=null) {
				queryString += " and tbs.enterprise_id=:enterpriseId";
			}
			if(status!=null) {
				queryString += " and tbs.status=:status";
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				queryString += " and tl.product_name like :name";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(status!=null) {
				query.setParameter("status",status);
			}
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
	
	
	public List<Map<String,Object>> findListByType(Integer enterpriseId,Integer nowPage,Integer pageCount,String productName,String status,Integer type,Integer upStatus) {
		try {
			String queryString = "select tbs.brand_id id,te.name enterpriseName,tl.product_name productName,tbs.price,tbs.num,date_format(tbs.input_time,'%Y-%m-%d') inputTime  "
					+ " ,tbs.brand_id brandId,tbs.describe_  'describe' ,tbs.status,tbs.id brandSaleId,tbs.up_status upStatus,te.id enterpriseId "
					+ " from tb_brand_sale tbs "
					+ " left join tb_enterprise te on te.id=tbs.enterprise_id "
					+ " left join tb_brand tl on tl.id= tbs.brand_id"
					+ " where tbs.del_Flag = 0 and tbs.status=1 ";
			if(enterpriseId!=null) {
				queryString+= " and tbs.enterprise_id=:enterpriseId";
			}
			if(!StringUtil.isNullOrEmpty(productName))
				queryString += " AND tl.product_name like :productName ";
			if(!StringUtil.isNullOrEmpty(status))
				queryString += " AND tl.status = :status ";
			if(type!=null)
				queryString += " AND tl.type = :type ";
			if(upStatus!=null) {
				queryString += " AND tbs.up_status = :upStatus ";
			}
			queryString+= " order by tbs.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(!StringUtil.isNullOrEmpty(productName)) {
				query.setParameter("productName","%"+productName+"%");
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				query.setParameter("status",status);
			}
			if(type!=null) {
				query.setParameter("type",type);
			}
			if(upStatus!=null) {
				query.setParameter("upStatus",upStatus);
			}
			if(pageCount != null && nowPage != null) {
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
	public Integer findListByTypeCount(Integer enterpriseId,String productName,String status,Integer type,Integer upStatus) {
		try {
			String queryString = "select count(*)  "
					+ "  from tb_brand_sale tbs  "
					+ "  left join tb_enterprise te on te.id=tbs.enterprise_id  "
					+ "  left join tb_brand tl on tl.id= tbs.brand_id "
					+ "  where tbs.del_Flag = 0 and tbs.status=1 ";
			if(enterpriseId!=null) {
				queryString += " and tbs.enterprise_id=:enterpriseId";
			}
			if(!StringUtil.isNullOrEmpty(productName))
				queryString += " AND tl.product_name like :productName ";
			if(!StringUtil.isNullOrEmpty(status))
				queryString += " AND tl.status = :status ";
			if(type!=null)
				queryString += " AND tl.type = :type ";
			if(upStatus!=null) {
				queryString += " AND tbs.up_status = :upStatus ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(!StringUtil.isNullOrEmpty(productName)) {
				query.setParameter("productName","%"+productName+"%");
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				query.setParameter("status",status);
			}
			if(type!=null) {
				query.setParameter("type",type);
			}
			if(upStatus!=null) {
				query.setParameter("upStatus",upStatus);
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
