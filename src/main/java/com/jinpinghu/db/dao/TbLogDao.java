package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbLogDao extends BaseZDao {

	public TbLogDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public 	Map<String,Object>	getLogTime(){  
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" (select count(id) from tb_log) allCount, ");
			sb.append(" (select count(id) from tb_log where to_days(input_time) = to_days(now())  ) todayCount, ");
			sb.append(" (select count(id) from tb_log where TO_DAYS( NOW( ) ) - TO_DAYS( input_time) <= 1  )yesCount, ");
			sb.append(" (select count(id) from tb_log where DATE_FORMAT( input_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) )mouthCount, ");
			sb.append(" (select count(distinct uid) from tb_log) allPeopleCount, ");
			sb.append(" (select count(distinct uid) from tb_log where to_days(input_time) = to_days(now())  ) todayPeopleCount, ");
			sb.append(" (select count(distinct uid) from tb_log where TO_DAYS( NOW( ) ) - TO_DAYS( input_time) <= 1  )yesPeopleCount, ");
			sb.append(" (select count(distinct uid) from tb_log where DATE_FORMAT( input_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) )mouthPeopleCount ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
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
	
	public 	List<Map<String,Object>> getLogDayTime(String startTime,String endTime){  
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append("  days.days, ");
			sb.append(" count(id) AS allCount, ");
			sb.append(" count(distinct uid) AS allPeopleCount ");
			sb.append(" FROM ");
			sb.append("   ( ");
			sb.append("   SELECT ");
			sb.append("      @date \\:= DATE_ADD(@date, INTERVAL + 1 DAY) days ");
			sb.append("  FROM ");
			sb.append("      ( ");
			sb.append("          SELECT ");
			sb.append("              @date\\:= DATE_ADD(:startTime, INTERVAL - 1 DAY) ");
			sb.append("          FROM ");
			sb.append("             tb_log ");
			sb.append("     ) time ");
			sb.append(" 	where ");
			sb.append("           @date < date_format(:endTime,'%Y-%m-%d') ");
			sb.append(" ) AS days ");
			sb.append(" LEFT JOIN tb_log tll ON TO_DAYS(tll.input_time) = TO_DAYS(days.days) ");
			sb.append(" where 1=1  ");
			sb.append( "AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') >=:startTime AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') <= :endTime ");
			sb.append(" GROUP BY ");
			sb.append("  days.days ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
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
	public 	List<Map<String,Object>> getLogRoleTime(String startTime,String endTime){  
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" count(distinct tll.id) AS allCount,tr.role_name roleName ");
			sb.append(" FROM ");
			sb.append("  tb_log tll inner join tb_user tu on tu.id=tll.uid ");
			sb.append(" inner join tb_res_user_role tru on tru.user_tab_id=tu.id and tru.del_flag=0 ");
			sb.append(" inner join tb_role tr on tr.id=tru.role_id ");
			sb.append(" where 1=1  ");
			sb.append( "AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') >=:startTime AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') <= :endTime ");
			sb.append(" GROUP BY ");
			sb.append("  tr.id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
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
	
	public 	List<Map<String,Object>> getLogPeopleTime(String name,String startTime,String endTime,Integer nowPage,Integer pageCount){  
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" DATE_FORMAT(tll.input_time,'%Y-%m-%d') inputTime,tll.name,count(tll.id) count ");
			sb.append(" FROM ");
			sb.append("  tb_log tll inner join tb_user tu on tu.id=tll.uid ");
			sb.append(" where 1=1  ");
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
				sb.append( "AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') >=:startTime AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') <= :endTime ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" AND tll.name like :name ");
			}
			sb.append(" GROUP BY ");
			sb.append("  DATE_FORMAT(tll.input_time,'%Y-%m-%d'),tu.id  ");
			sb.append(" order by  tll.input_time desc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
			if(!StringUtils.isEmpty(name)) {
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
	public 	Integer getLogPeopleTimeCount(String name,String startTime,String endTime){  
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT ");
			sb.append(" count(distinct DATE_FORMAT(tll.input_time,'%Y-%m-%d'),tu.id ) ");
			sb.append(" FROM ");
			sb.append("  tb_log tll inner join tb_user tu on tu.id=tll.uid ");
			sb.append(" where 1=1  ");
			if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)) {
				sb.append( "AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') >=:startTime AND DATE_FORMAT(tll.input_time,'%Y-%m-%d') <= :endTime ");
			}
			if(!StringUtils.isEmpty(name)) {
				sb.append(" AND tll.name like :name ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("startTime", startTime);
			query.setParameter("endTime", endTime);
			if(!StringUtils.isEmpty(name)) {
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
	
}
