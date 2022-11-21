package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbCropFarming;

@SuppressWarnings("unchecked")
public class TbCropFarmingDao extends BaseZDao{

	public TbCropFarmingDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public TbCropFarming findById(Integer id) {
		try {
			String queryString = "from TbCropFarming where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbCropFarming> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void delFarmingById(Integer id) {
		try {
			String queryString = 
					 " UPDATE "
					+ "	tb_crop_farming cf LEFT JOIN tb_res_crop_farming_file ff ON cf.id=ff.crop_farming_id "
					+ "SET ff.del_flag=1 , cf.del_flag=1 "
					+ "WHERE cf.id = :id";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id",id);
			query.executeUpdate();
		
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getFarmingInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	cf.id cid,"
 					+ "	group_concat(distinct tw.work_sn),"
 					+ "	te.name,"
 					+ "	cf.describe_,"
 					+ "	cf.add_people,"
 					+ "	cf.input_time,"
 					+ " tw.id tid "
 					+ "FROM "
 					+ "	tb_crop_farming cf LEFT JOIN tb_enterprise te ON cf.enterprise_id=te.id "
 					+ "	LEFT JOIN tb_res_crop_farming_work trcfw ON cf.id = trcfw.crop_farming_id left join tb_work tw on tw.id = trcfw.work_id "
 					+ "WHERE "
 					+ " cf.del_flag=0 AND te.del_flag=0 "
 					+ " AND cf.id = :id ";
			
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
	
	
	public List<Object[]> getFarmingList(String workSn, String enterpriseId, String startTime, String endTime,
			Integer nowPage, Integer pageCount) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	cf.id cid, "
 					+ "	tw.work_sn,"
 					+ "	te.name,"
 					+ "	cf.describe_,"
 					+ "	cf.add_people,"
 					+ "	cf.input_time,"
 					+ " tw.id tid "
 					+ " ,tb.product_name "
 					+ " FROM "
 					+ "	tb_crop_farming cf LEFT JOIN tb_enterprise te ON cf.enterprise_id=te.id "
 					+ " left join tb_res_crop_farming_work trcfw on trcfw.crop_farming_id = cf.id "
 					+ "	LEFT JOIN tb_work tw ON trcfw.work_id = tw.id "
 					+ " left join tb_brand tb on tb.id=tw.crop  "
 					+ " WHERE "
 					+ " cf.del_flag=0  AND te.del_flag=0 AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND cf.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND cf.input_time >= :startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND cf.input_time <= :endTime ";
			queryString+=" ORDER BY cf.input_time DESC";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
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
	
	public Integer getFarmingListCount(String workSn, String enterpriseId, String startTime, String endTime) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(1) "
 					+ "FROM "
 					+ "	tb_crop_farming cf left join tb_res_crop_farming_work trcfw on trcfw.crop_farming_id = cf.id LEFT JOIN tb_work tw ON trcfw.work_id=tw.id "
 					+ "WHERE "
 					+ " cf.del_flag=0 AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND cf.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND cf.input_time >= :startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND cf.input_time <= :endTime ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			
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
	

	public List<String> getFarmingTimeList(String workSn,String startTime,String endTime,Integer enterpriseId,Integer nowPage,Integer pageCount,Integer workId){
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" select DISTINCT a.tm from(SELECT DATE_FORMAT(tcf.input_time,'%Y-%m-%d') tm ");
			queryString.append(" from tb_crop_farming tcf inner join tb_res_crop_farming_work cfw on cfw.crop_farming_id=tcf.id inner join tb_work tw on tw.id=cfw.work_id  inner join tb_brand tb on tb.id=tw.crop where tcf.del_flag=0 ");
			if(!StringUtils.isEmpty(workSn))
				queryString.append(" and (tw.work_sn like :workSn or  tb.product_name like :workSn ) ");
			if(!StringUtils.isEmpty(startTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isEmpty(endTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') <= :endTime ");
			if(enterpriseId!=null) {
				queryString.append(" and tw.enterprise_id=:enterpriseId ");
			}
			if(workId!=null) {
				queryString.append(" and tw.id = :workId ");
			}
			queryString.append(" union all ");
			queryString.append(" SELECT DATE_FORMAT(tcf.input_time,'%Y-%m-%d') tm ");
			queryString.append(" from tb_res_crop_farming_tool tcf inner join tb_work tw on tw.id=tcf.work_id  inner join tb_brand tb on tb.id=tw.crop where tcf.del_flag=0 ");
			if(!StringUtils.isEmpty(workSn))
				queryString.append(" and (tw.work_sn like :workSn or  tb.product_name like :workSn ) ");
			if(!StringUtils.isEmpty(startTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') >= :startTime ");
			if(!StringUtils.isEmpty(endTime))
				queryString.append(" and DATE_FORMAT(tcf.input_time,'%Y-%m-%d') <= :endTime ");
			if(enterpriseId!=null) {
				queryString.append(" and tw.enterprise_id=:enterpriseId ");
			}
			if(workId!=null) {
				queryString.append(" and tw.id = :workId ");
			}
			queryString.append(" ) a ORDER BY a.tm DESC ");
			Query query = getEntityManager().createNativeQuery(queryString.toString());
			if(!StringUtils.isEmpty(workSn))
				query.setParameter("workSn", "%"+workSn+"%");
			if(!StringUtils.isEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isEmpty(endTime))
				query.setParameter("endTime", endTime);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(workId!=null) {
				query.setParameter("workId", workId);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
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
	
	public List<Map<String, Object>> getFarmingList(String workSn, String time,Integer enterpriseId,Integer workId
			) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	cf.id id, "
 					+ "	group_concat(distinct tw.work_sn) workSn,"
 					+ "	te.name enterpriseName,"
 					+ "	cf.describe_ 'describe',"
 					+ "	cf.add_people addPeople,"
 					+ "	date_format(cf.input_time,'%H:%i:%s') inputTime,"
 					+ " group_concat(distinct tw.id) workId, "
 					+ " group_concat(distinct tb.product_name) productName, "
 					+ " (SELECT CONCAT('[',GROUP_CONCAT(CONCAT('{\"fileType\":\"',ifnull(wf.file_type,''),'\"'), "
 					+ "CONCAT(',\"id\":\"',ifnull(wf.id,''),'\"'),"
 					+ "CONCAT(',\"fileName\":\"',ifnull(wf.file_name,''),'\"'),"
 					+ "CONCAT(',\"fileSize\":\"',ifnull(wf.file_size,''),'\"'), "
 		            +" CONCAT(',\"fileUrl\":\"',ifnull(wf.file_url,'')),'\"}'),']')  "
 		            +"     FROM tb_file AS wf, tb_res_crop_farming_file AS rrf WHERE rrf.file_id=wf.id AND rrf.crop_farming_id=cf.id ) as file "
 					+ "FROM "
 					+ "	tb_crop_farming cf LEFT JOIN tb_enterprise te ON cf.enterprise_id=te.id "
 					+ " inner join tb_res_crop_farming_work cfw on cfw.crop_farming_id=cf.id "
 					+ "	inner JOIN tb_work tw ON cfw.work_id=tw.id "
 					+ " inner join tb_brand tb on tb.id=tw.crop "
 					+ "WHERE "
 					+ " cf.del_flag=0  AND te.del_flag=0 AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(time))
				queryString += " AND date(cf.input_time) = :time ";
			if(enterpriseId!=null) {
				queryString += " AND tw.enterprise_id = :enterpriseId ";
			}
			if(workId != null)
				queryString += " and tw.id = :workId ";
			queryString += " group by cf.id ";
			queryString+=" ORDER BY cf.input_time DESC";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(time))
				query.setParameter("time",time);
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(workId != null)
				query.setParameter("workId", workId);
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
	
	public List<Object[]> statisticsFarmingByGrowth(Integer workId,String startTime,String endTime){
		try {
			String queryString = " select * from (SELECT count(DISTINCT tf.id) count,DATE_FORMAT(tcf.input_time,'%m-%d %H:%i') time,GROUP_CONCAT(tf.file_url) fileUrl ";
			queryString += " FROM tb_crop_farming tcf left join tb_res_crop_farming_file trcff on tcf.id = trcff.crop_farming_id ";
			queryString += " left join tb_file tf on trcff.file_id = tf.id left join tb_res_crop_farming_work trcfw on trcfw.crop_farming_id = tcf.id where tcf.del_flag = 0 ";
			if(workId != null)
				queryString += " and trcfw.work_id = :workId ";
			if(!StringUtils.isEmpty(startTime))
				queryString += " and date_format(tcf.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isEmpty(endTime))
				queryString += " and date_format(tcf.input_time,'%Y-%m-%d') <= :endTime ";
			queryString += " group by DATE_FORMAT(tcf.input_time,'%Y-%m-%d %H:%i') ";
			queryString += " union ";
			queryString += " SELECT count(DISTINCT tf.id),DATE_FORMAT(tcft.input_time,'%m-%d %H:%i'),GROUP_CONCAT(tf.file_url) ";
			queryString += " FROM tb_res_crop_farming_tool tcft left join tb_res_crop_farming_tool_file trcftf on tcft.id = trcftf.crop_farming_tool_id ";
			queryString += " left join tb_file tf on trcftf.file_id = tf.id where tcft.del_flag = 0 ";
			if(workId != null)
				queryString += " and tcft.work_id = :workId ";
			if(!StringUtils.isEmpty(startTime))
				queryString += " and date_format(tcft.input_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isEmpty(endTime))
				queryString += " and date_format(tcft.input_time,'%Y-%m-%d') <= :endTime ";
			queryString += " group by DATE_FORMAT(tcft.input_time,'%Y-%m-%d %H:%i') ";
			queryString += " )a order by a.time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(workId != null)
				query.setParameter("workId", workId);
			if(!StringUtils.isEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isEmpty(endTime))
				query.setParameter("endTime", endTime);
			List<Object[]> list = query.getResultList();
			if(list != null && list.size()>0)
				return list;
			return null;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public List<TbCropFarming> findByWorkIdTime(Integer workId,String time){
		try {
			String queryString = " select tcf.* from tb_crop_farming tcf left join tb_res_crop_farming_work trcfw on  ";
			queryString += " trcfw.crop_farming_id = tcf.id where del_flag = 0 and trcfw.work_id = :workId ";
			if(!StringUtils.isEmpty(time))
				queryString += " and date_format(input_time,'%Y-%m-%d %H:%i') = :time ";
			queryString += " group by tcf.id ";
			Query query = getEntityManager().createNativeQuery(queryString,TbCropFarming.class);
			query.setParameter("workId", workId);
			if(!StringUtils.isEmpty(time))
				query.setParameter("time", time);
			List<TbCropFarming> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getAllFarmingList(String workSn, String startTime) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	cf.id id, "
 					+ "	tw.work_sn wornSn,"
 					+ "	te.name name,"
 					+ "	cf.describe_ 'describe',"
 					+ "	cf.add_people addPeople,"
 					+ "	date_format(cf.input_time,'%Y-%m-%d %H:%i:%s') inputTime,"
 					+ " tw.id tid, "
 					+ " tb.product_name productName "
 					+ "FROM "
 					+ "	tb_crop_farming cf LEFT JOIN tb_enterprise te ON cf.enterprise_id=te.id "
 					+ " left join tb_res_crop_farming_work trcfw on trcfw.crop_farming_id = cf.id "
 					+ "	LEFT JOIN tb_work tw ON trcfw.work_id=tw.id "
 					+ " left join tb_brand tb on tb.id=tw.crop "
 					+ "WHERE "
 					+ " cf.del_flag=0  AND te.del_flag=0 AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
 			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND date_format(cf.input_time,'%Y-%m-%d') = :startTime ";
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
