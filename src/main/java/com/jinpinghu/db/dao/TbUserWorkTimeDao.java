package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbResUserWorkTime;


public class TbUserWorkTimeDao extends BaseZDao{

	public TbUserWorkTimeDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbResUserWorkTime findById(Integer id) {
		try {
			String queryString = "from TbResUserWorkTime where  id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbResUserWorkTime> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer getWorkTimeCount(Integer enterpriseId,String startTime,String endTime,List<String> roleId,Integer workId,String workSn,String name) {
		try {
 			String queryString = " SELECT COUNT(1) "
 					+ "FROM tb_res_user_work_time uwt  ";
 			queryString += "LEFT JOIN tb_enterprise_userinfo tu ON tu.id=uwt.user_tab_id " 
 					+ "		LEFT JOIN tb_work tw ON tw.id=uwt.work_id "
 					+ "WHERE tu.del_flag = 0 and uwt.enterprise_Id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND uwt.input_time>=:startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND uwt.input_time<=:endTime ";
			if(workId!=null)
				queryString += " AND uwt.work_id=:workId " ;
			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND tw.work_sn like :workSn ";
			if(!StringUtil.isNullOrEmpty(name))
				queryString += " AND tu.name like :name ";
			Query query = getEntityManager().createNativeQuery(queryString);
		
			query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(roleId!=null&&roleId.size()>0)
				query.setParameter("roleId",roleId);
			if(workId!=null)
				query.setParameter("workId",workId);
			if(!StringUtil.isNullOrEmpty(name))
				query.setParameter("name","%"+name+"%");
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
	
	public List<Object[]> getWorkTimeList(Integer enterpriseId,String startTime,String endTime,List<String> roleId,Integer nowPage,Integer pageCount,
			Integer workId,String workSn,String name) {
		try {
 			String queryString = " SELECT uwt.id ,uwt.user_tab_id ,uwt.input_time, work_time, tu.name,uwt.work_id,tw.work_sn "
 					+ "FROM tb_res_user_work_time uwt  ";
 			queryString += "LEFT JOIN tb_enterprise_userinfo tu ON tu.id=uwt.user_tab_id " 
 					+ "		LEFT JOIN tb_work tw ON tw.id=uwt.work_id "
 					+ "WHERE tu.del_flag = 0 and uwt.enterprise_Id = :enterpriseId ";
 			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND uwt.input_time>=:startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND uwt.input_time<=:endTime ";
			if(workId!=null)
				queryString += " AND uwt.work_id=:workId " ;
			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND tw.work_sn like :workSn ";
			if(!StringUtil.isNullOrEmpty(name))
				queryString += " AND tu.name like :name ";
			queryString += " ORDER BY uwt.input_time DESC ";
			Query query = getEntityManager().createNativeQuery(queryString);
			
			query.setParameter("enterpriseId",enterpriseId);
			if(workId!=null)
				query.setParameter("workId",workId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(name))
				query.setParameter("name","%"+name+"%");
			if(roleId!=null&&roleId.size()>0)
				query.setParameter("roleId",roleId);
			
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
	
	public Integer delWorkTimeByInputTime(Integer enterpriseId,String time,Integer workId) {
		try {
 			String queryString = "DELETE FROM tb_res_user_work_time  "
 					+ "			WHERE enterprise_id = :enterpriseId  ";
			if(!StringUtil.isNullOrEmpty(time))
				queryString += " AND input_time = :time ";
			if(workId!=null) {
				queryString += " and work_id = :workId ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("enterpriseId",enterpriseId);
			if(workId!=null) {
				query.setParameter("workId",workId);
			}
			if(!StringUtil.isNullOrEmpty(time))
				query.setParameter("time",time);
			
			Integer result = query.executeUpdate();
			{
				return result;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer delWorkTimeAllByInputTime(Integer enterpriseId,String time,Integer workId) {
		try {
 			String queryString = "DELETE FROM tb_res_user_work_time_all  "
 					+ "			WHERE enterprise_id = :enterpriseId  ";
			if(!StringUtil.isNullOrEmpty(time))
				queryString += " AND input_time = :time ";
			if(workId!=null) {
				queryString += " and work_id = :workId ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(workId!=null) {
				query.setParameter("workId",workId);
			}
			query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(time))
				query.setParameter("time",time);
			
			Integer result = query.executeUpdate();
			{
				return result;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> StatisticalWorkingHours(Integer enterpriseId,String startTime,String endTime,String nickName,List<String> roleId,Integer nowPage,Integer pageCount) {
		try {
 			String queryString = " SELECT uwt.id ,tu.id userTabId ,ifnull(SUM(work_time),0), tu.name,role_name "
 					+ "FROM tb_enterprise_userinfo tu  "
 					+ "		LEFT JOIN tb_res_user_work_time uwt ON tu.id=uwt.user_tab_id " ;
 					if(!StringUtil.isNullOrEmpty(startTime))
 						queryString += " AND uwt.input_time>=:startTime ";
 					if(!StringUtil.isNullOrEmpty(endTime))
 						queryString += " AND uwt.input_time<=:endTime ";
 					queryString+= "		LEFT JOIN tb_res_user_role ur ON ur.user_tab_id=tu.id "
 					+ "		LEFT JOIN tb_role tr ON tr.id=ur.role_id "
 					+ "WHERE tu.del_flag = 0 ";

 			if(enterpriseId!=null)
				queryString += " AND uwt.enterprise_Id=:enterpriseId ";
 			if(!StringUtil.isNullOrEmpty(nickName))
				queryString += " AND tu.nickname LIKE :nickName ";
			queryString += " GROUP BY tu.id "
					+ "ORDER BY SUM(work_time) DESC ";
			Query query = getEntityManager().createNativeQuery(queryString);
			
			if(enterpriseId!=null)
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(nickName))
				query.setParameter("nickName","%"+nickName+"%");
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			
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
	
	public Integer StatisticalWorkingHoursCount(Integer enterpriseId,String startTime,String endTime,String nickName,List<String> roleId) {
		try {
 			String queryString = " SELECT COUNT( distinct tu.id )  "
 					+ "FROM tb_enterprise_userinfo tu  "
 					+ "		LEFT JOIN tb_res_user_work_time uwt ON tu.id=uwt.user_tab_id " 
 					+ "WHERE tu.del_flag = 0 ";

 			if(enterpriseId!=null)
				queryString += " AND uwt.enterprise_id=:enterpriseId ";
 			if(!StringUtil.isNullOrEmpty(nickName))
				queryString += " AND tu.nickname LIKE :nickName ";
			Query query = getEntityManager().createNativeQuery(queryString);
			
			if(enterpriseId!=null)
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(nickName))
				query.setParameter("nickName","%"+nickName+"%");
			
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
	
	public Object[] getWorkTimeInfo(Integer id) {
		try {
 			String queryString = " SELECT uwt.id ,uwt.user_tab_id ,uwt.input_time, work_time, tu.name,uwt.work_id,tw.work_sn "
 					+ "FROM tb_res_user_work_time uwt  ";
 			queryString += "LEFT JOIN tb_enterprise_userinfo tu ON tu.id=uwt.user_tab_id " 
 					+ "		LEFT JOIN tb_work tw ON tw.id=uwt.work_id "
 					+ "WHERE tu.del_flag = 0  ";
			if(id!=null)
				queryString += " AND uwt.id=:id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(id!=null)
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
	
}
