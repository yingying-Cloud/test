package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbEvaluate;
import com.mysql.cj.util.StringUtils;

public class TbEvaluateDao extends BaseZDao {

	public TbEvaluateDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public TbEvaluate findById(Integer Id){
		try {
			TbEvaluate instance = getEntityManager().find(TbEvaluate.class, Id);
			if (instance != null) {
				return instance;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findByAll(String resId,String startTime,String endTime,String searchId,String type,String level,
			Integer nowPage,Integer pageCount){
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select te.id,te.name,te.content,date_format(te.input_time,'%Y-%m-%d %H:%i:%s') inputTime,te.level,te.type  ");
			sb.append(" ,( SELECT group_concat(file_url) ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_evaluate_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.evaluate_id = te.id ");
			sb.append(" AND f.file_type = 1 group by rfg.evaluate_id  )fileUrl ");
			
			sb.append("  from tb_evaluate te  ");
			// 订单商品关联
			sb.append( " left join tb_tool_order tto on tto.id=te.res_id and te.type=1 ");
			sb.append( " left join tb_res_order_car troc on troc.order_id=tto.id and te.type=1 ");
			sb.append( " left join tb_shopping_car tsc on troc.car_id=tsc.id and te.type=1 ");
			//农服商品关联
			sb.append( " left join tb_plant_protection_order tpo on tpo.id=te.res_id and te.type=2 ");
			sb.append( " left join tb_plant_protection tpp on tpp.id=tpo.plant_protection_id and te.type=2 ");
			//劳务商品关联
			sb.append( " left join tb_plant_service_order tso on tso.id=te.res_id and te.type=3 ");
			sb.append( " left join tb_plant_service tps on tps.id=tso.plant_service_id and te.type=3 ");
			//供销商品关联
			sb.append( " left join tb_sc_order tsco on tsco.id=te.res_id and te.type=4 ");
			sb.append( " left join tb_buy_release tbr on tbr.id=tsco.buy_release_id and te.type=4 ");
			sb.append( " left join tb_sc_order tsco2 on tsco2.id=te.res_id and te.type=5 ");
			sb.append( " left join tb_supply_release tsr on tsr.id=tsco2.supply_release_id and te.type=5 ");
			
			sb.append( " where te.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(resId)) {
				sb.append(" and te.res_id=:resId ");
			}
			if(!StringUtils.isNullOrEmpty(searchId)) {
				sb.append(" and (tsc.tool_id=:searchId or tpp.id=:searchId or tps.id=:searchId or tbr.id=:searchId or tsr.id=:searchId) ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and te.type = :type ");
			}
			if(!StringUtils.isNullOrEmpty(level)) {
				sb.append(" and te.level = :level ");
			}
			if(!StringUtil.isNullOrEmpty(startTime)) {
				sb.append(" AND date_format(input_time,'%Y-%m-%d %H:%i:%s') >= :startTime  ");
			}
			if(!StringUtil.isNullOrEmpty(endTime)) {
				sb.append(" AND date_format(input_time,'%Y-%m-%d %H:%i:%s') <= :endTime  ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(resId)) {
				query.setParameter("resId", resId);
			}
			if(!StringUtils.isNullOrEmpty(searchId)) {
				query.setParameter("searchId", searchId);
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtils.isNullOrEmpty(level)) {
				query.setParameter("level", level);
			}
			if(!StringUtil.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtil.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
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
	public Integer findByAllCount(String resId,String startTime,String endTime,String searchId,String type,String level){
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("  select count(*)  ");
			sb.append("  from tb_evaluate te  ");
			// 订单商品关联
			sb.append( " left join tb_tool_order tto on tto.id=te.res_id and te.type=1 ");
			sb.append( " left join tb_res_order_car troc on troc.order_id=tto.id and te.type=1 ");
			sb.append( " left join tb_shopping_car tsc on troc.car_id=tsc.id and te.type=1 ");
			//农服商品关联
			sb.append( " left join tb_plant_protection_order tpo on tpo.id=te.res_id and te.type=2 ");
			sb.append( " left join tb_plant_protection tpp on tpp.id=tpo.plant_protection_id and te.type=2 ");
			//劳务商品关联
			sb.append( " left join tb_plant_service_order tso on tso.id=te.res_id and te.type=3 ");
			sb.append( " left join tb_plant_service tps on tps.id=tso.plant_service_id and te.type=3 ");
			//供销商品关联
			sb.append( " left join tb_sc_order tsco on tsco.id=te.res_id and te.type=4 ");
			sb.append( " left join tb_buy_release tbr on tbr.id=tsco.buy_release_id and te.type=4 ");
			sb.append( " left join tb_sc_order tsco2 on tsco2.id=te.res_id and te.type=5 ");
			sb.append( " left join tb_supply_release tsr on tsr.id=tsco2.supply_release_id and te.type=5 ");
			
			sb.append( " where te.del_flag=0 ");
			if(!StringUtils.isNullOrEmpty(resId)) {
				sb.append(" and te.res_id=:resId ");
			}
			if(!StringUtils.isNullOrEmpty(searchId)) {
				sb.append(" and (tsc.tool_id=:searchId or tpp.id=:searchId or tps.id=:searchId or tbr.id=:searchId or tsr.id=:searchId) ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and te.type = :type ");
			}
			if(!StringUtils.isNullOrEmpty(level)) {
				sb.append(" and te.level = :level ");
			}
			if(!StringUtil.isNullOrEmpty(startTime)) {
				sb.append(" AND date_format(te.input_time,'%Y-%m-%d %H:%i:%s') >= :startTime  ");
			}
			if(!StringUtil.isNullOrEmpty(endTime)) {
				sb.append(" AND date_format(te.input_time,'%Y-%m-%d %H:%i:%s') <= :endTime  ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(resId)) {
				query.setParameter("resId", resId);
			}
			if(!StringUtils.isNullOrEmpty(searchId)) {
				query.setParameter("searchId", searchId);
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtils.isNullOrEmpty(level)) {
				query.setParameter("level", level);
			}
			if(!StringUtil.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtil.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime);
			}
			List<Object> list = query.getResultList();
			if (list != null && list.size()>0) {
				return Integer.valueOf(list.get(0).toString());
			}
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> statisticFindByAll(String resId,String startTime,String endTime,String searchId,String type){
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select COUNT(te.id) count,te.level,tt.name  ");
			sb.append("  from tb_type tt   ");
			sb.append("  left join tb_evaluate te on tt.id=te.level and te.del_flag=0 ");
			// 订单商品关联
			sb.append( " left join tb_tool_order tto on tto.id=te.res_id and te.type=1 ");
			sb.append( " left join tb_res_order_car troc on troc.order_id=te.res_id and te.type=1 ");
			sb.append( " left join tb_shopping_car tsc on troc.car_id=tsc.id and te.type=1 ");
			//农服商品关联
			sb.append( " left join tb_plant_protection_order tpo on tpo.id=te.res_id and te.type=2 ");
			sb.append( " left join tb_plant_protection tpp on tpp.id=tpo.plant_protection_id and te.type=2 ");
			//劳务商品关联
			sb.append( " left join tb_plant_service_order tso on tso.id=te.res_id and te.type=3 ");
			sb.append( " left join tb_plant_service tps on tps.id=tso.plant_service_id and te.type=3 ");
			//供销商品关联
			sb.append( " left join tb_sc_order tsco on tsco.id=te.res_id and te.type=4 ");
			sb.append( " left join tb_buy_release tbr on tbr.id=tsco.buy_release_id and te.type=4 ");
			sb.append( " left join tb_sc_order tsco2 on tsco2.id=te.res_id and te.type=5 ");
			sb.append( " left join tb_supply_release tsr on tsr.id=tsco2.supply_release_id and te.type=5 ");
			sb.append( " where tt.type=8 ");
			if(!StringUtils.isNullOrEmpty(resId)) {
				sb.append(" and te.res_id=:resId ");
			}
			if(!StringUtils.isNullOrEmpty(searchId)) {
				sb.append(" and (tsc.tool_id=:searchId or tpp.id=:searchId or tps.id=:searchId or tbr.id=:searchId or tsr.id=:searchId) ");
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				sb.append(" and te.type = :type ");
			}
			if(!StringUtil.isNullOrEmpty(startTime)) {
				sb.append(" AND date_format(te.input_time,'%Y-%m-%d %H:%i:%s') >= :startTime  ");
			}
			if(!StringUtil.isNullOrEmpty(endTime)) {
				sb.append(" AND date_format(te.input_time,'%Y-%m-%d %H:%i:%s') <= :endTime  ");
			}
			sb.append(" group by tt.id ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(resId)) {
				query.setParameter("resId", resId);
			}
			if(!StringUtils.isNullOrEmpty(searchId)) {
				query.setParameter("searchId", searchId);
			}
			if(!StringUtils.isNullOrEmpty(type)) {
				query.setParameter("type", type);
			}
			if(!StringUtil.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtil.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime);
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
