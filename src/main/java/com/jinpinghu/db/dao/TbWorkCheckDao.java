package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbWorkCheck;

public class TbWorkCheckDao extends BaseZDao {

	
	public TbWorkCheckDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}


	public TbWorkCheck findById(Integer id) {
		try {
			String queryString = "from TbWorkCheck where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbWorkCheck> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void delCheckById(Integer id) {
		try {
			String queryString = 
					 " UPDATE "
					+ "	tb_work_check wc LEFT JOIN tb_res_work_check_file cf ON wc.id=cf.work_check_id "
					+ "SET cf.del_flag=1 , wc.del_flag=1 "
					+ "WHERE wc.id = :id";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public Object[] getCheckInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	wc.id cid,"
 					+ "	wc.work_id wid,"
 					+ " wc.check_time,"
 					+ " wc.people,"
 					+ " wc.unit,"
 					+ " wc.content,"
 					+ " wc.input_time,"
 					+ " tw.work_sn,"
 					+ " te.name "
 					+ "FROM "
 					+ "	tb_work_check wc LEFT JOIN tb_work tw ON wc.work_id=tw.id "
 					+ " LEFT JOIN tb_enterprise te ON wc.enterprise_id=te.id "
 					+ "WHERE "
 					+ " wc.del_flag=0 AND tw.del_flag=0 "
 					+ " AND wc.id = :id ";
			
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
	
	
	public List<Object[]> getCheckList(Integer enterpriseId, String search, String startTimr, String endTime,
			Integer nowPage, Integer pageCount) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	wc.id cid,"
 					+ "	tw.id wid,"
 					+ " wc.check_time,"
 					+ " wc.people,"
 					+ " wc.unit,"
 					+ " wc.content,"
 					+ " wc.input_time,"
 					+ " tw.work_sn,"
 					+ " te.name "
 					+ "FROM "
 					+ "	tb_work_check wc LEFT JOIN tb_work tw ON wc.work_id=tw.id "
 					+ " LEFT JOIN tb_enterprise te ON wc.enterprise_id=te.id "
 					+ "WHERE "
 					+ " wc.del_flag=0  AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(search))
				queryString += " AND tw.work_sn LIKE :search ";
			if(!StringUtil.isNullOrEmpty(startTimr))
				queryString += " AND wc.check_time >= :startTimr ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND wc.check_time <= :endTime ";
			if(enterpriseId!=null)
				queryString += " AND wc.enterprise_id = :enterpriseId ";
			queryString+=" ORDER BY wc.check_time DESC";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(search))
				query.setParameter("search","%"+search+"%");
			if(!StringUtil.isNullOrEmpty(startTimr))
				query.setParameter("startTimr",startTimr);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(enterpriseId!=null)
				query.setParameter("enterpriseId",enterpriseId);
			
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
	
	public Integer getCheckListCount(Integer enterpriseId, String search, String startTimr, String endTime) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(1) "
 					+ "FROM "
 					+ "	tb_work_check wc LEFT JOIN tb_work tw ON wc.work_id=tw.id "
 					+ "WHERE "
 					+ " wc.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(search))
				queryString += " AND tw.work_sn LIKE :search ";
			if(!StringUtil.isNullOrEmpty(startTimr))
				queryString += " AND wc.check_time >= :startTimr ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND wc.check_time <= :endTime ";
			if(enterpriseId!=null)
				queryString += " AND wc.enterprise_id = :enterpriseId ";
			
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(search))
				query.setParameter("search","%"+search+"%");
			if(!StringUtil.isNullOrEmpty(startTimr))
				query.setParameter("startTimr",startTimr);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(enterpriseId!=null)
				query.setParameter("enterpriseId",enterpriseId);
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

	public List<Map<String, Object>> getAllCheckList( String workSn, String startTime) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	wc.id id,"
 					+ "	tw.id workId,"
 					+ "  date_format(wc.check_time,'%Y-%m-%d %H:%i:%s') checkTime,"
 					+ " wc.people,"
 					+ " wc.unit,"
 					+ " wc.content,"
 					+ " date_format(wc.input_time,'%Y-%m-%d %H:%i:%s') inputTime,"
 					+ " tw.work_sn wornSn,"
 					+ " te.name enterpriseName "
 					+ "FROM "
 					+ "	tb_work_check wc LEFT JOIN tb_work tw ON wc.work_id=tw.id "
 					+ " LEFT JOIN tb_enterprise te ON wc.enterprise_id=te.id "
 					+ "WHERE "
 					+ " wc.del_flag=0  AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND tw.work_sn LIKE :workSn ";
 			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND date_format(wc.input_time,'%Y-%m-%d') = :startTime ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			
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
	
}
