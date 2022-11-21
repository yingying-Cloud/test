package com.jinpinghu.db.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbToolOrder;

import fw.jbiz.jpa.ZJpaHelper;
import net.sf.json.JSONObject;

public class TbToolOrderDao extends BaseZDao{

	public TbToolOrderDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public TbToolOrder findById(Integer id) {
		try {
			TbToolOrder instance = getEntityManager().find(TbToolOrder.class, id);
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
	
	public TbToolOrder findByOrderNumber(String orderNumber) {
		try {
			String queryString = " from TbToolOrder where delFlag = 0 and orderNumber = :orderNumber ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("orderNumber", orderNumber);
			List<TbToolOrder> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			ZJpaHelper.log("find failed", Level.SEVERE, re);
			throw re;
		}
	}
	
	public List<Object[]> findByAll(String toolName,String orderNumber,String enterpriseName,String beginCreateTime,
			String endCreateTime,String beginPayTime,String endPayTime,Integer status,Integer nowPage,Integer pageCount,
			Integer enterpriseId,Integer check,String name,String isValidation,String selectAll,List<Integer> enterpriseIdList,
			String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("tto.id,");
			sb.append("tto.tool_enterprise_Id toolEnterpriseId,");
			sb.append("tto.plant_enterprise_Id plantEnterpriseId,");
			sb.append("tto.add_people addPeople,");
			sb.append("tto.order_number orderNumber,");
			sb.append("sum(tsc.price*tsc.num) price,");
			sb.append("tto.status status,");
			sb.append("tto.cancel_info cancelInfo,");
			sb.append("tto.rejected_info rejectedInfo,");
			sb.append("date_format(tto.input_time,'%Y-%m-%d %H:%i:%s') inputTime,");
			sb.append("date_format(tto.time_audit,'%Y-%m-%d %H:%i:%s') timeAudit,");
			sb.append("date_format(tto.time_pay,'%Y-%m-%d %H:%i:%s') timePay,");
			sb.append("date_format(tto.time_complete,'%Y-%m-%d %H:%i:%s') timeComplete,");
			sb.append("date_format(tto.time_cancel,'%Y-%m-%d %H:%i:%s') timeCancel,");
			sb.append("date_format(tto.time_rejected,'%Y-%m-%d %H:%i:%s') timeRejected,tto.check_,tto.check_money,tto.type, ");
			sb.append("tes.name plantEnterpriseName,tloi.name linkInfoName,tloi.type infoType,tes2.name toolEnterpriseName, ");
			sb.append("tloi.legal_person_idcard idcard,tloi.last_pic idcardPic ");
			sb.append(" from tb_tool_order tto LEFT JOIN tb_enterprise te on te.id=tto.tool_enterprise_Id   ");
			sb.append("  LEFT JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_Id and tto.type=2  ");
			sb.append("  LEFT JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_Id and tto.type=1  ");
			sb.append("  LEFT JOIN tb_enterprise tes2 on tes2.id=tto.tool_enterprise_Id ");
			sb.append(" left join tb_res_order_car troc on troc.order_id = tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
			sb.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
			sb.append(" where tto.del_flag=0  ");
			if (!StringUtils.isEmpty(uniformPrice)) {
				sb.append(" and tsc.uniform_price = :uniformPrice ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_Id  in (:enterpriseIdList) ");
			}
			if(!StringUtils.isEmpty(toolName)) {
				sb.append(" and tt.name like :toolName  ");
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				sb.append(" and tto.order_number like :orderNumber ");
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and (te.name like :enterpriseName) ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and (tes.enterprise_link_people like :name or tloi.name like :name ) ");
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				sb.append(" and tto.input_time >= :beginCreateTime ");
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				sb.append(" and tto.input_time <= :endCreateTime ");
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				sb.append(" and tto.time_pay >= :beginPayTime ");
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				sb.append(" and tto.time_pay <= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and (  tto.tool_enterprise_Id = :enterpriseId or tes.id = :enterpriseId ) ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			if ("1".equals(isValidation)) {
				sb.append(" and tloi.is_validation = '1' ");
			}else if ("0".equals(isValidation)) {
				sb.append(" and (tloi.is_validation <> '1' or tloi.is_validation is null) ");
			}
			sb.append(" group by tto.id order by tto.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isEmpty(toolName)) {
				query.setParameter("toolName","%"+toolName+"%" );
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				query.setParameter("orderNumber","%"+orderNumber+"%" );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime+" 00:00:00");
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime+" 23:59:59" );
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime+" 00:00:00" );
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime+" 23:59:59");
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
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
	public Integer findByAllCount(String toolName,String orderNumber,String enterpriseName,String beginCreateTime,
			String endCreateTime,String beginPayTime,String endPayTime,Integer status,Integer enterpriseId,Integer check,
			String name,String isValidation,String selectAll,List<Integer> enterpriseIdList,String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(distinct tto.id) ");
			sb.append(" from tb_tool_order tto LEFT JOIN tb_enterprise te on te.id=tto.tool_enterprise_Id   ");
			sb.append("  LEFT JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_Id and tto.type=2  ");
			sb.append("  LEFT JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_Id and tto.type=1  ");
			sb.append(" left join tb_res_order_car troc on troc.order_id = tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
			sb.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
			sb.append(" where tto.del_flag=0 ");
			if (!StringUtils.isEmpty(uniformPrice)) {
				sb.append(" and tsc.uniform_price = :uniformPrice  ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_Id  in (:enterpriseIdList) ");
			}
			if(!StringUtils.isEmpty(toolName)) {
				sb.append(" and tt.name like :toolName  ");
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				sb.append(" and tto.order_number like :orderNumber ");
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and (te.name like :enterpriseName ) ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and (tes.enterprise_link_people like :name or tloi.name like :name ) ");
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				sb.append(" and tto.input_time >= :beginCreateTime ");
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				sb.append(" and tto.input_time <= :endCreateTime ");
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				sb.append(" and tto.time_pay >= :beginPayTime ");
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				sb.append(" and tto.time_pay <= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and (tto.tool_enterprise_Id = :enterpriseId or tes.id = :enterpriseId) ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			if ("1".equals(isValidation)) {
				sb.append(" and tloi.is_validation = '1' ");
			}else if ("0".equals(isValidation)) {
				sb.append(" and (tloi.is_validation <> '1' or tloi.is_validation is null) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isEmpty(toolName)) {
				query.setParameter("toolName","%"+toolName+"%" );
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				query.setParameter("orderNumber","%"+orderNumber+"%" );
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime+" 00:00:00");
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime+" 23:59:59" );
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime+" 00:00:00" );
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime+" 23:59:59");
			}
			if(status!=null) {
				query.setParameter("status",status );
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
	
	public List<Map<String, Object>> orderDataGroupByToolType(String toolName,String orderNumber,String enterpriseName,String beginCreateTime,
			String endCreateTime,String beginPayTime,String endPayTime,Integer status,Integer enterpriseId,Integer check,String name,
			String isValidation,String selectAll,List<Integer> enterpriseIdList,String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tp.id,tp.name,ifnull(data.number,'') number,ifnull(data.price,'') price from tb_type tp left join (");
			sb.append(" select  ");
			sb.append(" tt2.id,tt2.name,ROUND(sum(tsc.num),2) number,ROUND(sum(tsc.num*tsc.price),2) price ");
			sb.append(" from tb_tool_order tto LEFT JOIN tb_enterprise te on te.id=tto.tool_enterprise_Id   ");
			sb.append(" LEFT JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_Id and tto.type=2  ");
			sb.append(" LEFT JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_Id and tto.type=1  ");
			sb.append(" LEFT JOIN tb_enterprise tes2 on tes2.id=tto.tool_enterprise_Id ");
			sb.append(" left join tb_res_order_car troc on troc.order_id = tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
			sb.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
			sb.append(" left join tb_type tt2 on tt.type = tt2.id ");
			sb.append(" where tto.del_flag=0 and tt.type is not null ");
			if (!StringUtils.isEmpty(uniformPrice)) {
				sb.append(" and tsc.uniform_price = :uniformPrice  ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_Id  in (:enterpriseIdList) ");
			}
			if(!StringUtils.isEmpty(toolName)) {
				sb.append(" and  tt.name like :toolName ");
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				sb.append(" and tto.order_number like :orderNumber ");
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and (te.name like :enterpriseName) ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and (tes.enterprise_link_people like :name or tloi.name like :name ) ");
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				sb.append(" and tto.input_time >= :beginCreateTime ");
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				sb.append(" and tto.input_time <= :endCreateTime ");
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				sb.append(" and tto.time_pay >= :beginPayTime ");
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				sb.append(" and tto.time_pay <= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and (  tto.tool_enterprise_Id = :enterpriseId or tes.id = :enterpriseId ) ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			if ("1".equals(isValidation)) {
				sb.append(" and tloi.is_validation = '1' ");
			}else if ("0".equals(isValidation)) {
				sb.append(" and (tloi.is_validation <> '1' or tloi.is_validation is null) ");
			}
			sb.append(" group by tt.type ");
			sb.append(" ) data on data.id = tp.id where tp.type = 4");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isEmpty(toolName)) {
				query.setParameter("toolName","%"+toolName+"%" );
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				query.setParameter("orderNumber","%"+orderNumber+"%" );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime+" 00:00:00");
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime+" 23:59:59" );
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime+" 00:00:00" );
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime+" 23:59:59");
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(check!=null) {
				query.setParameter("check",check );
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String, Object>> list = query.getResultList();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public BigDecimal findByAllMoney(String toolName,String orderNumber,String enterpriseName,String beginCreateTime,
			String endCreateTime,String beginPayTime,String endPayTime,Integer status,Integer enterpriseId,Integer check,String name,
			String isValidation,String selectAll,List<Integer> enterpriseIdList,String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" sum(tsc.num*tsc.price) ");
			sb.append(" from tb_tool_order tto LEFT JOIN tb_enterprise te on te.id=tto.tool_enterprise_Id   ");
			sb.append("  LEFT JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_Id and tto.type=2  ");
			sb.append("  LEFT JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_Id and tto.type=1  ");
			sb.append(" left join tb_res_order_car troc on troc.order_id = tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
			sb.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
			sb.append(" where tto.del_flag=0 ");
			if (!StringUtils.isEmpty(uniformPrice)) {
				sb.append(" and tsc.uniform_price = :uniformPrice  ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_Id  in (:enterpriseIdList) ");
			}
			if(!StringUtils.isEmpty(toolName)) {
				sb.append(" and tt.name like :toolName ");
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				sb.append(" and tto.order_number like :orderNumber ");
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and (te.name like :enterpriseName ) ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" and (te.enterprise_link_people like :name or tloi.name like :name ) ");
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				sb.append(" and date(tto.input_time) >= :beginCreateTime ");
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				sb.append(" and date(tto.input_time) <= :endCreateTime ");
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				sb.append(" and date(tto.time_pay) >= :beginPayTime ");
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				sb.append(" and date(tto.time_pay) <= :endPayTime ");
			}
			if(status!=null) {
				sb.append(" and tto.status = :status ");
			}
			if(enterpriseId!=null) {
				sb.append(" and (tto.tool_enterprise_Id = :enterpriseId or tes.id = :enterpriseId) ");
			}
			if(check!=null) {
				sb.append(" and tto.check_ = :check ");
			}
			if ("1".equals(isValidation)) {
				sb.append(" and tloi.is_validation = '1' ");
			}else if ("0".equals(isValidation)) {
				sb.append(" and (tloi.is_validation <> '1' or tloi.is_validation is null) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isEmpty(toolName)) {
				query.setParameter("toolName","%"+toolName+"%" );
			}
			if(!StringUtils.isEmpty(orderNumber)) {
				query.setParameter("orderNumber","%"+orderNumber+"%" );
			}
			if(!StringUtils.isEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isEmpty(name)) {
				query.setParameter("name","%"+name+"%" );
			}
			if(!StringUtils.isEmpty(beginCreateTime)) {
				query.setParameter("beginCreateTime",beginCreateTime);
			}
			if(!StringUtils.isEmpty(endCreateTime)) {
				query.setParameter("endCreateTime",endCreateTime );
			}
			if(!StringUtils.isEmpty(beginPayTime)) {
				query.setParameter("beginPayTime",beginPayTime );
			}
			if(!StringUtils.isEmpty(endPayTime)) {
				query.setParameter("endPayTime",endPayTime);
			}
			if(status!=null) {
				query.setParameter("status",status );
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId );
			}
			if(check!=null) {
				query.setParameter("check",check );
			}
			List <Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return new BigDecimal(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Object[]> getStatusCount(Integer toolEnterpriseId,Integer plantEnterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tto.id),tto.status,sum(tto.price+0) ");
			sb.append(" from tb_tool_order tto   ");
			sb.append(" where tto.del_flag=0 ");
			if(toolEnterpriseId!=null) {
				sb.append(" and  tool_enterprise_id=:toolEnterpriseId");
			}
			if(plantEnterpriseId!=null) {
				sb.append(" and  plant_enterprise_id=:plantEnterpriseId");
			}
			sb.append(" group by tto.status ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			
			if(toolEnterpriseId!=null) {
				query.setParameter("toolEnterpriseId", toolEnterpriseId);
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
	public Object[] getCompleteCount(Integer toolEnterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("count(tto.id),tto.status,sum(tto.price+0),sum(tto.check_money+0) ");
			sb.append(" from tb_tool_order tto   ");
			sb.append(" where tto.del_flag=0 and status=4 ");
			if(toolEnterpriseId!=null) {
				sb.append(" and  tool_enterprise_id=:toolEnterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			
			if(toolEnterpriseId!=null) {
				query.setParameter("toolEnterpriseId", toolEnterpriseId);
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
			sb.append(" select a.countId,a.s,a.price,a.checkMoney,buyNum");
			sb.append( " ,( select sum(case when ttos.status =4  then tsc.num else 0 end)  " + 
					" from tb_shopping_car tsc" + 
					" inner join tb_res_order_car troc on troc.car_id=tsc.id" + 
					" inner join tb_tool_order ttos on ttos.id=troc.order_id left join tb_enterprise te on te.id=ttos.tool_enterprise_id" + 
					" where ttos.del_flag = 0    ");
			
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and ttos.tool_enterprise_id=:enterpriseId ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and ttos.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and ttos.input_time <= :endTime ");
			}
			sb.append(" ) sellNum ");
			
			sb.append( " ,( select sum(tloi.number+0)  " 
					+ "from tb_tool_recovery_record tloi  "
					+ " left join tb_enterprise te on te.id=tloi.enterprise_id "
					+ " where tloi.del_flag = 0    ");
			
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and te.id=:enterpriseId ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and tloi.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and tloi.input_time <= :endTime ");
			}
			sb.append(" ) recoveryNum ");
			
			sb.append( " ,( select count(distinct tloi.id)  " 
					+ "from tb_link_order_info tloi left join tb_res_enterprise_linkorderinfo trel on trel.link_order_info_id=tloi.id "
					+ " left join tb_enterprise te on te.id=trel.enterprise_id "
					+ " where tloi.del_flag = 0    ");
			
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and te.id=:enterpriseId ");
			}
			sb.append(" ) peopleCount ");
			
			sb.append( " ,( select count(tt.id)  " 
					+ "from tb_tool tt  "
					+ " left join tb_enterprise te on te.id=tt.enterprise_id "
					+ " where tt.del_flag = 0    ");
			
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and te.id=:enterpriseId ");
			}
			sb.append(" ) toolCount ");
			
			
			sb.append(" from ( ");
			sb.append(" select  ");
			sb.append("count(tto.id) countId,tto.status s,sum(tto.price+0) price ,sum(tto.check_money+0) checkMoney ");
			sb.append( " ,(select sum(ttr.number) from tb_tool_record ttr where ttr.record_type=1 and te.id=ttr.enterprise_id  ");
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and ttr.use_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and ttr.use_time <= :endTime ");
			}
			sb.append(" ) buyNum ");
			sb.append(" from tb_tool_order tto left join tb_enterprise te on te.id=tto.tool_enterprise_id ");
			sb.append(" where tto.del_flag=0 and tto.status=4 and te.enterprise_type=3 ");
			if(!StringUtils.isEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName ");
			}
			if(!StringUtils.isEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tto.tool_enterprise_id=:enterpriseId ");
			}
			if(!StringUtils.isEmpty(startTime)) {
				sb.append(" and tto.input_time >= :startTime ");
			}
			if(!StringUtils.isEmpty(endTime)) {
				sb.append(" and tto.input_time <= :endTime ");
			}
			sb.append(" )a ");
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
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
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
			sb.append(" count(id) count,ifnull(cast( sum( roc.price ) AS DECIMAL ( 10, 2 ) ),0)  sum "); 
			sb.append(" FROM ");
			sb.append(" tb_tool_order roc ");
			sb.append(" WHERE ");
			sb.append("  roc.del_flag = 0 and roc.status=4 ");
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
	
	public List<Map<String, Object>> statisticToolOrder(String startTime,String endTime,String type,String dscd,String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		if("1".equals(type)) {
			queryString.append(" SELECT te.dscd,ta.name dsnm, ");
		}else if("2".equals(type)) {
			queryString.append(" SELECT te.id,te.name dsnm, ");
		}
		queryString.append(" sum(ifnull(totalOut,0)) totalOut,sum(ifnull(fertilizerOut,0)) fertilizerOut, ");
		queryString.append(" sum(ifnull(pesticideOut,0)) pesticideOut,sum(ifnull(seedOut,0)) seedOut, ");
		queryString.append(" sum(ifnull(otherOut,0)) otherOut,sum(ifnull(totalIn,0)) totalIn, ");
		queryString.append(" sum(ifnull(fertilizerIn,0)) fertilizerIn,sum(ifnull(pesticideIn,0)) pesticideIn, ");
		queryString.append(" sum(ifnull(seedIn,0)) seedIn,sum(ifnull(otherIn,0)) otherIn ");
		queryString.append(" from tb_enterprise te	LEFT JOIN tb_area ta ON te.dscd = ta.id left join ");
		queryString.append(" (select tto.tool_enterprise_id enterpriseId,count(distinct tto.id) totalOut, ");
		queryString.append(" count(distinct case when tt.type = 12 then tto.id end) fertilizerOut, ");
		queryString.append(" count(distinct case when tt.type = 13 then tto.id end) pesticideOut, ");
		queryString.append(" count(distinct case when tt.type = 14 then tto.id end) seedOut, ");
		queryString.append(" count(distinct case when tt.type = 15 then tto.id end) otherOut ");
		queryString.append(" from tb_tool_order tto left join tb_res_order_car troc on tto.id = troc.order_id ");
		queryString.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
		queryString.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
		queryString.append(" where tto.del_flag = 0 and tto.status = 4 ");
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by tto.tool_enterprise_id) o on o.enterpriseId = te.id left join ");
		queryString.append(" (select ttr.enterprise_id enterpriseId,count(distinct ttr.id) totalIn, ");
		queryString.append(" count(distinct case when tt.type = 12 then ttr.id end) fertilizerIn, ");
		queryString.append(" count(distinct case when tt.type = 13 then ttr.id end) pesticideIn, ");
		queryString.append(" count(distinct case when tt.type = 14 then ttr.id end) seedIn, ");
		queryString.append(" count(distinct case when tt.type = 15 then ttr.id end) otherIn  ");
		queryString.append(" from tb_tool_record ttr left join tb_tool tt on tt.id = ttr.tool_id ");
		queryString.append(" where ttr.del_flag = 0 and ttr.record_type = 1 ");
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by ttr.enterprise_id)i on i.enterpriseId = te.id ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		if(!StringUtils.isEmpty(dscd)) {
			queryString.append(" and te.dscd = :dscd ");
		}
		if("1".equals(type)) {
			queryString.append(" group by te.dscd order by te.dscd ");
		}else if("2".equals(type)) {
			queryString.append(" group by te.id order by te.dscd ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if(!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Map<String, Object> statisticTotalToolOrder(String startTime,String endTime,String dscd,String selectAll,List<Integer> enterpriseIdList){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT '' dscd,'合计' dsnm, ");
		queryString.append(" sum(ifnull(totalOut,0)) totalOut,sum(ifnull(fertilizerOut,0)) fertilizerOut, ");
		queryString.append(" sum(ifnull(pesticideOut,0)) pesticideOut,sum(ifnull(seedOut,0)) seedOut, ");
		queryString.append(" sum(ifnull(otherOut,0)) otherOut,sum(ifnull(totalIn,0)) totalIn, ");
		queryString.append(" sum(ifnull(fertilizerIn,0)) fertilizerIn,sum(ifnull(pesticideIn,0)) pesticideIn, ");
		queryString.append(" sum(ifnull(seedIn,0)) seedIn,sum(ifnull(otherIn,0)) otherIn ");
		queryString.append(" from tb_enterprise te	LEFT JOIN tb_area ta ON te.dscd = ta.id left join ");
		queryString.append(" (select tto.tool_enterprise_id enterpriseId,count(distinct tto.id) totalOut, ");
		queryString.append(" count(distinct case when tt.type = 12 then tto.id end) fertilizerOut, ");
		queryString.append(" count(distinct case when tt.type = 13 then tto.id end) pesticideOut, ");
		queryString.append(" count(distinct case when tt.type = 14 then tto.id end) seedOut, ");
		queryString.append(" count(distinct case when tt.type = 15 then tto.id end) otherOut ");
		queryString.append(" from tb_tool_order tto left join tb_res_order_car troc on tto.id = troc.order_id ");
		queryString.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
		queryString.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
		queryString.append(" where tto.del_flag = 0 and tto.status = 4 ");
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by tto.tool_enterprise_id) o on o.enterpriseId = te.id left join ");
		queryString.append(" (select ttr.enterprise_id enterpriseId,count(distinct ttr.id) totalIn, ");
		queryString.append(" count(distinct case when tt.type = 12 then ttr.id end) fertilizerIn, ");
		queryString.append(" count(distinct case when tt.type = 13 then ttr.id end) pesticideIn, ");
		queryString.append(" count(distinct case when tt.type = 14 then ttr.id end) seedIn, ");
		queryString.append(" count(distinct case when tt.type = 15 then ttr.id end) otherIn  ");
		queryString.append(" from tb_tool_record ttr left join tb_tool tt on tt.id = ttr.tool_id ");
		queryString.append(" where ttr.del_flag = 0 and ttr.record_type = 1 ");
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by ttr.enterprise_id)i on i.enterpriseId = te.id ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		if(!StringUtils.isEmpty(dscd)) {
			queryString.append(" and te.dscd = :dscd ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if(!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> map = (Map<String, Object>) query.getSingleResult();
		return map;
	}
	
	public List<Map<String, Object>> statisticFlowInfo(String toolName,String type,String dscd,String selectAll,
			List<Integer> enterpriseIdList,String startTime,String endTime){
		StringBuilder queryString = new StringBuilder();
		if("1".equals(type)) {
			queryString.append(" SELECT te.dscd,ta.name dsnm, ");
		}else if("2".equals(type)) {
			queryString.append(" SELECT te.id,te.name dsnm, ");
		}
		queryString.append(" sum(ifnull(totalOut,0)) totalOut,sum(ifnull(totalIn,0)) totalIn,sum(ifnull(tt.number,0)) stock ");
		queryString.append(" from tb_enterprise te	LEFT JOIN tb_area ta ON te.dscd = ta.id left join ");
		queryString.append(" (select tto.tool_enterprise_id enterpriseId,count(distinct tto.id) totalOut ");
		queryString.append(" from tb_tool_order tto left join tb_res_order_car troc on tto.id = troc.order_id ");
		queryString.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
		queryString.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
		queryString.append(" where tto.del_flag = 0 and tto.status = 4 ");
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') <= :endTime ");
		}
		if(!StringUtils.isEmpty(toolName)) {
			queryString.append(" and tt.name like :toolName ");
		}
		queryString.append(" group by tto.tool_enterprise_id) o on o.enterpriseId = te.id left join ");
		queryString.append(" (select ttr.enterprise_id enterpriseId,count(distinct ttr.id) totalIn ");
		queryString.append(" from tb_tool_record ttr left join tb_tool tt on tt.id = ttr.tool_id ");
		queryString.append(" where ttr.del_flag = 0 and ttr.record_type = 1 ");
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') <= :endTime ");
		}
		if(!StringUtils.isEmpty(toolName)) {
			queryString.append(" and tt.name like :toolName ");
		}
		queryString.append(" group by ttr.enterprise_id)i on i.enterpriseId = te.id ");
		queryString.append(" left join (select sum(number) number,enterprise_id from tb_tool where del_flag = 0 group by enterprise_id) tt on te.id = tt.enterprise_id ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		if(!StringUtils.isEmpty(toolName)) {
			queryString.append(" and (tt.name like :toolName or tt2.name like :toolName) ");
		}
		if(!StringUtils.isEmpty(dscd)) {
			queryString.append(" and te.dscd = :dscd ");
		}
		if("1".equals(type)) {
			queryString.append(" group by te.dscd order by te.dscd ");
		}else if("2".equals(type)) {
			queryString.append(" group by te.id order by te.dscd ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if(!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if(!StringUtils.isEmpty(toolName)) {
			query.setParameter("toolName", "%"+toolName+"%");
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Map<String, Object> statisticTotalFlowInfo(String toolName,String dscd,String selectAll,
			List<Integer> enterpriseIdList,String startTime,String endTime){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT '' dscd,'合计' dsnm,sum(ifnull(totalOut,0)) totalOut,sum(ifnull(totalIn,0)) totalIn,sum(ifnull(tt.number,0)) stock ");
		queryString.append(" from tb_enterprise te	LEFT JOIN tb_area ta ON te.dscd = ta.id left join ");
		queryString.append(" (select tto.tool_enterprise_id enterpriseId,count(distinct tto.id) totalOut ");
		queryString.append(" from tb_tool_order tto left join tb_res_order_car troc on tto.id = troc.order_id ");
		queryString.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
		queryString.append(" left join tb_tool tt on tt.id = tsc.tool_id ");
		queryString.append(" where tto.del_flag = 0 and tto.status = 4 ");
		if(!StringUtils.isEmpty(toolName)) {
			queryString.append(" and tt.name like :toolName ");
		}
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(tto.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by tto.tool_enterprise_id) o on o.enterpriseId = te.id left join ");
		queryString.append(" (select ttr.enterprise_id enterpriseId,count(distinct ttr.id) totalIn ");
		queryString.append(" from tb_tool_record ttr left join tb_tool tt on tt.id = ttr.tool_id ");
		queryString.append(" where ttr.del_flag = 0 and ttr.record_type = 1 ");
		if(!StringUtils.isEmpty(toolName)) {
			queryString.append(" and tt.name like :toolName ");
		}
		if(!StringUtils.isEmpty(startTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if(!StringUtils.isEmpty(endTime)) {
			queryString.append(" and date_format(ttr.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by ttr.enterprise_id)i on i.enterpriseId = te.id ");
		queryString.append(" left join (select sum(number) number,enterprise_id from tb_tool where del_flag = 0 group by enterprise_id) tt on te.id = tt.enterprise_id  ");
		queryString.append(" where te.del_flag = 0 and te.enterprise_type = 3 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and te.id in (:enterpriseIdList) ");
		}
		if(!StringUtils.isEmpty(dscd)) {
			queryString.append(" and te.dscd = :dscd ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if(!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime", startTime);
		}
		if(!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime", endTime);
		}
		if(!StringUtils.isEmpty(toolName)) {
			query.setParameter("toolName", "%"+toolName+"%");
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		if(!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> map = (Map<String, Object>) query.getSingleResult();
		return map;
	}
	
	public List<Object[]> statisticOut(String tm,String name,String dscd,String isValidation,String selectAll,List<Integer> enterpriseIdList,String uniformPrice){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select enterpriseId,time,sum(count) from (");
		queryString.append(" SELECT tto.tool_enterprise_id enterpriseId,DATE_FORMAT(tto.input_time,'%Y-%m-%d') time,count(distinct tsc.id) count ");
		queryString.append(" FROM tb_tool_order tto left join tb_enterprise te on tto.tool_enterprise_id = te.id ");
		queryString.append(" left join tb_res_order_car troc on tto.id = troc.order_id ");
		queryString.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
		queryString.append(" left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id ");
		queryString.append(" where tto.status = 4 and tto.del_flag = 0 and DATE_FORMAT(tto.input_time,'%Y-%m') = :tm ");
		if (!StringUtils.isEmpty(uniformPrice)) {
			queryString.append(" and tsc.uniform_price = :uniformPrice ");
		}
		if ("1".equals(isValidation)) {
			queryString.append(" and tloi.is_validation = '1' ");
		}else if ("0".equals(isValidation)) {
			queryString.append(" and (tloi.is_validation <> '1' or tloi.is_validation is null) ");
		}
		if(!StringUtils.isEmpty(name)) {
			queryString.append(" and te.name like :name ");
		}
		if(!StringUtils.isEmpty(dscd)) {
			queryString.append(" and te.dscd like :dscd ");
		}
		queryString.append(" group by tto.tool_enterprise_id,DATE_FORMAT(tto.input_time,'%Y-%m-%d') ");
		queryString.append(" union all ");
		queryString.append(" SELECT tto.enterprise_id enterpriseId,DATE_FORMAT(tto.use_time,'%Y-%m-%d') time,count(distinct tto.id) count ");
		queryString.append(" FROM tb_tool_record tto left join tb_enterprise te on te.id = tto.enterprise_id left join tb_tool tt on tto.tool_id = tt.id ");
		queryString.append(" where tto.record_type = 2 and tto.del_flag = 0 and DATE_FORMAT(tto.use_time,'%Y-%m') = :tm ");
		if (!StringUtils.isEmpty(uniformPrice)) {
			queryString.append(" and tt.uniform_price = :uniformPrice ");
		}
		if(!StringUtils.isEmpty(name)) {
			queryString.append(" and te.name like :name ");
		}
		if(!StringUtils.isEmpty(dscd)) {
			queryString.append(" and te.dscd like :dscd ");
		}
		queryString.append(" group by tto.enterprise_id,DATE_FORMAT(tto.use_time,'%Y-%m-%d') ");
		queryString.append(" )data where 1=1 ");
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			queryString.append(" and data.enterpriseId in (:enterpriseIdList) ");
		}
		queryString.append(" group by data.enterpriseId,data.time ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("tm", tm);
		if (!StringUtils.isEmpty(uniformPrice)) {
			query.setParameter("uniformPrice", uniformPrice);
		}
		if(!StringUtils.isEmpty(name)) {
			query.setParameter("name", "%"+name+"%");
		}
		if(!StringUtils.isEmpty(dscd)) {
			query.setParameter("dscd", dscd);
		}
		if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
			query.setParameter("enterpriseIdList", enterpriseIdList);
		}
		List<Object[]> list = query.getResultList();
		return list;
	}
	
	public List<Object[]> findAllIsSync() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("te.name,te.sync_number syncNumber,");
			sb.append("tloi.link_people addPeople,");
			sb.append("tto.order_number orderNumber,");	
			sb.append("tto.price price,");
			sb.append("date_format(tto.input_time,'%Y-%m-%d %H:%i:%s') inputTime,");
			
			sb.append(" (select ");
			sb.append(" cast(sum(tsc.num) as decimal(10,0)) ");
			sb.append(" from Tb_Shopping_Car tsc inner join tb_res_order_car reoc on reoc.car_id=tsc.id ");
			sb.append("  where  tsc.del_Flag = 0  ");
			sb.append(" and   order_id=tto.id  )num, ");
			sb.append("tto.id,tloi.sync_number ");
			
			sb.append(" from tb_tool_order tto LEFT JOIN tb_enterprise te on te.id=tto.tool_enterprise_Id   ");
			sb.append("  LEFT JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_Id and tto.type=2  ");
			sb.append("  LEFT JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_Id and tto.type=1  ");
			sb.append(" where tto.del_flag=0 and ( tto.is_sync=0 or tto.is_sync is null ) and ( plant_enterprise_Id is not null ) "
					+ " ");
			sb.append(" order by tto.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Object[] findSyncById(Integer orderId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("te.name,te.sync_number syncNumber,");
			sb.append("tloi.link_people addPeople,");
			sb.append("tto.order_number orderNumber,");	
			sb.append("tto.price price,");
			sb.append("date_format(tto.input_time,'%Y-%m-%d %H:%i:%s') inputTime,");
			
			sb.append(" (select ");
			sb.append(" cast(sum(tsc.num) as decimal(10,0)) ");
			sb.append(" from Tb_Shopping_Car tsc inner join tb_res_order_car reoc on reoc.car_id=tsc.id ");
			sb.append("  where  tsc.del_Flag = 0  ");
			sb.append(" and   order_id=tto.id  )num, ");
			sb.append("tto.id,tloi.sync_number,tloi.legal_person_idcard ");
			
			sb.append(" from tb_tool_order tto LEFT JOIN tb_enterprise te on te.id=tto.tool_enterprise_Id   ");
			sb.append("  LEFT JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_Id and tto.type=2  ");
			sb.append("  LEFT JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_Id and tto.type=1  ");
			sb.append(" where tto.id=:orderId ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("orderId", orderId);
			List <Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<JSONObject> findInOrderId(Integer orderId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" cast(tsc.num as decimal(10,0))  num,");
			sb.append(" ifnull(tl.price,0) unitPrice,ifnull(tl.sync_number,'') productNum,'件' numUnit ");
			sb.append(" from Tb_Shopping_Car tsc ");
			sb.append(" inner join tb_tool tl on tl.id = tsc.tool_id "
					+ " inner join tb_tool_catalog ttc on ttc.code=tl.code  ");
			sb.append("  where  tsc.del_Flag = 0  ");
			sb.append(" and  tsc.id in (select car_id from tb_res_order_car where order_id=:orderId  ) ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("orderId",orderId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <JSONObject> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> listToolOrderByBuyEnterpriseId(Integer buyEnterpriseId,String toolName,String productionUnit,
			String startTime,String endTime,Integer nowPage,Integer pageCount,String enterpriseName,String orderNumber){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tsc.id id,tt.name toolName,tt.code code,ifnull(tt.production_units,'') productionUnit,tto.order_number orderNumber,round(tsc.num*tsc.price,2) price, ");
		queryString.append(" ifnull(tt2.name,2) type,tsc.num num,DATE_FORMAT(tto.input_time,'%Y-%m-%d') buyTime,te.name sellEnterpriseName,ifnull(tto.is_evaluate,'') isEvaluate,tto.id orderId  ");
		queryString.append(" FROM tb_shopping_car tsc left join tb_tool tt on tsc.tool_id = tt.id ");
		queryString.append(" left join tb_res_order_car troc on troc.car_id = tsc.id ");
		queryString.append(" left join tb_tool_order tto on troc.order_id = tto.id ");
		queryString.append(" left join tb_enterprise te on te.id = tto.tool_enterprise_id ");
		queryString.append(" left join tb_res_enterprise_tool_order treto on treto.tool_order_id = tto.id ");
		queryString.append(" left join tb_type tt2 on tt2.id = tt.type ");
		queryString.append(" where tto.del_flag = 0 and treto.enterprise_id is not null ");
		if (buyEnterpriseId != null) {
			queryString.append(" and treto.enterprise_id = :buyEnterpriseId ");
		}
		if (!StringUtils.isEmpty(orderNumber)) {
			queryString.append(" and tto.order_number like :orderNumber ");
		}
		if (!StringUtils.isEmpty(enterpriseName)) {
			queryString.append(" and te.name like :enterpriseName ");
		}
		if (!StringUtils.isEmpty(toolName)) {
			queryString.append(" and tt.name like :toolName ");
		}
		if (!StringUtils.isEmpty(productionUnit)) {
			queryString.append(" and tt.production_units like :productionUnit ");
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryString.append(" and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryString.append(" and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" order by tto.input_time desc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (buyEnterpriseId != null) {
			query.setParameter("buyEnterpriseId",buyEnterpriseId);
		}
		if (!StringUtils.isEmpty(orderNumber)) {
			query.setParameter("orderNumber","%"+orderNumber+"%");
		}
		if (!StringUtils.isEmpty(enterpriseName)) {
			query.setParameter("enterpriseName","%"+enterpriseName+"%");
		}
		if (!StringUtils.isEmpty(toolName)) {
			query.setParameter("toolName","%"+toolName+"%");
		}
		if (!StringUtils.isEmpty(productionUnit)) {
			query.setParameter("productionUnit","%"+productionUnit+"%");
		}
		if (!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime",startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime",endTime);
		}
		if (nowPage != null && pageCount != null) {
			query.setFirstResult((nowPage-1)*pageCount).setMaxResults(pageCount);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Integer listToolOrderByBuyEnterpriseIdCount(Integer buyEnterpriseId,String toolName,String productionUnit,
			String startTime,String endTime,String enterpriseName,String orderNumber){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT count(distinct tsc.id) FROM tb_shopping_car tsc left join tb_tool tt on tsc.tool_id = tt.id ");
		queryString.append(" left join tb_res_order_car troc on troc.car_id = tsc.id ");
		queryString.append(" left join tb_tool_order tto on troc.order_id = tto.id ");
		queryString.append(" left join tb_enterprise te on te.id = tto.tool_enterprise_id ");
		queryString.append(" left join tb_res_enterprise_tool_order treto on treto.tool_order_id = tto.id ");
		queryString.append(" left join tb_type tt2 on tt2.id = tt.type ");
		queryString.append(" where tto.del_flag = 0 and treto.enterprise_id is not null ");
		if (buyEnterpriseId != null) {
			queryString.append(" and treto.enterprise_id = :buyEnterpriseId ");
		}
		if (!StringUtils.isEmpty(orderNumber)) {
			queryString.append(" and tto.order_number like :orderNumber ");
		}
		if (!StringUtils.isEmpty(enterpriseName)) {
			queryString.append(" and te.name like :enterpriseName ");
		}
		if (!StringUtils.isEmpty(toolName)) {
			queryString.append(" and tt.name like :toolName ");
		}
		if (!StringUtils.isEmpty(productionUnit)) {
			queryString.append(" and tt.production_units like :productionUnit ");
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryString.append(" and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryString.append(" and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ");
		}
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (buyEnterpriseId != null) {
			query.setParameter("buyEnterpriseId",buyEnterpriseId);
		}
		if (!StringUtils.isEmpty(orderNumber)) {
			query.setParameter("orderNumber","%"+orderNumber+"%");
		}
		if (!StringUtils.isEmpty(enterpriseName)) {
			query.setParameter("enterpriseName","%"+enterpriseName+"%");
		}
		if (!StringUtils.isEmpty(toolName)) {
			query.setParameter("toolName","%"+toolName+"%");
		}
		if (!StringUtils.isEmpty(productionUnit)) {
			query.setParameter("productionUnit","%"+productionUnit+"%");
		}
		if (!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime",startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime",endTime);
		}
		List<Object> list = query.getResultList();
		return Integer.valueOf(list.get(0).toString());
	}
	
	public List<Map<String, Object>> statisticToolOrderByBuyEnterpriseId(Integer buyEnterpriseId,String startTime,String endTime){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tto.tool_enterprise_id enterpriseId, te.name enterpriseName,count(distinct tto.id) orderCount,Round(sum(tto.price),2) orderMoney ");
		queryString.append(" FROM tb_tool_order tto left join tb_res_enterprise_tool_order treto on treto.tool_order_id = tto.id ");
		queryString.append(" left join tb_enterprise te on te.id = tto.tool_enterprise_id ");
		queryString.append(" where tto.del_flag = 0 and tto.status = 4 and treto.enterprise_id is not null ");
		if (buyEnterpriseId != null) {
			queryString.append(" and treto.enterprise_id = :buyEnterpriseId ");
		}
		if (!StringUtils.isEmpty(startTime)) {
			queryString.append(" and DATE_FORMAT(tto.input_time,'%Y-%m-%d') >= :startTime ");
		}
		if (!StringUtils.isEmpty(endTime)) {
			queryString.append(" and DATE_FORMAT(tto.input_time,'%Y-%m-%d') <= :endTime ");
		}
		queryString.append(" group by tto.tool_enterprise_id order by te.id asc ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		if (buyEnterpriseId != null) {
			query.setParameter("buyEnterpriseId",buyEnterpriseId);
		}
		if (!StringUtils.isEmpty(startTime)) {
			query.setParameter("startTime",startTime);
		}
		if (!StringUtils.isEmpty(endTime)) {
			query.setParameter("endTime",endTime);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}
	
	public Map<String, Object> getOrderInfoByCarId(Integer carId){
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT tto.id,tto.order_number orderNumber,date_format(tto.input_time,'%Y-%m-%d %H:%i:%s') inputTime, ");
		queryString.append(" tto.price,ifnull(tloi.link_people,'') linkPeople,ifnull(tloi.link_mobile,'') phone,ifnull(tloi.address,'') address, ");
		queryString.append(" te.name sellEnterpriseName,tloi.legal_person_idcard idcard ");
		queryString.append(" FROM tb_tool_order tto left join tb_enterprise te on te.id = tto.tool_enterprise_id ");
		queryString.append(" left join tb_link_order_info tloi on tloi.id = tto.plant_enterprise_id ");
		queryString.append(" where tto.id = (select distinct order_id from tb_res_order_car where car_id = :carId limit 1) ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("carId", carId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
}
