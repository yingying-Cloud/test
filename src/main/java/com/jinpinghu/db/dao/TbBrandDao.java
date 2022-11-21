package com.jinpinghu.db.dao;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbBrand;
import com.mysql.cj.util.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;


public class TbBrandDao extends BaseZDao{

	public TbBrandDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public TbBrand findById(Integer id) {
		try {
			String queryString = "from TbBrand where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbBrand> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getBrandInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tb.id,"
 					+ "	te.name ename,"
 					+ "	product_name,"
 					+ "	model,"
 					+ "	registered_trademark,"
 					+ "	authorization_category,"
 					+ "	tb.input_time,"
 					+ " tb.status,tb.type "
 					+ " ,tb.price,tb.unit,tb.spec,tb.describe_ "
 					+ " ,(select group_concat(name) from tb_res_enterprise_brand trtb left join tb_enterprise tr on trtb.enterprise_id=tr.id where trtb.brand_id=tb.id  ) enterpriseName "
 					+ " FROM "
 					+ "	tb_brand tb LEFT JOIN tb_enterprise te ON te.id=tb.enterprise_id "
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
	
	public List<Object[]> getBrandList(String productName,String enterpriseId,String status,Integer nowPage,Integer pageCount,Integer type,Integer upStatus) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tb.id,"
 					+ "	te.name ename,"
 					+ "	tb.product_name,"
 					+ "	tb.model,"
 					+ "	tb.registered_trademark,"
 					+ "	tb.authorization_category,"
 					+ "	tb.input_time,"
 					+ " tb.status,tb.type "
 					+ " ,tb.price,tb.unit,tb.spec,tb.describe_ "
 					+ " ,(select group_concat(name) from tb_res_enterprise_brand trtb left join tb_enterprise tr on trtb.enterprise_id=tr.id where trtb.brand_id=tb.id  ) enterpriseName "
// 					+ " ,tb.up_status "
 					+ " FROM "
 					+ " 	tb_brand tb LEFT JOIN tb_enterprise te ON te.id=tb.enterprise_id "
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
//			if(upStatus!=null) {
//				queryString += " AND tb.up_status = :upStatus ";
//			}
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
//			if(upStatus!=null) {
//				query.setParameter("upStatus",upStatus);
//			}
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
	
	public Integer getBrandListCount(String productName,String enterpriseId,String status,Integer type,Integer upStatus) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ " COUNT(1) "
 					+ "FROM "
 					+ "	tb_brand tb "
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
//			if(upStatus!=null)
//				queryString += " AND tb.up_status = :upStatus ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(productName))
				query.setParameter("productName","%"+productName+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(status))
				query.setParameter("status",status);
			if(type!=null)
				query.setParameter("type",type);
//			if(upStatus!=null) {
//				query.setParameter("upStatus",upStatus);
//			}
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
	
	public List<Map<String, Object>> findNotAddBrandList(Integer enterpriseId,List<Integer> brandIdList) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tb.id id,"
 					+ "	ifnull(te.name,'') enterpriseName,"
 					+ "	ifnull(tb.product_name,'') productName,"
 					+ "	ifnull(tb.model,'') model,"
 					+ "	ifnull(tb.registered_trademark,'') registeredTrademark,"
 					+ "	ifnull(tb.authorization_category,'') authorizationCategory,"
 					+ "	date_format(tb.input_time,'%Y-%m-%d %H:%i:%s') inputTime,"
 					+ " ifnull(tb.status,'') status,ifnull(tb.type,'') type "
 					+ "FROM "
 					+ "	tb_brand tb LEFT JOIN tb_enterprise te ON te.id=tb.enterprise_id "
 					+ "WHERE "
 					+ " tb.del_flag=0 ";
			if(brandIdList != null && brandIdList.size()>0)
				queryString += " AND tb.id not in (:brandIdList) ";
			if(enterpriseId != null)
				queryString += " AND tb.enterprise_id = :enterpriseId ";
			queryString += " ORDER BY tb.type asc, tb.input_time DESC ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(brandIdList != null && brandIdList.size()>0)
				query.setParameter("brandIdList", brandIdList);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
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

	public List<Object[]> findTrademarkByBrandId(Integer brandId,Integer carId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("tt.id,tt.name ");
			if(carId!=null) {
				sb.append(",(SELECT num from tb_res_brand_car_num bcn where bcn.enterprise_id=tt.id ");
				sb.append(" and bcn.brand_car_id=:carId ) ");
				sb.append(",(select num from tb_brand_shopping_car where id=:carId)");
			}else {
				sb.append(" ,'' num ,'' carNum ");
			}
			sb.append("from tb_res_enterprise_brand trtb left join tb_enterprise tt on tt.id=trtb.enterprise_id  ");
			sb.append("left join tb_brand tb on tb.id=trtb.brand_id  ");
			sb.append("where tt.del_flag=0 and tt.enterprise_type=1 ");
			if(brandId!=null) {
				sb.append(" and trtb.brand_id=:brandId ");
			}
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(brandId!=null) {
				query.setParameter("brandId",brandId);
			}
			if(carId!=null) {
				query.setParameter("carId",carId);
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

	
	public List<Object[]> findAllCarNum(String goodsName,String startTime,String endTime,String enterpriseName) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" tb.product_name, ");
			sb.append(" tbo.order_number, ");
			sb.append(" te.`name`, ");
			sb.append(" te.enterprise_link_people, ");
			sb.append(" te.enterprise_link_mobile, ");
			sb.append(" tbsc.num, ");
			sb.append(" tbsc.price, ");
			sb.append("  tt.name plantEnterprisenName, ");
			sb.append("  tbo.input_time ");
			sb.append(" FROM ");
			sb.append(" tb_brand_shopping_car tbsc  ");
			sb.append(" INNER JOIN tb_brand tb ON tb.id = tbsc.brand_id ");
			sb.append(" INNER JOIN tb_res_brand_order_car trboc ON trboc.brand_car_id = tbsc.id ");
			sb.append(" INNER JOIN tb_brand_order tbo ON tbo.id = trboc.brand_order_id ");
			sb.append(" INNER JOIN tb_enterprise te ON te.id = tbo.enterprise_id ");
			sb.append(" where tbo.status=4  ");

			if(!StringUtils.isNullOrEmpty(goodsName)) {
				sb.append(" and tb.product_name like :goodsName ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and tbo.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and tbo.input_time <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and tt.name like :enterpriseName ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", DateTimeUtil.formatTime(startTime));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", DateTimeUtil.formatTime(endTime));
			}
			if(!StringUtils.isNullOrEmpty(goodsName)) {
				query.setParameter("goodsName","%"+goodsName+"%");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%");
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
	
	public List<Object[]> findAllOrder(String startTime,String endTime,String enterpriseName) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" te.name, ");
			sb.append(" tbo.input_time, ");
			sb.append(" tbo.order_number, ");
			sb.append(" te.enterprise_link_mobile, ");
			sb.append(" te.enterprise_link_people, ");
			sb.append(" tbo.price,tt.id ttid, ");
			sb.append(" te.name enterpriseName,tbo.id oid ");
			sb.append(" FROM ");
			sb.append(" tb_brand_shopping_car tbsc  ");
			sb.append(" INNER JOIN tb_brand tb ON tb.id = tbsc.brand_id ");
			sb.append(" INNER JOIN tb_res_brand_order_car trboc ON trboc.brand_car_id = tbsc.id ");
			sb.append(" INNER JOIN tb_brand_order tbo ON tbo.id = trboc.brand_order_id ");
			sb.append(" INNER JOIN tb_enterprise te ON te.id = tbo.enterprise_id ");
			sb.append(" where tbo.status=4 ");
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and tbo.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and tbo.input_time <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			sb.append(" group by te.id,tbo.id ");
			sb.append(" order by te.id asc,tbo.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", DateTimeUtil.formatTime(startTime));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", DateTimeUtil.formatTime(endTime));
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%");
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
	
	public List<Object[]> findOrderInfo(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tt.name , ");
			sb.append(" tb.product_name, ");
			sb.append(" tbsc.num, ");
			sb.append(" tb.unit,tb.price  ");
			sb.append(" FROM ");
			sb.append(" tb_brand_shopping_car tbsc ");
			sb.append(" INNER JOIN tb_brand tb ON tb.id = tbsc.brand_id ");
			sb.append(" INNER JOIN tb_res_brand_order_car trboc ON trboc.brand_car_id = tbsc.id ");
			sb.append(" inner join tb_enterprise tt on tt.id=tbsc.enterprise_id ");
			sb.append(" where 1=1 ");
			if(enterpriseId!=null) {
				sb.append(" and tt.id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
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
	
	public List<Object[]> getAllBrandList(String productName,String enterpriseId,String status,Integer nowPage,Integer pageCount,Integer type,Integer upStatus) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	tb.id,"
 					+ "	tr.name ename,"
 					+ "	tb.product_name,"
 					+ "	tb.model,"
 					+ "	tb.registered_trademark,"
 					+ "	tb.authorization_category,"
 					+ "	tb.input_time,"
 					+ " tb.status,tb.type "
 					+ " ,tb.price,tb.unit,tb.spec,tb.describe_ "
 					+ " ,tr.id enterpriseId "
 					+ " FROM "
 					+ " 	tb_brand tb inner join  tb_res_enterprise_brand trtb on trtb.brand_id=tb.id inner join tb_enterprise tr on trtb.enterprise_id=tr.id  "
 					+ " WHERE "
 					+ " tb.del_flag=0 ";
			if(!StringUtil.isNullOrEmpty(productName)) {
				queryString += " AND tb.product_name like :productName ";
			}
			if(!StringUtil.isNullOrEmpty(enterpriseId)) {
				queryString += " AND tr.id = :enterpriseId ";
			}
			if(!StringUtil.isNullOrEmpty(status)) {
				queryString += " AND tb.status = :status ";
			}
			if(type!=null) {
				queryString += " AND tb.type = :type ";
			}
//			if(upStatus!=null) {
//				queryString += " AND tb.up_status = :upStatus ";
//			}
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
//			if(upStatus!=null) {
//				query.setParameter("upStatus",upStatus);
//			}
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
	
	public Integer getAllBrandListCount(String productName,String enterpriseId,String status,Integer type,Integer upStatus) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ " COUNT(1) "
 					+ "FROM "
 					+ " 	tb_brand tb inner  join  tb_res_enterprise_brand trtb on trtb.brand_id=tb.id inner join tb_enterprise tr on trtb.enterprise_id=tr.id  "
 					+ "WHERE "
 					+ " tb.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(productName))
				queryString += " AND tb.product_name like :productName ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND tr.id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(status))
				queryString += " AND tb.status = :status ";
			if(type!=null)
				queryString += " AND tb.type = :type ";
//			if(upStatus!=null)
//				queryString += " AND tb.up_status = :upStatus ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(productName))
				query.setParameter("productName","%"+productName+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(status))
				query.setParameter("status",status);
			if(type!=null)
				query.setParameter("type",type);
//			if(upStatus!=null) {
//				query.setParameter("upStatus",upStatus);
//			}
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
	
	
	
}
