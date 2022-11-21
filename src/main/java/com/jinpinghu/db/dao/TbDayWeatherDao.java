package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.mysql.cj.util.StringUtils;

public class TbDayWeatherDao extends BaseZDao{

	public TbDayWeatherDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<Map<String,Object>> findDayWeatherListByTime(String startTime,String endTime,String area){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("  id, ");
			sb.append("  weather_condition weatherCondition, ");
			sb.append("  temperature,  ");
			sb.append("  wind_direction windDirection, ");
			sb.append("  wind_power windPower, ");
			sb.append("  humidity , ");
			sb.append("  date_format(input_time,'%Y-%m-%d') inputTime ,");
			sb.append("  water_content waterContent, ");
			sb.append("  area ");
			sb.append(" from tb_day_weather tdw ");
			sb.append(" where del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(input_time) <= :endTime ");
			}
			if(!StringUtils.isNullOrEmpty(area)) {
				sb.append(" and area = :area ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", DateTimeUtil.formatTime(startTime));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", DateTimeUtil.formatTime(endTime));
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

}
