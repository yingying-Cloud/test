package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbReceivingAddress;

public class TbAddressDao extends BaseZDao{

	public TbAddressDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbReceivingAddress findById(Integer id) {
		try {
			String queryString = "from TbReceivingAddress where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbReceivingAddress> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void setDefaultAddress(String userId, Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" UPDATE"
					+ "	tb_receiving_address "
					+ "	set list_index = ( case when id = :id then 1 else 0 end) "
					+ "WHERE "
					+ "	del_flag = 0 "
					+ "	AND user_id = :userId ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("userId",userId);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Integer getAddressListCount(String userId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ " COUNT(1) "
 					+ "FROM "
 					+ "	tb_receiving_address "
 					+ "WHERE "
 					+ " del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " AND user_id = :userId ";
			
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId",userId);
			
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
	
	
	
	public List<Map<String, Object>> findAddressList(String userId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	id,"
 					+ " user_id userId,"
 					+ " link_people linkPeople,"
 					+ " link_mobile linkMobile,"
 					+ " province,"
 					+ "	city,"
 					+ " county,"
 					+ " address "
 					+ "FROM "
 					+ "	tb_receiving_address  "
 					+ "WHERE "
 					+ " del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " AND user_id = :userId ";
 			queryString += " order by list_index desc";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId",userId);
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
	
	public Map<String, Object> findAddressInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	id,"
 					+ " user_id userId,"
 					+ " link_people linkPeople,"
 					+ " link_mobile linkMobile,"
 					+ " province,"
 					+ "	city,"
 					+ " county,"
 					+ " address,"
 					+ " input_time inputTime,"
 					+ " del_flag "
 					+ "FROM "
 					+ "	tb_receiving_address  "
 					+ "WHERE "
 					+ " id = :id ";
			
			Query query = getEntityManager().createNativeQuery(queryString);

			query.setParameter("id",id);
			
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
