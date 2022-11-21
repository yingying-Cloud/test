package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbCart;

import fw.jbiz.jpa.ZJpaHelper;

public class TbCartDao extends BaseZDao{

	public TbCartDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbCart findById(Integer id){
		try {
			TbCart instance = getEntityManager().find(TbCart.class, id);
			if (instance != null ) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}	
	
	public List<TbCart> findByUserIdAndToolId(String userId, Integer toolId) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			String queryString = "from TbCart where status = 0 ";
			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " and userid = :userId ";
			if(toolId != null)
				queryString += " and toolId = :toolId";
			Query query = getEntityManager().createQuery(queryString);
			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId", userId);
			if(toolId != null)
				query.setParameter("toolId", toolId);
			List<TbCart> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}	
	
	
	public List<Map<String, Object>> GetEnterpriseInCart(String userId) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			String queryString = 
					  " select "
					  + " te.`name` enterpriseName,"
					  + " te.id enterpriseId,"
					  + " GROUP_CONCAT(tc.id) ids "
					  + "from tb_cart tc left join tb_tool tt on tc.tool_id = tt.id "
					  + "	left join tb_enterprise te on te.id = tt.enterprise_id "
					  + "where tc.`status` = 0 and tc.userid = :userId "
					  + "GROUP BY te.id";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("userId", userId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
	
	public List<Map<String, Object>> GetMyCartList(List<String> ids) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			String queryString = 
					  "SELECT "
					+ "	tc.id cartId,"
					+ "	tc.cart_num cartNum,"
					+ "	tc.tool_id toolId,"
					+ "	te.id enterpriseId,"
					+ "	te.name enterpriseName,"
					+ "	tt.name toolName,"
					+ "	tt.type toolType,"
					+ " tt.number toolNum,"
					+ "	tty.`name` typeName,"
					+ "	tt.number toolNumber,"
					+ "	(SELECT	file_url FROM tb_res_tool_file res LEFT JOIN tb_file tf ON tf.id = res.file_id "
					+ "	WHERE res.del_flag= 0 and res.tool_id = tt.id ORDER BY tf.id LIMIT 1) fileUrls,"
					+ "	tt.price toolPrice,"
					+ " tt.del_flag delFlag "
					+ "FROM"
					+ "	tb_cart tc "
					+ "	LEFT JOIN tb_tool tt ON tc.tool_id = tt.id "
					+ "	LEFT JOIN tb_enterprise te ON te.id = tt.enterprise_id "
					+ "	left join tb_type tty on tt.type = tty.id "
					+ "WHERE tc.id in (:ids) ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("ids", ids);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}	
	
	public Integer delCart(List<String> cartIdList) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			
			String queryString = "update Tb_Cart set status = 2  where id in :cartIdList ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("cartIdList", cartIdList);
			
			Integer result = query.executeUpdate();
			if (null != result) {
				return result;
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}	
	
	public List<Object[]> getEnterpriseIds(List<String> cartIdList) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			
			String queryString = 
					" SELECT distinct te.id, te.name  "
					+ "FROM "
					+ " tb_cart tc 	"
					+ " LEFT JOIN tb_tool tt ON tc.tool_id = tt.id "
					+ "	LEFT JOIN tb_enterprise te ON te.id = tt.enterprise_id "
					+ "WHERE tt.del_flag = 0 "
					+ " AND tc.id in :cartIdList ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			
			query.setParameter("cartIdList", cartIdList);
			
			List<Object[]> result = query.getResultList();
			if (null != result && result.get(0) != null) {
				return result;
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}	
	
	public List<Map<String, Object>> GetToolByEnterpriseId(String userId, String enterpriseId, List<String> cartIds ) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			String queryString = 
					  "SELECT "
					+ "	tc.id cartId,"
					+ "	tc.cart_num cartNum,"
					+ "	tc.tool_id toolId,"
					+ "	te.id enterpriseId,"
					+ "	te.name enterpriseName,"
					+ "	tt.name toolName,"
					+ "	tt.type toolType,"
					+ "	tty.`name` typeName,"
					+ "	tt.number toolNumber,"
					+ "	(SELECT	file_url FROM tb_res_tool_file res LEFT JOIN tb_file tf ON tf.id = res.file_id WHERE res.tool_id = tg.id ORDER BY tf.id LIMIT 1) fileUrls,"
					+ "	tt.price toolPrice "
					+ "FROM"
					+ "	tb_cart tc "
					+ "	LEFT JOIN tb_tool tt ON tc.tool_id = tt.id "
					+ "	LEFT JOIN tb_enterprise te ON te.id = tt.enterprise_id "
					+ "	left join tb_type tty on tt.type = tty.id "
					+ "WHERE"
					+ "	tt.del_flag = 0 "
					+ "	AND tc.STATUS = 0  ";
			if(!StringUtil.isNullOrEmpty(userId))
				queryString += " and tc.userid = :userId ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " and tt.enterprise_id = :enterpriseId ";
			if(cartIds != null)
				queryString += " and tc.id in :cartIds ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtil.isNullOrEmpty(userId))
				query.setParameter("userId", userId);
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId", enterpriseId);
			if(cartIds != null)
				query.setParameter("cartIds", cartIds);
			
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
	
	@SuppressWarnings("deprecation")
	public Integer updateStatusByCartIds(List<String> cartIdList) {
		ZJpaHelper.log("finding WwInfo instance", Level.INFO, null);
		try {
			
			String queryString = "update tb_cart set status = 1 where id in :cartIdList ";
			
			Query query = getEntityManager().createNativeQuery(queryString);
			
			query.setParameter("cartIdList", cartIdList);
			
			Integer result = query.executeUpdate();
			
			return result;
			
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}	
}
