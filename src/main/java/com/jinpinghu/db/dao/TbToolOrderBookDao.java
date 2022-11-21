package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbToolOrderBook;

public class TbToolOrderBookDao extends BaseZDao{

	public TbToolOrderBookDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbToolOrderBook findById(Integer id) {
		try {
			String queryString = "from TbToolOrderBook where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbToolOrderBook> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	

	public List<Map<String, Object>> staticByMonth(String userId, String year, String name, Integer status) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	DATE_FORMAT( ob.input_time, '%Y年%m月' ) inputTime,"
 					+ "	sum( (cb.price + 0) * cb.num ) totalAmount, "
 					+ "	GROUP_CONCAT(cb.id ORDER BY	ob.input_time desc) ids "
 					+ "FROM "
 					+ "	tb_tool_order_book ob "
 					+ "	LEFT JOIN tb_shopping_car_book cb ON ob.id = cb.order_book_id "
 					+ " LEFT JOIN tb_enterprise te ON ob.tool_enterprise_id = te.id "
 					+ "	LEFT JOIN tb_tool tt ON cb.tool_id = tt.id "
 					+ "WHERE "
 					+ "	ob.del_flag = 0  ";
 			if(status != null)
 				queryString += " AND ob.`status` = :status ";
 			if(!StringUtil.isNullOrEmpty(year))
 				queryString += " AND YEAR ( ob.input_time ) = :year ";
 			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " AND ob.plant_enterprise_id = :userId ";
 			if(!StringUtil.isNullOrEmpty(name))
 				queryString += " AND ( te.NAME LIKE :name OR tt.NAME LIKE :name )  ";
 			queryString += " GROUP BY MONTH ( ob.input_time ) "
 					+ " ORDER BY MONTH ( ob.input_time ) desc ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(status != null)
				query.setParameter("status", status);
			if(!StringUtil.isNullOrEmpty(year))
				query.setParameter("year",year);
			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId",userId);
 			if(!StringUtil.isNullOrEmpty(name))
 				query.setParameter("name", "%"+name+"%");
			
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

	public Integer staticByMonthCount(String userId, String year, String name, Integer status) {
		try {
 			String queryString = 
 					"  select count(ob.id) "
 					+ "FROM "
 					+ "	tb_tool_order_book ob "
 					+ "	LEFT JOIN tb_shopping_car_book cb ON ob.id = cb.order_book_id "
 					+ " LEFT JOIN tb_enterprise te ON ob.tool_enterprise_id = te.id "
 					+ "	LEFT JOIN tb_tool tt ON cb.tool_id = tt.id "
 					+ "WHERE "
 					+ "	ob.del_flag = 0  ";
 			if(status != null)
 				queryString += " AND ob.`status` = :status ";
 			if(!StringUtil.isNullOrEmpty(year))
 				queryString += " AND YEAR ( ob.input_time ) = :year ";
 			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " AND ob.plant_enterprise_id = :userId ";
 			if(!StringUtil.isNullOrEmpty(name))
 				queryString += " AND ( te.NAME LIKE :name OR tt.NAME LIKE :name )  ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(status != null)
				query.setParameter("status", status);
			if(!StringUtil.isNullOrEmpty(year))
				query.setParameter("year",year);
			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId",userId);
 			if(!StringUtil.isNullOrEmpty(name))
 				query.setParameter("name", "%"+name+"%");
			
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}


	public List<Map<String, Object>> getShopCarBookList(List<String> idList ) {
		try {
 			String queryString = 
 					"  SELECT"
 					+ "	DATE_FORMAT(cb.input_time,'%m-%d %H:%i') inputTime,"
 					+ "	tt.name toolName,"
 					+ "	te.name enterpriseName,"
 					+ "	(cb.price + 0) * cb.num price "
 					+ "FROM"
 					+ "	tb_shopping_car_book cb left join tb_tool tt on cb.tool_id = tt.id "
 					+ "		left join tb_enterprise te on tt.enterprise_id = te.id "
 					+ "WHERE cb.id in(:idList) "
 					+ "order BY cb.input_time desc ";
				
			Query query = getEntityManager().createNativeQuery(queryString);
			
			query.setParameter("idList",idList);
			
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && !list.isEmpty()) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String, Object>> getShopCarBookList2(String userId, String year, String name, Integer status, Integer nowPage, Integer pageCount) {
		try {
			String queryString = 
 					"  SELECT "
 					+ " cb.id id,"
 					+ "	DATE_FORMAT( cb.input_time, '%m-%d %H:%i' ) inputTime,"
 					+ "	tt.NAME toolName,"
 					+ "	te.NAME enterpriseName,"
 					+ "	( cb.price + 0 ) * cb.num price "
 					+ "FROM "
 					+ "	tb_tool_order_book ob "
 					+ "	LEFT JOIN tb_shopping_car_book cb ON ob.id = cb.order_book_id "
 					+ " LEFT JOIN tb_enterprise te ON ob.tool_enterprise_id = te.id "
 					+ "	LEFT JOIN tb_tool tt ON cb.tool_id = tt.id "
 					+ "WHERE "
 					+ "	ob.del_flag = 0  ";
 			if(status != null)
 				queryString += " AND ob.`status` = :status ";
 			if(!StringUtil.isNullOrEmpty(year))
 				queryString += " AND YEAR ( ob.input_time ) = :year ";
 			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " AND ob.plant_enterprise_id = :userId ";
 			if(!StringUtil.isNullOrEmpty(name))
 				queryString += " AND ( te.NAME LIKE :name OR tt.NAME LIKE :name )  ";
 			queryString += " ORDER BY cb.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(status != null)
				query.setParameter("status", status);
			if(!StringUtil.isNullOrEmpty(year))
				query.setParameter("year",year);
			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId",userId);
 			if(!StringUtil.isNullOrEmpty(name))
 				query.setParameter("name", "%"+name+"%");
 			
 			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && !list.isEmpty()) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object> getShopCarBookList3(String userId, String year, String name, Integer status, Integer nowPage, Integer pageCount) {
		try {
			String queryString = 
 					"  SELECT "
 					+ " cb.id "
 					+ "FROM "
 					+ "	tb_tool_order_book ob "
 					+ "	Right JOIN tb_shopping_car_book cb ON ob.id = cb.order_book_id "
 					+ " LEFT JOIN tb_enterprise te ON ob.tool_enterprise_id = te.id "
 					+ "	LEFT JOIN tb_tool tt ON cb.tool_id = tt.id "
 					+ "WHERE "
 					+ "	ob.del_flag = 0  ";
 			if(status != null)
 				queryString += " AND ob.`status` = :status ";
 			if(!StringUtil.isNullOrEmpty(year))
 				queryString += " AND YEAR ( ob.input_time ) = :year ";
 			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " AND ob.plant_enterprise_id = :userId ";
 			if(!StringUtil.isNullOrEmpty(name))
 				queryString += " AND ( te.NAME LIKE :name OR tt.NAME LIKE :name )  ";
 			queryString += " ORDER BY cb.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(status != null)
				query.setParameter("status", status);
			if(!StringUtil.isNullOrEmpty(year))
				query.setParameter("year",year);
			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId",userId);
 			if(!StringUtil.isNullOrEmpty(name))
 				query.setParameter("name", "%"+name+"%");
 			
 			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object> list = query.getResultList();
			if (null != list && !list.isEmpty()) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getOrderBookCount(String userId, Integer enterpriseId, String name, Integer status) {
		try {
 			String queryString = 
 					"  select count(id) from( "
 					+ "select ob.id id "
 					+ "FROM "
 					+ "	tb_tool_order_book ob "
 					+ "	LEFT JOIN tb_shopping_car_book cb ON ob.id = cb.order_book_id "
 					+ " LEFT JOIN tb_enterprise te ON ob.tool_enterprise_id = te.id "
 					+ "	LEFT JOIN tb_tool tt ON cb.tool_id = tt.id "
 					+ "WHERE "
 					+ "	ob.del_flag = 0  ";

 			if(status != null)
 				queryString += " AND ob.`status` = :status ";
 			if(enterpriseId != null)
 				queryString += " AND tool_enterprise_id = :enterpriseId ";
 			if(!StringUtil.isNullOrEmpty(userId) && enterpriseId == null)
				queryString += " AND ob.plant_enterprise_id = :userId ";
 			if(!StringUtil.isNullOrEmpty(name))
 				queryString += " AND ( te.NAME LIKE :name OR tt.NAME LIKE :name )  ";
 			queryString += " group by ob.id )a";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(status != null)
				query.setParameter("status", status);
			if(enterpriseId != null)
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(userId) && enterpriseId == null)
				query.setParameter("userId",userId);
 			if(!StringUtil.isNullOrEmpty(name))
 				query.setParameter("name", "%"+name+"%");
			
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String, Object>> getOrderList(String userId, Integer enterpriseId, String name, Integer status, Integer nowPage, Integer pageCount) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	DATE_FORMAT( ob.input_time, '%Y-%m-%d %T' ) inputTime,"
 					+ " ob.order_number orderNumber,"
 					+ "	ob.price totalAmount, "
 					+ "	te.name toolEnterpriseName,"
 					+ " tu.name userName,"
 					+ " ob.id id,"
 					+ " ob.status status "
 					+ "FROM "
 					+ "	tb_tool_order_book ob "
 					+ "	LEFT JOIN tb_shopping_car_book cb ON ob.id = cb.order_book_id "
 					+ " LEFT JOIN tb_enterprise te ON ob.tool_enterprise_id = te.id "
 					+ "	LEFT JOIN tb_tool tt ON cb.tool_id = tt.id "
 					+ " LEFT JOIN tb_user tu ON ob.plant_enterprise_id = tu.user_id "
 					+ "WHERE "
 					+ "	ob.del_flag = 0  ";

 			if(status != null)
 				queryString += " AND ob.`status` = :status ";
 			if(enterpriseId != null)
 				queryString += " AND tool_enterprise_id = :enterpriseId ";
 			if(!StringUtil.isNullOrEmpty(userId) && enterpriseId == null)
				queryString += " AND ob.plant_enterprise_id = :userId ";
 			if(!StringUtil.isNullOrEmpty(name))
 				queryString += " AND ( te.NAME LIKE :name OR tt.NAME LIKE :name )  ";
 			queryString += " GROUP BY ob.id "
				+ "ORDER BY	 ob.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(status != null)
				query.setParameter("status", status);
			if(enterpriseId != null)
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(userId) && enterpriseId == null)
				query.setParameter("userId",userId);
 			if(!StringUtil.isNullOrEmpty(name))
 				query.setParameter("name", "%"+name+"%");
 			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
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


	public List<Map<String, Object>> getShopCarBookListByOrderId(Integer orderId) {
		try {
 			String queryString = 
 					"  SELECT"
 					+ "	cb.num,"
 					+ "	tt.name toolName,"
 					+ "	tt.production_units productionUnits,"
 					+ "	cb.price price,"
 					+ " (select file_url from tb_file tf left join tb_res_tool_file res on tf.id = res.file_id where res.del_flag = 0 and tool_id = cb.tool_id limit 1) fileUrl "
 					+ "FROM"
 					+ "	tb_shopping_car_book cb left join tb_tool tt on cb.tool_id = tt.id "
 					+ "WHERE order_book_id = :orderId ";
				
			Query query = getEntityManager().createNativeQuery(queryString);
			
			query.setParameter("orderId",orderId);
			
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && !list.isEmpty()) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Map<String, Object> getOrderInfo(Integer orderId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	DATE_FORMAT( ob.input_time, '%Y-%m-%d %T' ) inputTime,"
 					+ " ob.order_number orderNumber,"
 					+ "	sum(ob.price + 0) totalAmount, "
 					+ "	te.name toolEnterpriseName,"
 					+ " ob.contact_name userName,"
 					+ " ob.id id,"
 					+ " ob.contact_mobile mobile,"
 					+ " ob.contact_address address "
 					+ "FROM "
 					+ "	tb_tool_order_book ob "
 					+ "	LEFT JOIN tb_shopping_car_book cb ON ob.id = cb.order_book_id "
 					+ " LEFT JOIN tb_enterprise te ON ob.tool_enterprise_id = te.id "
 					+ "	LEFT JOIN tb_tool tt ON cb.tool_id = tt.id "
 					+ "WHERE "
 					+ "	 ob.id = :orderId ";
 			
			Query query = getEntityManager().createNativeQuery(queryString);
			
			if(orderId != null)
				query.setParameter("orderId",orderId);
			
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
