package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbWork;

public class TbWorkDao extends BaseZDao{

	public TbWorkDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public TbWork findById(Integer id) {
		try {
			String queryString = "from TbWork where id = :id and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbWork> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public TbWork findByWorkSn(String workSn) {
		try {
			String queryString = "from TbWork where workSn = :workSn and delFlag = 0 ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("workSn",workSn);
			List<TbWork> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public String getProductNameByWorkId(Integer workId) {
		try {
			String queryString = "select tb.product_name from tb_work tw left join tb_brand tb on tw.crop = tb.id where tw.id = :workId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("workId",workId);
			List<String> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public Object[] getWorkInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	tw.id workId,"
 					+ "	te.name,"
 					+ "	add_people,"
 					+ "	work_name,"
 					+ "	work_sn,"
 					+ "	land_block_sn,"
 					+ "	tw.area,"
 					+ "	tb.id brandId,"
 					+ "	tw.start_time,"
 					+ "	tw.end_time,"
 					+ "	recovery_time,"
 					+ "	expected_production,"
 					+ "	tw.input_time "
 					+ "	,tw.purchase_source "
					+ "	,tw.purchase_people "
					+ "	,date_format(tw.purchase_time,'%Y-%m-%d'),tag.greenhouses_name,tag.id greenhousesId,tb.product_name "
//					+ ",(select ifnull(SUM(work_time+0),0) from tb_res_user_work_time where work_id=tw.id ) workTime  "
// 					
//					+ " ,(SELECT sum(tt.number+0)   "
//		 			+ " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id where tt.del_flag=0 and record_type=1) inNum "
// 					
//					+ ",(SELECT sum(tt.number+0)   "
//					+ " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id where tt.del_flag=0 and record_type=2 ) outNum "
//					+ ",purchase_source "
 					+ "FROM "
 					+ "	tb_work tw LEFT JOIN tb_enterprise te ON tw.enterprise_id=te.id "
 					+ " LEFT JOIN tb_brand tb ON tb.id=tw.crop "
 					+ " LEFT join tb_Agricultural_Greenhouses tag on tag.id=tw.greenhouses_id "
 					+ "WHERE "
 					+ " tw.del_flag=0 "
 					+ " AND te.del_flag=0 "
 					+ " AND tw.id=:id ";
			
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
	
	public Object[] getWorkCropInfo(Integer id) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	tw.id workId,"
 					+ "	te.name,"
 					+ "	add_people,"
 					+ "	work_name,"
 					+ "	work_sn,"
 					+ "	land_block_sn,"
 					+ "	tw.area,"
 					+ "	tb.id brandId,"
 					+ "	tw.start_time,"
 					+ "	tw.end_time,"
 					+ "	recovery_time,"
 					+ "	expected_production,"
 					+ "	tw.input_time "
 					+ ",(select sum(num) from tb_res_crop_farming_tool rft "
 					+ " left join tb_tool tl on tl.id=rft.tool_id "
 					+ " where tl.type=12 and tl.unit='g' and rft.del_flag=0 and rft.work_id=tw.id"
 					+ " )fl"
 					+ ",(select sum(num) from tb_res_crop_farming_tool rft "
 					+ " left join tb_tool tl on tl.id=rft.tool_id "
 					+ " where tl.type=13 and tl.unit='ml' and rft.del_flag=0 and rft.work_id=tw.id"
 					+ " )ny,tw.enterprise_id "
 					+ ",(select ifnull(SUM(work_time+0),0) from tb_res_user_work_time where work_id=tw.id ) workTime  "
 					
					+ " ,(SELECT sum(tt.number+0)   "
					+ " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id where tt.del_flag=0 and record_type=1) inNum "
	
					+ ",(SELECT sum(tt.number+0)   "
					+ " FROM tb_plant_warehouse tt left join tb_brand tb on tt.brand_id = tb.id where tt.del_flag=0 and record_type=2 ) outNum "
					+ "	,tw.purchase_source "
 					+ " FROM "
 					+ "	tb_work tw LEFT JOIN tb_enterprise te ON tw.enterprise_id=te.id "
 					+ " LEFT JOIN tb_brand tb ON tb.id=tw.crop "
 					+ " WHERE "
 					+ " tw.del_flag=0 "
 					+ " AND te.del_flag=0 "
 					+ " AND tw.id=:id ";
			
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
	
	
	public List<Object[]> getWorkList(String search,String enterpriseId, String crop, String workSn, String startTime,
			String endTime, Integer nowPage, Integer pageCount,List<Integer> statusList,String brandId) {
		try {
 			String queryString = 
 					"  select * from (SELECT "
					+ "	tw.id,"
 					+ "	te.name,"
 					+ "	add_people,"
 					+ "	work_name,"
 					+ "	work_sn,"
 					+ "	land_block_sn,"
 					+ "	tw.area,"
 					+ "	tb.product_name,"
 					+ "	tw.start_time,"
 					+ "	tw.end_time,"
 					+ "	recovery_time,"
 					+ "	expected_production,"
 					+ "	tw.input_time "
 					
 					+ ",(select file_url from  tb_res_brand_file tbf  "
 					+ " left join tb_file tf on tf.id= tbf.file_id "
 					+ " where tbf.del_flag=0 and tbf.brand_id=tb.id limit 1) fileUrl "
 					
					+ "	,tw.purchase_source "
					+ "	,tw.purchase_people "
					+ "	,date_format(tw.purchase_time,'%Y-%m-%d'), "
					+ " case when date_format(now(),'%Y-%m-%d') < date_format(tw.start_time,'%Y-%m-%d') then 0 "
					+ " when date_format(now(),'%Y-%m-%d') >= date_format(tw.start_time,'%Y-%m-%d') and date_format(now(),'%Y-%m-%d') <= date_format(tw.end_time,'%Y-%m-%d') then 1 "
					+ " when date_format(now(),'%Y-%m-%d') > date_format(tw.end_time,'%Y-%m-%d') then 2 end status,tag.greenhouses_name "
					+ ",(select ifnull(SUM(work_time+0),0) from tb_res_user_work_time tru where work_id=tw.id ) workTime  "
 					+ "FROM "
 					+ "	tb_work tw LEFT JOIN tb_enterprise te ON tw.enterprise_id=te.id "
 					+ " LEFT JOIN tb_brand tb ON tb.id=tw.crop "
 					+ " LEFT join tb_Agricultural_Greenhouses tag on tag.id=tw.greenhouses_id "
 					+ "WHERE "
 					+ " tw.del_flag=0 "
 					+ " AND te.del_flag=0 "
 					+ " AND tb.del_flag=0 ";
 			if(statusList != null && statusList.size()>0) {
 				int status0 = 0,status1 = 0;
 				if(statusList.contains(0)) {
 					queryString += " and (date_format(now(),'%Y-%m-%d') < date_format(tw.start_time,'%Y-%m-%d') ";
 					status0 = 1;
 				}
 				if(statusList.contains(1) && status0 == 1) {
 					queryString += " or (date_format(now(),'%Y-%m-%d') >= date_format(tw.start_time,'%Y-%m-%d') and date_format(now(),'%Y-%m-%d') <= date_format(tw.end_time,'%Y-%m-%d')) ";
 					status1 = 1;
 				}else if(statusList.contains(1)) {
 					queryString += " and ((date_format(now(),'%Y-%m-%d') >= date_format(tw.start_time,'%Y-%m-%d') and date_format(now(),'%Y-%m-%d') <= date_format(tw.end_time,'%Y-%m-%d')) ";
 					status1 = 1;
 				}
 				if(statusList.contains(2) && (status0==1 || status1 == 1)) {
 					queryString += " or date_format(now(),'%Y-%m-%d') > date_format(tw.end_time,'%Y-%m-%d') ";
 				}else if(statusList.contains(2)){
 					queryString += " and (date_format(now(),'%Y-%m-%d') > date_format(tw.end_time,'%Y-%m-%d') ";
 				}
 				queryString += " ) ";
 			}
 			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND (work_name like :workSn OR work_sn LIKE :workSn ) ";
			if(!StringUtil.isNullOrEmpty(search))
				queryString += " AND (tb.product_name like :search OR work_sn LIKE :search or te.name like :search ) ";
			if(!StringUtil.isNullOrEmpty(crop))
				queryString += " AND tb.product_name like :crop ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND tw.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND tw.start_time >= :startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND tw.start_time <= :endTime ";
			if(!StringUtil.isNullOrEmpty(brandId))
				queryString += " AND tb.id = :brandId ";
			queryString += "ORDER BY tw.start_time DESC";
			queryString += " ) a order by case when a.status = 1 then 3 when a.status = 0 then 2 when a.status = 2 then 1 end desc,a.start_time desc,a.id desc ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(search))
				query.setParameter("search","%"+search+"%");
			if(!StringUtil.isNullOrEmpty(crop))
				query.setParameter("crop","%"+crop+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(!StringUtil.isNullOrEmpty(brandId))
				query.setParameter("brandId",brandId);
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
	
	public Integer getWorkListCount(String search, String enterpriseId, String crop, String workSn,String startTime,
			String endTime,List<Integer> statusList,String brandId) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ "	COUNT(1) "
 					+ "FROM "
 					+ "	tb_work tw LEFT JOIN tb_brand tb ON tb.id=tw.crop "
 					+ " left join tb_enterprise te on te.id = tw.enterprise_id "
 					+ "WHERE "
 					+ " tw.del_flag=0 "
 					+ " AND tb.del_flag=0 ";
 			if(statusList != null && statusList.size()>0) {
 				int status0 = 0,status1 = 0;
 				if(statusList.contains(0)) {
 					queryString += " and (date_format(now(),'%Y-%m-%d') < date_format(tw.start_time,'%Y-%m-%d') ";
 					status0 = 1;
 				}
 				if(statusList.contains(1) && status0 == 1) {
 					queryString += " or (date_format(now(),'%Y-%m-%d') >= date_format(tw.start_time,'%Y-%m-%d') and date_format(now(),'%Y-%m-%d') <= date_format(tw.end_time,'%Y-%m-%d')) ";
 					status1 = 1;
 				}else if(statusList.contains(1)) {
 					queryString += " and ((date_format(now(),'%Y-%m-%d') >= date_format(tw.start_time,'%Y-%m-%d') and date_format(now(),'%Y-%m-%d') <= date_format(tw.end_time,'%Y-%m-%d')) ";
 					status1 = 1;
 				}
 				if(statusList.contains(2) && (status0==1 || status1 == 1)) {
 					queryString += " or date_format(now(),'%Y-%m-%d') > date_format(tw.end_time,'%Y-%m-%d') ";
 				}else if(statusList.contains(2)){
 					queryString += " and (date_format(now(),'%Y-%m-%d') > date_format(tw.end_time,'%Y-%m-%d') ";
 				}
 				queryString += " ) ";
 			}
			if(!StringUtil.isNullOrEmpty(workSn))
				queryString += " AND (work_name like :workSn OR work_sn LIKE :workSn ) ";
			if(!StringUtil.isNullOrEmpty(search))
				queryString += " AND (tb.product_name like :search OR work_sn LIKE :search or te.name like :search ) ";
			if(!StringUtil.isNullOrEmpty(crop))
				queryString += " AND tb.product_name like :crop ";
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				queryString += " AND tw.enterprise_id = :enterpriseId ";
			if(!StringUtil.isNullOrEmpty(startTime))
				queryString += " AND tw.start_time >= :startTime ";
			if(!StringUtil.isNullOrEmpty(endTime))
				queryString += " AND tw.start_time <= :endTime ";
			if(!StringUtil.isNullOrEmpty(brandId))
				queryString += " AND tb.id = :brandId ";
			Query query = getEntityManager().createNativeQuery(queryString);

			if(!StringUtil.isNullOrEmpty(workSn))
				query.setParameter("workSn","%"+workSn+"%");
			if(!StringUtil.isNullOrEmpty(search))
				query.setParameter("search","%"+search+"%");
			if(!StringUtil.isNullOrEmpty(crop))
				query.setParameter("crop","%"+crop+"%");
			if(!StringUtil.isNullOrEmpty(enterpriseId))
				query.setParameter("enterpriseId",enterpriseId);
			if(!StringUtil.isNullOrEmpty(startTime))
				query.setParameter("startTime",startTime);
			if(!StringUtil.isNullOrEmpty(endTime))
				query.setParameter("endTime",endTime);
			if(!StringUtil.isNullOrEmpty(brandId))
				query.setParameter("brandId",brandId);
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
	
	public Map<String, Object> getSum(Integer enterpriseId){
		try {
			String queryString = " SELECT round(sum(area),2) area,"
					+ "(select sum(number*price) from tb_plant_warehouse where record_type=2 and del_flag=0 and work_id=tw.id) expectedProduction,"
					+ "GROUP_CONCAT( distinct tb.product_name) crop ";
			queryString += ",(select ifnull(SUM(work_time+0),0) from tb_res_user_work_time uwt   ";
			if(enterpriseId != null) {
				queryString += " where uwt.enterprise_id=:enterpriseId " ;
			}
			queryString += " )workTime ";	
			queryString += " FROM tb_work tw left join tb_brand tb on tw.crop = tb.id ";
			queryString += " where tw.del_flag = 0 ";
//			queryString += "  and DATE_FORMAT(now(),'%Y-%m-%d') >= DATE_FORMAT(tw.start_time,'%Y-%m-%d') and DATE_FORMAT(now(),'%Y-%m-%d') <= DATE_FORMAT(tw.end_time,'%Y-%m-%d')  ";
			if(enterpriseId != null)
				queryString += " and tw.enterprise_id = :enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Object[] getWorkInfo(Integer id,String workSn) {
		try {
 			String queryString = 
 					"  SELECT "
					+ "	tw.id workId,"
 					+ "	add_people,"
 					+ "	work_name,"
 					+ "	work_sn,"
 					+ "	land_block_sn,"
 					+ "	area,"
 					+ "	tb.id brandId,"
 					+ "	tw.start_time,"
 					+ "	tw.end_time,"
 					+ "	recovery_time,"
 					+ "	expected_production,"
 					+ "	tw.input_time, "
 					+ "  tb.product_name  "
 					+ "	,tw.purchase_source "
					+ "	,tw.purchase_people "
					+ "	,date_format(tw.purchase_time,'%Y-%m-%d') "
 					+ "FROM "
 					+ "	tb_work tw  "
 					+ " LEFT JOIN tb_brand tb ON tb.id=tw.crop "
 					+ "WHERE "
 					+ " tw.del_flag=0 "
 					+ "";
 			if(id!=null) {
 				queryString+= " AND tw.id=:id ";
 			}	
 			if(!StringUtils.isNullOrEmpty(workSn)) {
 				queryString+= " AND tw.work_sn=:workSn ";
 			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(id!=null) {
				query.setParameter("id",id);
			}
			if(!StringUtils.isNullOrEmpty(workSn)) {
				query.setParameter("workSn",workSn);
			}
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
