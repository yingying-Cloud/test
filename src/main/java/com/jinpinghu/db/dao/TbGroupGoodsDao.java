package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbGroup;
import com.mysql.cj.util.StringUtils;

public class TbGroupGoodsDao extends BaseZDao{

	public TbGroupGoodsDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	public  List<Map<String, Object>> findByGroupId(List<Integer> groupId,Integer nowPage,Integer pageCount,Integer type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tgg.id,");
			sb.append(" tgg.goods_name goodsName,");
			sb.append(" tgg.group_id groupId ,");
			sb.append(" cast(tgg.price as decimal(10,2)) price ,");
			sb.append(" tgg.sources,");
			sb.append(" tgg.price_increase priceIncrease,");
			sb.append(" tgg.unit,date_format(tgg.input_time,'%Y-%m-%d') inputTime,tgt.name typeName ");
			sb.append(" from tb_group_goods tgg left join tb_goods_type tgt on tgt.id=tgg.type  ");
			sb.append(" where tgg.del_flag=0  ");
			if(groupId!=null) {
				sb.append(" and tgg.group_id in :groupId ");
			}
			if(type!=null) {
				sb.append(" and tgg.type = :type ");
			}
			sb.append(" order by tgg.group_id asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(groupId!=null) {
				query.setParameter("groupId", groupId);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public  Integer findByGroupIdCount(List<Integer> groupId,Integer type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(id) ");
			sb.append(" from tb_group_goods tgg  ");
			sb.append(" where del_flag=0  ");
			if(groupId!=null) {
				sb.append(" and group_id in :groupId ");
			}
			if(type!=null) {
				sb.append(" and type = :type ");
			}
			sb.append(" order by goods_name asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(groupId!=null) {
				query.setParameter("groupId", groupId);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			List <Object> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return Integer.valueOf(list.get(0).toString());
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public  Object[] findById(Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tgg.id,");
			sb.append(" tgg.goods_name goodsName,");
			sb.append(" tgg.group_id groupId ,");
			sb.append(" tgg.price ,");
			sb.append(" tgg.sources,");
			sb.append(" tgg.price_increase priceIncrease,");
			sb.append(" tgg.unit,tgt.name ");
			sb.append(" from tb_group_goods tgg left join tb_goods_type tgt on tgt.id=tgg.type  ");
			sb.append(" where tgg.del_flag=0  ");
			if(id!=null) {
				sb.append(" and tgg.id=:id ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(id!=null) {
				query.setParameter("id", id);
			}
			List<Object[]> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public  Object[] findGoodsPriceByGoodsId(Integer id,String startTime,String endTime,String unit){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" cast(max(price) as decimal(10,2)),");
			sb.append(" cast(min(price) as decimal(10,2)),");
			sb.append(" cast(avg(price) as decimal(10,2)),");
			sb.append(" unit ");
			sb.append(" from tb_goods_price tgg  ");
			sb.append(" where del_flag=0  ");
			if(id!=null) {
				sb.append(" and group_goods_id=:id ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(input_time)>=:startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(input_time)<=:endTime ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and unit=:unit ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(id!=null) {
				query.setParameter("id", id);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", DateTimeUtil.formatTime(startTime));
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", DateTimeUtil.formatTime(endTime));
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit", unit);
			}
			List<Object[]> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public  Object[] findGoodsPriceByNow(Integer id,String startTime,String unit){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" cast(price as decimal(10,2)),");
			sb.append(" unit ");
			sb.append(" from tb_goods_price tgg  ");
			sb.append(" where del_flag=0  ");
			if(id!=null) {
				sb.append(" and group_goods_id=:id ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(input_time)>=:startTime ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and unit=:unit ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(id!=null) {
				query.setParameter("id", id);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", DateTimeUtil.formatTime(startTime));
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit", unit);
			}
			List<Object[]> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public  List<Map<String, Object>> findGoodsPriceChangeByGoodsId(Integer id,String startTime,String endTime,String unit){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" price,");
			sb.append(" date_format(input_time,'%Y-%m-%d') inputTime ");
			sb.append(" from tb_goods_price tgg  ");
			sb.append(" where del_flag=0  ");
			if(id!=null) {
				sb.append(" and group_goods_id=:id ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and  date_format(input_time,'%Y-%m-%d')>=:startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and  date_format(input_time,'%Y-%m-%d')<=:endTime ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and unit=:unit ");
			}
			sb.append(" order by input_time asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(id!=null) {
				query.setParameter("id", id);
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime", startTime);
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",endTime);
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit", unit);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
	public  List<String> findGoodsPriceUnitByGoodsId(Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" distinct unit ");
			sb.append(" from tb_goods_price tgg  ");
			sb.append(" where del_flag=0  ");
			if(id!=null) {
				sb.append(" and group_goods_id=:id ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(id!=null) {
				query.setParameter("id", id);
			}
			List<String> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
	public  List<Map<String, Object>> findOtherByGroupId(Integer id,String isIn,Integer groupId ){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tgg.id,");
			sb.append(" tgg.goods_name goodsName,");
			sb.append(" tgg.group_id groupId ,");
			sb.append(" cast(tgg.price as decimal(10,2)) price ,");
			sb.append(" tgg.sources,");
			sb.append(" tgg.price_increase priceIncrease,");
			sb.append(" tgg.unit,date_format(tgg.input_time,'%Y-%m-%d') inputTime,tgt.name typeName ");
			sb.append(" from tb_group_goods tgg left join tb_goods_type tgt on tgt.id=tgg.type  ");
			sb.append(" where tgg.del_flag=0  ");
			if(groupId!=null) {
				sb.append(" and tgg.group_id = :groupId ");
			}
			if(!StringUtils.isNullOrEmpty(isIn)&&isIn.equals("0")) {
				sb.append(" and tgg.id != :id ");
			}
			sb.append(" order by tgg.group_id asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(groupId!=null) {
				query.setParameter("groupId", groupId);
			}
			if(!StringUtils.isNullOrEmpty(isIn)&&isIn.equals("0")) {
				query.setParameter("id", id);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public  List<Map<String, Object>> findGoodsByGroupId(Integer groupId,Integer type ){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" tgg.id,");
			sb.append(" tgg.goods_name goodsName,");
			sb.append(" tgg.group_id groupId ,");
			sb.append(" cast(tgg.price as decimal(10,2)) price ,");
			sb.append(" tgg.sources,");
			sb.append(" tgg.price_increase priceIncrease,");
			sb.append(" tgg.unit,date_format(tgg.input_time,'%Y-%m-%d') inputTime,ifnull(tgt.name,'') typeName ");
			sb.append(" from tb_group_goods tgg left join tb_goods_type tgt on tgt.id=tgg.type  ");
			sb.append(" where tgg.del_flag=0  ");
			if(groupId!=null) {
				sb.append(" and tgg.group_id = :groupId ");
			}
			if(type!=null) {
				sb.append(" and tgg.type = :type ");
			}
			sb.append(" order by group_id asc,type asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(groupId!=null) {
				query.setParameter("groupId", groupId);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public  List<Object[]> findGroupId(List<Integer> groupId,Integer nowPage,Integer pageCount,Integer type ){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" distinct tgg.group_id,tg.name ");
			sb.append(" from tb_group_goods tgg left join tb_group tg on tg.id=tgg.group_id   ");
			sb.append(" where tgg.del_flag=0  ");
			if(groupId!=null) {
				sb.append(" and tgg.group_id in :groupId ");
			}
			if(type!=null) {
				sb.append(" and tgg.type in :type ");
			}
			sb.append(" order by group_id asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(groupId!=null) {
				query.setParameter("groupId", groupId);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			List<Object[]> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public  Integer findGroupIdCount(List<Integer> groupId,Integer type ){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select  ");
			sb.append(" count(distinct tgg.group_id) ");
			sb.append(" from tb_group_goods tgg left join tb_group tg on tg.id=tgg.group_id   ");
			sb.append(" where tgg.del_flag=0  ");
			if(groupId!=null) {
				sb.append(" and group_id in :groupId ");
			}
			if(type!=null) {
				sb.append(" and tgg.type in :type ");
			}
			sb.append(" order by group_id asc ");
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(groupId!=null) {
				query.setParameter("groupId", groupId);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			List<Object> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return Integer.valueOf(list.get(0).toString());
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
	
	
	
}
