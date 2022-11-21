package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbResCropFarmingWater;

public class TbResCropFarmingWaterDao extends BaseZDao{

	public TbResCropFarmingWaterDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbResCropFarmingWater findById(Integer id) {
		try {
			String queryString = "from TbResCropFarmingWater where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbResCropFarmingWater> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getFarminWaterInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	ft.id fid,"
 					+ "	tw.work_sn,"
 					+ "	te.name,"
 					+ "	ft.add_people,"
 					+ "	ft.traffic,"
 					+ "	ft.start_irrigation_time,"
 					+ "	ft.end_irrigation_time,"
 					+ "	ft.water_amount,"
 					+ "	ft.input_time,"
 					+ " ft.describe_ ,"
 					+ " tw.id tid "
 					+ "FROM "
 					+ "	tb_res_crop_farming_water ft LEFT JOIN tb_enterprise te ON ft.enterprise_id=te.id "
 					+ "	LEFT JOIN tb_work tw ON ft.work_id=tw.id "
 					+ "WHERE "
 					+ " ft.del_flag=0 "
 					+ " AND te.del_flag=0 "
 					+ " AND tw.del_flag=0 "
 					+ "	AND ft.id=:id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id", id);			
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> getFarminWaterList(String workSn, String addPeople, String enterpriseId, String startTime, String endTime,
			Integer nowPage, Integer pageCount) {
		try {
 			String queryString = 
 					"  SELECT "
 							+ "	ft.id fid,"
 		 					+ "	tw.work_sn,"
 		 					+ "	te.name,"
 		 					+ "	ft.add_people,"
 		 					+ "	ft.traffic,"
 		 					+ "	ft.start_irrigation_time,"
 		 					+ "	ft.end_irrigation_time,"
 		 					+ "	ft.water_amount,"
 		 					+ "	ft.input_time,"
 		 					+ " ft.describe_ ,"
 		 					+ " tw.id tid, "
 		 					+" TIMESTAMPDIFF(HOUR,ft.start_irrigation_time,ft.end_irrigation_time) "
 					+ "FROM "
 					+ "	tb_res_crop_farming_water ft LEFT JOIN tb_enterprise te ON ft.enterprise_id=te.id "
 					+ "	LEFT JOIN tb_work tw ON tw.id=ft.work_id "
 					+ "WHERE "
 					+ " ft.del_flag=0  AND te.del_flag=0 AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(addPeople))
				queryString += " AND ft.add_people like :addPeople ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND ft.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND date(ft.input_time) = :startTime ";
//			if(!StringUtil.isNullOrEmpty(endTime))
//				queryString += " AND ft.input_time <= :endTime ";
			queryString += " ORDER BY ft.input_time DESC";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(addPeople))
				query.setParameter("addPeople","%"+addPeople+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
//			if(!StringUtil.isNullOrEmpty(endTime))
//				query.setParameter("endTime",endTime);
			
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
	
	public Integer getFarminWaterListCount(String workSn, String addPeople, String enterpriseId, String startTime, String endTime) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(1) "
 					+ "FROM "
 					+ "	tb_res_crop_farming_water ft LEFT JOIN tb_work tw ON tw.id=ft.work_id "
 					+ "WHERE "
 					+ " ft.del_flag=0 AND tw.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(addPeople))
				queryString += " AND ft.add_people like :addPeople ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND ft.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND date(ft.input_time) = :startTime ";
//			if(!StringUtil.isNullOrEmpty(endTime))
//				queryString += " AND ft.input_time <= :endTime ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(addPeople))
				query.setParameter("addPeople","%"+addPeople+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
//			if(!StringUtil.isNullOrEmpty(endTime))
//				query.setParameter("endTime",endTime);
			
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
	
	public List<TbResCropFarmingWater> findByWorkIdTime(Integer workId,String time){
		try {
			String queryString = " select tcft.* from tb_res_crop_farming_water tcft where del_flag = 0 and work_id = :workId ";
			if(!StringUtils.isEmpty(time))
				queryString += " and date_format(input_time,'%Y-%m-%d %H:%i') = :time ";
			Query query = getEntityManager().createNativeQuery(queryString,TbResCropFarmingWater.class);
			query.setParameter("workId", workId);
			if(!StringUtils.isEmpty(time))
				query.setParameter("time", time);
			List<TbResCropFarmingWater> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<TbResCropFarmingWater> findByWorkIdTime(String workSn, String time){
		try {
			String queryString = " select tcft.* from tb_res_crop_farming_water tcft inner join tb_work tw on tcft.work_id = tw.id where tcft.del_flag = 0 ";
			if(!StringUtils.isEmpty(workSn))
				queryString += " and work_sn = :workSn ";
			if(!StringUtils.isEmpty(time))
				queryString += " and date_format(tcft.input_time,'%Y-%m-%d') = :time ";
			Query query = getEntityManager().createNativeQuery(queryString,TbResCropFarmingWater.class);
			if(!StringUtils.isEmpty(workSn))
				query.setParameter("workSn", workSn);
			if(!StringUtils.isEmpty(time))
				query.setParameter("time", time);
			List<TbResCropFarmingWater> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getFarminWaterList(String workSn,String startTime) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	ft.id id,"
 					+ "	tw.work_sn wornSn,"
 					+ "	te.name enterpriseName,"
 					+ "	ft.add_people addPeople,"
 					+ "	ft.water_id waterId,"
 					+ "	ft.water_name waterName,"
 					+ "	ft.specification,"
 					+ "	ft.unit,"
 					+ "	ft.num,"
 					+ "	date_format(ft.input_time,'%Y-%m-%d %H:%i:%s') inputTime,"
 					+ " tt.type,"
 					+ " tw.id tid, "
 					+ " ttp.name typeName "
 					+ "FROM "
 					+ "	tb_res_crop_farming_water ft LEFT JOIN tb_enterprise te ON ft.enterprise_id=te.id "
 					+ "	LEFT JOIN tb_work tw ON tw.id=ft.work_id "
 					+ " LEFT JOIN tb_water tt ON tt.id=ft.water_id "
 					+ " LEFT JOIN tb_type ttp on ttp.id=tt.type "
 					+ "WHERE "
 					+ " ft.del_flag=0  AND te.del_flag=0 AND tw.del_flag=0 AND tt.del_flag=0 ";
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND date_format(ft.input_time,'%Y-%m-%d') = :startTime ";
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
