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
import com.jinpinghu.db.bean.TbResCropFarmingTool;

public class TbResCropFarmingToolDao extends BaseZDao{

	public TbResCropFarmingToolDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbResCropFarmingTool findById(Integer id) {
		try {
			String queryString = "from TbResCropFarmingTool where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbResCropFarmingTool> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] getFarminToolInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	ft.id fid,"
 					+ "	tw.work_sn,"
 					+ "	te.name,"
 					+ "	ft.add_people,"
 					+ "	ft.tool_id,"
 					+ "	ft.tool_name,"
 					+ "	ft.specification,"
 					+ "	ft.unit,"
 					+ "	ft.num,"
 					+ "	ft.input_time,"
 					+ " tt.type ,"
 					+ " tw.id tid "
 					+ "FROM "
 					+ "	tb_res_crop_farming_tool ft LEFT JOIN tb_enterprise te ON ft.enterprise_id=te.id "
 					+ "	LEFT JOIN tb_work tw ON ft.work_id=tw.id "
 					+ " LEFT JOIN tb_tool tt ON tt.id=ft.tool_id "
 					+ "WHERE "
 					+ " ft.del_flag=0 "
 					+ " AND te.del_flag=0 "
 					+ " AND tw.del_flag=0 "
 					+ " AND tt.del_flag=0 "
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
	
	public List<Object[]> getFarminToolList(String workSn, String toolName, String enterpriseId, String type, String startTime, String endTime,
			Integer nowPage, Integer pageCount,String code,String isEffective) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	ft.id fid,"
 					+ "	tw.work_sn,"
 					+ "	te.name,"
 					+ "	ft.add_people,"
 					+ "	ft.tool_id,"
 					+ "	ft.tool_name,"
 					+ "	tt.specification,"
 					+ "	ft.unit,"
 					+ "	ft.num,"
 					+ "	ft.input_time,"
 					+ " tt.type,"
 					+ " tw.id tid, "
 					+ " ttp.name ttnm, "
 					+ " tt.production_units productionUnits "
 					+ "FROM "
 					+ "	tb_res_crop_farming_tool ft LEFT JOIN tb_enterprise te ON ft.enterprise_id=te.id "
 					+ "	LEFT JOIN tb_work tw ON tw.id=ft.work_id "
 					+ " LEFT JOIN tb_tool tt ON tt.id=ft.tool_id "
 					+ " LEFT JOIN tb_type ttp on ttp.id=tt.type "
 					+ "WHERE "
 					+ " ft.del_flag=0  AND te.del_flag=0 AND tw.del_flag=0 AND tt.del_flag=0 ";
 			if ("1".equals(isEffective)) {
				queryString += " AND tt.code in (SELECT distinct tt.code FROM tb_shopping_car tsc left join tb_tool tt on tt.id = tsc.tool_id left join tb_res_order_car troc on troc.car_id = tsc.id  left join tb_tool_order tto on tto.id = troc.order_id left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id where tto.del_flag = 0  ";
				if(!StringUtil.isNullOrEmpty(enterpriseId)) {
					queryString += " AND tloi.enterprise_id = :enterpriseId ";
				}
				queryString += " )";
			}
 			if (!StringUtils.isEmpty(code)) {
				queryString += " and tt.code = :code ";
			}
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(toolName))
				queryString += " AND ft.tool_name like :toolName ";
			if(!StringUtil.isNullOrEmpty(enterpriseId)) {
				queryString += " AND ft.enterprise_id = :enterpriseId ";
			}
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND ft.input_time >= :startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND ft.input_time <= :endTime ";
			if(!StringUtil.isNullOrEmpty(type))
				queryString += " AND tt.type = :type ";
			queryString += " ORDER BY ft.input_time DESC";
			Query query = getEntityManager().createNativeQuery(queryString);
			if (!StringUtils.isEmpty(code)) {
				query.setParameter("code", code);
			}
			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(toolName))
				query.setParameter("toolName","%"+toolName+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(!StringUtil.isNullOrEmpty(type))
				query.setParameter("type",type);
			
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
	
	public Integer getFarminToolListCount(String workSn, String toolName, String enterpriseId, String type, String startTime, 
			String endTime,String code,String isEffective) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(1) "
 					+ "FROM "
 					+ "	tb_res_crop_farming_tool ft LEFT JOIN tb_work tw ON tw.id=ft.work_id "
 					+ " LEFT JOIN tb_tool tt ON tt.id=ft.tool_id "
 					+ "WHERE "
 					+ " ft.del_flag=0 AND tw.del_flag=0 ";
 			if ("1".equals(isEffective)) {
				queryString += " AND tt.code in (SELECT distinct tt.code FROM tb_shopping_car tsc left join tb_tool tt on tt.id = tsc.tool_id left join tb_res_order_car troc on troc.car_id = tsc.id  left join tb_tool_order tto on tto.id = troc.order_id left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id where tto.del_flag = 0  ";
				if(!StringUtil.isNullOrEmpty(enterpriseId)) {
					queryString += " AND tloi.enterprise_id = :enterpriseId ";
				}
				queryString += " )";
			}
 			if (!StringUtils.isEmpty(code)) {
				queryString += " and tt.code = :code ";
			}
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND work_sn LIKE :workSn ";
			if(!StringUtil.isNullOrEmpty(toolName))
				queryString += " AND ft.tool_name like :toolName ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND ft.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND ft.input_time >= :startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND ft.input_time <= :endTime ";
			if(!StringUtil.isNullOrEmpty(type))
				queryString += " AND tt.type = :type ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if (!StringUtils.isEmpty(code)) {
				query.setParameter("code", code);
			}
			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(toolName))
				query.setParameter("toolName","%"+toolName+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(!StringUtil.isNullOrEmpty(type))
				query.setParameter("type",type);
			
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
	
	public List<TbResCropFarmingTool> findByWorkIdTime(Integer workId,String time){
		try {
			String queryString = " select tcft.* from tb_res_crop_farming_tool tcft where del_flag = 0 and work_id = :workId ";
			if(!StringUtils.isEmpty(time))
				queryString += " and date_format(input_time,'%Y-%m-%d %H:%i') = :time ";
			Query query = getEntityManager().createNativeQuery(queryString,TbResCropFarmingTool.class);
			query.setParameter("workId", workId);
			if(!StringUtils.isEmpty(time))
				query.setParameter("time", time);
			List<TbResCropFarmingTool> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<TbResCropFarmingTool> findByWorkIdTime(String workSn, String time){
		try {
			String queryString = " select tcft.* from tb_res_crop_farming_tool tcft inner join tb_work tw on tcft.work_id = tw.id where tcft.del_flag = 0 ";
			if(!StringUtils.isEmpty(workSn))
				queryString += " and work_sn = :workSn ";
			if(!StringUtils.isEmpty(time))
				queryString += " and date_format(tcft.input_time,'%Y-%m-%d') = :time ";
			Query query = getEntityManager().createNativeQuery(queryString,TbResCropFarmingTool.class);
			if(!StringUtils.isEmpty(workSn))
				query.setParameter("workSn", workSn);
			if(!StringUtils.isEmpty(time))
				query.setParameter("time", time);
			List<TbResCropFarmingTool> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> findByWorkIdTime2(String workSn, String time,Integer workId){
		try {
			String queryString = " select tcft.add_people addPeople,tcft.tool_id toolId,tcft.tool_name toolName, ";
			queryString += " tt.type toolType,tcft.specification specification,tcft.unit,tcft.num,date_format(tcft.input_time,'%Y-%m-%d') inputTime ";
			queryString += " from tb_res_crop_farming_tool tcft inner join tb_work tw on tcft.work_id = tw.id ";
			queryString += " left join tb_tool tt on tt.id = tcft.tool_id where tcft.del_flag = 0 ";
			if(!StringUtils.isEmpty(workSn))
				queryString += " and work_sn = :workSn ";
			if(!StringUtils.isEmpty(time))
				queryString += " and date_format(tcft.input_time,'%Y-%m-%d') = :time ";
			if(workId != null)
				queryString += " and tw.id = :workId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(workSn))
				query.setParameter("workSn", workSn);
			if(!StringUtils.isEmpty(time))
				query.setParameter("time", time);
			if(workId != null)
				query.setParameter("workId", workId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getFarminToolList(String workSn,String startTime) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	ft.id id,"
 					+ "	tw.work_sn wornSn,"
 					+ "	te.name enterpriseName,"
 					+ "	ft.add_people addPeople,"
 					+ "	ft.tool_id toolId,"
 					+ "	ft.tool_name toolName,"
 					+ "	ft.specification,"
 					+ "	ft.unit,"
 					+ "	ft.num,"
 					+ "	date_format(ft.input_time,'%Y-%m-%d %H:%i:%s') inputTime,"
 					+ " tt.type,"
 					+ " tw.id tid, "
 					+ " ttp.name typeName "
 					+ "FROM "
 					+ "	tb_res_crop_farming_tool ft LEFT JOIN tb_enterprise te ON ft.enterprise_id=te.id "
 					+ "	LEFT JOIN tb_work tw ON tw.id=ft.work_id "
 					+ " LEFT JOIN tb_tool tt ON tt.id=ft.tool_id "
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
