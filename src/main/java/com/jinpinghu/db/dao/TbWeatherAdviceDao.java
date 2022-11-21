package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.mysql.cj.util.StringUtils;

public class TbWeatherAdviceDao extends BaseZDao {

	public TbWeatherAdviceDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public Map<String,Object> findDayWeatherAdviceByTime(String time,String area){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append("  id, ");
			sb.append("  crop_farming_advice cropFarmingAdvice, ");
			sb.append("  date_format(input_time,'%Y-%m-%d') inputTime ,");
			sb.append("  area ");
			sb.append(" from tb_weather_advice tdw ");
			sb.append(" where del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(time)) {
				sb.append(" and  date_format(input_time,'%Y-%m-%d') = :time ");
			}
			if(!StringUtils.isNullOrEmpty(area)) {
				sb.append(" and area = :area ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(time)) {
				query.setParameter("time", (time));
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
}
