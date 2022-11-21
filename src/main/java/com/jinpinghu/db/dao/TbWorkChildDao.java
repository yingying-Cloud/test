package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbWorkChild;
import com.mysql.cj.util.StringUtils;

public class TbWorkChildDao extends BaseZDao{

	public TbWorkChildDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	
	public TbWorkChild findById(Integer id) {
		try {
			String queryString = "from TbWorkChild where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbWorkChild> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TbWorkChild> findByWorkId(Integer workId) {
		try {
			String queryString = "from TbWorkChild where workId = :workId order by startTime asc ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("workId",workId);
			List<TbWorkChild> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getWorkChildList(Integer workId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	id,"
 					+ "	work_id,"
 					+ "	name,"
 					+ "	start_time,"
 					+ "	end_time "
 					+ "FROM "
 					+ "	tb_work_child  "
 					+ "WHERE "
 					+ " work_id=:workId ";

			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("workId", workId);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Object[]> getWorkChildListTime(String workSn,Integer enterpriseId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	twc.id,"
 					+ "	twc.work_id,"
 					+ "	twc.name,"
 					+ "	twc.start_time,"
 					+ "	twc.end_time, "
 					+ "	tw.work_sn "
 					+ "FROM "
 					+ "	tb_work_child twc left join tb_work tw on twc.work_id=tw.id  "
 					+ "WHERE 1=1 " ;
 			if(!StringUtils.isNullOrEmpty(workSn)) {
 				queryString += " and tw.work_sn like :workSn ";
 			}
 			if(enterpriseId!=null) {
 				queryString += " and tw.enterprise_id = :enterpriseId ";
 			}
 			queryString += " order by twc.start_time asc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("workSn", "%"+workSn+"%");
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public void delById(Integer id) {
		try {
			String queryString = 
					" DELETE FROM  tb_work_child  "
					+ "where work_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getFarmingFileCountByWorkSn(String workSn,String startTime,String endTime){
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" select count(distinct tf.id) from tb_res_crop_farming_file trcfff left join tb_file tf on trcfff.file_id = tf.id ");
			queryString.append(" left join tb_crop_farming tcf on trcfff.crop_farming_id = tcf.id left join tb_res_crop_farming_work trcfw on trcfw.crop_farming_id = tcf.id ");
			queryString.append(" where  trcfff.del_flag = 0   ");
			queryString.append(" and trcfw.work_id in (select tw.id from tb_work tw where work_sn LIKE :workSn ) ");
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') <= :endTime ");
			Query query = getEntityManager().createNativeQuery(queryString.toString());
			query.setParameter("workSn", workSn);
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public List<String> getTimeList(Integer workId,String startTime,String endTime){
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" select DISTINCT a.tm from (SELECT DATE_FORMAT(trcff.input_time,'%Y-%m-%d') tm "
					+ "FROM tb_crop_farming trcff left join tb_res_crop_farming_work trcfw on trcff.id = trcfw.crop_farming_id where del_flag=0 ");
			if(workId != null)
				queryString.append(" and trcfw.work_id = :workId ");
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString.append(" and DATE_FORMAT(trcff.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString.append(" and DATE_FORMAT(trcff.input_time,'%Y-%m-%d') <= :endTime ");
			queryString.append("union all ");
			queryString.append(" SELECT DATE_FORMAT(tcf.input_time,'%Y-%m-%d') tm "
					+ "FROM tb_crop_farming tcf  where del_flag=0 ");
			if(workId != null)
				queryString.append(" and tcf.work_id = :workId ");
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') <= :endTime ");
			queryString.append("union all ");
			queryString.append(" SELECT DATE_FORMAT(tcft.input_time,'%Y-%m-%d') tm "
					+ "FROM tb_res_crop_farming_tool tcft  where del_flag=0 ");
			if(workId != null)
				queryString.append(" and tcft.work_id = :workId ");
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString.append(" and DATE_FORMAT(tcft.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString.append(" and DATE_FORMAT(tcft.input_time,'%Y-%m-%d') <= :endTime ");
			queryString.append(" ) a  ORDER BY a.tm DESC ");
			Query query = getEntityManager().createNativeQuery(queryString.toString());
			if(workId != null)
				query.setParameter("workId", workId);
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
			List<String> timeList = query.getResultList();
			if (timeList != null && timeList.size()>0) {
				return timeList;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
}
