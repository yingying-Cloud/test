package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbSellBrand;


public class TbSellBrandDao extends BaseZDao{

	public TbSellBrandDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public TbSellBrand findById(Integer id) {
		try {
			String queryString = "from TbSellBrand where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbSellBrand> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbSellBrand findByBrand(Integer brandId,Integer enterpriseId) {
		try {
			String queryString = "from TbSellBrand where brandId = :brandId and delFlag = 0 and enterpriseId=:enterpriseId ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("brandId",brandId);
			query.setParameter("enterpriseId",enterpriseId);
			List<TbSellBrand> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public Object[] getSellBrandInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tb.id,"
 					+ "	te.name ename,"
 					+ "	product_name,"
 					+ "	tb.input_time,"
 					+ " tb.status,tb.type "
 					+ " ,tb.price,tb.unit,tb.spec,tb.describe_ "
 					+ " ,te.name enterpriseName "
 					+ " FROM "
 					+ "	tb_sell_brand tb LEFT JOIN tb_enterprise te ON te.id=tb.enterprise_id "
 					+  "WHERE "
 					+ " tb.del_flag=0 ";
				queryString += " AND tb.id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getSellBrandList(String productName,String enterpriseId,String status,Integer nowPage,Integer pageCount,Integer type,Integer upStatus) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tb.id,"
 					+ "	te.name enterpriseName,"
 					+ "	tb.product_name,"
 					+ "	tb.input_time,"
 					+ " tb.status,tb.type "
 					+ " ,tb.price,tb.unit,tb.spec,tb.describe_ "
 					+ " ,te.id enterpriseId ,tb.number "
 					+ " FROM "
 					+ " 	tb_sell_brand tb LEFT JOIN tb_enterprise te ON te.id=tb.enterprise_id "
 					+ " WHERE "
 					+ " tb.del_flag=0 ";
			if(!StringUtil.isNullOrEmpty(productName)) {
				queryString += " AND tb.product_name like :productName ";
			}
			if(!StringUtil.isNullOrEmpty(enterpriseId)) {
				queryString += " AND tb.enterprise_id = :enterpriseId ";
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				queryString += " AND tb.status = :status ";
			}
			if(type!=null) {
				queryString += " AND tb.type = :type ";
			}
			queryString += " ORDER BY tb.type asc,tb.input_time DESC ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(productName))
				query.setParameter("productName","%"+productName+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(status))
				query.setParameter("status",status);
			if(type!=null)
				query.setParameter("type",type);
			if(pageCount != null && nowPage != null) {
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
	
	public Integer getSellBrandListCount(String productName,String enterpriseId,String status,Integer type,Integer upStatus) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ " COUNT(1) "
 					+ "FROM "
 					+ "	tb_sell_brand tb "
 					+ "WHERE "
 					+ " tb.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(productName))
				queryString += " AND tb.product_name like :productName ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND tb.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(status))
				queryString += " AND tb.status = :status ";
			if(type!=null)
				queryString += " AND tb.type = :type ";
			if(upStatus!=null)
				queryString += " AND tb.up_status = :upStatus ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(productName))
				query.setParameter("productName","%"+productName+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(status))
				query.setParameter("status",status);
			if(type!=null)
				query.setParameter("type",type);
			if(upStatus!=null) {
				query.setParameter("upStatus",upStatus);
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				String count=list.get(0).toString();
				return Integer.valueOf(count);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> findNotAddSellBrandList(Integer enterpriseId) {
		try {
 			String queryString = 
 					"  SELECT "
 							+ "	tb.id id,"
 		 					+ "	ifnull(tb.product_name,'') productName,"
 		 					+ "	ifnull(tb.model,'') model,"
 		 					+ "	ifnull(tb.registered_trademark,'') registeredTrademark,"
 		 					+ "	ifnull(tb.authorization_category,'') authorizationCategory,"
 		 					+ "	date_format(tb.input_time,'%Y-%m-%d %H:%i:%s') inputTime,"
 		 					+ " ifnull(tb.status,'') status,ifnull(tb.type,'') type "
 					+ "FROM "
 					+ "	tb_brand tb "
 					+ "WHERE "
 					+ " tb.del_flag=0 ";
				queryString += " AND tb.id not in (select brand_id from tb_sell_brand where del_flag=0 ";
			if(enterpriseId != null) {
				queryString += " AND tb.enterprise_id = :enterpriseId ";
			}
			queryString += " ) ";
			queryString += " ORDER BY tb.type asc, tb.input_time DESC ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null) {
				query.setParameter("enterpriseId", enterpriseId);
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

	public Integer findNotAddSellBrandListCount(Integer enterpriseId) {
		try {
 			String queryString = 
 					"  SELECT "
 							+ "	count(tb.id) "
 					+ "FROM "
 					+ "	tb_brand tb "
 					+ "WHERE "
 					+ " tb.del_flag=0 ";
				queryString += " AND tb.id not in (select brand_id from tb_sell_brand where del_flag=0 ";
			if(enterpriseId != null) {
				queryString += " AND tb.enterprise_id = :enterpriseId ";
			}
			queryString += " ) ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null) {
				query.setParameter("enterpriseId", enterpriseId);
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
