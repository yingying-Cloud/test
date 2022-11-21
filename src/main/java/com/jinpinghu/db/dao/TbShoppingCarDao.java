package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbShoppingCar;
import com.mysql.cj.util.StringUtils;

public class TbShoppingCarDao extends BaseZDao{

	public TbShoppingCarDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbShoppingCar findById(Integer id) {
		try {
			String queryString = "from TbShoppingCar where  delFlag = 0 and id=:id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbShoppingCar> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public List<Object[]> findByCarId(List<Integer> carId) {
		try {
			String queryString = "select group_concat(tsc.id),tool.enterprise_id,sum(tsc.price)  from Tb_Shopping_Car tsc inner join tb_tool tool on tool.id=tsc.tool_id where  tsc.del_Flag = 0 and tsc.id in (:carId) group by tool.enterprise_id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("carId",carId);
			List<Object[]> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbShoppingCar findByToolId(Integer toolId,Integer enterpriseId) {
		try {
			String queryString = "from TbShoppingCar where  delFlag = 0 and status != 2 ";
			if(toolId!=null) {
				queryString += " and  toolId=:toolId ";
			}
			if(enterpriseId!=null) {
				queryString += " and  enterpriseId=:enterpriseId ";
			}
			Query query = getEntityManager().createQuery(queryString);
			if(toolId!=null) {
				query.setParameter("toolId",toolId);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List<TbShoppingCar> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public TbShoppingCar findInByToolId(Integer toolId,Integer enterpriseId) {
		try {
			String queryString = "from TbShoppingCar where  delFlag = 0 and status = 1";
			if(toolId!=null) {
				queryString += " and  toolId=:toolId ";
			}
			if(enterpriseId!=null) {
				queryString += " and  enterpriseId=:enterpriseId ";
			}
			Query query = getEntityManager().createQuery(queryString);
			if(toolId!=null) {
				query.setParameter("toolId",toolId);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List<TbShoppingCar> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findByEnterpriseId(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" tsc.id,");
			sb.append(" te.name enterpriseName,");
			sb.append(" tsc.status,");
			sb.append(" tl.name toolName,");
			sb.append(" tsc.num,");
			sb.append(" tsc.price,");
			sb.append(" tsc.is_pay ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_id = tl.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') fileUrl ");
			sb.append(" ,tl.price toolPrice,tl.production_units productionUnits ");
			sb.append(" from Tb_Shopping_Car tsc ");
			sb.append(" inner join tb_tool tl on tl.id = tsc.tool_id  inner JOIN tb_enterprise te on te.id=tl.enterprise_id ");
			sb.append("  where  tsc.del_Flag = 0 and tsc.status != 2 ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public String findByEnterpriseIdCount(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" ifnull(cast(sum(tsc.num+0) as decimal(10,0)),0)");
			sb.append(" from Tb_Shopping_Car tsc inner JOIN tb_enterprise te on te.id=tsc.enterprise_id ");
			sb.append(" inner join tb_tool tl on tl.id = tsc.tool_id ");
			sb.append("  where  tsc.del_Flag = 0 and tsc.status != 2 ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			List <Object> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0).toString();
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer findByCarCount(Integer enterpriseId) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" count(tsc.id)");
			sb.append(" from Tb_Shopping_Car tsc inner JOIN tb_enterprise te on te.id=tsc.enterprise_id ");
			sb.append(" inner join tb_tool tl on tl.id = tsc.tool_id ");
			sb.append("  where  tsc.del_Flag = 0 and tsc.status != 2 ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
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
	
	public List<Map<String,Object>> findInOrderId(Integer orderId,String uniformPrice) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" ifnull(tsc.id,'') id,");
			sb.append(" ifnull(te.name,'') enterpriseName,");
			sb.append(" tsc.status,");
			sb.append(" ifnull(tl.name,'') toolName,");
			sb.append(" cast(tsc.num as decimal(18,2)) num,");
			sb.append(" cast(tsc.price as decimal(18,2)) price,");
			sb.append(" tsc.is_Pay, ");
			sb.append(" tsc.tool_id toolId ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_id = tl.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') fileUrl ");
			sb.append(" ,ifnull(tl.unit,'') unit ");
			sb.append(" ,ifnull(tl.price,'') toolPrice,ifnull(tl.production_units,'') productionUnits,ifnull(tl.specification,'') specification, ");
			sb.append(" cast(tsc.original_price as decimal(18,2)) originalPrice,tsc.uniform_price uniformPrice from Tb_Shopping_Car tsc ");
			sb.append(" inner join tb_tool tl on tl.id = tsc.tool_id inner JOIN tb_enterprise te on te.id=tl.enterprise_id  ");
			sb.append("  where  tsc.del_Flag = 0  ");
			if(orderId!=null) {
				sb.append(" and  tsc.id in (select car_id from tb_res_order_car where order_id=:orderId  ) ");
			}
			if (!StringUtils.isNullOrEmpty(uniformPrice)) {
				sb.append(" and tsc.uniform_price = :uniformPrice ");
			}
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(orderId!=null) {
				query.setParameter("orderId",orderId);
			}
			if (!StringUtils.isNullOrEmpty(uniformPrice)) {
				query.setParameter("uniformPrice", uniformPrice);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String,Object>> findByAll(Integer enterpriseId,String unit,String toolName,String startTime,String endTime,
			Integer nowPage,Integer pageCount,String enterpriseName,String buyEnterpriseName) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" tsc.id,");
			sb.append(" te.name enterpriseName,");
			sb.append(" tsc.status,");
			sb.append(" tl.name toolName,");
			sb.append(" tsc.num,");
			sb.append(" tsc.price,");
			sb.append(" tsc.is_pay ");
			sb.append(" ,ifnull(( SELECT file_url ");
			sb.append(" FROM tb_file f INNER JOIN tb_res_tool_file rfg ");
			sb.append(" ON f.id = rfg.file_id  ");
			sb.append(" WHERE rfg.tool_id = tl.id ");
			sb.append(" AND f.file_type = 1  LIMIT 1 ),'') fileUrl ");
			sb.append(" ,tl.price toolPrice,tl.production_units productionUnits  ");
			sb.append(" , date_format(tto.time_complete,'%Y-%m-%d %H:%i:%s') completeTime ");
			sb.append(" ,ifnull(tes.name,tloi.name) buyEnterpriseName ");
			sb.append(" from Tb_Shopping_Car tsc ");
			sb.append(" inner join tb_tool tl on tl.id = tsc.tool_id  inner JOIN tb_enterprise te on te.id=tl.enterprise_id ");
			sb.append(" inner join tb_res_order_car roc on roc.car_id=tsc.id  ");
			sb.append(" inner join tb_tool_order tto on tto.id=roc.order_id ");
			sb.append(" left JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_id and tto.type=1  ");
			sb.append(" left JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_id and tto.type=2  ");
			sb.append("  where  tsc.del_Flag = 0 and tto.status=4   ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and  tl.production_units like :unit ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName  ");
			}
			if(!StringUtils.isNullOrEmpty(buyEnterpriseName)) {
				sb.append(" and (tes.name like :buyEnterpriseName or tloi.name like :buyEnterpriseName)  ");
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				sb.append(" and tl.name like :toolName ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tto.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tto.input_time) <= :endTime ");
			}
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(buyEnterpriseName)) {
				query.setParameter("buyEnterpriseName","%"+buyEnterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				query.setParameter("toolName","%"+toolName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",startTime);
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",endTime );
			}
			if(nowPage!=null&pageCount!=null){
				query.setFirstResult((nowPage-1)*pageCount);
				query.setMaxResults(pageCount);
			}
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List <Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	public Integer findByAllCount(Integer enterpriseId,String unit,String toolName,String startTime,String endTime,
			String enterpriseName,String buyEnterpriseName) {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select ");
			sb.append(" count(tsc.id)");
			sb.append(" from Tb_Shopping_Car tsc ");
			sb.append(" inner join tb_tool tl on tl.id = tsc.tool_id  inner JOIN tb_enterprise te on te.id=tl.enterprise_id ");
			sb.append(" inner join tb_res_order_car roc on roc.car_id=tsc.id  ");
			sb.append(" inner join tb_tool_order tto on tto.id=roc.order_id ");
			sb.append(" left JOIN tb_enterprise tes on tes.id=tto.plant_enterprise_id and tto.type=1  ");
			sb.append(" left JOIN tb_link_order_info tloi on tloi.id=tto.plant_enterprise_id and tto.type=2  ");
			sb.append("  where  tsc.del_Flag = 0 and tto.status=4   ");
			if(enterpriseId!=null) {
				sb.append(" and  tsc.enterprise_Id=:enterpriseId ");
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				sb.append(" and  tl.production_units like :unit ");
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				sb.append(" and te.name like :enterpriseName  ");
			}
			if(!StringUtils.isNullOrEmpty(buyEnterpriseName)) {
				sb.append(" and (tes.name like :buyEnterpriseName or tloi.name like :buyEnterpriseName)  ");
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				sb.append(" and tl.name like :toolName ");
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				sb.append(" and date(tto.input_time) >= :startTime ");
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				sb.append(" and date(tto.input_time) <= :endTime ");
			}
			
			Query query = getEntityManager().createNativeQuery(sb.toString());
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId",enterpriseId);
			}
			if(!StringUtils.isNullOrEmpty(unit)) {
				query.setParameter("unit","%"+unit+"%" );
			}
			if(!StringUtils.isNullOrEmpty(enterpriseName)) {
				query.setParameter("enterpriseName","%"+enterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(buyEnterpriseName)) {
				query.setParameter("buyEnterpriseName","%"+buyEnterpriseName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(toolName)) {
				query.setParameter("toolName","%"+toolName+"%" );
			}
			if(!StringUtils.isNullOrEmpty(startTime)) {
				query.setParameter("startTime",startTime);
			}
			if(!StringUtils.isNullOrEmpty(endTime)) {
				query.setParameter("endTime",endTime );
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
	
	public Map<String, Object> getCarInfoById(Integer carId){
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT tf.file_url fileUrl,tt.name toolName,tsc.price price,tsc.num num,tt.production_units productionUnit ");
		queryString.append(" FROM tb_shopping_car tsc left join tb_tool tt on tt.id = tsc.tool_id ");
		queryString.append(" left join tb_res_tool_file trtf on trtf.tool_id = tt.id ");
		queryString.append(" left join tb_file tf on tf.id = trtf.file_id ");
		queryString.append(" where tsc.id = :carId ");
		queryString.append(" group by tsc.id ");
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
