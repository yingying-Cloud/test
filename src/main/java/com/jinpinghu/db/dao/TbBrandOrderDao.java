package com.jinpinghu.db.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbBrandOrder;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.jpa.ZJpaHelper;

public class TbBrandOrderDao extends BaseZDao{

	public TbBrandOrderDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public TbBrandOrder findById(Integer id) {
		try {
			TbBrandOrder instance = getEntityManager().find(TbBrandOrder.class, id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
	

	public TbBrandOrder findByCarId(Integer carId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" from TbBrandOrder where delFlag=0 and id in (select brandOrderId from TbResBrandOrderCar where brandCarId=:carId ) ");
			Query query = getEntityManager().createQuery(sb.toString());
			query.setParameter("carId",carId );
			List <TbBrandOrder> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Object[]> findByAll(String brandName,String orderNumber,String enterpriseName,String beginCreateTime,
			String endCreateTime,String beginPayTime,String endPayTime,Integer status,Integer nowPage,Integer pageCount,Integer enterpriseId,Integer check) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("tto.id,");
			sb.append("tto.enterprise_Id enterpriseId,");
			sb.append("tto.add_people addPeople,");
			sb.append("tto.order_number orderNumber,");
			sb.append("tto.price price,");
			sb.append("tto.status status,");
			sb.append("tto.cancel_info cancelInfo,");
			sb.append("tto.rejected_info rejectedInfo,");
			sb.append("date_format(tto.input_time,'%Y-%m-%d %H:%i:%s') inputTime,");
			sb.append("date_format(tto.time_audit,'%Y-%m-%d %H:%i:%s') timeAudit,");
			sb.append("date_format(tto.time_pay,'%Y-%m-%d %H:%i:%s') timePay,");
			sb.append("date_format(tto.time_complete,'%Y-%m-%d %H:%i:%s') timeComplete,");
			sb.append("date_format(tto.time_cancel,'%Y-%m-%d %H:%i:%s') timeCancel,");
			sb.append("date_format(tto.time_rejected,'%Y-%m-%d %H:%i:%s') timeRejected ");
			sb.append(" from tb_brand_order tto LEFT JOIN tb_enterprise te on te.id=tto.enterprise_Id  ");
			sb.append(" where tto.del_flag=0  ");
			if(!StringUtils.isNullOrEmpty(brandName)) {
				sb.append(" and (tto.id in (select brand_order_id from tb_res_brand_order_car roc left join tb_brand_shopping_car tsc on tsc.id=roc.brand_car_id left join tb_brand tl on tl.id=tsc.brand_id where tl.product_name like :brandName and tsc.del_flag=0 and tl.del_flag=0 ) ");
				sb.append(" or tto.order_number like :brandName) ");
			}
//			if(!StringUtils.isNullOrEmpty(orderNumber)) {
//				
//			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				sb.append(" and date(tto.input_time) >= :beginCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				sb.append(" and date(tto.input_time) <= :endCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				sb.append(" and date(tto.time_pay) >= :beginPayTime ");
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				sb.append(" and date(tto.time_pay) <= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tto.enterprise_Id = :enterpriseId  ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			sb.append(" order by tto.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(brandName)) {
				query.setParameter("brandName","%"+brandName+"%" );
			}
//			if(!StringUtils.isNullOrEmpty(orderNumber)) {
//				query.setParameter("orderNumber","%"+orderNumber+"%" );
//			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime);
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime );
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime );
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime);
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(check!=null) {
				query.setParameter("check",check );
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
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
	public Integer findByAllCount(String brandName,String orderNumber,String enterpriseName,String beginCreateTime,
			String endCreateTime,String beginPayTime,String endPayTime,Integer status,Integer enterpriseId,Integer check) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tto.id) ");
			sb.append(" from tb_brand_order tto LEFT JOIN tb_enterprise te on te.id=tto.enterprise_Id  ");
			sb.append(" where tto.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(brandName)) {
				sb.append(" and (tto.id in (select brand_order_id from tb_res_brand_order_car roc left join tb_brand_shopping_car tsc on tsc.id=roc.brand_car_id left join tb_brand tl on tl.id=tsc.brand_id where tl.product_name like :brandName and tsc.del_flag=0 and tl.del_flag=0 ) ");
				sb.append("  or tto.order_number like :brandName) ");
			}
//			if(!StringUtils.isNullOrEmpty(orderNumber)) {
//				sb.append(" ");
//			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				sb.append(" and date(tto.input_time) >= :beginCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				sb.append(" and date(tto.input_time) <= :endCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				sb.append(" and date(tto.time_pay) >= :beginPayTime ");
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				sb.append(" and date(tto.time_pay) >= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tto.enterprise_Id = :enterpriseId  ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(brandName)) {
				query.setParameter("brandName","%"+brandName+"%" );
			}
//			if(!StringUtils.isNullOrEmpty(orderNumber)) {
//				query.setParameter("orderNumber","%"+orderNumber+"%" );
//			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime);
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime );
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime );
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime);
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(check!=null) {
				query.setParameter("check",check );
			}
			List <Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Object[]> getStatusCount(Integer brandEnterpriseId,Integer plantEnterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tto.id),tto.status,sum(tto.price+0) ");
			sb.append(" from tb_brand_order tto   ");
			sb.append(" where tto.del_flag=0 ");
			if(brandEnterpriseId!=null) {
				sb.append(" and  enterprise_id=:brandEnterpriseId");
			}
			if(plantEnterpriseId!=null) {
				sb.append(" and  plant_enterprise_id=:plantEnterpriseId");
			}
			sb.append(" group by tto.status ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			
			if(brandEnterpriseId!=null) {
				query.setParameter("brandEnterpriseId", brandEnterpriseId);
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
	public Object[] getCompleteCount(Integer brandEnterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tto.id),tto.status,sum(tto.price+0),sum(tto.check_money+0) ");
			sb.append(" from tb_brand_order tto   ");
			sb.append(" where tto.del_flag=0 and status=4 ");
			if(brandEnterpriseId!=null) {
				sb.append(" and  enterprise_Id=:brandEnterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			
			if(brandEnterpriseId!=null) {
				query.setParameter("brandEnterpriseId", brandEnterpriseId);
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
			sb.append("count(tto.id),tto.status,sum(tto.price+0),sum(tto.check_money+0) ");
			sb.append(" from tb_brand_order tto left join tb_enterprise te on te.id=tto.enterprise_Id ");
			sb.append(" where tto.del_flag=0 and tto.status=4 and te.enterprise_type=3 ");
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tto.enterprise_Id=:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tto.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tto.input_time) <= :endTime ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName", "%"+enterpriseName+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
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
	
	
	public Object[] findByTrademarkCount(String brandName,String orderNumber,String enterpriseName,String beginCreateTime,
			String endCreateTime,String beginPayTime,String endPayTime,Integer status,Integer enterpriseId,Integer check,Integer plantEnterpriseId,Integer type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tto.id),cast( sum(tto.price ) as decimal)  ");
			sb.append(" from tb_brand_order tto LEFT JOIN tb_enterprise te on te.id=tto.enterprise_Id  ");
			sb.append(" where tto.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(brandName)) {
				sb.append(" and (tto.id in (select brand_order_id from tb_res_brand_order_car roc left join tb_brand_shopping_car tsc on tsc.id=roc.brand_car_id left join tb_brand tl on tl.id=tsc.brand_id where tl.product_name like :brandName and tsc.del_flag=0 and tl.del_flag=0 ) ");
				sb.append("  or tto.order_number like :brandName) ");
			}
			if(!StringUtils.isNullOrEmpty(orderNumber)) {
				sb.append(" and tto.order_number like :orderNumber  ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				sb.append(" and date_format(tto.input_time,'%Y-%m-%d') >= :beginCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				sb.append(" and date_format(tto.input_time,'%Y-%m-%d') <= :endCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				sb.append(" and date_format(tto.time_pay,'%Y-%m-%d') >= :beginPayTime ");
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				sb.append(" and date_format(tto.time_pay,'%Y-%m-%d') <= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tto.enterprise_Id = :enterpriseId  ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			if(type!=null) {
				sb.append(" and tto.type = :type ");
			}
			if(plantEnterpriseId!=null) {
				sb.append(" and tto.sell_id=:plantEnterpriseId ");
//				sb.append("  and  tto.id in (select brand_order_id from tb_res_brand_order_car roc "
//						+ " left join tb_brand_shopping_car tsc on tsc.id=roc.brand_car_id "
//						+ " left join tb_res_brand_car_num trbc on trbc.brand_car_id=tsc.id " 
//						+ "where trbc.enterprise_id=:plantEnterpriseId and tsc.del_flag=0   ) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(brandName)) {
				query.setParameter("brandName","%"+brandName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(orderNumber)) {
				query.setParameter("orderNumber","%"+orderNumber+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime);
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime );
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime );
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime);
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(check!=null) {
				query.setParameter("check",check );
			}
			if(type!=null) {
				query.setParameter("type",type );
			}
			if(plantEnterpriseId!=null) {
				query.setParameter("plantEnterpriseId",plantEnterpriseId );
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
	public List<Object[]> findByTrademark(String brandName,String orderNumber,String enterpriseName,String beginCreateTime,String endCreateTime,
			String beginPayTime,String endPayTime,Integer status,Integer nowPage,Integer pageCount,Integer enterpriseId,Integer check,Integer plantEnterpriseId,Integer type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("tto.id,");
			sb.append("tto.enterprise_Id enterpriseId,");
			sb.append("tto.add_people addPeople,");
			sb.append("tto.order_number orderNumber,");
			sb.append("cast(tto.price as decimal) price,");
			sb.append("tto.status status,");
			sb.append("tto.cancel_info cancelInfo,");
			sb.append("tto.rejected_info rejectedInfo,");
			sb.append("date_format(tto.input_time,'%Y-%m-%d %H:%i:%s') inputTime,");
			sb.append("date_format(tto.time_audit,'%Y-%m-%d %H:%i:%s') timeAudit,");
			sb.append("date_format(tto.time_pay,'%Y-%m-%d %H:%i:%s') timePay,");
			sb.append("date_format(tto.time_complete,'%Y-%m-%d %H:%i:%s') timeComplete,");
			sb.append("date_format(tto.time_cancel,'%Y-%m-%d %H:%i:%s') timeCancel,");
			sb.append("date_format(tto.time_rejected,'%Y-%m-%d %H:%i:%s') timeRejected ");
			sb.append(" ,tto.type type,tto.sell_id sellEnterpriseId ");
			sb.append(" from tb_brand_order tto LEFT JOIN tb_enterprise te on te.id=tto.enterprise_Id  ");
			sb.append(" where tto.del_flag=0  ");
			if(!StringUtils.isNullOrEmpty(brandName)) {
				sb.append(" and (tto.id in (select brand_order_id from tb_res_brand_order_car roc left join tb_brand_shopping_car tsc on tsc.id=roc.brand_car_id left join tb_brand tl on tl.id=tsc.brand_id where tl.product_name like :brandName and tsc.del_flag=0 and tl.del_flag=0 ) ");
				sb.append(" or tto.order_number like :brandName) ");
			}
			if(!StringUtils.isNullOrEmpty(orderNumber)) {
				sb.append(" and tto.order_number like :orderNumber  ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				sb.append(" and date_format(tto.input_time,'%Y-%m-%d') >= :beginCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				sb.append(" and date_format(tto.input_time,'%Y-%m-%d') <= :endCreateTime ");
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				sb.append(" and date_format(tto.time_pay,'%Y-%m-%d') >= :beginPayTime ");
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				sb.append(" and date_format(tto.time_pay,'%Y-%m-%d') <= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tto.enterprise_Id = :enterpriseId  ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			if(type!=null) {
				sb.append(" and tto.type = :type ");
			}
			if(plantEnterpriseId!=null) {
				sb.append(" and tto.sell_id=:plantEnterpriseId ");
//				sb.append("  and  tto.id in (select brand_order_id from tb_res_brand_order_car roc "
//						+ " left join tb_brand_shopping_car tsc on tsc.id=roc.brand_car_id "
//						+ " left join tb_res_brand_car_num trbc on trbc.brand_car_id=tsc.id " 
//						+ "where trbc.enterprise_id=:plantEnterpriseId and tsc.del_flag=0   ) ");
			}
			sb.append(" order by tto.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(brandName)) {
				query.setParameter("brandName","%"+brandName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(orderNumber)) {
				query.setParameter("orderNumber","%"+orderNumber+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime);
			}
			if(!StringUtils.isNullOrEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime );
			}
			if(!StringUtils.isNullOrEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime );
			}
			if(!StringUtils.isNullOrEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime);
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(check!=null) {
				query.setParameter("check",check );
			}
			if(type!=null) {
				query.setParameter("type",type );
			}
			if(plantEnterpriseId!=null) {
				query.setParameter("plantEnterpriseId",plantEnterpriseId );
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
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
	
	
	
	
	
	public Map<String, Object> getOrderCountSum(Integer enterpriseId,String startTime,String endTime) {
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" count(distinct brand_order_id) count,ifnull(cast( sum( trbc.num * tl.price ) AS DECIMAL(10,2)  ),0)  sum "); 
			sb.append(" FROM ");
			sb.append(" tb_res_brand_order_car roc ");
			sb.append(" LEFT JOIN tb_brand_shopping_car tsc ON tsc.id = roc.brand_car_id ");
			sb.append(" LEFT JOIN tb_res_brand_car_num trbc ON trbc.brand_car_id = tsc.id  ");
			sb.append(" LEFT JOIN tb_brand tl ON tl.id = tsc.brand_id  ");
			sb.append(" LEFT JOIN tb_brand_order tbo ON tbo.id = roc.brand_order_id  ");
			sb.append(" WHERE ");
			sb.append("  tsc.del_flag = 0 and tbo.status=4 and tbo.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and trbc.enterprise_id=:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and tbo.input_time between :startTime and :endTime ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
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
	
	public List<Map<String, Object>> getEnterpriseAllOrderCountSum(Integer enterpriseId,String startTime,String endTime) {
		try {
			StringBuffer sb=new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" count(distinct tbo.id) count,ifnull(cast( sum( tbo.price ) AS DECIMAL ( 10, 2 ) ),0)  sum ,tbo.status "); 
			sb.append(" FROM ");
			sb.append(" tb_brand_order tbo  ");
			sb.append(" WHERE ");
			sb.append(" tbo.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and tbo.sell_id=:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and tbo.input_time between :startTime and :endTime ");
			}
			sb.append("group by tbo.status ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)&&!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("startTime", startTime);
				query.setParameter("endTime", endTime);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}

	
	
	
	
}
