package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbEnterpriseUserProductionInfo;
import com.mysql.cj.util.StringUtils;

public class TbEnterpriseUserProductionInfoDao extends BaseZDao {

	public TbEnterpriseUserProductionInfoDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Integer> getEnterpriseIdByIdcard(String idcard) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT distinct enterprise_id FROM tb_enterprise_user_production_info where user_id_card = :idcard ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("idcard", idcard);
		List<Integer> list = query.getResultList();
		return list;
	}
	
	public Object[] getUniformPriceData(List<Integer> enterpriseIdList) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select round(sum(ifnull(te.area,0)) * (select `value` from app_util where `key` = 'uniform_price_limit'),2) limitPrice, ");
		queryString.append(" (select round(ifnull(sum(tsc.num*tsc.price),0),2) from tb_shopping_car tsc left join tb_res_order_car troc on tsc.id = troc.car_id  ");
		queryString.append(" left join tb_tool_order o on o.id = troc.order_id left join tb_link_order_info tloi on tloi.id = o.plant_enterprise_id ");
		queryString.append(" where o.del_flag = 0 and o.status = 4 and o.input_time >= (case when YEAR(NOW()) in (2020,2021) then '2020-12-18 00:00:00' else CONCAT(YEAR(now()),'-01-01 00:00:00') end)   ");
		queryString.append(" and tloi.legal_person_idcard in (select user_id_card from tb_enterprise_user_production_info where enterprise_id in (:enterpriseIdList)) and tsc.uniform_price = 1) buyPrice, ");
		queryString.append(" group_concat(distinct te.name) enterpriseName,group_concat(distinct ta.name) town,sum(ifnull(te.area,0)),group_concat(distinct te.village) village from tb_enterprise te left join tb_area ta on te.dscd = ta.id where te.id in (:enterpriseIdList) ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("enterpriseIdList", enterpriseIdList);
		List<Object[]> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public Object[] getRecoveryData(List<Integer> enterpriseIdList) {
		StringBuilder queryString = new StringBuilder();
		queryString.append(" select case when te.enterprise_type = 99 then round(sum(ifnull(te.area,0)) * (select `value` from app_util where `key` = 'recovery_nm_limit'),2) when te.enterprise_type = 100 then te.nm_limit_amount else 0 end recoveryNmLimit, ");
		queryString.append(" case when te.enterprise_type = 99 then round(sum(ifnull(te.area,0)) * (select `value` from app_util where `key` = 'recovery_not_uniform_price_trash_limit'),2) when  te.enterprise_type = 100 then te.ny_limit_amount else 0 end notUniformPriceTrashLimit,  ");
		queryString.append(" (select concat(round(ifnull(sum(case when tt.unit = '瓶' then tsc.num else 0 end),0),2),',', ");
		queryString.append(" round(ifnull(sum(case when tt.unit = '包' or tt.unit = '袋' then tsc.num else 0 end),0),2))  ");
		queryString.append(" from tb_shopping_car tsc left join tb_res_order_car troc on tsc.id = troc.car_id  ");
		queryString.append(" left join tb_tool_order o on o.id = troc.order_id left join tb_link_order_info tloi on tloi.id = o.plant_enterprise_id ");
		queryString.append(" left join tb_tool tt on tsc.tool_id = tt.id ");
		queryString.append(" where o.del_flag = 0 and o.status = 4 and o.input_time >= (case when YEAR(NOW()) in (2020,2021) then '2020-12-18 00:00:00' else CONCAT(YEAR(now()),'-01-01 00:00:00') end) and tloi.legal_person_idcard in (select user_id_card ");
		queryString.append(" from tb_enterprise_user_production_info where enterprise_id in (:enterpriseIdList)) and tsc.uniform_price = 1) uniformPriceNum, ");
		queryString.append(" (select concat(ifnull(sum(case when ttrr.tool_recovery_id = 4 then ttrr.number else 0 end),0),',', ");
		queryString.append(" ifnull(sum(case when ttrr.tool_recovery_id = 5 then ttrr.number else 0 end),0)) ");
		queryString.append(" from tb_tool_recovery_record ttrr left join tb_link_order_info tloi on ttrr.link_order_info_id = tloi.id  ");
		queryString.append(" where ttrr.del_flag = 0 and ttrr.input_time >= (case when YEAR(NOW()) in (2020,2021) then '2020-12-18 00:00:00' else CONCAT(YEAR(now()),'-01-01 00:00:00') end) and tloi.legal_person_idcard in ");
		queryString.append(" (select user_id_card from tb_enterprise_user_production_info where enterprise_id in (:enterpriseIdList))) uniformPriceRecoveryNum, ");
		queryString.append(" (select concat(ifnull(sum(case when ttrr.tool_recovery_id = 6 then ttrr.total_price else 0 end),0),',', ");
		queryString.append(" ifnull(sum(case when ttrr.tool_recovery_id = 7 then ttrr.total_price  else 0 end),0),',', ");
		queryString.append(" ifnull(sum(case when ttrr.tool_recovery_id = 13 then ttrr.total_price  else 0 end),0)) ");
		queryString.append(" from tb_tool_recovery_record ttrr left join tb_link_order_info tloi on ttrr.link_order_info_id = tloi.id  ");
		queryString.append(" where ttrr.del_flag = 0 and ttrr.input_time >= (case when YEAR(NOW()) in (2020,2021) then '2020-12-18 00:00:00' else CONCAT(YEAR(now()),'-01-01 00:00:00') end) and tloi.legal_person_idcard in ");
		queryString.append(" (select user_id_card from tb_enterprise_user_production_info where enterprise_id in (:enterpriseIdList))) normalRecoveryPrice, ");
		queryString.append(" group_concat(distinct te.name) enterpriseName,group_concat(distinct ta.name) town,sum(ifnull(te.area,0)),group_concat(distinct te.village) village from tb_enterprise te left join tb_area ta on te.dscd = ta.id where te.id in (:enterpriseIdList) ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.setParameter("enterpriseIdList", enterpriseIdList);
		List<Object[]> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	public void delUser(Integer enterpriseId) {
		try {
			String queryString = " delete from tb_enterprise_user_production_info where enterprise_id = :enterpriseId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("enterpriseId", enterpriseId);
			query.executeUpdate();
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getListByEnterpriseId(Integer enterpriseId,Integer type){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select id,user_name userName , ifnull(user_id_card,'') userIdCard "
					+ " from tb_enterprise_user_production_info tref "
					+ " where tref.enterprise_id =:enterpriseId     ");
			if(type!=null) {
				sb.append(" and type=:type");
			}
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
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
	
	public List<Map<String, Object>> getEnterpiseList(String name,String dscd,Integer nowPage,Integer pageCount,String permissionDscd,
			String userName,String userIdCard,String enterpriseType){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select te.id,te.name enterpriseName ,te.area,confirm_area confirmArea,inflow_area inflowArea,outflow_area outflowArea,"
					+ " (select group_concat(user_name )  from tb_enterprise_user_production_info  where te.id=enterprise_id and type=2 ) userName, "
					+ " te.village,ta.name dscdName,(select group_concat(user_name )  from tb_enterprise_user_production_info  where te.id=enterprise_id and type=1 ) user,"
					+ " ifnull(tref.user_id_card,'') userIdCard,te.ny_limit_amount nyLimitAmount,te.nm_limit_amount nmLimitAmount,te.enterprise_type enterpriseType "
					+ " from tb_enterprise te inner join tb_enterprise_user_production_info tref "
					+ " on te.id=tref.enterprise_id "
					+ "  left join tb_area ta on ta.id=te.dscd where del_flag=0  ");
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				sb.append(" and te.enterprise_type = :enterpriseType ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and te.name like :name  ");
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				sb.append(" and tref.user_name like :userName  ");
			}
			if(!StringUtils.isNullOrEmpty(userIdCard)) {
				sb.append(" and tref.user_id_card like :userIdCard  ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			if(!StringUtils.isNullOrEmpty(permissionDscd)) {
				sb.append(" and te.dscd like :permissionDscd ");
			}
			sb.append( "  group by te.id  ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				query.setParameter("enterpriseType", enterpriseType);
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				query.setParameter("userName", "%"+userName+"%");
			}
			if(!StringUtils.isNullOrEmpty(userIdCard)) {
				query.setParameter("userIdCard", "%"+userIdCard+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(!StringUtils.isNullOrEmpty(permissionDscd)) {
				query.setParameter("permissionDscd", permissionDscd);
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
	public Integer getEnterpiseListCount(String name,String dscd,String permissionDscd,String userName,String userIdCard,String enterpriseType){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct te.id) "
					+ " from tb_enterprise te inner join tb_enterprise_user_production_info tref "
					+ " on te.id=tref.enterprise_id  "
					+ " left join tb_area ta on ta.id=te.dscd where del_flag=0  ");
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				sb.append(" and enterprise_type = :enterpriseType ");
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and te.name like :name   ");
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				sb.append(" and tref.user_name like :userName  ");
			}
			if(!StringUtils.isNullOrEmpty(userIdCard)) {
				sb.append(" and tref.user_id_card like :userIdCard  ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd like :dscd ");
			}
			if(!StringUtils.isNullOrEmpty(permissionDscd)) {
				sb.append(" and te.dscd like :permissionDscd ");
			}
			Query query =getEntityManager().createNativeQuery(sb.toString());
			if (!StringUtils.isNullOrEmpty(enterpriseType)) {
				query.setParameter("enterpriseType", enterpriseType);
			}
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(userName)) {
				query.setParameter("userName", "%"+userName+"%");
			}
			if(!StringUtils.isNullOrEmpty(userIdCard)) {
				query.setParameter("userIdCard", "%"+userIdCard+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(!StringUtils.isNullOrEmpty(permissionDscd)) {
				query.setParameter("permissionDscd", permissionDscd);
			}
			List <Object> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return Integer.valueOf(list.get(0).toString());
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public TbEnterpriseUserProductionInfo getByUserIdCard(String userIdCard,Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  "
					+ " from TbEnterpriseUserProductionInfo tref "
					+ " where tref.userIdCard =:userIdCard    ");
			if(id!=null) {
				sb.append(" and id!=:id  ");
			}
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("userIdCard", userIdCard);
			if(id!=null) {
				query.setParameter("id", id);
			}
			List <TbEnterpriseUserProductionInfo> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public TbEnterpriseUserProductionInfo getByUserIdCardType(String userIdCard,Integer type,Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  "
					+ " from TbEnterpriseUserProductionInfo tref "
					+ " where tref.userIdCard =:userIdCard    ");
			if(id!=null) {
				sb.append(" and id!=:id  ");
			}
			if(type!=null) {
				sb.append(" and type=:type  ");
			}
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("userIdCard", userIdCard);
			if(id!=null) {
				query.setParameter("id", id);
			}
			if(type!=null) {
				query.setParameter("type", type);
			}
			List <TbEnterpriseUserProductionInfo> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public TbEnterpriseUserProductionInfo getById(Integer id){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  "
					+ " from TbEnterpriseUserProductionInfo tref "
					+ " where tref.id =:id     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("id", id);
			List <TbEnterpriseUserProductionInfo> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> getUserProductionList(Integer enterpriseId,String name,String dscd,Integer nowPage,Integer pageCount){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select tref.id,te.name enterpriseName,tref.user_name userName,tref.user_id_card userIdCard,  "
					+ " te.village,ta.name dscdName "
					+ " from tb_enterprise te inner join tb_enterprise_user_production_info tref "
					+ " on te.id=tref.enterprise_id"
					+ "  left join tb_area ta on ta.id=te.dscd where del_flag=0 and enterprise_type=99 and tref.type=2  ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and te.name like :name  ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and te.id=:enterpriseId ");
			}
			Query query =getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
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
	public Integer getUserProductionListCount(Integer enterpriseId,String name,String dscd){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(distinct tref.id) "
					+ " from tb_enterprise te inner join tb_enterprise_user_production_info tref "
					+ " on te.id=tref.enterprise_id "
					+ " left join tb_area ta on ta.id=te.dscd where del_flag=0 and enterprise_type=99 and tref.type=2  ");
			if(!StringUtils.isNullOrEmpty(name)) {
				sb.append(" and te.name like :name  ");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				sb.append(" and te.dscd = :dscd ");
			}
			if(enterpriseId!=null) {
				sb.append(" and te.id=:enterpriseId ");
			}
			Query query =getEntityManager().createNativeQuery(sb.toString());
			if(!StringUtils.isNullOrEmpty(name)) {
				query.setParameter("name", "%"+name+"%");
			}
			if(!StringUtils.isNullOrEmpty(dscd)) {
				query.setParameter("dscd", dscd);
			}
			if(enterpriseId!=null) {
				query.setParameter("enterpriseId", enterpriseId);
			}
			List <Object> list = query.getResultList();
			if(list!=null&&list.size()>0)
				return Integer.valueOf(list.get(0).toString());
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	
}
