package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.StringUtil;

public class StatisticDao extends BaseZDao{

	public StatisticDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}

	public Object[] allRecoveryStatistic(Integer enterpriseId,String startTime,String endTime,String selectAll,List<Integer> enterpriseIdList) {
		try {
 			String queryString = 
 					"  SELECT "
 					+ " SUM(if(ttrr.tool_recovery_id <> 13,ttrr.number,0)),"
 					+ " cast(SUM(ttrr.number * ttr.price ) as decimal(18,2)),"
 					
 					+ " (SELECT SUM(CAST(area AS decimal)) "
 					+ "  FROM tb_zone tz "
 					+ "  WHERE del_flag=0 ";
 			if(enterpriseId!=null){
 				queryString+=" AND enterprise_id=:enterpriseId ";
 			}
 			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
 				queryString+=" and enterprise_id in (:enterpriseIdList) ";
			}
 			queryString+="), "
 					+ "SUM(if(ttrr.tool_recovery_id = 13,ttrr.number,0)) "
 					+ "FROM tb_tool_recovery_record ttrr left join tb_tool_recovery ttr on ttr.id=ttrr.tool_recovery_id "
 					+ "WHERE ttrr.del_flag=0  ";
 			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
 			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
 				queryString+=" and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
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
	
	
	public List<Object[]> recStatByStreet(Integer enterpriseId,String startTime,String endTime,Integer type,String selectAll,List<Integer> enterpriseIdList) {
		try {
			String queryString="";
			if(type==null||type==1){
				 queryString += "SELECT ta.name,SUM(ttrr.number),ta.id "
						 	+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id LEFT JOIN "
		 					+ " tb_area ta ON ta.id=te.dscd "
		 					+ "WHERE ttrr.del_flag=0 AND ta.name is not null and ttrr.tool_recovery_id <> 13 ";
			}else if(type==2){
				 queryString += "SELECT ta.name,cast(SUM(ttrr.number * ttr.price ) as decimal(18,2)),ta.id "
						 	+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id LEFT JOIN "
		 					+ " tb_area ta ON ta.id=te.dscd "
		 					+ " left join tb_tool_recovery ttr on ttr.id=ttrr.tool_recovery_id "
		 					+ " WHERE ttrr.del_flag=0  AND ta.name is not null and ttrr.tool_recovery_id <> 13 ";
			}else if(type==3){
				 queryString += "SELECT ta.name,CAST(SUM(tz.area )/10000 AS decimal(18,2)),ta.id "
						 	+ "FROM tb_zone ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id LEFT JOIN "
		 					+ " tb_area ta ON ta.id=te.dscd "
		 					+ "WHERE ttrr.del_flag=0  AND ta.name is not null ";
			}else if(type==4){
				 queryString += "SELECT ta.name,SUM(ttrr.number),ta.id "
						 	+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id LEFT JOIN "
		 					+ " tb_area ta ON ta.id=te.dscd "
		 					+ "WHERE ttrr.del_flag=0 AND ta.name is not null and ttrr.tool_recovery_id = 13 ";
			}
 					
 			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
 			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
 			if(!StringUtil.isNullOrEmpty(startTime)&&type!=3){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)&&type!=3){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			queryString +="GROUP BY ta.name";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
 			if(!StringUtil.isNullOrEmpty(startTime)&&type!=3){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)&&type!=3){
 				query.setParameter("endTime",endTime);
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
	
	public List<Object[]> recStatByStore(Integer enterpriseId,String startTime,String endTime,String selectAll,
			List<Integer> enterpriseIdList, String name, Integer nowPage, Integer pageCount) {
		try {
			String queryString="";
				 queryString += "SELECT te.id,te.name, SUM(case when ttrr.tool_recovery_id <> 13 then ttrr.number end) value1,"
				 		+ "sum(case when ttrr.tool_recovery_id = 13 then ttrr.number end) value2 "
				 		+ "FROM tb_tool_recovery_record ttrr LEFT JOIN  tb_enterprise te ON te.id=ttrr.enterprise_id "
				 		+ "WHERE ttrr.del_flag=0  AND te.name is not null ";
			
 			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
 			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				queryString+="AND te.name LIKE :name ";
 			}
 			
 			queryString +=" GROUP BY te.name";
 				queryString +=" ORDER BY value1 desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				query.setParameter("name", "%"+name+"%");
 			}
 			if(nowPage!=null&pageCount!=null){
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
	
	public Integer recStatByStoreCount(Integer enterpriseId,String startTime,String endTime,String selectAll,
			List<Integer> enterpriseIdList, String name) {
		try {
			String queryString="";
			queryString += "SELECT count(distinct te.name) "
				 		+ "FROM tb_tool_recovery_record ttrr LEFT JOIN  tb_enterprise te ON te.id=ttrr.enterprise_id "
				 		+ "WHERE ttrr.del_flag=0  AND te.name is not null ";
			
 			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
 			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				queryString+="AND te.name LIKE :name ";
 			}
 			
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				query.setParameter("name", "%"+name+"%");
 			}
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
	
	public List<Object[]> recStatByLinkPeople(Integer enterpriseId,String startTime,String endTime, String selectAll,
			List<Integer> enterpriseIdList, String name, Integer nowPage, Integer pageCount) {
		try {
			String queryString="";
				 queryString += "SELECT oi.id, oi.name, SUM(case when ttrr.tool_recovery_id <> 13 then ttrr.number end) value1,"
				 		+ "sum(case when ttrr.tool_recovery_id = 13 then ttrr.number end) value2 "
				 		+ "FROM tb_tool_recovery_record ttrr LEFT JOIN  tb_enterprise te ON te.id=ttrr.enterprise_id "
				 		+ "	LEFT JOIN tb_link_order_info oi on oi.id=ttrr.link_order_info_id "
				 		+ "WHERE ttrr.del_flag=0  AND te.name is not null ";
		
 			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
 			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				queryString+="AND oi.name LIKE :name ";
 			}
 			
 			queryString +=" GROUP BY oi.name";
 				queryString +=" ORDER BY value1 desc ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				query.setParameter("name", "%"+name+"%");
 			}
 			if(nowPage!=null&pageCount!=null){
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
	
	public Integer recStatByLinkPeopleCount(Integer enterpriseId,String startTime,String endTime,String selectAll,
			List<Integer> enterpriseIdList, String name) {
		try {
			String queryString="";
			 queryString += "SELECT count(distinct oi.name) "
				 		+ "FROM tb_tool_recovery_record ttrr LEFT JOIN  tb_enterprise te ON te.id=ttrr.enterprise_id "
				 		+ "	LEFT JOIN tb_link_order_info oi on oi.id=ttrr.link_order_info_id "
				 		+ "WHERE ttrr.del_flag=0  AND te.name is not null ";
			 if(enterpriseId!=null){
				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
			if(!StringUtil.isNullOrEmpty(startTime)){
				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
			}
			if(!StringUtil.isNullOrEmpty(endTime)){
				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
			}
			if(!StringUtil.isNullOrEmpty(name)){
				queryString+="AND oi.name LIKE :name ";
			}
 			
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				query.setParameter("name", "%"+name+"%");
 			}
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
	
	public List<Object[]> recStatByMonth(Integer enterpriseId,String startTime,String endTime,Integer type,String selectAll,List<Integer> enterpriseIdList) {
		try {
			String queryString="";
			if(type==null||type==1){
				 queryString += "SELECT SUM(ttrr.number), Date_format(ttrr.input_time,'%Y-%m') "
						 	+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id "
		 					+ "WHERE ttrr.del_flag=0 AND  ttrr.tool_recovery_id <> 13 ";
			}else if(type==2){
				 queryString += "SELECT cast(SUM(ttrr.number * ttr.price ) as decimal(18,2)), month(ttrr.input_time) "
						 	+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id "
		 					+ " left join tb_tool_recovery ttr on ttr.id=ttrr.tool_recovery_id "
		 					+ " WHERE ttrr.del_flag=0  AND ttrr.tool_recovery_id <> 13 ";
			}else if(type==3){
				 queryString += "SELECT CAST(SUM(tz.area )/10000 AS decimal(18,2)), month(ttrr.input_time) "
						 	+ "FROM tb_zone ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id "
		 					+ "WHERE ttrr.del_flag=0   ";
			}else if(type==4){
				 queryString += "SELECT SUM(ttrr.number), Date_format(ttrr.input_time,'%Y-%m')  "
						 	+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
		 					+ " tb_enterprise te ON te.id=ttrr.enterprise_id "
		 					+ "WHERE ttrr.del_flag=0 AND  ttrr.tool_recovery_id = 13 ";
			}
			queryString += " and year(ttrr.input_time) = year(now()) ";
			if(enterpriseId!=null){
				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
			if(!StringUtil.isNullOrEmpty(startTime)&&type!=3){
				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
			}
			if(!StringUtil.isNullOrEmpty(endTime)&&type!=3){
				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
			}
			queryString +="GROUP BY  month(ttrr.input_time)";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtil.isNullOrEmpty(startTime)&&type!=3){
				query.setParameter("startTime",startTime);
			}
			if(!StringUtil.isNullOrEmpty(endTime)&&type!=3){
				query.setParameter("endTime",endTime);
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
	
	public List<Object[]> recStatByKinds(Integer enterpriseId,String startTime,String endTime) {
		try {
			String queryString=
					  "SELECT "
					+ " SUM(ttrr.number), "
					+ " ttr.name "
					+ "FROM tb_tool_recovery_record ttrr RIGHT JOIN tb_tool_recovery ttr "
					+ " ON ttrr.tool_recovery_id = ttr.id AND ttrr.del_flag=0 ";
			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
			queryString+= "WHERE ttr.del_flag=0 "
					+"GROUP BY ttr.name";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
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
	
	public Map<String,Object> recStatByTime(Integer enterpriseId,String startTime,String endTime,String selectAll,List<Integer> enterpriseIdList) {
		try {
			String queryString=
 					  " SELECT "
 					+ " ifnull(SUM(ttrr.number),0) number"
 					+ " FROM tb_tool_recovery_record ttrr "
 					+ " WHERE  ttrr.del_flag=0 and ttrr.tool_recovery_id <> 13 ";
 			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
 			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString += " and ttrr.enterprise_id in (:enterpriseIdList) ";
			}
 			if(!StringUtils.isNullOrEmpty(startTime)) {
 				queryString += " and  date_format(ttrr.input_time,'%Y-%m') >= :startTime ";
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				queryString += " and   date_format(ttrr.input_time,'%Y-%m') <= :endTime ";
			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Object[]> recStatByTimeMap(Integer enterpriseId,String startTime,String endTime) {
		try {
			String queryString=
 					  "SELECT "
 					+ " SUM(ttrr.number),"
 					+ " HOUR(input_time) "
 					+ "FROM tb_tool_recovery_record ttrr "
 					+ "WHERE 21>HOUR(input_time) "
 					+ " AND HOUR(input_time)>=9 "
 					+ " AND ttrr.del_flag=0 ";
 			if(enterpriseId!=null){
 				queryString+="AND ttrr.enterprise_id=:enterpriseId ";
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			queryString +="GROUP BY HOUR(input_time)";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(enterpriseId!=null){
				query.setParameter("enterpriseId",enterpriseId);
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
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
	
	public List<Object[]> recStatByEnterprise(String startTime, String endTime, Integer nowPage, Integer pageCount,String name) {
		try {
			String queryString=
  					  "SELECT " 
					+ " te.name, "
 					+ " SUM(ttrr.number), "
 					+ " SUM(ttrr.total_price), "
 					+ " SUM(CAST(tz.area as decimal)), "
 					+ " CAST(SUM(ttrr.number)/SUM(CAST(tz.area as decimal))*550 AS decimal(10,2)) "
 					+ "FROM tb_tool_recovery_record ttrr Right JOIN "
  					+ " tb_zone tz ON ttrr.enterprise_id=tz.enterprise_id "
  					+ "	AND ttrr.del_flag=0 ";
			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			queryString+= " LEFT JOIN "
  					+ " tb_enterprise te ON te.id=tz.enterprise_id LEFT JOIN "
  					+ " tb_area ta ON ta.id=te.dscd "
  					+ "WHERE ta.name is not null ";
  					if(!StringUtil.isNullOrEmpty(name)){
  						queryString+= " AND te.name like :name "; 
  		 			}
  					queryString+= "GROUP BY te.name "
 					+ "ORDER BY SUM(ttrr.number) desc";
			Query query = getEntityManager().createNativeQuery(queryString);
			
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
 			if(nowPage!=null&pageCount!=null){
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
	
	public Integer recStatByEnterpriseCount(String startTime, String endTime,String name) {
			try {
				String queryString=
	  					  "SELECT COUNT(1)" 
	 					+ "FROM tb_tool_recovery_record ttrr Right JOIN "
	  					+ " tb_zone tz ON ttrr.enterprise_id=tz.enterprise_id "
	  					+ "	AND ttrr.del_flag=0 ";
				if(!StringUtil.isNullOrEmpty(startTime)){
	 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
	 			}
	 			if(!StringUtil.isNullOrEmpty(endTime)){
	 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
	 			}
	 			queryString+= " LEFT JOIN "
	  					+ " tb_enterprise te ON te.id=tz.enterprise_id LEFT JOIN "
	  					+ " tb_area ta ON ta.id=te.dscd "
	  					+ "WHERE ta.name is not null ";
	 			if(!StringUtil.isNullOrEmpty(name)){
						queryString+= " AND te.name like :name "; 
		 			}
			Query query = getEntityManager().createNativeQuery(queryString);
			
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
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
	
	public List<Object[]> recStatByTool(String startTime,String endTime, String recoveryId) {
		try {
			String queryString=
 					  "SELECT "
 			 		+ " te.name, "
 					+ " SUM(ttrr.number) "
 					+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
  					+ " tb_enterprise te ON te.id=ttrr.enterprise_id "
 					+ "WHERE ttrr.del_flag=0 ";
			if(recoveryId!=null){
 				queryString+="AND tool_recovery_id=:recoveryId ";
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+="AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			queryString +="GROUP BY te.name "
 					+ " ORDER BY SUM(ttrr.number) desc "
 					+ " LIMIT 5";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(recoveryId!=null){
				query.setParameter("recoveryId",recoveryId);
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
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
	//	-----全市农资销售统计
	public Map<String, Object> getAllOrderCount(Integer enterpriseId,String startTime,String endTime,String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct tto.id) num ,cast(sum( tsc.price * tsc.num ) as decimal(10,2) )price ");
			sb.append(" from tb_tool_order tto ");
			sb.append(" inner join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" inner JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" inner JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" inner JOIN tb_tool tl ON tl.id = tsc.tool_id  ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and te.id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tto.tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  date(tto.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and  date(tto.input_time) <= :endTime ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
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
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	//	-----各街道销售统计
	public List<Map<String, Object>> getAllOrderDscd(Integer enterpriseId,String startTime,String endTime,String selectAll,
			List<Integer> enterpriseIdList,String code,String productAttributes,String toolType,Integer nowPage,Integer pageCount,String name) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from (select count(distinct tto.id) num,cast(ifnull(sum(tsc.price),0) as decimal) price,te.dscd,ta.name dscdName,sum(tsc.num) toolNum ");
			sb.append(" from tb_enterprise te left join tb_tool_order tto on te.id=tto.tool_enterprise_id  ");
			sb.append(" and tto.STATUS = 4 AND tto.del_flag = 0  ");
			sb.append(" left JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" left JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" left join tb_tool tl on tl.id = tsc.tool_id ");
			sb.append(" left join tb_area ta on ta.id=te.dscd ");
			sb.append(" where  1=1 ");
			if (!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and ta.name like :name ");
			}
			if (!StringUtils.isNullOrEmpty(toolType)) {
				sb.append(" and tl.type = :toolType ");
			}
			if (!StringUtils.isNullOrEmpty(productAttributes)) {
				if ("其他".equals(productAttributes)) {
					sb.append(" and (tl.product_attributes is null or tl.product_attributes = '') ");
				}else {
					sb.append(" and tl.product_attributes = :productAttributes ");
				}
				
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" and tl.code =:code ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and tto.input_time <= :endTime ");
			}
			sb.append(" GROUP BY te.dscd) a where toolNum is not null");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if (!StringUtils.isNullOrEmpty(toolType)) {
				query.setParameter("toolType", toolType);
			}
			if (!StringUtils.isNullOrEmpty(productAttributes) && !"其他".equals(productAttributes)) {
				query.setParameter("productAttributes", productAttributes);
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", code);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+ " 23:59:59");
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			if(nowPage!=null && pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getAllOrderDscdCount(Integer enterpriseId,String startTime,String endTime,String selectAll,
			List<Integer> enterpriseIdList,String code,String productAttributes,String toolType,String name) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(*) from (select sum(tsc.num) toolNum ");
			sb.append(" from tb_enterprise te left join tb_tool_order tto on te.id=tto.tool_enterprise_id  ");
			sb.append(" and tto.STATUS = 4 AND tto.del_flag = 0  ");
			sb.append(" left JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" left JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" left join tb_tool tl on tl.id = tsc.tool_id ");
			sb.append(" left join tb_area ta on ta.id=te.dscd ");
			sb.append(" where  1=1 ");
			if (!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and ta.name like :name ");
			}
			if (!StringUtils.isNullOrEmpty(toolType)) {
				sb.append(" and tl.type = :toolType ");
			}
			if (!StringUtils.isNullOrEmpty(productAttributes)) {
				if ("其他".equals(productAttributes)) {
					sb.append(" and (tl.product_attributes is null or tl.product_attributes = '') ");
				}else {
					sb.append(" and tl.product_attributes = :productAttributes ");
				}
				
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" and tl.code =:code ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and tto.input_time <= :endTime ");
			}
			sb.append(" GROUP BY te.dscd) a where toolNum is not null");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if (!StringUtils.isNullOrEmpty(toolType)) {
				query.setParameter("toolType", toolType);
			}
			if (!StringUtils.isNullOrEmpty(productAttributes) && !"其他".equals(productAttributes)) {
				query.setParameter("productAttributes", productAttributes);
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", code);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			List<Object> list = query.getResultList();
			return Integer.valueOf(list.get(0).toString());
		} catch (RuntimeException re) {
			throw re;
		}
	}
	

	
	public List<Object[]> recStatByType(String startTime,String endTime, Integer type,Integer nowPage,Integer pageCount,String name) {
		try {
			String queryString=
 					  "SELECT "
 					+ " tloi.name, "
 					+ " SUM(ttrr.number) "
 					+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
  					+ " tb_link_order_info tloi ON tloi.id=ttrr.link_order_info_id "
 					+ "WHERE ttrr.del_flag=0 ";
			if(type!=null){
 				queryString+=" AND tloi.type=:type ";
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+=" AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+=" AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				queryString+=" AND tloi.name like :name "; 
 			}
 			queryString +=" GROUP BY tloi.name "
 					+ " ORDER BY SUM(ttrr.number) desc "
 					+ " ";
			Query query = getEntityManager().createNativeQuery(queryString);
			if(type!=null){
				query.setParameter("type",type);
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
 			if(nowPage!=null&pageCount!=null){
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
	public Integer recStatByTypeCount(String startTime,String endTime, Integer type,String name) {
		try {
			String queryString=
 					  "SELECT "
 					+ " count(distinct tloi.name) "
 					+ "FROM tb_tool_recovery_record ttrr LEFT JOIN "
  					+ " tb_link_order_info tloi ON tloi.id=ttrr.link_order_info_id "
 					+ "WHERE ttrr.del_flag=0 ";
			if(type!=null){
 				queryString+=" AND tloi.type=:type ";
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				queryString+=" AND date_format(ttrr.input_time,'%Y-%m-%d') >=:startTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				queryString+=" AND date_format(ttrr.input_time,'%Y-%m-%d') <=:endTime ";
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
 				queryString+=" AND tloi.name like :name "; 
 			}
			Query query = getEntityManager().createNativeQuery(queryString);
			if(type!=null){
				query.setParameter("type",type);
 			}
 			if(!StringUtil.isNullOrEmpty(startTime)){
 				query.setParameter("startTime",startTime);
 			}
 			if(!StringUtil.isNullOrEmpty(endTime)){
 				query.setParameter("endTime",endTime);
 			}
 			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return  Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
//	------时间段统计
	public Map<String, Object> getAllOrderTimeCount(Integer enterpriseId,String startTime,String endTime) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select ifnull(count(distinct tto.id),0) num,ifnull(sum(tsc.price),0) price ");
			sb.append(" from tb_tool_order tto ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" left JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" left JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 ");
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  date_format(tto.input_time,'%Y-%m') >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and   date_format(tto.input_time,'%Y-%m') <= :endTime ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getAllOrderTimeCountByQuarter(Integer enterpriseId,Integer year,String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT 1 'quarter',ifnull( count( DISTINCT tto.id ), 0 ) num,ifnull( sum( tto.price ), 0 ) price,'1月' startTime,	'3月' endTime ");
			sb.append(" FROM tb_tool_order tto LEFT JOIN tb_enterprise te ON te.id = tto.tool_enterprise_id ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if (year != null) {
				sb.append(" and tto.input_time >= concat(:year,'-01-01 00:00:00') ");
				sb.append(" and tto.input_time <= concat(:year,'-03-31 23:59:59') ");
			}
			sb.append(" union all ");
			sb.append(" SELECT 2 'quarter',ifnull( count( DISTINCT tto.id ), 0 ) num,ifnull( sum( tto.price ), 0 ) price,'4月' startTime,	'6月' endTime ");
			sb.append(" FROM tb_tool_order tto LEFT JOIN tb_enterprise te ON te.id = tto.tool_enterprise_id ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if (year != null) {
				sb.append(" and tto.input_time >= concat(:year,'-04-01 00:00:00') ");
				sb.append(" and tto.input_time <= concat(:year,'-06-30 23:59:59') ");
			}
			sb.append(" union all ");
			sb.append(" SELECT 3 'quarter',ifnull( count( DISTINCT tto.id ), 0 ) num,ifnull( sum( tto.price ), 0 ) price,'7月' startTime,	'9月' endTime ");
			sb.append(" FROM tb_tool_order tto LEFT JOIN tb_enterprise te ON te.id = tto.tool_enterprise_id ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if (year != null) {
				sb.append(" and tto.input_time >= concat(:year,'-07-01 00:00:00') ");
				sb.append(" and tto.input_time <= concat(:year,'-09-30 23:59:59') ");
			}
			sb.append(" union all ");
			sb.append(" SELECT 4 'quarter',ifnull( count( DISTINCT tto.id ), 0 ) num,ifnull( sum( tto.price ), 0 ) price,'10月' startTime,'12月' endTime ");
			sb.append(" FROM tb_tool_order tto LEFT JOIN tb_enterprise te ON te.id = tto.tool_enterprise_id ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if (year != null) {
				sb.append(" and tto.input_time >= concat(:year,'-10-01 00:00:00') ");
				sb.append(" and tto.input_time <= concat(:year,'-12-31 23:59:59') ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if (year != null ) {
				query.setParameter("year", year);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
//	-----各农资店销售统计
	public List<Map<String, Object>> getAllOrderEnterpriseCount(Integer enterpriseId,String startTime,String endTime,
			Integer nowPage,Integer pageCount,String name,String selectAll,List<Integer> enterpriseIdList,String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select te.id enterpriseId, te.name enterpriseName,count(distinct tto.id) num,sum(tto.price) price  ");
			sb.append(" from tb_tool_order tto ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" where  tto.status=4  and tto.del_flag=0  and te.state!=0 ");
			if (!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and   tto.input_time <= :endTime ");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" AND te.name like :name "); 
 			}
			sb.append(" group by  tto.tool_enterprise_id order by num desc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
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
	public Integer getAllOrderEnterpriseCountCount(Integer enterpriseId,String startTime,String endTime,String name,
			String selectAll,List<Integer> enterpriseIdList,String dscd) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct tto.tool_enterprise_id)  ");
			sb.append(" from tb_tool_order tto ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 and te.state!=0 ");
			if (!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and   tto.input_time <= :endTime ");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" AND te.name like :name "); 
 			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
//	-----各农资同一商品售卖数量统计
	public List<Map<String, Object>> getAllOrderToolToolCount(Integer enterpriseId,String startTime,String endTime,String code) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select te.name enterpriseName,sum(tsc.num) num,sum(tsc.price) price  ");
			sb.append(" from tb_tool_order tto ");
			sb.append(" inner join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" inner JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" inner JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" inner JOIN tb_tool tl ON tl.id = tsc.tool_id  ");
			
			sb.append(" where  tto.status=4  and tto.del_flag=0 AND te.enterprise_type = 3  ");
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  date_format(tto.input_time,'%Y-%m-%d') >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and  date_format(tto.input_time,'%Y-%m-%d') <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" and tl.code =:code ");
			}
			sb.append(" group by te.id order by num desc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", code);
			}
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

//	------按照农药种类统计订单 
	public List<Map<String, Object>> getAllOrderToolTypeCount(Integer enterpriseId,String startTime,String endTime,Integer toolType,
			String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select s.productAttributes,sum(s.num) num,sum(s.price) price, ");
			sb.append(" sum(if(s.type=13, s.num * IF(lower(s.s2) = 'kg' OR s.s2 = '千克',s.specification * 1000,IF(lower(s.s2) = 'g' OR s.s2 = '克',s.specification, 0 )),0)) ng, ");
			sb.append(" sum(if(s.type=13, s.num * IF(lower(s.s2) = 'l' OR s.s2 = '升',s.specification * 1000,IF(lower(s.s2) = 'ml' OR s.s2 = '毫升',s.specification, 0 )),0)) nml, ");
			sb.append(" sum(if(s.type=12, s.num * IF(lower(s.s2) = 'kg' OR s.s2 = '千克',s.specification * 1000,IF(lower(s.s2) = 'g' OR s.s2 = '克',s.specification, 0 )),0)) fg, ");
			sb.append(" sum(if(s.type=12, s.num * IF(lower(s.s2) = 'l' OR s.s2 = '升',s.specification * 1000,IF(lower(s.s2) = 'ml' OR s.s2 = '毫升',s.specification, 0 )),0)) fml ");
			sb.append(" from (SELECT SUBSTRING_INDEX( tl.specification, '/', 1 ) specification, ");
			sb.append(" SUBSTRING_INDEX( SUBSTRING_INDEX( tl.specification, '/', 2 ), '/',- 1 ) s2, ");
			sb.append(" tl.yxcf_unit,if(tl.product_attributes is null or tl.product_attributes = '','其他',tl.product_attributes) productAttributes, ");
			sb.append(" tl.id,tl.code,tsc.num,tsc.num*tsc.price price,tl.type ");
			sb.append(" from tb_tool_order tto ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" left JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" left JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" left JOIN tb_tool tl ON tl.id = tsc.tool_id  ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 AND te.enterprise_type = 3  ");
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and  tto.input_time <= :endTime ");
			}
			if(toolType!=null) {
				sb.append(" and tl.type =:toolType ");
			}
			sb.append(" ) s group by productAttributes order by num desc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			if(toolType!=null) {
				query.setParameter("toolType", toolType);
			}
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
	
//	------按照企业/个人订单 
	public List<Map<String, Object>> getAllOrderLinkInfoCount(Integer enterpriseId,String startTime,String endTime,Integer type,Integer nowPage,
			Integer pageCount,String name,String selectAll,List<Integer> enterpriseIdList,String toolId,String code,String productAttributes) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tloi.id linkOrderInfoId, tloi.name linkName,sum(tsc.num) num,sum(tsc.price) price  ");
			sb.append(" from tb_link_order_info tloi left JOIN tb_tool_order tto on tloi.id=tto.plant_enterprise_id ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" left JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" left JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" left JOIN tb_tool tl ON tl.id = tsc.tool_id  ");
			sb.append(" where tto.type=2 and tto.status=4  and tto.del_flag=0 AND te.enterprise_type = 3  ");
			if (!StringUtils.isNullOrEmpty(productAttributes)) {
				if ("其他".equals(productAttributes)) {
					sb.append(" and (tl.product_attributes is null or tl.product_attributes = '') ");
				}else {
					sb.append(" and tl.product_attributes = :productAttributes ");
				}
				
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" and tl.code =:code ");
			}
			if (!StringUtils.isNullOrEmpty(toolId)) {
				sb.append(" and tl.code = :toolId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and  tto.input_time <= :endTime ");
			}
			if(type!=null) {
				sb.append(" and tloi.type =:type ");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" AND tloi.name like :name "); 
 			}
			sb.append(" GROUP BY tloi.id order by num desc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(productAttributes) && !"其他".equals(productAttributes)) {
				query.setParameter("productAttributes", productAttributes);
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", code);
			}
			if (!StringUtils.isNullOrEmpty(toolId)) {
				query.setParameter("toolId", toolId);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
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
	
	public Integer getAllOrderLinkInfoCountCount(Integer enterpriseId,String startTime,String endTime,Integer type,String name,
			String selectAll,List<Integer> enterpriseIdList,String toolId,String code,String productAttributes) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct tto.plant_enterprise_id)   ");
			sb.append(" from tb_tool_order tto left join tb_res_order_car troc on troc.order_id = tto.id ");
			sb.append(" left join tb_shopping_car tsc on tsc.id = troc.car_id ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" left JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_id and tto.type=2  ");
			sb.append(" left JOIN tb_tool tl ON tl.id = tsc.tool_id  ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 AND te.enterprise_type = 3  ");
			if (!StringUtils.isNullOrEmpty(productAttributes)) {
				if ("其他".equals(productAttributes)) {
					sb.append(" and (tl.product_attributes is null or tl.product_attributes = '') ");
				}else {
					sb.append(" and tl.product_attributes = :productAttributes ");
				}
				
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" and tl.code =:code ");
			}
			if (!StringUtils.isNullOrEmpty(toolId)) {
				sb.append(" and tl.code = :toolId ");
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and  tto.input_time <= :endTime ");
			}
			if(type!=null) {
				sb.append(" and tloi.type =:type ");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" AND tloi.name like :name "); 
 			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(productAttributes) && !"其他".equals(productAttributes)) {
				query.setParameter("productAttributes", productAttributes);
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", code);
			}
			if (!StringUtils.isNullOrEmpty(toolId)) {
				query.setParameter("toolId", toolId);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	public List<Map<String, Object>> getAllOrderToolToolTopCount(Integer enterpriseId,String startTime,String endTime,String code,
			Integer nowPage,Integer pageCount,String name,String selectAll,List<Integer> enterpriseIdList,String productAttributes,String toolType) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tl.code toolId,tl.name toolName,sum(tsc.num) num,sum(tsc.price) price, ");
			sb.append(" te.name enterpriseName,ifnull(tl.product_attributes,'') productAttributes from tb_tool_order tto ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" left JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" left JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" left JOIN tb_tool tl ON tl.id = tsc.tool_id  ");
			
			sb.append(" where  tto.status=4  and tto.del_flag=0 AND te.enterprise_type = 3  ");
			if (!StringUtils.isNullOrEmpty(toolType)) {
				sb.append(" and tl.type = :toolType ");
			}
			if (!StringUtils.isNullOrEmpty(productAttributes)) {
				if ("其他".equals(productAttributes)) {
					sb.append(" and (tl.product_attributes is null or tl.product_attributes = '') ");
				}else {
					sb.append(" and tl.product_attributes = :productAttributes ");
				}
				
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and tto.input_time <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" and tl.code =:code ");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" AND tl.name like :name "); 
 			}
			sb.append(" group by tl.code order by num desc");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(toolType)) {
				query.setParameter("toolType", toolType);
			}
			if (!StringUtils.isNullOrEmpty(productAttributes) && !"其他".equals(productAttributes)) {
				query.setParameter("productAttributes", productAttributes);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", code);
			}
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
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

	public Integer getAllOrderToolToolTopCountCount(Integer enterpriseId,String startTime,String endTime,String code,
			String name,String selectAll,List<Integer> enterpriseIdList,String productAttributes,String toolType) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct tl.code) count ");
			sb.append(" from tb_tool_order tto ");
			sb.append(" left join tb_enterprise te on te.id=tto.tool_enterprise_id   ");
			sb.append(" left JOIN tb_res_order_car troc ON troc.order_id = tto.id ");
			sb.append(" left JOIN tb_shopping_car tsc ON tsc.id = troc.car_id ");
			sb.append(" left JOIN tb_tool tl ON tl.id = tsc.tool_id  ");
			
			sb.append(" where  tto.status=4  and tto.del_flag=0 AND te.enterprise_type = 3  ");
			if (!StringUtils.isNullOrEmpty(toolType)) {
				sb.append(" and tl.type = :toolType ");
			}
			if (!StringUtils.isNullOrEmpty(productAttributes)) {
				if ("其他".equals(productAttributes)) {
					sb.append(" and (tl.product_attributes is null or tl.product_attributes = '') ");
				}else {
					sb.append(" and tl.product_attributes = :productAttributes ");
				}
				
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_id in (:enterpriseIdList) ");
			}
			if(enterpriseId!=null) {
				sb.append(" and tool_enterprise_id =:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  tto.input_time >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and  tto.input_time <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				sb.append(" and tl.code =:code ");
			}
			if(!StringUtil.isNullOrEmpty(name)){
				sb.append(" AND tl.name like :name "); 
 			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(toolType)) {
				query.setParameter("toolType", toolType);
			}
			if (!StringUtils.isNullOrEmpty(productAttributes) && !"其他".equals(productAttributes)) {
				query.setParameter("productAttributes", productAttributes);
			}
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime+" 00:00:00");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			if(!StringUtils.isNullOrEmpty(code)) {
				query.setParameter("code", code);
			}
			if(!StringUtil.isNullOrEmpty(name)){
				query.setParameter("name", "%"+name+"%");
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Integer getAllToolCatalogStatistic(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(id) count ");
			sb.append(" from tb_tool_catalog tto ");
			sb.append(" where   tto.del_flag=0  and tto.status=1  ");
			if(enterpriseId!=null) {
				sb.append(" and tto.code in (select code from tb_tool where enterprise_id=:enterpriseId) ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> getAllToolCatalogTypeStatistic(Integer enterpriseId,Integer type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(id) count,tto.product_attributes productAttributes ");
			sb.append(" from tb_tool_catalog tto ");
			sb.append(" where   tto.del_flag=0  and tto.status=1  ");
			if(enterpriseId!=null) {
				sb.append(" and tto.code in (select code from tb_tool where enterprise_id=:enterpriseId) ");
			}
			if(type!=null) {
				sb.append(" and tto.type=:type ");
			}
			sb.append(" group by tto.product_attributes ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
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
	public List<Map<String, Object>> getAllEnterpriseToolStatistic(Integer enterpriseId,Integer type) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(id) count,tto.product_attributes productAttributes ");
			sb.append(" from tb_tool tto ");
			sb.append(" where   tto.del_flag=0  ");
			if(enterpriseId!=null) {
				sb.append(" and enterprise_id=:enterpriseId ");
			}
			if(type!=null) {
				sb.append(" and tto.type=:type ");
			}
			sb.append(" group by tto.product_attributes ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
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
	public Integer getAllEnterpriseToolStatistic(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(id) count ");
			sb.append(" from tb_tool tto ");
			sb.append(" where   tto.del_flag=0  ");
			if(enterpriseId!=null) {
				sb.append(" and  enterprise_id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			List<Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	//实名制订单/人数/统计
	public Map<String, Object> statisticValidation(String selectAll,List<Integer> enterpriseIdList,String startTime,String endTime) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT 	o.orderCount,o.orderPrice,enterprise.enterpriseCount,enterprise.enterpriseCount toolEnterpriseCount,people.peopleCount FROM ");
			sb.append(" (SELECT count( DISTINCT tto.id ) orderCount,cast( sum( tto.price + 0.0 ) AS DECIMAL ) orderPrice ");
			sb.append(" FROM tb_tool_order tto LEFT JOIN tb_link_order_info tloi ON tloi.id = tto.plant_enterprise_id ");
			sb.append(" WHERE tto.STATUS = 4 AND tto.del_flag = 0 AND tto.type = 2 AND tloi.is_validation = 1 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_Id in (:enterpriseIdList) ");
			}
			if(!StringUtil.isNullOrEmpty(startTime)){
				sb.append(" and date(tto.input_time) >= :startTime ");
			}
			if(!StringUtil.isNullOrEmpty(endTime)){
				sb.append(" and date(tto.input_time) <= :endTime ");
			}
			sb.append(" ) o	LEFT JOIN (SELECT count( DISTINCT te.id ) enterpriseCount FROM tb_enterprise te ");
			sb.append(" WHERE te.del_Flag = 0 AND te.enterprise_type = 3 AND te.state != 0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and te.id in (:enterpriseIdList) ");
			}
			sb.append(" ) enterprise ON 1 = 1 LEFT JOIN (SELECT	count( DISTINCT tloi.id ) peopleCount FROM tb_link_order_info tloi ");
			sb.append(" LEFT JOIN tb_res_enterprise_linkorderinfo trel ON tloi.id = trel.link_order_info_id ");
			sb.append(" WHERE tloi.del_flag = 0 AND tloi.type = 2 AND tloi.is_validation = 1 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and trel.enterprise_id in (:enterpriseIdList) ");
			}
			sb.append(" ) people ON 1 =1 ");		
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if(!StringUtil.isNullOrEmpty(startTime)){
				query.setParameter("startTime",startTime);
			}
			if(!StringUtil.isNullOrEmpty(endTime)){
				query.setParameter("endTime",endTime);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	//实名制订单/人数/统计
	public List<Map<String,Object>> statisticValidationDscdOrder(String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select count(distinct tto.id) orderCount,cast(sum(tto.price) as decimal) orderPrice,ta.name dsName  ");
			sb.append(" from tb_tool_order tto LEFT JOIN tb_enterprise te on te.id=tto.tool_enterprise_Id   ");
			sb.append("  LEFT JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_id and tto.type=2  ");
			sb.append(" LEFT JOIN tb_area ta on ta.id=te.dscd ");
			sb.append(" where  tto.status=4  and tto.del_flag=0 and tto.type=2 and is_validation=1 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and tto.tool_enterprise_Id in (:enterpriseIdList) ");
			}
			sb.append(" group by te.dscd  ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
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
	//地区实名制人数统计
	public List<Map<String, Object>> statisticValidationDscdPeople(String selectAll,List<Integer> enterpriseIdList) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select count(distinct tloi.id) peopleCount,ta.name dsName,te.dscd  ");
			sb.append(" from    ");
			sb.append("  tb_link_order_info tloi left join tb_res_enterprise_linkorderinfo trel on trel.link_order_info_id=tloi.id "
					+ " left join tb_enterprise te on te.id=trel.enterprise_id ");
			sb.append(" LEFT JOIN tb_area ta on ta.id=te.dscd ");
			sb.append(" where  is_validation=1 and tloi.del_flag=0 and te.id is not null ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				sb.append(" and te.id in (:enterpriseIdList) ");
			}
			sb.append(" group by te.dscd  ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
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
	
	//地区农资店统计
		public List<Map<String, Object>> statisticValidationDscdEnterprise(String selectAll,List<Integer> enterpriseIdList) {
			try {
				StringBuffer sb = new StringBuffer();
				
				sb.append(" select count(distinct te.id) enterpriseCount,ta.name dsName,te.dscd  ");
				sb.append(" from    ");
				sb.append( "  tb_enterprise te  ");
				sb.append(" LEFT JOIN tb_area ta on ta.id=te.dscd ");
				sb.append(" where   te.del_flag=0 and enterprise_Type=3   ");
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					sb.append(" and te.id in (:enterpriseIdList) ");
				}
				sb.append(" group by te.dscd  ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					query.setParameter("enterpriseIdList", enterpriseIdList);
				}
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
		//乡镇承包面积统计
		public List<Map<String, Object>> statisticValidationDscdLand(String selectAll,List<Integer> enterpriseIdList) {
			try {
				StringBuffer sb = new StringBuffer();
				
				sb.append(" select CAST(sum(te.area+0) as decimal(10,2) ) area,CAST(sum(rice_area+0) as decimal(10,2) ) riceArea,CAST(sum(wheat_area+0) as decimal(10,2) ) wheatArea ");
				sb.append(" 	,CAST(sum(vegetables_area+0) as decimal(10,2) ) vegetablesArea,CAST(sum(fruits_area+0) as decimal(10,2) ) fruitsArea,CAST(sum(other_area+0) as decimal(10,2) ) otherArea, ");
				sb.append(" CAST(sum(IFNULL( rice_area, 0 ) + IFNULL( wheat_area, 0 ) + IFNULL( vegetables_area, 0 ) + IFNULL( fruits_area,0 ) + ifnull( other_area, 0 )) AS DECIMAL ( 10, 2 ) )  areaSum,ta.name dsName,te.town  "); 
				sb.append(" from    ");
				sb.append( "  tb_arable_land te  ");
				sb.append(" LEFT JOIN tb_area ta on ta.id=te.town ");
				sb.append(" where   te.del_flag=0  ");
//				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
//					sb.append(" and te.id in (:enterpriseIdList) ");
//				}
				sb.append(" group by te.town  ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
//				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
//					query.setParameter("enterpriseIdList", enterpriseIdList);
//				}
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
		public List<Map<String, Object>> statisticToolIngredientsByDscd(String selectAll,List<Integer> enterpriseIdList,
				String productAttributes,String group,String startMonth,String endMonth) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" sum(if(s.type=13, ");
				sb.append(" 	num * effective_ingredients * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'kg'  ");
				sb.append(" 		OR s2 = '千克'  ");
				sb.append(" 		OR s2 = 'KG', ");
				sb.append(" 		specification * 1000, ");
				sb.append(" 	IF ");
				sb.append(" 			( s2 = 'g' OR s2 = '克' OR s2 = 'G', specification, 0 )  ");
				sb.append(" 		)  ,0) ");
				sb.append(" 	)/ 100 + 0  g, ");
				sb.append(" 	sum(if(s.type=13, ");
				sb.append(" 		num * effective_ingredients * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', s2 * 1000, 0 )  ");
				sb.append(" 	)   ,0) ");
				sb.append(" )/ 100 + 0 ml, ");
				sb.append(" dscd, ");
				sb.append(" sum( if(s.type=13,");
				sb.append(" 	num * n * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 ) ");
				sb.append(" 	)  ,0) ");
				sb.append(" )/ 100 + 0 nml, ");
				sb.append(" sum(if(s.type=12,");
				sb.append(" 	num * p * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )  ");
				sb.append(" 	) ,0) ");
				sb.append(" )/ 100 + 0  pml, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 	num * k * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )   ");
				sb.append(" 	)  ,0) ");
				sb.append(" 	)/ 100 + 0 kml, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 	num * npk * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )   ");
				sb.append(" 	)  ,0) "); 
				sb.append(" )/ 100 + 0 npkml, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 	num * np * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )  ");
				sb.append(" 		)  ,0) ");
				sb.append(" 	)/ 100 + 0  npml, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * nk * ");
				sb.append(" IF ");
				sb.append(" ( ");
				sb.append(" 	s2 = 'ml'  ");
				sb.append(" 	OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )  ");
				sb.append(" 	)  ,0)  ");
				sb.append(" 	)/ 100 + 0 nkml, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * pk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'ml'  ");
				sb.append(" 	OR s2 = '毫升'  ");
				sb.append(" 	OR s2 = 'ML', ");
				sb.append(" 	specification, ");
				sb.append(" IF ");
				sb.append(" 			( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )   ");
				sb.append(" 	),0)  ");
				sb.append(" 	) / 100 + 0 pkml, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * n * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )   ");
				sb.append(" 		) ,0)  ");
				sb.append(" 	)/ 100 + 0 ng, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * p * ");
				sb.append(" 	IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'g'  ");
				sb.append(" 		OR s2 = '克'  ");
				sb.append(" 		OR s2 = 'G', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  "); 
				sb.append(" 		),0) "); 
				sb.append(" 	) / 100 + 0 pg, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * k * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append("	specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  ");
				sb.append(" 	)  ,0) ");
				sb.append(" )/ 100 + 0  kg, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 		num * npk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 		OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  ");
				sb.append(" 		)   ,0) ");
				sb.append(" 	)/ 100 + 0 npkg, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * np * ");
				sb.append(" 	IF ");
				sb.append(" 	( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  ");
				sb.append(" 		)   ,0) ");
				sb.append(" 	)/ 100 + 0 npg, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * nk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )   ");
				sb.append(" 		) ,0)  ");
				sb.append(" 	)/ 100 + 0 nkg, ");
				sb.append(" 	sum(if(s.type=12, ");
				sb.append(" 		num * pk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 				s2 = 'g'  ");
				sb.append(" 		OR s2 = '克'  ");
				sb.append(" 		OR s2 = 'G', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )   ");
				sb.append(" 	)  ,0) ");
				sb.append(" )/ 100 + 0 pkg, ");
				sb.append(" NAME  ");
				sb.append(" FROM ");
				sb.append(" ( ");
				sb.append(" SELECT ");
				sb.append(" 	tool.effective_ingredients, ");
				sb.append(" 	SUBSTRING_INDEX( tool.specification, '/', 1 ) specification, ");
				sb.append(" 	SUBSTRING_INDEX( SUBSTRING_INDEX( tool.specification, '/', 2 ), '/',- 1 ) s2, ");
				sb.append(" tool.n, ");
				sb.append(" tool.p, ");
				sb.append(" tool.k, ");
				sb.append(" tool.npk, ");
				sb.append(" tool.np, ");
				sb.append(" tool.nk, ");
				sb.append(" tool.pk, ");
				sb.append(" ta.NAME, ");
				sb.append(" ta.id dscd, ");
				sb.append(" tool.type,tsc.num  ");
				sb.append(" FROM ");
				sb.append(" 	tb_shopping_car tsc left join tb_res_order_Car troc on troc.car_id = tsc.id " + 
						"		left join tb_tool_order o on o.id=troc.order_id and o.status=4 ");
				sb.append(" 	inner JOIN `tb_tool` tool ON tool.id = tsc.tool_id  ");
				sb.append(" 	AND ( ( tool.type = 13 AND yxcf_unit = '%' ) OR tool.type = 12 ) ");
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					sb.append(" 	AND product_attributes=:productAttributes ");
				}
				sb.append(" 	LEFT JOIN tb_enterprise te ON te.id = tool.enterprise_id  ");
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					sb.append(" and te.id in (:enterpriseIdList) ");
				}
				sb.append(" 	LEFT JOIN tb_area ta ON ta.id = te.dscd where 1=1  ");
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') >= :startMonth ");
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') <= :endMonth ");
				}
				sb.append(" 	) s  ");
				if(!StringUtils.isNullOrEmpty(group)&&group.equals("1")){
					sb.append(" 	GROUP BY ");
					sb.append(" 	dscd ");
				}
				sb.append(" order by dscd asc ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					query.setParameter("enterpriseIdList", enterpriseIdList);
				}
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					query.setParameter("productAttributes", productAttributes);
				}
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					query.setParameter("startMonth", startMonth);
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					query.setParameter("endMonth", endMonth);
				}
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
		
		public List<Map<String, Object>> statisticToolWeightByDscd(String selectAll,List<Integer> enterpriseIdList,
				String productAttributes,String group,String startMonth,String endMonth) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" sum(if(s.type=13, ");
				sb.append(" 	num * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'kg'  ");
				sb.append(" 		OR s2 = '千克'  ");
				sb.append(" 		OR s2 = 'KG', ");
				sb.append(" 		specification * 1000, ");
				sb.append(" 	IF ");
				sb.append(" 			( s2 = 'g' OR s2 = '克' OR s2 = 'G', specification, 0 )  ");
				sb.append(" 		)   ,0) ");
				sb.append(" 	) ng, ");
				sb.append(" 	sum(if(s.type=13, ");
				sb.append(" 		num * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', s2 * 1000, 0 )  ");
				sb.append(" 	)   ,0) ");
				sb.append(" ) nml, ");
				
				sb.append(" sum(if(s.type=12, ");
				sb.append(" 	num * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'kg'  ");
				sb.append(" 		OR s2 = '千克'  ");
				sb.append(" 		OR s2 = 'KG', ");
				sb.append(" 		specification * 1000, ");
				sb.append(" 	IF ");
				sb.append(" 			( s2 = 'g' OR s2 = '克' OR s2 = 'G', specification, 0 )  ");
				sb.append(" 		)  ,0) ");
				sb.append(" 	) fg, ");
				sb.append(" 	sum(if(s.type=12, ");
				sb.append(" 		num * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', s2 * 1000, 0 )  ");
				sb.append(" 	)  ,0) ");
				sb.append(" ) fml, ");
				
				sb.append(" dscd, ");
				sb.append(" NAME  ");
				sb.append(" FROM ");
				sb.append(" ( ");
				sb.append(" SELECT ");
				sb.append(" 	tool.effective_ingredients, ");
				sb.append(" 	SUBSTRING_INDEX( tool.specification, '/', 1 ) specification, ");
				sb.append(" 	SUBSTRING_INDEX( SUBSTRING_INDEX( tool.specification, '/', 2 ), '/',- 1 ) s2, ");
				sb.append(" tool.n, ");
				sb.append(" tool.p, ");
				sb.append(" tool.k, ");
				sb.append(" tool.npk, ");
				sb.append(" tool.np, ");
				sb.append(" tool.nk, ");
				sb.append(" tool.pk, ");
				sb.append(" ta.NAME, ");
				sb.append(" ta.id dscd, ");
				sb.append(" tool.type,tsc.num  ");
				sb.append(" FROM ");
				sb.append(" 	tb_shopping_car tsc left join tb_res_order_Car troc on troc.car_id = tsc.id " + 
						"		left join tb_tool_order o on o.id=troc.order_id and o.status=4 ");
				sb.append(" 	inner JOIN `tb_tool` tool ON tool.id = tsc.tool_id  ");
				sb.append(" 	AND ( ( tool.type = 13 AND yxcf_unit = '%' ) OR tool.type = 12 ) ");
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					sb.append(" 	AND product_attributes=:productAttributes ");
				}
				sb.append(" 	LEFT JOIN tb_enterprise te ON te.id = tool.enterprise_id  ");
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					sb.append(" and te.id in (:enterpriseIdList) ");
				}
				sb.append(" 	LEFT JOIN tb_area ta ON ta.id = te.dscd where 1=1  ");
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') >= :startMonth ");
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') <= :endMonth ");
				}
				sb.append(" 	) s  ");
				if(!StringUtils.isNullOrEmpty(group)&&group.equals("1")){
					sb.append(" 	GROUP BY ");
					sb.append(" 	dscd ");
				}
				sb.append(" order by dscd asc ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					query.setParameter("enterpriseIdList", enterpriseIdList);
				}
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					query.setParameter("productAttributes", productAttributes);
				}
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					query.setParameter("startMonth", startMonth);
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					query.setParameter("endMonth", endMonth);
				}
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
		
		
		public List<Map<String, Object>> statisticToolIngredientsDetailByDscd(String selectAll,List<Integer> enterpriseIdList,
				String productAttributes,String dscd,String startMonth,String endMonth,String name,Integer nowPage,Integer pageCount) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" sum( ");
				sb.append(" 	num * effective_ingredients * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'kg'  ");
				sb.append(" 		OR s2 = '千克'  ");
				sb.append(" 		OR s2 = 'KG', ");
				sb.append(" 		specification * 1000, ");
				sb.append(" 	IF ");
				sb.append(" 			( s2 = 'g' OR s2 = '克' OR s2 = 'G', specification, 0 )  ");
				sb.append(" 		)   ");
				sb.append(" 	)/ 100 + 0  g, ");
				sb.append(" 	sum( ");
				sb.append(" 		num * effective_ingredients * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', s2 * 1000, 0 )  ");
				sb.append(" 	)    ");
				sb.append(" )/ 100 + 0 ml, ");
				sb.append(" effective_ingredients_name  effectiveIngredientsName ");
				sb.append(" FROM ");
				sb.append(" ( ");
				sb.append(" SELECT ");
				sb.append(" 	tool.effective_ingredients, ");
				sb.append(" 	SUBSTRING_INDEX( tool.specification, '/', 1 ) specification, ");
				sb.append(" 	SUBSTRING_INDEX( SUBSTRING_INDEX( tool.specification, '/', 2 ), '/',- 1 ) s2, ");
				sb.append(" ta.id dscd, ");
				sb.append("tsc.num ,tty.effective_ingredients_name,effective_ingredients_value ");
				sb.append(" FROM ");
				sb.append(" 	tb_shopping_car tsc left join tb_res_order_Car troc on troc.car_id = tsc.id " + 
						"		left join tb_tool_order o on o.id=troc.order_id and o.status=4 ");
				sb.append(" 	left JOIN `tb_tool` tool ON tool.id = tsc.tool_id  ");
				sb.append(" 		inner join tb_tool_yxcf tty on tty.tool_id=tool.id 	AND tty.unit = '%' ");
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					sb.append(" 	AND product_attributes=:productAttributes ");
				}
				sb.append(" 	LEFT JOIN tb_enterprise te ON te.id = tool.enterprise_id  ");
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					sb.append(" and te.id in (:enterpriseIdList) ");
				}
				sb.append(" 	LEFT JOIN tb_area ta ON ta.id = te.dscd where 1=1  ");
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') >= :startMonth ");
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') <= :endMonth ");
				}
				if(!StringUtils.isNullOrEmpty(dscd)) {
					sb.append(" 	AND ta.id=:dscd ");
				}
				if (!StringUtils.isNullOrEmpty(name)) {
					sb.append(" and effective_ingredients_name like :name ");
				}
				sb.append(" 	) s  ");
					sb.append(" 	GROUP BY ");
					sb.append(" 	effective_ingredients_name order by g desc ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					query.setParameter("productAttributes", productAttributes);
				}
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					query.setParameter("startMonth", startMonth);
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					query.setParameter("endMonth", endMonth);
				}
				if (!StringUtils.isNullOrEmpty(dscd)) {
					query.setParameter("dscd", dscd);
				}
				if (!StringUtils.isNullOrEmpty(name)) {
					query.setParameter("name", "%"+name+"%");
				}
				if(nowPage!=null&pageCount!=null){
					query.setFirstResult((nowPage-1)*pageCount);
					query.setMaxResults(pageCount);
				}
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

		public Integer statisticToolIngredientsDetailByDscdCount(String selectAll,List<Integer> enterpriseIdList,
				String productAttributes,String dscd,String startMonth,String endMonth,String name) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" 	count(distinct tty.effective_ingredients_name) ");
				sb.append(" FROM ");
				sb.append(" 	tb_shopping_car tsc left join tb_res_order_Car troc on troc.car_id = tsc.id " + 
						"		left join tb_tool_order o on o.id=troc.order_id and o.status=4 ");
				sb.append(" 	inner JOIN `tb_tool` tool ON tool.id = tsc.tool_id  ");
				sb.append(" 		inner join tb_tool_yxcf tty on tty.tool_id=tool.id 	AND tty.unit = '%' ");
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					sb.append(" 	AND product_attributes=:productAttributes ");
				}
				sb.append(" 	LEFT JOIN tb_enterprise te ON te.id = tool.enterprise_id  ");
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					sb.append(" and te.id in (:enterpriseIdList) ");
				}
				sb.append(" 	LEFT JOIN tb_area ta ON ta.id = te.dscd where 1=1  ");
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') >= :startMonth ");
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') <= :endMonth ");
				}
				if (!StringUtils.isNullOrEmpty(name)) {
					sb.append(" and effective_ingredients_name like :name ");
				}
				if(!StringUtils.isNullOrEmpty(dscd)) {
					sb.append(" 	AND ta.id=:dscd ");
				}
				Query query = getEntityManager().createNativeQuery(sb.toString());
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					query.setParameter("productAttributes", productAttributes);
				}
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					query.setParameter("startMonth", startMonth);
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					query.setParameter("endMonth", endMonth);
				}
				if (!StringUtils.isNullOrEmpty(dscd)) {
					query.setParameter("dscd", dscd);
				}
				if (!StringUtils.isNullOrEmpty(name)) {
					query.setParameter("name", "%"+name+"%");
				}
				List<Object> list = query.getResultList();
				if (null != list && list.size() > 0) {
					return Integer.valueOf(list.get(0).toString());
				}
				return null;
			} catch (RuntimeException re) {
				throw re;
			}
		}
		public List<Map<String, Object>> statisticToolIngredientsByLinkOrderInfoId(String selectAll,List<Integer> enterpriseIdList,
				String productAttributes,String group,String startMonth,String endMonth,Integer linkOrderInfoId) {
			try {
				StringBuffer sb = new StringBuffer();
				sb.append(" SELECT ");
				sb.append(" sum(if(s.type=13, ");
				sb.append(" 	num * effective_ingredients * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'kg'  ");
				sb.append(" 		OR s2 = '千克'  ");
				sb.append(" 		OR s2 = 'KG', ");
				sb.append(" 		specification * 1000, ");
				sb.append(" 	IF ");
				sb.append(" 			( s2 = 'g' OR s2 = '克' OR s2 = 'G', specification, 0 )  ");
				sb.append(" 		)  ,0) ");
				sb.append(" 	)/ 100 + 0  g, ");
				sb.append(" 	sum(if(s.type=13, ");
				sb.append(" 		num * effective_ingredients * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', s2 * 1000, 0 )  ");
				sb.append(" 	)   ,0) ");
				sb.append(" )/ 100 + 0 ml, ");
				sb.append(" dscd, ");
				sb.append(" sum( if(s.type=13,");
				sb.append(" 	num * n * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 ) ");
				sb.append(" 	)  ,0) ");
				sb.append(" )/ 100 + 0 nml, ");
				sb.append(" sum(if(s.type=12,");
				sb.append(" 	num * p * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )  ");
				sb.append(" 	) ,0) ");
				sb.append(" )/ 100 + 0  pml, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 	num * k * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )   ");
				sb.append(" 	)  ,0) ");
				sb.append(" 	)/ 100 + 0 kml, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 	num * npk * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )   ");
				sb.append(" 	)  ,0) "); 
				sb.append(" )/ 100 + 0 npkml, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 	num * np * ");
				sb.append(" IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'ml'  ");
				sb.append(" 		OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )  ");
				sb.append(" 		)  ,0) ");
				sb.append(" 	)/ 100 + 0  npml, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * nk * ");
				sb.append(" IF ");
				sb.append(" ( ");
				sb.append(" 	s2 = 'ml'  ");
				sb.append(" 	OR s2 = '毫升'  ");
				sb.append(" 		OR s2 = 'ML', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )  ");
				sb.append(" 	)  ,0)  ");
				sb.append(" 	)/ 100 + 0 nkml, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * pk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'ml'  ");
				sb.append(" 	OR s2 = '毫升'  ");
				sb.append(" 	OR s2 = 'ML', ");
				sb.append(" 	specification, ");
				sb.append(" IF ");
				sb.append(" 			( s2 = 'l' OR s2 = '升' OR s2 = 'L', specification * 1000, 0 )   ");
				sb.append(" 	),0)  ");
				sb.append(" 	) / 100 + 0 pkml, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * n * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )   ");
				sb.append(" 		) ,0)  ");
				sb.append(" 	)/ 100 + 0 ng, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * p * ");
				sb.append(" 	IF ");
				sb.append(" 	( ");
				sb.append(" 		s2 = 'g'  ");
				sb.append(" 		OR s2 = '克'  ");
				sb.append(" 		OR s2 = 'G', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  "); 
				sb.append(" 		),0) "); 
				sb.append(" 	) / 100 + 0 pg, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * k * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append("	specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  ");
				sb.append(" 	)  ,0) ");
				sb.append(" )/ 100 + 0  kg, ");
				sb.append(" sum( if(s.type=12,");
				sb.append(" 		num * npk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 		OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  ");
				sb.append(" 		)   ,0) ");
				sb.append(" 	)/ 100 + 0 npkg, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * np * ");
				sb.append(" 	IF ");
				sb.append(" 	( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )  ");
				sb.append(" 		)   ,0) ");
				sb.append(" 	)/ 100 + 0 npg, ");
				sb.append(" 	sum( if(s.type=12,");
				sb.append(" 		num * nk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 			s2 = 'g'  ");
				sb.append(" 			OR s2 = '克'  ");
				sb.append(" 			OR s2 = 'G', ");
				sb.append(" 			specification, ");
				sb.append(" 		IF ");
				sb.append(" 			( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )   ");
				sb.append(" 		) ,0)  ");
				sb.append(" 	)/ 100 + 0 nkg, ");
				sb.append(" 	sum(if(s.type=12, ");
				sb.append(" 		num * pk * ");
				sb.append(" 	IF ");
				sb.append(" 		( ");
				sb.append(" 				s2 = 'g'  ");
				sb.append(" 		OR s2 = '克'  ");
				sb.append(" 		OR s2 = 'G', ");
				sb.append(" 		specification, ");
				sb.append(" 	IF ");
				sb.append(" 		( s2 = 'kg' OR s2 = '千克' OR s2 = 'KG', specification * 1000, 0 )   ");
				sb.append(" 	)  ,0) ");
				sb.append(" )/ 100 + 0 pkg, ");
				sb.append(" NAME  ");
				sb.append(" FROM ");
				sb.append(" ( ");
				sb.append(" SELECT ");
				sb.append(" 	tool.effective_ingredients, ");
				sb.append(" 	SUBSTRING_INDEX( tool.specification, '/', 1 ) specification, ");
				sb.append(" 	SUBSTRING_INDEX( SUBSTRING_INDEX( tool.specification, '/', 2 ), '/',- 1 ) s2, ");
				sb.append(" tool.n, ");
				sb.append(" tool.p, ");
				sb.append(" tool.k, ");
				sb.append(" tool.npk, ");
				sb.append(" tool.np, ");
				sb.append(" tool.nk, ");
				sb.append(" tool.pk, ");
				sb.append(" ta.NAME, ");
				sb.append(" ta.id dscd, ");
				sb.append(" tool.type,tsc.num  ");
				sb.append(" FROM ");
				sb.append(" 	tb_shopping_car tsc left join tb_res_order_Car troc on troc.car_id = tsc.id " + 
						"		left join tb_tool_order o on o.id=troc.order_id and o.status=4 and o.type=2 ");
				sb.append(" 	inner JOIN `tb_tool` tool ON tool.id = tsc.tool_id  ");
				sb.append(" 	AND ( ( tool.type = 13 AND yxcf_unit = '%' ) OR tool.type = 12 ) ");
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					sb.append(" 	AND product_attributes=:productAttributes ");
				}
				sb.append(" 	LEFT JOIN tb_enterprise te ON te.id = tool.enterprise_id  ");
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					sb.append(" and te.id in (:enterpriseIdList) ");
				}
				sb.append(" 	LEFT JOIN tb_area ta ON ta.id = te.dscd where 1=1  ");
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') >= :startMonth ");
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					sb.append(" and date_format(o.input_time,'%Y-%m') <= :endMonth ");
				}
				if (linkOrderInfoId!=null) {
					sb.append(" and o.plant_enterprise_id= :linkOrderInfoId  ");
				}
				sb.append(" 	) s  ");
				if(!StringUtils.isNullOrEmpty(group)&&group.equals("1")){
					sb.append(" 	GROUP BY ");
					sb.append(" 	dscd ");
				}
				sb.append(" order by dscd asc ");
				Query query = getEntityManager().createNativeQuery(sb.toString());
				if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
					query.setParameter("enterpriseIdList", enterpriseIdList);
				}
				if(!StringUtils.isNullOrEmpty(productAttributes)) {
					query.setParameter("productAttributes", productAttributes);
				}
				if (!StringUtils.isNullOrEmpty(startMonth)) {
					query.setParameter("startMonth", startMonth);
				}
				if (!StringUtils.isNullOrEmpty(endMonth)) {
					query.setParameter("endMonth", endMonth);
				}
				if (linkOrderInfoId!=null) {
					query.setParameter("linkOrderInfoId", linkOrderInfoId);
				}
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
		public List<Map<String, Object>> statisticOrderByToolType(String selectAll,List<Integer> enterpriseIdList,String dscd,
				String startTime,String endTime,String firstDayOfMonth,String lastDayOfMonth){
			StringBuilder queryString = new StringBuilder();
			queryString.append(" SELECT tt.type,tt2.name,count(distinct tto.id) orderCount,round(sum(tsc.num*tsc.price)/10000,2) price ");
			queryString.append(" FROM tb_shopping_car tsc left join tb_res_order_car troc on tsc.id = troc.car_id ");
			queryString.append(" left join tb_tool_order tto on troc.order_id = tto.id left join tb_tool tt on tt.id = tsc.tool_id ");
			queryString.append(" left join tb_enterprise te on tto.tool_enterprise_id = te.id left join tb_type tt2 on tt2.id = tt.type ");
			queryString.append(" where tto.status = 4 and tto.del_flag = 0 ");
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				queryString.append(" and te.id in (:enterpriseIdList) ");
			}
			if (!StringUtils.isNullOrEmpty(dscd)) {
				queryString.append(" and te.dscd like :dscd ");
			}
			if (!StringUtils.isNullOrEmpty(firstDayOfMonth)) {
				queryString.append(" and tto.input_time >= :firstDayOfMonth ");
			}
			if (!StringUtils.isNullOrEmpty(lastDayOfMonth)) {
				queryString.append(" and tto.input_time <= :lastDayOfMonth ");
			}
			if (!StringUtils.isNullOrEmpty(startTime)) {
				queryString.append(" and tto.input_time >= :startTime ");
			}
			if (!StringUtils.isNullOrEmpty(endTime)) {
				queryString.append(" and tto.input_time <= :endTime ");
			}
			queryString.append(" group by tt.type");
			Query query = getEntityManager().createNativeQuery(queryString.toString());
			if ("all".equals(selectAll) && !enterpriseIdList.isEmpty()) {
				query.setParameter("enterpriseIdList", enterpriseIdList);
			}
			if (!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if (!StringUtils.isNullOrEmpty(firstDayOfMonth)) {
				query.setParameter("firstDayOfMonth", firstDayOfMonth+" 00:00:00");
			}
			if (!StringUtils.isNullOrEmpty(lastDayOfMonth)) {
				query.setParameter("lastDayOfMonth", lastDayOfMonth+" 23:59:59");
			}
			if (!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime + " 00:00:00");
			}
			if (!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime+" 23:59:59");
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			return list;
		}
		
}
