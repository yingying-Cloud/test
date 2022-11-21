package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.mysql.cj.util.StringUtils;

public class TbHourWeatherDao extends BaseZDao{

	public TbHourWeatherDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<Map<String,Object>> findHourWeatherListByTime(String startTime,String endTime,String area){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("  id, ");
			sb.append("  weather_condition weatherCondition, ");
			sb.append("  temperature,  ");
			sb.append("  wind_direction windDirection, ");
			sb.append("  wind_power windPower, ");
			sb.append("  humidity , ");
			sb.append("  date_format(input_time,'%Y-%m-%d %H') inputTime ,");
			sb.append("  water_content waterContent, ");
			sb.append("  area ");
			sb.append(" from tb_hour_weather tdw ");
			sb.append(" where del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  date_format(input_time,'%Y-%m-%d %H') >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date_format(input_time,'%Y-%m-%d %H') <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(area)) {
				sb.append(" and area = :area ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", (startTime));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", (endTime));
			}
			if(!StringUtils.isNullOrEmpty(area)) {
				query.setParameter("area", area);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public Map<String,Object> findNowHourWeatherByTime(String time,String area){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("  id, ");
			sb.append("  weather_condition weatherCondition, ");
			sb.append("  temperature,  ");
			sb.append("  wind_direction windDirection, ");
			sb.append("  wind_power windPower, ");
			sb.append("  humidity , ");
			sb.append("  date_format(input_time,'%Y-%m-%d %H') inputTime ,");
			sb.append("  water_content waterContent, ");
			sb.append("  area ");
			sb.append(" from tb_hour_weather tdw ");
			sb.append(" where del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(time)) {
				sb.append(" and  date_format(input_time,'%Y-%m-%d %H') = :time ");
			}
			if(!StringUtils.isNullOrEmpty(area)) {
				sb.append(" and area = :area ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(time)) {
				query.setParameter("time", time);
			}
			if(!StringUtils.isNullOrEmpty(area)) {
				query.setParameter("area", area);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list.get(0);
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findAreaList(){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("  id, ");
			sb.append("  name  ");
			sb.append(" from tb_area  ");
			sb.append(" where 1=1 ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findPinghuAreaList(String dscd,String permissionDscd,String needSelf){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("  id, ");
			sb.append("  name  ");
			sb.append(" from tb_area  ");
			sb.append(" where 1=1 and id like :dscd1 ");
			if (!"1".equals(needSelf)) {
				sb.append(" and id <> :dscd2 ");
			}
			if (!StringUtils.isNullOrEmpty(permissionDscd)) {
				sb.append(" and id like :permissionDscd ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("dscd1", StringUtil.handleArea(dscd));
			if (!"1".equals(needSelf)) {
				query.setParameter("dscd2", dscd);
			}
			if (!StringUtils.isNullOrEmpty(permissionDscd)) {
				query.setParameter("permissionDscd", permissionDscd);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if (list != null && list.size()>0) {
				return list;
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
}
