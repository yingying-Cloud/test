package com.jinpinghu.db.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResCropFarmingFile;
import com.mysql.cj.util.StringUtils;

public class TbResCropFarmingFileDao extends BaseZDao{

	public TbResCropFarmingFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbResCropFarmingFile findById(Integer id) {
		try {
			String queryString = "from TbResCropFarmingFile where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbResCropFarmingFile> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public void DelResByFarmingId(Integer id) {
		try {
			String queryString = "UPDATE tb_res_crop_farming_file SET del_flag=1 WHERE crop_farming_id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> findFileById(Integer id) {
		try {
			String queryString = 
					 " SELECT "
					 + "ff.id,"
					 + "file_name,"
					 + "file_size,"
					 + "file_type,"
					 + "file_url "
					+ "FROM "
					+ " tb_res_crop_farming_file  ff "
					+ "	LEFT JOIN "
					+ "	tb_file tf "
					+ "	ON ff.file_id=tf.id "
					+ "where crop_farming_id = :id and ff.del_flag = 0";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer getFarmingFileCountByWorkSn(String workSn,String startTime,String endTime){
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" select count(distinct tf.id) from tb_res_crop_farming_file trcfff left join tb_file tf on trcfff.file_id = tf.id ");
			queryString.append(" left join tb_crop_farming tcf on trcfff.crop_farming_id = tcf.id ");
			queryString.append(" where  trcfff.del_flag = 0   ");
			queryString.append(" and tcf.work_id in (select tw.id from tb_work  where work_sn LIKE :workSn ) ");
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString.append(" and DATE_FORMAT(trcff.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString.append(" and DATE_FORMAT(trcff.input_time,'%Y-%m-%d') <= :endTime ");
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
	
	public List<Object[]> getFarmingFileByWorkSn(String workSn,String startTime,String endTime){
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" select tf.file_url,tf.file_type,tf.file_name from tb_res_crop_farming_file trcfff left join tb_file tf on trcfff.file_id = tf.id ");
			queryString.append(" left join tb_crop_farming tcf on trcfff.crop_farming_id = tcf.id ");
			queryString.append(" left join tb_res_crop_farming_work trcfw on trcfw.crop_farming_id = tcf.id ");
			queryString.append(" where  trcfff.del_flag = 0   ");
			if(!StringUtils.isNullOrEmpty(workSn))
				queryString.append(" and trcfw.work_id in (select tw.id from tb_work tw where work_sn LIKE :workSn ) ");
			if(!StringUtils.isNullOrEmpty(startTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isNullOrEmpty(endTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') <= :endTime ");
			queryString.append(" group by tf.id ");
			Query query = getEntityManager().createNativeQuery(queryString.toString());
			if(!StringUtils.isNullOrEmpty(workSn))
				query.setParameter("workSn", workSn);
			if(!StringUtils.isNullOrEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isNullOrEmpty(endTime))
				query.setParameter("endTime", endTime);
			List<Object[]> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
