package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbPlantServiceOrder;

public class TbPlantServiceOrderDao extends BaseZDao{

	public TbPlantServiceOrderDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbPlantServiceOrder findById(Integer id) {
		try {
			String queryString = " from TbPlantServiceOrder where delFlag = 0 and id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id", id);
			List<TbPlantServiceOrder>  list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public Map<String,Object> getPlantServiceOrderInfo(Integer id) {
		try {
			String queryString = " SELECT tppo.id,tpp.name plantServiceName,tpp.price perPrice,te.name plantServiceEnterpriseName,DATE_FORMAT(tppo.input_time,'%Y-%m-%d %H:%m:%s') inputTime, ";
			queryString += " tpp.order_describe orderDescribe,tu.name name,tu.mobile,tppo.order_number,tppo.service_name serviceName,tppo.status, ";
			queryString += " DATE_FORMAT(tppo.service_start_time,'%Y-%m-%d') serviceStartTime,tppo.area,tppo.price allPrice,DATE_FORMAT(tppo.time_receive,'%Y-%m-%d %H:%m:%s') receiveTime, ";
			queryString += " DATE_FORMAT(tppo.time_confirm,'%Y-%m-%d %H:%m:%s') confirmTime,DATE_FORMAT(tppo.time_cancle,'%Y-%m-%d %H:%m:%s') cancelTime, ";
			queryString += " tppoc.id completeId,tppoc.area completeArea,tppoc.price completePrice,tppoc.content completeContent,DATE_FORMAT(tppoc.complete_time,'%Y-%m-%d') completeTime,";
			queryString += " date_format(tpp.start_time,'%Y-%m-%d') startTime,date_format(tpp.end_time,'%Y-%m-%d') endTime,server_type sserverType,tppo.day, ";
			queryString += " ifnull(tppo.contact,'') contact,ifnull(tppo.phone,'') phone,ifnull(tppo.address,'') address,DATE_FORMAT(tppo.service_end_time,'%Y-%m-%d') serviceEndTime";
			queryString += " FROM tb_plant_service_order tppo left join tb_plant_service tpp on tppo.plant_service_id = tpp.id ";
			queryString += " left join tb_enterprise te on te.id = tpp.enterprise_id left join tb_res_user_enterprise rue on te.id = rue.enterprise_id ";
			queryString += " left join tb_user tu on tu.id = rue.user_tab_id left join tb_plant_service_order_completion tppoc on tppo.id = tppoc.plant_service_order_id where tppo.del_flag = 0 and tppo.id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id", id);
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
	
	public List<Map<String,Object>> getPlantServiceOrderList(String name,String startTime,String endTime,Integer plantEnterpriseId,
			Integer plantServiceEnterpriseId,Integer status,Integer nowPage,Integer pageCount,Integer enterpriseId) {
		try {
			String queryString = " SELECT tppo.id,tppo.order_number orderNumber,pe.name plantEnterpriseName, ";
			queryString += " ppe.name plantServiceEnterpriseName,tppo.price,tppo.area,DATE_FORMAT(tppo.service_start_time,'%Y-%m-%d')serviceStartTime, ";
			queryString += " tpp.name serviceName,tppo.status,tppo.day,tppo.is_evaluate isEvaluate,DATE_FORMAT(tppo.service_end_time,'%Y-%m-%d')serviceEndTime  ";
			queryString += " FROM tb_plant_service_order tppo left join tb_plant_service tpp on tpp.id = tppo.plant_service_id ";
			queryString += " left join tb_enterprise pe on tppo.plant_enterprise_id = pe.id ";
			queryString += " left join tb_enterprise ppe on tppo.plant_service_enterprise_id = ppe.id where tppo.del_flag = 0 ";
			if(!StringUtils.isEmpty(name))
				queryString += " and tppo.service_name like :name ";
			if(!StringUtils.isEmpty(startTime))
				queryString += " and date_format(tppo.service_end_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isEmpty(endTime))
				queryString += " and date_format(tppo.service_start_time,'%Y-%m-%d') <= :endTime ";
			if(plantEnterpriseId != null)
				queryString += " and tppo.plant_enterprise_id = :plantEnterpriseId ";
			if(plantServiceEnterpriseId != null)
				queryString += " and tppo.plant_service_enterprise_id = :plantServiceEnterpriseId ";
			if(enterpriseId != null)
				queryString += " and (tppo.plant_enterprise_id = :enterpriseId or tppo.plant_service_enterprise_id = :enterpriseId) ";
			if(status != null)
				queryString += " and tppo.status = :status ";
			queryString += " group by tppo.id ";
			queryString += " order by tppo.input_time desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(!StringUtils.isEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isEmpty(endTime))
				query.setParameter("endTime", endTime);
			if(plantEnterpriseId != null)
				query.setParameter("plantEnterpriseId", plantEnterpriseId);
			if(plantServiceEnterpriseId != null)
				query.setParameter("plantServiceEnterpriseId", plantServiceEnterpriseId);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(status != null)
				query.setParameter("status", status);
			if(nowPage != null && pageCount != null)
				query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
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
	
	public Integer getPlantServiceOrderCount(String name,String startTime,String endTime,Integer plantEnterpriseId,
			Integer plantServiceEnterpriseId,Integer status,Integer enterpriseId) {
		try {
			String queryString = " SELECT count(distinct tppo.id) FROM tb_plant_service_order tppo ";
			queryString += " left join tb_enterprise pe on tppo.plant_enterprise_id = pe.id ";
			queryString += " left join tb_enterprise ppe on tppo.plant_service_enterprise_id = ppe.id where tppo.del_flag = 0 ";
			if(!StringUtils.isEmpty(name))
				queryString += " and tppo.service_name like :name ";
			if(!StringUtils.isEmpty(startTime))
				queryString += " and date_format(tppo.service_end_time,'%Y-%m-%d') >= :startTime ";
			if(!StringUtils.isEmpty(endTime))
				queryString += " and date_format(tppo.service_start_time,'%Y-%m-%d') <= :endTime ";
			if(plantEnterpriseId != null)
				queryString += " and tppo.plant_enterprise_id = :plantEnterpriseId ";
			if(plantServiceEnterpriseId != null)
				queryString += " and tppo.plant_service_enterprise_id = :plantServiceEnterpriseId ";
			if(enterpriseId != null)
				queryString += " and (tppo.plant_enterprise_id = :enterpriseId or tppo.plant_service_enterprise_id = :enterpriseId) ";
			if(status != null)
				queryString += " and tppo.status = :status ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(!StringUtils.isEmpty(name))
				query.setParameter("name", "%"+name+"%");
			if(!StringUtils.isEmpty(startTime))
				query.setParameter("startTime", startTime);
			if(!StringUtils.isEmpty(endTime))
				query.setParameter("endTime", endTime);
			if(plantEnterpriseId != null)
				query.setParameter("plantEnterpriseId", plantEnterpriseId);
			if(plantServiceEnterpriseId != null)
				query.setParameter("plantServiceEnterpriseId", plantServiceEnterpriseId);
			if(enterpriseId != null)
				query.setParameter("enterpriseId", enterpriseId);
			if(status != null)
				query.setParameter("status", status);
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	public List<Object[]> getStatusCount(Integer plantEnterpriseId,Integer plantServiceEnterprise) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tppo.id),tppo.status,sum(tppo.price+0),sum(tppo.area+0) ");
			sb.append(" from tb_plant_service_order tppo left join tb_enterprise pe on tppo.plant_enterprise_id = pe.id  ");
			sb.append(" where tppo.del_flag=0 ");
			if(plantEnterpriseId!=null) {
				sb.append(" and  tppo.plant_enterprise_id=:plantEnterpriseId");
			}
			if(plantServiceEnterprise!=null) {
				sb.append(" and  tppo.plant_service_enterprise_id=:plantServiceEnterprise");
			}
			sb.append(" group by tppo.status ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(plantServiceEnterprise!=null) {
				query.setParameter("plantServiceEnterprise", plantServiceEnterprise);
			}
			if(plantEnterpriseId!=null) {
				query.setParameter("plantEnterpriseId", plantEnterpriseId);
			}
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Object[] getCompleteCount(Integer plantEnterpriseId ) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tppo.id),count( DISTINCT tppo.plant_service_enterprise_id),sum(tppo.price+0),sum(tppo.area+0) ");
			sb.append(" from tb_plant_service_order tppo left join tb_enterprise pe on tppo.plant_enterprise_id = pe.id  ");
			sb.append(" where tppo.del_flag=0 and tppo.status=3 ");
			if(plantEnterpriseId!=null) {
				sb.append(" and  tppo.plant_service_enterprise_id=:plantEnterpriseId");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(plantEnterpriseId!=null) {
				query.setParameter("plantEnterpriseId", plantEnterpriseId);
			}
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] statisticalAllCompleteOrder(String enterpriseName,String dscd,Integer enterpriseId,String startTime,String endTime) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tppo.id),count( DISTINCT tppo.plant_service_enterprise_id),sum(tppo.price+0),sum(tppo.area+0) ");
			sb.append(" from tb_plant_service_order tppo left join tb_enterprise pe on tppo.plant_service_enterprise_id = pe.id  ");
			sb.append(" where tppo.del_flag=0 and tppo.status=3 and pe.enterprise_type=2  ");
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and pe.name like :enterpriseName ");
			}
			if(!StringUtils.isEmpty(dscd)) {
				sb.append(" and pe.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tppo.plant_service_enterprise_id=:enterpriseId ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and date(tppo.input_time) <= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and date(tppo.input_time) >= :endTime ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isEmpty(enterpriseName)) {
				query.setParameter("enterpriseName", "%"+enterpriseName+"%");
			}
			if(!StringUtils.isEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Map<String, Object> getOrderCountSum(Integer enterpriseId,String startTime,String endTime) {
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" count(id) count,ifnull(cast( sum( roc.price ) AS DECIMAL ( 10, 2 ) ),0)  sumPrice,ifnull(cast( sum( roc.area ) AS DECIMAL ( 10, 2 ) ),0)  sumArea "); 
			sb.append(" FROM ");
			sb.append(" tb_plant_service_order roc ");
			sb.append(" WHERE ");
			sb.append("  roc.del_flag = 0 and roc.status=3 ");
			if(enterpriseId!=null) {
				sb.append(" and roc.plant_enterprise_id=:enterpriseId ");
			}
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
				sb.append(" and roc.input_time between :startTime and :endTime ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
				query.setParameter("startTime", startTime);
				query.setParameter("endTime", endTime);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
}
